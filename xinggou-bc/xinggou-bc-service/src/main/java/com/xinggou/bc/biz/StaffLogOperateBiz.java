package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinggou.bc.bean.dto.StaffLogOperateDTO;
import com.xinggou.bc.bean.params.staff.CreateStaffLogOperateParams;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogOperateParams;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.dao.StaffLogOperateDao;
import com.xinggou.bc.entity.StaffLogOperate;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.utils.StrKit;
import com.xinggou.common.vo.PageWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StaffLogOperateBiz extends BaseBiz<StaffLogOperateDao, StaffLogOperate> {

    @Autowired
    private StaffBiz staffBiz;

    @Override
    protected QueryChainWrapper<StaffLogOperate> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<StaffLogOperate> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }


    public void createOperateLog(CreateStaffLogOperateParams params) {
        StaffLogOperate log = BcBizConverter.INSTANCE.toStaffLogOperate(params);
        log.setCreateDate(new Date());
        log.setDelFlag(StateConst.FALSE);
        this.save(log);
    }

    public PageWrap<StaffLogOperateDTO> findStaffLogOperate(PaginateStaffLogOperateParams params) {
        IPage<StaffLogOperate> iPage = lambdaQuery()
                .eq(params.getStaffId() != null, StaffLogOperate::getStaffId, params.getStaffId())
                .eq(StrKit.notBlank(params.getOperation()), StaffLogOperate::getOperation, params.getOperation())
                .eq(StrKit.notBlank(params.getIp()), StaffLogOperate::getIp, params.getIp())
                .eq(StaffLogOperate::getDelFlag, StateConst.FALSE)
                .orderByDesc(StaffLogOperate::getId)
                .page(new Page<>(params.getCurrentPage(), params.getPageSize()));
        if (iPage.getRecords().size() == 0) {
            return PageWrap.empty(params);
        }

        List<Long> staffIds = iPage.getRecords().stream().map(StaffLogOperate::getStaffId).distinct().collect(Collectors.toList());
        Map<Long, String> staffIdNameMap = staffBiz.buildIdNameMap(staffIds);

        List<StaffLogOperateDTO> dtoList = iPage.getRecords().stream().map(log -> {
            StaffLogOperateDTO dto = BcBizConverter.INSTANCE.toStaffLogOperateDTO(log);
            dto.setStaffName(staffIdNameMap.get(dto.getStaffId()));
            return dto;
        }).collect(Collectors.toList());
        return PageWrap.of(dtoList, iPage);
    }
}
