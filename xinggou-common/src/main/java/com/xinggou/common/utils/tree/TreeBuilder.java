package com.xinggou.common.utils.tree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author luoyb
 * Created on 2020/8/14
 */
public final class TreeBuilder {

    public static <E extends TreeNode> void convertTree(E rootNode, List<E> elementList) {
        if (CollectionUtils.isEmpty(elementList)) {
            return;
        }

        Map<Long, List<E>> treeNodeMap = Maps.newTreeMap();
        for (E element : elementList) {
            if (treeNodeMap.containsKey(element.getParentNodeId())) {
                treeNodeMap.get(element.getParentNodeId()).add(element);
            } else {
                treeNodeMap.put(element.getParentNodeId(), Lists.newArrayList(element));
            }
        }

        // 根节点
        List<E> firstLevelNodes = treeNodeMap.get(0L);
        if (CollectionUtils.isEmpty(firstLevelNodes)) {
            return;
        }
        rootNode.getChildNodes().addAll(firstLevelNodes);

        // 子节点
        setTreeNodeLevel(treeNodeMap, firstLevelNodes, rootNode.getLevel());
    }

    private static <E extends TreeNode> void setTreeNodeLevel(Map<Long, List<E>> treeNodeMap, List<E> parentNodes, int parentLevel) {
        if (CollectionUtils.isEmpty(parentNodes)) {
            return;
        }

        for (E element : parentNodes) {
            element.setLevel(parentLevel + 1);
            List<E> childNodes = treeNodeMap.get(element.getNodeId());
            if (!CollectionUtils.isEmpty(childNodes)) {
                element.getChildNodes().addAll(childNodes);
                setTreeNodeLevel(treeNodeMap, childNodes, element.getLevel());
            }
        }
    }
}
