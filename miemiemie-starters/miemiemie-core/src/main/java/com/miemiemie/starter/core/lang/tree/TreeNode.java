package com.miemiemie.starter.core.lang.tree;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;

import static com.miemiemie.starter.core.lang.tree.Tree.TREE_NODE_COMPARATOR;

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
     * 节点深度，根节点的深度为0
     */
    @Getter
    private int depth;

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

    void setDepth(int depth) {
        this.depth = depth;
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

    /**
     * 获取路径
     *
     * @param containsRoot 路径是否包含根节点
     * @param function     路径获取function
     * @param reverse      是否反转路径，默认根路径在前
     * @param <P>          路径元素类型
     * @return 路径List
     */
    public <P> List<P> getPath(boolean containsRoot, Function<TreeNode<T>, P> function, boolean reverse) {
        List<P> path = new ArrayList<>();
        if (containsRoot) {
            path.add(function.apply(this));
        }
        TreeNode<T> current = this.getParent();
        while (current != null) {
            path.add(function.apply(current));
            current = current.getParent();
        }
        if (!reverse) {
            Collections.reverse(path);
        }
        return path;
    }

    public <P> List<P> getPath(boolean containsRoot, Function<TreeNode<T>, P> function) {
        return getPath(containsRoot, function, false);
    }

}