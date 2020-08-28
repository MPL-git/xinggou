package com.xinggou.bc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.bc.entity.StaffRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 平台账号与角色对应关系
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface StaffRoleDao extends BaseMapper<StaffRole> {

    void deleteByStaffRole(@Param("staffId") Long staffId, @Param("roleIds") List<Long> roleIds);

    void deleteByRoleId(Long roleId);

    void deleteByStaffIds(List<Long> staffIds);

    void bulkAddStaffRole(@Param("staffId") Long staffId, @Param("roleIdList") List<Long> roleIdList);
}
