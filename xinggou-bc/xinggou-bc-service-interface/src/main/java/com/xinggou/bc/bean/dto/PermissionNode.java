package com.xinggou.bc.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinggou.bc.enums.MenuTypeEnum;
import com.xinggou.common.utils.tree.TreeNode;

import java.io.Serializable;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/14
 */
public class PermissionNode extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long menuId;
    private Long parentId;

    private String name;
    private MenuTypeEnum type; // 10：目录 20：菜单
    private String icon;
    private String href;
    private List<String> permList;

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

    public MenuTypeEnum getType() {
        return type;
    }

    public void setType(MenuTypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getPermList() {
        return permList;
    }

    public void setPermList(List<String> permList) {
        this.permList = permList;
    }
}
