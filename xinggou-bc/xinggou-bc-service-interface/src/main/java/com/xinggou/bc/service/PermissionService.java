package com.xinggou.bc.service;

import com.xinggou.bc.bean.dto.MenuNode;
import com.xinggou.bc.bean.dto.PermissionNode;
import com.xinggou.bc.bean.dto.RolePermissionDTO;
import com.xinggou.bc.bean.dto.RolePermissionNode;

import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
public interface PermissionService {

    /**
     * 获取菜单列表
     *
     * @return 菜单树 {@link MenuNode}
     */
    MenuNode getMenuTree();

    /**
     * 获取用户权限树(用户所拥有的)
     *
     * @param staffId 用户ID
     * @return 权限树 {@link PermissionNode}
     */
    PermissionNode getStaffPermissionTree(long staffId);

    /**
     * 获取角色权限
     *
     * @param roleId 角色ID
     * @return 完整权限树，角色拥有的权限 own = true {@link RolePermissionNode}
     */
    RolePermissionDTO getRolePermission(long roleId);

    /**
     * 更新角色权限
     *
     * @param roleId  角色ID
     * @param menuIds 授权菜单ID集合
     */
    void updateRolePermission(Long roleId, List<Long> menuIds);
}
