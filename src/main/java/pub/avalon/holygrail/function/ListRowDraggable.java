package pub.avalon.holygrail.function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.avalon.holygrail.function.beans.DropType;
import pub.avalon.holygrail.function.beans.ListRow;
import pub.avalon.holygrail.utils.SortUtil;

import java.util.Collection;
import java.util.List;

/**
 * 列表行拖动
 *
 * @param <T> 列表行
 * @param <P> 用于传递的参数
 */
public interface ListRowDraggable<T extends ListRow, P> {

    Log LOGGER = LogFactory.getLog(ListRowDraggable.class);

    /**
     * 查询排序下标的之间的所有行
     *
     * @param startRow
     * @param endRow
     * @param params   传递的参数
     * @return
     */
    List<T> findRowsInSortIndex(T startRow, T endRow, P params);

    /**
     * 更新行的排序下标
     *
     * @param row
     * @param sortIndex
     * @param params
     */
    void updateRowSortIndex(T row, long sortIndex, P params);

    /**
     * 将行下标减去指定数值
     *
     * @param rows
     * @param minusNum 始终大于0
     * @param params
     */
    void minusRowsSortIndex(Collection<T> rows, long minusNum, P params);

    /**
     * 将行下标加上指定数值
     *
     * @param rows
     * @param plusNum 始终大于0
     * @param params
     */
    void plusRowsSortIndex(Collection<T> rows, long plusNum, P params);

    /**
     * 拖拽
     *
     * @param drag
     * @param drop
     * @param dropType
     * @param params
     */
    default void draggableRow(T drag, T drop, DropType dropType, P params) {
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
            case AFTER:
                draggableAfter(drag, drop, params);
                break;
            default:
                throw new RuntimeException("dropType incorrect.");
        }
    }

    /**
     * 拖拽到目标行前面
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
        long dragIndex = drag.getSortIndex();
        long dropIndex = drop.getSortIndex();
        if (dragIndex == dropIndex) {
            // 正常情况下, 如果是行之间拖拽, 两者下标不能相同
            LOGGER.warn("draggableBrotherBefore: dragIndex are equal to dropIndex.");
            return;
        }
        if (dragIndex < dropIndex) {
            // 自上而下
            draggableBeforeFromUpToDown(drag, drop, params);
            return;
        }
        // 自下而上
        draggableBeforeFromDownToUp(drag, drop, params);
    }

    /**
     * 自上而下拖拽到行前面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBeforeFromUpToDown(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findRowsInSortIndex(drag, drop, params);
        if (drags == null) {
            LOGGER.warn("findRowsInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            // 正常情况下, 应该能获取到元素
            LOGGER.warn("draggableBeforeFromUpToDown: no drags between brother sortIndex.");
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        //计算出拖动元素与将要移动的第一个元素之间的下标间隔
        long minusNum = drags.get(0).getSortIndex() - drag.getSortIndex();
        if (minusNum <= 0) {
            LOGGER.warn("draggableBeforeFromUpToDown: minusNum must be greater than zero.");
            return;
        }
        //将拖动放置之间的行减少一定排序下标
        minusRowsSortIndex(drags, minusNum, params);
        //将拖动行放置到最后一个行位置上
        updateRowSortIndex(drag, drags.get(drags.size() - 1).getSortIndex(), params);
    }

    /**
     * 自下而上拖拽到行前面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableBeforeFromDownToUp(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findRowsInSortIndex(drop, drag, params);
        if (drags == null) {
            LOGGER.warn("findRowsInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            //这种情况是相邻行, 只需要交换位置
            updateRowSortIndex(drag, drop.getSortIndex(), params);
            updateRowSortIndex(drop, drag.getSortIndex(), params);
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        long plusNum = drag.getSortIndex() - drags.get(drags.size() - 1).getSortIndex();
        if (plusNum <= 0) {
            LOGGER.warn("draggableBeforeFromDownToUp: plusNum must be greater than zero.");
            return;
        }
        drags.add(drop);
        plusRowsSortIndex(drags, plusNum, params);
        //将拖动行放置到目标元素的位置
        updateRowSortIndex(drag, drop.getSortIndex(), params);
    }

    /**
     * 拖拽到目标行后面
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
        long dragIndex = drag.getSortIndex();
        long dropIndex = drop.getSortIndex();
        if (dragIndex == dropIndex) {
            // 正常情况下, 如果是行之间拖拽, 两者下标不能相同
            LOGGER.warn("draggableAfter: dragIndex are equal to dropIndex.");
            return;
        }
        if (dragIndex < dropIndex) {
            // 自上而下
            draggableAfterFromUpToDown(drag, drop, params);
            return;
        }
        // 自下而上
        draggableAfterFromDownToUp(drag, drop, params);
    }

    /**
     * 自上而下拖拽到行后面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableAfterFromUpToDown(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findRowsInSortIndex(drag, drop, params);
        if (drags == null) {
            LOGGER.warn("findRowsInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            //这种情况是相邻行, 只需要交换位置
            updateRowSortIndex(drag, drop.getSortIndex(), params);
            updateRowSortIndex(drop, drag.getSortIndex(), params);
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        //计算出拖动元素与将要移动的第一个元素之间的下标间隔
        long minusNum = drags.get(0).getSortIndex() - drag.getSortIndex();
        if (minusNum <= 0) {
            LOGGER.warn("draggableAfterFromUpToDown: minusNum must be greater than zero.");
            return;
        }
        drags.add(drop);
        minusRowsSortIndex(drags, minusNum, params);
        //将拖动行放置目标行位置上
        updateRowSortIndex(drag, drop.getSortIndex(), params);
    }

    /**
     * 自下而上拖拽到行后面
     *
     * @param drag
     * @param drop
     * @param params
     */
    default void draggableAfterFromDownToUp(T drag, T drop, P params) {
        if (drag == null) {
            throw new RuntimeException("drag cannot be null.");
        }
        if (drop == null) {
            throw new RuntimeException("drop cannot be null.");
        }
        List<T> drags = findRowsInSortIndex(drop, drag, params);
        if (drags == null) {
            LOGGER.warn("findRowsInSortIndex can not return null.");
            return;
        }
        if (drags.size() == 0) {
            // 正常情况下, 应该能获取到元素
            LOGGER.warn("draggableAfterFromDownToUp: no drags between brother sortIndex.");
            return;
        }
        //从小到大排序
        SortUtil.bubbleSort(drags, (left, right) -> left.getSortIndex() > right.getSortIndex());
        long plusNum = drag.getSortIndex() - drags.get(drags.size() - 1).getSortIndex();
        if (plusNum <= 0) {
            LOGGER.warn("draggableAfterFromDownToUp: plusNum must be greater than zero.");
            return;
        }
        plusRowsSortIndex(drags, plusNum, params);
        updateRowSortIndex(drag, drags.get(0).getSortIndex(), params);
    }

}
