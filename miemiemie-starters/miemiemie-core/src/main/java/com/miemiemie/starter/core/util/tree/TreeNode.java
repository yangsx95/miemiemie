package com.miemiemie.starter.core.util.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 *
 * @author yangshunxiang
 * @since 2024/8/17
 */
@Getter
public class TreeNode<T extends TreeNodeCapable> {

    /**
     * 树节点的值
     */
    private final T value;

    /**
     * 树节点的父节点
     * -- SETTER --
     * 设置节点的父节点
     */
    @Setter
    private TreeNode<T> parent;

    /**
     * 树节点的子节点
     */
    private final List<TreeNode<T>> children = new ArrayList<>();

    public TreeNode(T value) {
        this.value = value;
    }

    /**
     * 添加节点的子节点
     *
     * @param childTreeNode 子节点
     */
    public void addChild(TreeNode<T> childTreeNode) {
        this.children.add(childTreeNode);
    }

}
