package pub.avalon.holygrail.function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;
import pub.avalon.holygrail.function.beans.DropType;
import pub.avalon.holygrail.function.beans.TreeNode;
import pub.avalon.holygrail.utils.SortUtil;

import java.util.Collection;
import java.util.List;

/**
 * 树节点拖动
 *
 * @param <T> 树节点
 * @param <P> 用于传递的参数
 */
public interface TreeNodeDraggable<T extends TreeNode, P> {

    Log LOGGER = LogFactory.getLog(TreeNodeDraggable.class);

    /**
     * 查询排序下标的之间的所有兄弟节点
     * 只有当拖拽节点和放置节点的parentKey相同时才有可能触发
     *
     * @param startNode
     * @param endNode
     * @param params    传递的参数
     * @return
     */
    List<T> findBrotherNodesInSortIndex(T startNode, T endNode, P params);

    /**
     * 查询放置节点相邻的上一个节点
     *
     * @param drop
     * @param params
     * @return
     */
    T findDropPreviousBrotherNode(T drop, P params);

    /**
     * 查询拖拽节点相邻的下一个节点
     *
     * @param drag
     * @param params
     * @return
     */
    T findDragNextBrotherNode(T drag, P params);

    /**
     * 查询放置节点相邻的下一个节点
     *
     * @param drop
     * @param params
     * @return
     */
    T findDropNextBrotherNode(T drop, P params);

    /**
     * 查找父级节点
     *
     * @param node
     * @return
     */
    T findParentNode(T node);

    /**
     * 获取下标间隔
     * 主要用于在最后一个节点后面追加节点时,设置sortIndex用
     *
     * @return
     */
    long getSortIndexInterval();

    /**
     * 查询出放置节点下最大下标
     *
     * @param drop
     * @param params
     * @return
     */
    long findDropChildMaxSortIndex(T drop, P params);

    /**
     * 更新节点的排序下标
     *
     * @param node
     * @param sortIndex
     * @param params
     */
    void updateNodeSortIndex(T node, long sortIndex, P params);

    /**
     * 将所有相同父级节点的放置节点后移一位
     * 后移一位指的是将sortIndex增加一个单位,至于增加多少,根据你自己的规则而定
     * 比如你的sortIndex是按照 [0, 10, 20, ..., 100, ...] 间隔10的方式递增,那你就要将对应的节点的sortIndex增加10
     * 该操作的目的是给即将插入过来的节点腾出位置
     *
     * @param drop
     * @param params
     */
    void updateDropBrotherNodesSortIndexToNext(T drop, P params);

    /**
     * 将节点下标减去指定数值
     *
     * @param nodes
     * @param minusNum 始终大于0
     * @param params
     */
    void minusNodesSortIndex(Collection<T> nodes, long minusNum, P params);

    /**
     * 将节点下标加上指定数值
     *
     * @param nodes
     * @param plusNum 始终大于0
     * @param params
     */
    void plusNodesSortIndex(Collection<T> nodes, long plusNum, P params);

    /**
     * 将大于节点排序下标的兄弟节点排序下标减去指定数值
     *
     * @param node
     * @param minusNum
     * @param params
     */
    void minusGreaterThanNodeSortIndexBrotherNodesSortIndex(T node, long minusNum, P params);

    /**
     * 将大于节点排序下标的兄弟节点排序下标加上指定数值
     *
     * @param node
     * @param plusNum
     * @param params
     */
    void plusGreaterThanNodeSortIndexBrotherNodesSortIndex(T node, long plusNum, P params);

    /**
     * 改变拖拽节点的父级
     * 注意: 如果节点的子孙节点记录了如父级节点路径,记得同时将子孙节点对应字段也做修改
     *
     * @param drag          拖拽节点
     * @param newParentNode 新的父级节点
     * @param params
     */
    void changeDragNodeParent(T drag, @Nullable T newParentNode, P params);

    /**
     * 拖拽
     *
     * @param drag
     * @param drop
     * @param dropType
     * @param params
     */
    default void draggableNode(T drag, T drop, DropType dropType, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        if (dropType == null) {
            throw new RuntimeException("dropType cannot be null.");
        }
        switch (dropType) {
            case BEFORE:
                draggableBefore(drag, drop, params);
                break;
            case INNER:
                draggableInner(drag, drop, params);
                break;
            case AFTER:
                draggableAfter(drag, drop, params);
                break;
            default:
                throw new RuntimeException("dropType incorrect.");
        }
    }

    /**
     * 拖拽到目标节点前面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBefore(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        if (drag.isBrother(drop)) {
            // 兄弟节点之间拖拽
            draggableBrotherBefore(drag, drop, params);
            return;
        }
        // 跨节点拖拽, 改变父级
        // 查询出拖拽节点的下一个节点
        T nextDrag = findDragNextBrotherNode(drag, params);
        if (nextDrag != null) {
            //存在下一个
            long minusNum = nextDrag.getSortIndex() - drag.getSortIndex();
            if (minusNum <= 0) {
                LOGGER.warn("draggableBefore: minusNum must be greater than zero.");
                return;
            }
            // 更新拖拽节点同级后所有节点的下标
            minusGreaterThanNodeSortIndexBrotherNodesSortIndex(drag, minusNum, params);
        }
        // 查询出目标节点的上一个节点
        T previousDrop = findDropPreviousBrotherNode(drop, params);
        if (previousDrop == null) {
            //没有上一个, 说明是第一个, 所有兄弟节点(包括自身)后移一位
            updateDropBrotherNodesSortIndexToNext(drop, params);
        } else {
            // 有上一个, 后移所有后面的兄弟节点
            long plusNum = drop.getSortIndex() - previousDrop.getSortIndex();
            if (plusNum <= 0) {
                LOGGER.warn("draggableBefore: plusNum must be greater than zero.");
                return;
            }
            //后移所有兄弟节点
            plusGreaterThanNodeSortIndexBrotherNodesSortIndex(previousDrop, plusNum, params);
        }
        // 改变父级并设置sortIndex为目标节点
        T newParentNode = findParentNode(drop);
        changeDragNodeParent(drag, newParentNode, params);
        updateNodeSortIndex(drag, drop.getSortIndex(), params);
    }

    /**
     * 拖拽到兄弟节点前面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBrotherBefore(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        long dragIndex = drag.getSortIndex();
        long dropIndex = drop.getSortIndex();
        if (dragIndex == dropIndex) {
            // 正常情况下, 如果是兄弟节点之间拖拽, 两者下标不能相同
            LOGGER.warn("draggableBrotherBefore: dragIndex are equal to dropIndex.");
            return;
        }
        if (dragIndex < dropIndex) {
            // 自上而下
            draggableBrotherBeforeFromUpToDown(drag, drop, params);
            return;
        }
        // 自下而上
        draggableBrotherBeforeFromDownToUp(drag, drop, params);
    }

    /**
     * 自上而下拖拽到兄弟节点前面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBrotherBeforeFromUpToDown(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findBrotherNodesInSortIndex(drag, drop, params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            // 正常情况下, 应该能获取到元素
            LOGGER.warn("draggableBrotherBeforeFromUpToDown: no drags between brother sortIndex.");
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        //计算出拖动元素与将要移动的第一个元素之间的下标间隔
        long minusNum = drags.get(0).getSortIndex() - drag.getSortIndex();
        if (minusNum <= 0) {
            LOGGER.warn("draggableBrotherBeforeFromUpToDown: minusNum must be greater than zero.");
            return;
        }
        //将拖动放置之间的节点减少一定排序下标
        minusNodesSortIndex(drags, minusNum, params);
        //将拖动节点放置到最后一个节点位置上
        updateNodeSortIndex(drag, drags.get(drags.size() - 1).getSortIndex(), params);
    }

    /**
     * 自下而上拖拽到兄弟节点前面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBrotherBeforeFromDownToUp(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findBrotherNodesInSortIndex(drop, drag, params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            //这种情况是相邻节点, 只需要交换位置
            updateNodeSortIndex(drag, drop.getSortIndex(), params);
            updateNodeSortIndex(drop, drag.getSortIndex(), params);
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        long plusNum = drag.getSortIndex() - drags.get(drags.size() - 1).getSortIndex();
        if (plusNum <= 0) {
            LOGGER.warn("draggableBrotherBeforeFromDownToUp: plusNum must be greater than zero.");
            return;
        }
        drags.add(drop);
        plusNodesSortIndex(drags, plusNum, params);
        //将拖动节点放置到目标元素的位置
        updateNodeSortIndex(drag, drop.getSortIndex(), params);
    }

    /**
     * 拖拽到目标节点后面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableAfter(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        if (drag.isBrother(drop)) {
            // 兄弟节点之间拖拽
            draggableBrotherAfter(drag, drop, params);
            return;
        }
        // 跨节点拖拽, 改变父级
        // 查询出拖拽节点的下一个节点
        T nextDrag = findDragNextBrotherNode(drag, params);
        if (nextDrag != null) {
            long minusNum = nextDrag.getSortIndex() - drag.getSortIndex();
            if (minusNum <= 0) {
                LOGGER.warn("draggableBefore: minusNum must be greater than zero.");
                return;
            }
            // 更新拖拽节点同级后所有节点的下标
            minusGreaterThanNodeSortIndexBrotherNodesSortIndex(drag, minusNum, params);
        }
        // 查询目标节点的下一个节点
        T nextDrop = findDropNextBrotherNode(drop, params);
        if (nextDrop == null) {
            //没有下一个, 改变父级
            T newParentNode = findParentNode(drop);
            changeDragNodeParent(drag, newParentNode, params);
            //设置index为下一个
            long indexInterval = this.getSortIndexInterval();
            updateNodeSortIndex(drag, drop.getSortIndex() + indexInterval, params);
            return;
        }
        // 有下一个, 后移所有后面的兄弟节点
        long plusNum = nextDrop.getSortIndex() - drop.getSortIndex();
        if (plusNum <= 0) {
            LOGGER.warn("draggableBefore: plusNum must be greater than zero.");
            return;
        }
        //后移所有兄弟节点
        plusGreaterThanNodeSortIndexBrotherNodesSortIndex(drop, plusNum, params);
        //改变拖拽父级节点
        T newParentNode = findParentNode(drop);
        changeDragNodeParent(drag, newParentNode, params);
        //设置排序号为放置节点的下一个节点
        updateNodeSortIndex(drag, nextDrop.getSortIndex(), params);
    }

    /**
     * 拖拽到兄弟节点后面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBrotherAfter(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        long dragIndex = drag.getSortIndex();
        long dropIndex = drop.getSortIndex();
        if (dragIndex == dropIndex) {
            // 正常情况下, 如果是兄弟节点之间拖拽, 两者下标不能相同
            LOGGER.warn("draggableBrotherBefore: dragIndex are equal to dropIndex.");
            return;
        }
        if (dragIndex < dropIndex) {
            // 自上而下
            draggableBrotherAfterFromUpToDown(drag, drop, params);
            return;
        }
        // 自下而上
        draggableBrotherAfterFromDownToUp(drag, drop, params);
    }

    /**
     * 自上而下拖拽到兄弟节点后面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBrotherAfterFromUpToDown(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findBrotherNodesInSortIndex(drag, drop, params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            //这种情况是相邻节点, 只需要交换位置
            updateNodeSortIndex(drag, drop.getSortIndex(), params);
            updateNodeSortIndex(drop, drag.getSortIndex(), params);
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        //计算出拖动元素与将要移动的第一个元素之间的下标间隔
        long minusNum = drags.get(0).getSortIndex() - drag.getSortIndex();
        if (minusNum <= 0) {
            LOGGER.warn("draggableBrotherBeforeFromUpToDown: minusNum must be greater than zero.");
            return;
        }
        drags.add(drop);
        minusNodesSortIndex(drags, minusNum, params);
        //将拖动节点放置目标节点位置上
        updateNodeSortIndex(drag, drop.getSortIndex(), params);
    }

    /**
     * 自下而上拖拽到兄弟节点后面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBrotherAfterFromDownToUp(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findBrotherNodesInSortIndex(drop, drag, params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            // 正常情况下, 应该能获取到元素
            LOGGER.warn("draggableBrotherAfterFromDownToUp: no drags between brother sortIndex.");
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        long plusNum = drag.getSortIndex() - drags.get(drags.size() - 1).getSortIndex();
        if (plusNum <= 0) {
            LOGGER.warn("draggableBrotherBeforeFromDownToUp: plusNum must be greater than zero.");
            return;
        }
        plusNodesSortIndex(drags, plusNum, params);
        updateNodeSortIndex(drag, drags.get(0).getSortIndex(), params);
    }

    /**
     * 拖拽到目标节点内部
     * 该行为默认追加到目标节点最后
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableInner(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        //查询出指定节点下子级的最大下标
        long maxSortIndex = findDropChildMaxSortIndex(drop, params);
        // 查询出拖拽节点的下一个节点
        T nextDrag = findDragNextBrotherNode(drag, params);
        if (nextDrag != null) {
            //存在下一个
            long minusNum = nextDrag.getSortIndex() - drag.getSortIndex();
            if (minusNum <= 0) {
                LOGGER.warn("draggableBefore: minusNum must be greater than zero.");
                return;
            }
            // 更新拖拽节点同级后所有节点的下标
            minusGreaterThanNodeSortIndexBrotherNodesSortIndex(drag, minusNum, params);
        }
        //改变父级
        changeDragNodeParent(drag, drop, params);
        long indexInterval = this.getSortIndexInterval();
        updateNodeSortIndex(drag, maxSortIndex + indexInterval, params);
    }

}
