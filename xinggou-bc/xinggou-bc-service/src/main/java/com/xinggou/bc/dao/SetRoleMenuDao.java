package com.xinggou.bc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinggou.bc.entity.SetRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author hdl
 * @date 2020-08-06 11:43:33
 */
@Mapper
public interface SetRoleMenuDao extends BaseMapper<SetRoleMenu> {

    void deleteByMenuId(Long menuId);

    void deleteByRoleId(Long roleId);

    void bulkCreate(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
