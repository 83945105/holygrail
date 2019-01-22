package pub.avalon.holygrail.function.beans;

/**
 * 树节点
 *
 * @author 白超
 * @date 2019/1/19
 */
public interface TreeNode<T extends TreeNode> {

    /**
     * 获取排序喜爱宝
     *
     * @return
     */
    long getSortIndex();

    /**
     * 判断是否是兄弟节点
     *
     * @param node
     * @return
     */
    boolean isBrother(T node);

    /**
     * 判断父级唯一标识符是否相等
     *
     * @param sourceNode
     * @param targetNode
     * @return
     */
    static boolean isBrother(TreeNode sourceNode, TreeNode targetNode) {
        return sourceNode.isBrother(targetNode);
    }

}
