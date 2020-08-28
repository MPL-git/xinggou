package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinggou.bc.bean.params.staff.PaginateStaffLogLoginParams;
import com.xinggou.bc.dao.StaffLogLoginDao;
import com.xinggou.bc.entity.StaffLogLogin;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.vo.PageWrap;
import org.springframework.stereotype.Service;

@Service
public class StaffLogLoginBiz extends BaseBiz<StaffLogLoginDao, StaffLogLogin> {

    @Override
    protected QueryChainWrapper<StaffLogLogin> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<StaffLogLogin> wrapper = queryObject.fillWrapper(query());
        if(queryObject.getQuery("id") != null){
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }


    public PageWrap<StaffLogLogin> findStaffLogLogin(PaginateStaffLogLoginParams params) {
        IPage<StaffLogLogin> iPage = lambdaQuery()
                .eq(params.getStaffId() != null, StaffLogLogin::getStaffId, params.getStaffId())
                .eq(params.getIp() != null, StaffLogLogin::getIp, params.getIp())
                .eq(StaffLogLogin::getDelFlag, StateConst.FALSE)
                .orderByDesc(StaffLogLogin::getCreateDate)
                .page(new Page<>(params.getCurrentPage(), params.getPageSize()));
        return PageWrap.of(iPage);
    }
}
