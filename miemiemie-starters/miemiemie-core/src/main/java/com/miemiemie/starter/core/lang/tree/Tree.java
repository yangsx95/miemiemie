package com.miemiemie.starter.core.lang.tree;

import cn.hutool.core.lang.Assert;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

/**
 * 树节点
 * <p>
 * 注意：不会产生循环依赖，因为循环以来的元素不会添加道rootNodes中
 *
 * @author yangshunxiang
 * @since 2024/8/17
 */
public class Tree<T extends TreeNodeCapable> {

    @SuppressWarnings("ComparatorMethodParameterNotUsed")
    public static final Comparator<TreeNode<?>> TREE_NODE_COMPARATOR = (o1, o2) -> {
        // 这里为什么不直接使用  o1.getOrdinal() - o2.getOrdinal() ？
        // 因为在TreeSet中，如果排序相同（comparator return 0），不会加入到元素中
        if (Objects.equals(o1.getOrdinal(), o2.getOrdinal())) {
            return 1;
        }
        return o1.getOrdinal() - o2.getOrdinal();
    };

    /**
     * 树节点的初始深度
     */
    public static final int INIT_DEPTH = 1;

    @Getter
    private final TreeSet<TreeNode<T>> rootNodes = new TreeSet<>(TREE_NODE_COMPARATOR);

    @Getter
    private final List<TreeNode<T>> allNodes = new ArrayList<>();

    private final Map<Object, TreeNode<T>> keyNodeMap = new HashMap<>();

    /**
     * 构造树
     *
     * @param eleList 元素列表
     */
    public Tree(List<T> eleList) {
        this(eleList, null);
    }

    /**
     * 构造树
     *
     * @param eleList  元素列表
     * @param maxDepth 最大深度
     */
    public Tree(List<T> eleList, @Nullable Integer maxDepth) {
        if (Objects.isNull(eleList) || eleList.isEmpty()) {
            return;
        }
        Assert.noNullElements(eleList.toArray(), "tree ele can not be null");

        if (maxDepth != null && maxDepth == 0) {
            throw new IllegalArgumentException("maxDepth can be zero");
        }

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

        // 递归设置树的深度
        rootNodes.forEach(root -> setDepth(root, INIT_DEPTH, maxDepth));
    }

    private void setDepth(TreeNode<T> node, int depth, Integer maxDepth) {
        node.setDepth(depth);
        // 到达最大深度，清空所有当前深度的子元素
        if (maxDepth != null && depth == maxDepth) {
            node.getChildren().clear();
            return;
        }
        if (!node.getChildren().isEmpty()) {
            setDepth(node, depth + 1, maxDepth);
        }
    }

    public List<T> getRoots() {
        return rootNodes.stream()
                .map(TreeNode::getValue)
                .sorted(Comparator.comparingInt(TreeNodeCapable::obtainOrdinal))
                .toList();
    }

    public TreeNode<T> getByNodeKey(Object nodeKey) {
        return keyNodeMap.get(nodeKey);
    }

}
