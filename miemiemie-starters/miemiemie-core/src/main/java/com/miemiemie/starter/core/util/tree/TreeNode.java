package com.miemiemie.starter.core.util.tree;

import com.miemiemie.starter.core.result.Result;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.miemiemie.starter.core.util.tree.Tree.TREE_NODE_COMPARATOR;

/**
 * 树节点
 *
 * @author yangshunxiang
 * @since 2024/8/17
 */
@Getter
@EqualsAndHashCode
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
    private final TreeSet<TreeNode<T>> children = new TreeSet<>(TREE_NODE_COMPARATOR);

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

    public int getOrdinal() {
        if (Objects.nonNull(value)) {
            return value.obtainOrdinal();
        }
        return 0;
    }

    /**
     * 获取子孙节点列表，将会平铺
     */
    public List<TreeNode<T>> getDescendants() {
        List<TreeNode<T>> descendants = new ArrayList<>();
        getDescendants(descendants, this);
        return descendants;
    }

    private void getDescendants(List<TreeNode<T>> nodes, TreeNode<T> node) {
        TreeSet<TreeNode<T>> nodeChildren = node.getChildren();
        if (Objects.nonNull(nodeChildren)) {
            nodeChildren.forEach(c -> {
                if (nodes.contains(c)) {
                    return;
                }
                nodes.add(c);
                getDescendants(nodes, c);
            });
        }
    }

}