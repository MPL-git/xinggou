package com.xinggou.bc.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xinggou.bc.bean.dto.MenuNode;
import com.xinggou.bc.bean.dto.PermissionNode;
import com.xinggou.bc.bean.dto.RolePermissionDTO;
import com.xinggou.bc.bean.dto.RolePermissionNode;
import com.xinggou.bc.biz.SetMenuBiz;
import com.xinggou.bc.biz.SetRoleBiz;
import com.xinggou.bc.biz.SetRoleMenuBiz;
import com.xinggou.bc.converter.BcBizConverter;
import com.xinggou.bc.entity.SetMenu;
import com.xinggou.bc.entity.SetRole;
import com.xinggou.bc.enums.MenuTypeEnum;
import com.xinggou.common.constant.Const;
import com.xinggou.common.exception.Assert;
import com.xinggou.common.utils.tree.TreeBuilder;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private SetMenuBiz setMenuBiz;
    @Autowired
    private SetRoleMenuBiz setRoleMenuBiz;
    @Autowired
    private SetRoleBiz setRoleBiz;

    @Override
    public MenuNode getMenuTree() {
        List<SetMenu> list = setMenuBiz.findAllMenu();
        List<MenuNode> menuNodeList = BcBizConverter.INSTANCE.toMenuNodeList(list);

        MenuNode rootNode = new MenuNode();
        rootNode.setLevel(0);
        TreeBuilder.convertTree(rootNode, menuNodeList);
        return rootNode;
    }

    @Override
    public PermissionNode getStaffPermissionTree(long staffId) {
        List<SetMenu> list = setMenuBiz.findStaffMenu(staffId);

        List<PermissionNode> permissionList = Lists.newArrayList();
        Map<Long, List<String>> permMap = Maps.newHashMap();
        for (SetMenu menu : list) {
            if (menu.getType() == MenuTypeEnum.PERM) {  //按钮权限
                if (!StringUtils.hasText(menu.getPerms())) {
                    continue;
                }
                fillWithPerm(permMap, menu);
            } else {  //目录、菜单
                permissionList.add(BcBizConverter.INSTANCE.toPermissionNode(menu));
            }
        }

        for (PermissionNode node : permissionList) {
            List<String> permList = permMap.get(node.getMenuId());
            if (!CollectionUtils.isEmpty(permList)) {
                node.setPermList(permList);
            }
        }
        PermissionNode rootNode = new PermissionNode();
        rootNode.setLevel(0);
        TreeBuilder.convertTree(rootNode, permissionList);
        return rootNode;
    }

    @Override
    public RolePermissionDTO getRolePermission(long roleId) {
        List<SetMenu> list = setMenuBiz.findAllMenu();
        Set<Long> roleMenuIdSet = setRoleMenuBiz.findRoleMenuSet(roleId);

        List<RolePermissionNode> permissionNodeList = list.stream().map(menu -> {
            RolePermissionNode node = new RolePermissionNode();
            node.setMenuId(menu.getId());
            node.setParentId(menu.getParentId());
            node.setName(menu.getName());
            node.setOwn(roleMenuIdSet.contains(node.getMenuId()));
            return node;
        }).collect(Collectors.toList());
        RolePermissionNode rootNode = new RolePermissionNode();
        rootNode.setLevel(0);
        TreeBuilder.convertTree(rootNode, permissionNodeList);

        RolePermissionDTO result = new RolePermissionDTO();
        result.setTree(rootNode);
        result.getOwnMenuIdList().addAll(roleMenuIdSet);
        return result;
    }

    @Override
    public void updateRolePermission(Long roleId, List<Long> menuIds) {
        SetRole role = setRoleBiz.getById(roleId);
        Assert.notNull(role, "ID为[" + roleId + "]的角色不存在");

        setRoleMenuBiz.updateRolePermission(roleId, menuIds);
    }

    private void fillWithPerm(Map<Long, List<String>> permMap, SetMenu menu) {
        List<String> list = permMap.get(menu.getParentId());
        if (list == null) {
            list = Lists.newArrayList();
        }
        list.addAll(Arrays.asList(menu.getPerms().split(Const.COMMA)));
        permMap.put(menu.getParentId(), list);
    }

}
