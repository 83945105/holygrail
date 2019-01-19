package pub.avalon.holygrail.function.beans;

/**
 * 树节点
 *
 * @author 白超
 * @date 2019/1/19
 */
public interface TreeNode {

    /**
     * 唯一标识符
     *
     * @return
     */
    Object getKey();

    /**
     * 获取排序喜爱宝
     *
     * @return
     */
    long getSortIndex();

    /**
     * 获取父级唯一标识
     *
     * @return
     */
    Object getParentKey();

}
