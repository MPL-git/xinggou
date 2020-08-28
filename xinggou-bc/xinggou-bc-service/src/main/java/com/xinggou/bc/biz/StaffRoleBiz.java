package com.xinggou.bc.biz;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xinggou.bc.dao.StaffRoleDao;
import com.xinggou.bc.entity.StaffRole;
import com.xinggou.common.core.BaseBiz;
import com.xinggou.common.utils.QueryObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StaffRoleBiz extends BaseBiz<StaffRoleDao, StaffRole> {

    @Override
    protected QueryChainWrapper<StaffRole> builderQuery(QueryObject queryObject) {
        QueryChainWrapper<StaffRole> wrapper = queryObject.fillWrapper(query());
        if (queryObject.getQuery("id") != null) {
            wrapper.eq("id", queryObject.getQuery("id"));
        }
        return wrapper;
    }

    public void save(Long staffId, List<Long> roleIdList) {
        //先删除用户与角色关系
        deleteByStaffRole(staffId, null);

        //保存用户与角色关系
        for (Long roleId : roleIdList) {
            StaffRole sysUserRoleEntity = new StaffRole();
            sysUserRoleEntity.setStaffId(staffId);
            sysUserRoleEntity.setRoleId(roleId);
            save(sysUserRoleEntity);
        }

    }

    public void deleteByStaffRole(long staffId, List<Long> roleIds) {
        baseMapper.deleteByStaffRole(staffId, roleIds);
    }

    public void deleteByRoleId(long roleId) {
        baseMapper.deleteByRoleId(roleId);
    }

    public void deleteByStaffIds(List<Long> staffIds) {
        baseMapper.deleteByStaffIds(staffIds);
    }

    public List<StaffRole> findByRoleIds(List<Long> roleIds) {
        return lambdaQuery()
                .in(StaffRole::getRoleId, roleIds)
                .list();
    }

    public Set<Long> findStaffRoleId(Long staffId) {
        List<StaffRole> list = lambdaQuery()
                .eq(StaffRole::getStaffId, staffId)
                .list();
        return list.stream().map(StaffRole::getRoleId).collect(Collectors.toSet());
    }


    public void bulkAddRole(Long staffId, List<Long> roleIdList) {
        baseMapper.bulkAddStaffRole(staffId, roleIdList);
    }
}
