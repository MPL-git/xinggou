package com.xinggou.bc.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinggou.bc.enums.MenuTypeEnum;
import com.xinggou.common.utils.tree.TreeNode;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/14
 */
public class MenuNode extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long menuId;
    private Long parentId;

    private String name;
    private MenuTypeEnum type; // 10：目录 20：菜单 30：按钮
    private String icon;
    private String url;
    private String perms;
    private Integer seqNo;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
