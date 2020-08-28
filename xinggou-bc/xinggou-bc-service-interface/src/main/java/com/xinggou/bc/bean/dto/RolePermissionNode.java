package com.xinggou.bc.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinggou.bc.enums.MenuTypeEnum;
import com.xinggou.common.utils.tree.TreeNode;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
public class RolePermissionNode extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long menuId;
    private Long parentId;

    private String name;
    private MenuTypeEnum type; // 10：目录   20：菜单   30：按钮
    private boolean own;

    @JsonIgnore
    @Override
    public Long getNodeId() {
        return menuId;
    }

    @JsonIgnore
    @Override
    public Long getParentNodeId() {
        return parentId;
    }

    public boolean isOwn() {
        return own;
    }

    public void setOwn(boolean own) {
        this.own = own;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuTypeEnum getType() {
        return type;
    }

    public void setType(MenuTypeEnum type) {
        this.type = type;
    }
}
