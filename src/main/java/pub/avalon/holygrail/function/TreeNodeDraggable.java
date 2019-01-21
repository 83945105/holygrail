package pub.avalon.holygrail.function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.avalon.holygrail.function.beans.TreeNode;
import pub.avalon.holygrail.function.beans.DropType;
import pub.avalon.holygrail.utils.SortUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 树节点拖动
 *
 * @param <T> 树节点
 * @param <P> 用于传递的参数
 */
public interface TreeNodeDraggable<T extends TreeNode, P> {

    Log LOGGER = LogFactory.getLog(TreeNodeDraggable.class);

    /**
     * 查询指定开区间(不包含俩端值)排序下标的所有兄弟节点
     *
     * @param startBrotherSortIndex 开始的兄弟节点排序下标
     * @param endBrotherSortIndex   结束的兄弟节点排序下标
     * @param parentKey             父级key
     * @param params                传递的参数
     * @return
     */
    List<T> findBrotherNodesByOpenIntervalIndex(long startBrotherSortIndex, long endBrotherSortIndex, Object parentKey, P params);

    /**
     * 查询放置节点相邻的上一个节点
     *
     * @param key
     * @param sortIndex
     * @param parentKey
     * @param params
     * @return
     */
    T findDropPreviousBrotherNodeByKey(Object key, long sortIndex, Object parentKey, P params);

    /**
     * 查询拖拽节点相邻的下一个节点
     *
     * @param key
     * @param sortIndex
     * @param parentKey
     * @param params
     * @return
     */
    T findDragNextBrotherNodeByKey(Object key, long sortIndex, Object parentKey, P params);

    /**
     * 查询放置节点相邻的下一个节点
     *
     * @param key
     * @param sortIndex
     * @param parentKey
     * @param params
     * @return
     */
    T findDropNextBrotherNodeByKey(Object key, long sortIndex, Object parentKey, P params);

    /**
     * 查询出指定节点下最大下标
     *
     * @param key
     * @param params
     * @return
     */
    long findChildMaxSortIndexByKey(Object key, P params);

    /**
     * 根据key更新节点的排序下标
     *
     * @param key
     * @param sortIndex
     * @param params
     */
    void updateNodeSortIndexByKey(Object key, long sortIndex, P params);

    /**
     * 将所有相同父级节点的放置节点后移一位
     * 后移一位指的是将sortIndex增加一个单位,至于增加多少,根据你自己的规则而定
     * 比如你的sortIndex是按照 [0, 10, 20, ..., 100, ...] 间隔10的方式递增,那你就要将对应的节点的sortIndex增加10
     * 该操作的目的是给即将插入过来的节点腾出位置
     *
     * @param parentKey
     * @param params
     */
    void updateDropBrotherNodesSortIndexToNext(Object parentKey, P params);

    /**
     * 将节点的排序下标更新为指定兄弟下标的下一个
     * 下一个指的是将sortIndex增加一个单位,至于增加多少,根据你自己的规则而定
     * 比如你的sortIndex是按照 [0, 10, 20, ..., 100, ...] 间隔10的方式递增,那你就要将对应的节点的sortIndex增加10
     * 该操作的目的是将一个节点移动到一个节点的最后
     *
     * @param key
     * @param brotherSortIndex
     * @param params
     */
    void updateNodeSortIndexToBrotherSortIndexNextByKey(Object key, long brotherSortIndex, P params);

    /**
     * 将指定key的节点下标减去指定数值
     *
     * @param keys
     * @param minusNum 始终大于0
     * @param params
     */
    void minusNodesSortIndexByKeys(Set<Object> keys, long minusNum, P params);

    /**
     * 将所有排序下标大于指定排序下标的拖拽兄弟节点的排序下标减去指定数值
     *
     * @param sortIndex
     * @param minusNum
     * @param parentKey
     * @param params
     */
    void minusDragBrotherNodesSortIndexGreaterThanSortIndex(long sortIndex, long minusNum, Object parentKey, P params);

    /**
     * 将指定key的节点下标加上指定数值
     *
     * @param keys
     * @param plusNum 始终大于0
     * @param params
     */
    void plusNodesSortIndexByKeys(Set<Object> keys, long plusNum, P params);

    /**
     * 将所有排序下标大于指定排序下标的放置兄弟节点的排序下标加上指定数值
     *
     * @param sortIndex
     * @param plusNum
     * @param parentKey
     * @param params
     */
    void plusDropBrotherNodesSortIndexGreaterThanSortIndex(long sortIndex, long plusNum, Object parentKey, P params);

    /**
     * 改变拖拽节点的父级
     * 注意: 如果节点的子孙节点记录了如父级节点路径,记得同时将子孙节点对应字段也做修改
     *
     * @param key          当前节点key
     * @param newParentKey 新的父级节点key
     * @param params
     */
    void changeDragNodeParentByKey(Object key, Object newParentKey, P params);

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
        Object dragParentKey = drag.getParentKey();
        Object dropParentKey = drop.getParentKey();
        if (dragParentKey.equals(dropParentKey)) {
            // 兄弟节点之间拖拽
            draggableBrotherBefore(drag, drop, params);
            return;
        }
        // 跨节点拖拽, 改变父级
        // 查询出拖拽节点的下一个节点
        T nextDrag = findDragNextBrotherNodeByKey(drag.getKey(), drag.getSortIndex(), drag.getParentKey(), params);
        if (nextDrag != null) {
            //存在下一个
            long minusNum = nextDrag.getSortIndex() - drag.getSortIndex();
            if (minusNum <= 0) {
                LOGGER.warn("draggableBefore: minusNum must be greater than zero.");
                return;
            }
            // 更新拖拽节点同级后所有节点的下标
            minusDragBrotherNodesSortIndexGreaterThanSortIndex(drag.getSortIndex(), minusNum, drag.getParentKey(), params);
        }
        // 查询出目标节点的上一个节点
        T previousDrop = findDropPreviousBrotherNodeByKey(drop.getKey(), drop.getSortIndex(), drop.getParentKey(), params);
        if (previousDrop == null) {
            //没有上一个, 说明是第一个, 所有兄弟节点(包括自身)后移一位
            updateDropBrotherNodesSortIndexToNext(drop.getParentKey(), params);
        } else {
            // 有上一个, 后移所有后面的兄弟节点
            long plusNum = drop.getSortIndex() - previousDrop.getSortIndex();
            if (plusNum <= 0) {
                LOGGER.warn("draggableBefore: plusNum must be greater than zero.");
                return;
            }
            //后移所有兄弟节点
            plusDropBrotherNodesSortIndexGreaterThanSortIndex(previousDrop.getSortIndex(), plusNum, drop.getParentKey(), params);
        }
        // 改变父级并设置sortIndex为目标节点
        changeDragNodeParentByKey(drag.getKey(), drop.getParentKey(), params);
        updateNodeSortIndexByKey(drag.getKey(), drop.getSortIndex(), params);
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
        List<T> drags = findBrotherNodesByOpenIntervalIndex(drag.getSortIndex(), drop.getSortIndex(), drag.getParentKey(), params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesByOpenIntervalIndex can not return null.");
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
        minusNodesSortIndexByKeys(drags.stream().map(TreeNode::getKey).collect(Collectors.toSet()), minusNum, params);
        //将拖动节点放置到最后一个节点位置上
        updateNodeSortIndexByKey(drag.getKey(), drags.get(drags.size() - 1).getSortIndex(), params);
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
        List<T> drags = findBrotherNodesByOpenIntervalIndex(drop.getSortIndex(), drag.getSortIndex(), drag.getParentKey(), params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesByOpenIntervalIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            //这种情况是相邻节点, 只需要交换位置
            updateNodeSortIndexByKey(drag.getKey(), drop.getSortIndex(), params);
            updateNodeSortIndexByKey(drop.getKey(), drag.getSortIndex(), params);
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        long plusNum = drag.getSortIndex() - drags.get(drags.size() - 1).getSortIndex();
        if (plusNum <= 0) {
            LOGGER.warn("draggableBrotherBeforeFromDownToUp: plusNum must be greater than zero.");
            return;
        }
        Set<Object> keys = drags.stream().map(TreeNode::getKey).collect(Collectors.toSet());
        keys.add(drop.getKey());
        plusNodesSortIndexByKeys(keys, plusNum, params);
        //将拖动节点放置到目标元素的位置
        updateNodeSortIndexByKey(drag.getKey(), drop.getSortIndex(), params);
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
        Object dragParentKey = drag.getParentKey();
        Object dropParentKey = drop.getParentKey();
        if (dragParentKey.equals(dropParentKey)) {
            // 兄弟节点之间拖拽
            draggableBrotherAfter(drag, drop, params);
            return;
        }
        // 跨节点拖拽, 改变父级
        // 查询出拖拽节点的下一个节点
        T nextDrag = findDragNextBrotherNodeByKey(drag.getKey(), drag.getSortIndex(), drag.getParentKey(), params);
        if (nextDrag != null) {
            long minusNum = nextDrag.getSortIndex() - drag.getSortIndex();
            if (minusNum <= 0) {
                LOGGER.warn("draggableBefore: minusNum must be greater than zero.");
                return;
            }
            // 更新拖拽节点同级后所有节点的下标
            minusDragBrotherNodesSortIndexGreaterThanSortIndex(drag.getSortIndex(), minusNum, drag.getParentKey(), params);
        }
        // 查询目标节点的下一个节点
        T nextDrop = findDropNextBrotherNodeByKey(drop.getKey(), drop.getSortIndex(), drop.getParentKey(), params);
        if (nextDrop == null) {
            //没有下一个, 改变父级
            changeDragNodeParentByKey(drag.getKey(), drop.getParentKey(), params);
            //设置index为下一个
            updateNodeSortIndexToBrotherSortIndexNextByKey(drag.getKey(), drop.getSortIndex(), params);
            return;
        }
        // 有下一个, 后移所有后面的兄弟节点
        long plusNum = nextDrop.getSortIndex() - drop.getSortIndex();
        if (plusNum <= 0) {
            LOGGER.warn("draggableBefore: plusNum must be greater than zero.");
            return;
        }
        //后移所有兄弟节点
        plusDropBrotherNodesSortIndexGreaterThanSortIndex(drop.getSortIndex(), plusNum, drop.getParentKey(), params);
        //改变拖拽父级节点
        changeDragNodeParentByKey(drag.getKey(), drop.getParentKey(), params);
        //设置排序号为放置节点的下一个节点
        updateNodeSortIndexByKey(drag.getKey(), nextDrop.getSortIndex(), params);
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
        List<T> drags = findBrotherNodesByOpenIntervalIndex(drag.getSortIndex(), drop.getSortIndex(), drag.getParentKey(), params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesByOpenIntervalIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            //这种情况是相邻节点, 只需要交换位置
            updateNodeSortIndexByKey(drag.getKey(), drop.getSortIndex(), params);
            updateNodeSortIndexByKey(drop.getKey(), drag.getSortIndex(), params);
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
        Set<Object> keys = drags.stream().map(TreeNode::getKey).collect(Collectors.toSet());
        keys.add(drop.getKey());
        minusNodesSortIndexByKeys(keys, minusNum, params);
        //将拖动节点放置目标节点位置上
        updateNodeSortIndexByKey(drag.getKey(), drop.getSortIndex(), params);
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
        List<T> drags = findBrotherNodesByOpenIntervalIndex(drop.getSortIndex(), drag.getSortIndex(), drag.getParentKey(), params);
        if (drags == null) {
            LOGGER.warn("findBrotherNodesByOpenIntervalIndex can not return null.");
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
        plusNodesSortIndexByKeys(drags.stream().map(TreeNode::getKey).collect(Collectors.toSet()), plusNum, params);
        updateNodeSortIndexByKey(drag.getKey(), drags.get(0).getSortIndex(), params);
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
        long maxSortIndex = findChildMaxSortIndexByKey(drop.getKey(), params);
        // 查询出拖拽节点的下一个节点
        T nextDrag = findDragNextBrotherNodeByKey(drag.getKey(), drag.getSortIndex(), drag.getParentKey(), params);
        if (nextDrag != null) {
            //存在下一个
            long minusNum = nextDrag.getSortIndex() - drag.getSortIndex();
            if (minusNum <= 0) {
                LOGGER.warn("draggableBefore: minusNum must be greater than zero.");
                return;
            }
            // 更新拖拽节点同级后所有节点的下标
            minusDragBrotherNodesSortIndexGreaterThanSortIndex(drag.getSortIndex(), minusNum, drag.getParentKey(), params);
        }
        //改变父级
        changeDragNodeParentByKey(drag.getKey(), drop.getKey(), params);
        updateNodeSortIndexToBrotherSortIndexNextByKey(drag.getKey(), maxSortIndex, params);
    }

}
