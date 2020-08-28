package com.xinggou.bc.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinggou.common.utils.tree.TreeNode;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
public class DeptNode extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long deptId;
    private Long parentId;

    private String name;

    @JsonIgnore
    @Override
    public Long getNodeId() {
        return deptId;
    }

    @JsonIgnore
    @Override
    public Long getParentNodeId() {
        return parentId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
}
