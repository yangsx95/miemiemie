package com.miemiemie.starter.core.util.tree;

/**
 * 树节点能力标记
 *
 * @author yangshunxiang
 * @since 2024/8/17
 */
public interface TreeNodeCapable {

    /**
     * 树节点唯一key
     *
     * @return key
     */
    Object obtainNodeKey();

    /**
     * 树节点的父节点key
     *
     * @return parent key
     */
    Object obtainParentNodeKey();

    /**
     * 如果实现类需要嵌套记录父节点，就需要实现该方法
     *
     * @param parent 父节点
     */
    default void putParent(Object parent) {

    }

    /**
     * 如果实现类需要嵌套记录子节点，就需要实现该方法
     *
     * @param child 子节点
     */
    default void addChild(Object child) {

    }

}
