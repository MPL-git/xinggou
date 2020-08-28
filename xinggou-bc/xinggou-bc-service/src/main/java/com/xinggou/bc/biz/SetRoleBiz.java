package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinggou.bc.bean.dto.RoleDTO;
import com.xinggou.bc.bean.params.PaginateRoleForStaffParams;
import com.xinggou.bc.bean.params.PaginateRoleParams;
import com.xinggou.bc.bean.params.SaveRoleParams;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.dao.SetRoleDao;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.entity.StaffRole;
import com.xinggou.common.constant.StateConst;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.exception.Assert;
import com.xinggou.common.utils.QueryObject;
import com.xinggou.common.utils.StrKit;
import com.xinggou.common.vo.PageWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SetRoleBiz extends BaseBiz<SetRoleDao, SetRole> {

    @Autowired
    private StaffRoleBiz staffRoleBiz;
    @Autowired
    private StaffBiz staffBiz;

    public PageWrap<RoleDTO> findRole(PaginateRoleParams params) {
        IPage<SetRole> iPage = lambdaQuery()
                .likeRight(StrKit.notBlank(params.getName()), SetRole::getName, params.getName())
                .eq(SetRole::getDelFlag, StateConst.FALSE)
                .orderByAsc(SetRole::getId)
                .page(new Page<>(params.getCurrentPage(), params.getPageSize()));
        if (iPage.getRecords().size() == 0) {
            return PageWrap.empty(params);
        }
        List<RoleDTO> dtoList = BcBizConverter.INSTANCE.toRoleDTOs(iPage.getRecords());
        //填充关联员工
        fillWithAssignedStaff(dtoList);
        return PageWrap.of(dtoList, iPage);
    }

    private void fillWithAssignedStaff(List<RoleDTO> dtoList) {
        List<Long> allRoleIds = dtoList.stream().map(RoleDTO::getId).collect(Collectors.toList());
        List<StaffRole> mappingList = staffRoleBiz.findByRoleIds(allRoleIds);

        Map<Long, List<Long>> roleStaffMap = mappingList.stream().collect(Collectors.groupingBy(StaffRole::getRoleId, Collectors.mapping(StaffRole::getStaffId, Collectors.toList())));
        List<Long> allStaffIds = mappingList.stream().map(StaffRole::getStaffId).distinct().collect(Collectors.toList());
        Map<Long, String> idNameMap = staffBiz.buildIdNameMap(allStaffIds);
        for (RoleDTO dto : dtoList) {
            List<Long> staffIds = roleStaffMap.get(dto.getId());
            if (CollectionUtils.isEmpty(staffIds)) {
                continue;
            }
            for (Long staffId : staffIds) {
                dto.getAssignedStaffList().add(idNameMap.get(staffId));
            }
        }
    }

    @Override
    protected QueryChainWrapper<SetRole> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<SetRole> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("deptId") != null) {
            wrapper.eq("dept_id", queryObject.getQuery("deptId"));
        }
        return wrapper;
    }

    public void deleteById(Long id) {
        this.removeById(id);
        //删除菜单与角色关联
        staffRoleBiz.deleteByRoleId(id);
    }

    public SetRole createRole(SaveRoleParams params) {
        SetRole role = new SetRole();
        role.setName(params.getName());
        role.setRemarks(params.getRemarks());
        role.setCreateDate(new Date());
        role.setDelFlag(StateConst.FALSE);
        this.save(role);
        return role;
    }

    public void updateRole(SaveRoleParams params) {
        SetRole role = this.getById(params.getRoleId());
        Assert.notNull(role, "ID为[" + params.getRoleId() + "]的角色不存在");
        role.setName(params.getName());
        role.setRemarks(params.getRemarks());
        role.setUpdateDate(new Date());
        this.updateById(role);
    }

    public IPage<SetRole> findRoleForStaff(PaginateRoleForStaffParams params) {
        if (CollectionUtils.isEmpty(params.getStaffRoleIdList())) {
            params.getStaffRoleIdList().add(0L);
        }
        return lambdaQuery()
                .likeRight(StrKit.notBlank(params.getRoleName()), SetRole::getName, params.getRoleName())
                .in(params.getBoundType() == 1, SetRole::getId, params.getStaffRoleIdList())
                .notIn(params.getBoundType() == 2, SetRole::getId, params.getStaffRoleIdList())
                .eq(SetRole::getDelFlag, StateConst.FALSE)
                .orderByAsc(SetRole::getId)
                .page(new Page<>(params.getCurrentPage(), params.getPageSize()));
    }
}
