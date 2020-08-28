package com.xinggou.common.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/14
 */
public class TreeNode {

    private int level;

    public Long getNodeId() {
        return 0L;
    }

    public Long getParentNodeId() {
        return 0L;
    }

    private final List<TreeNode> childNodes = new ArrayList<>();

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
    }
}
