package com.miemiemie.starter.core.util.tree;

import cn.hutool.core.lang.Assert;
import lombok.Getter;

import java.util.*;

/**
 * 树节点
 *
 * @author yangshunxiang
 * @since 2024/8/17
 */
public class Tree <T extends TreeNodeCapable> {

    @Getter
    private final TreeSet<TreeNode<T>> rootNodes = new TreeSet<>(Comparator.comparingInt(TreeNode::getOrdinal));

    @Getter
    private final List<TreeNode<T>> allNodes = new ArrayList<>();

    private final Map<Object, TreeNode<T>> keyNodeMap = new HashMap<>();

    public Tree(List<T> eleList) {
        if (Objects.isNull(eleList) || eleList.isEmpty()) {
            return;
        }
        Assert.noNullElements(eleList.toArray(), "树节点元素不可存在空元素");

        for (T ele : eleList) {
            TreeNode<T> node = new TreeNode<>(ele);
            allNodes.add(node);
            keyNodeMap.put(ele.obtainNodeKey(), node);
        }

        allNodes.forEach(node -> {
            Object parentNodeKey = node.getValue().obtainParentNodeKey();
            TreeNode<T> parentNode = keyNodeMap.get(parentNodeKey);
            if (Objects.nonNull(parentNode)) {
                // 处理节点关系
                node.setParent(parentNode);
                parentNode.addChild(node);
                // 处理节点元素内部关系
                node.getValue().putParent(parentNode.getValue());
                parentNode.getValue().addChild(node.getValue());
            } else {
                rootNodes.add(node);
            }
        });
    }

    public List<T> getRoots() {
        return rootNodes.stream()
                .map(TreeNode::getValue)
                .sorted(Comparator.comparingInt(TreeNodeCapable::obtainOrdinal))
                .toList();
    }

}
