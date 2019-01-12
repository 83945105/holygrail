package pub.avalon.holygrail.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具
 *
 * @author 白超
 * @date 2018/2/27
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    public interface ListHandlerA<T> {

        /**
         * 接收数据
         *
         * @param records
         * @throws Exception
         */
        void accept(List<T> records);
    }

    public interface ListHandlerB<T> {

        /**
         * 接收数据
         *
         * @param records
         * @return
         * @throws Exception
         */
        boolean apply(List<T> records);
    }

    /**
     * 批处理
     *
     * @param records 数据集合
     * @param start   开始下标
     * @param length  处理长度,可以为负值,负值表示从后向前
     * @param handler 回调函数
     * @param <T>
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> void batchProcess(List<T> records, int start, int length, ListHandlerB<T> handler) {
        if (records == null || records.size() == 0) {
            return;
        }
        if (start < 0) {
            throw new RuntimeException("The starting number should not be less than zero.");
        }
        if (length == 0) {
            throw new RuntimeException("Processing length cannot be equal to zero.");
        }
        if (start == 0 && length > records.size()) {
            handler.apply(records);
            return;
        }
        List<T> list = new ArrayList<>();
        int i;
        int end = start + length;
        if (length > 0) {
            end--;
            for (i = start; i <= end; i++) {
                if (i >= records.size()) {
                    break;
                }
                list.add(records.get(i));
            }
        } else {
            ++end;
            for (i = start; i >= end; i--) {
                if (i < 0) {
                    break;
                }
                list.add(records.get(i));
            }
        }
        if (handler.apply(list) && i >= 0 && i < records.size()) {
            CollectionUtil.batchProcess(records, i, length, handler);
        }
    }

    /**
     * 批处理
     * 默认从第一条数据开始
     *
     * @param records 数据集合
     * @param size    长度,默认取绝对值
     * @param handler 回调函数
     * @param <T>
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> void batchProcess(List<T> records, int size, ListHandlerB<T> handler) {
        CollectionUtil.batchProcess(records, 0, Math.abs(size), handler);
    }

    /**
     * 批处理
     *
     * @param records 数据集合
     * @param start   开始下标
     * @param length  处理长度,可以为负值,负值表示从后向前
     * @param handler 回调函数
     * @param <T>
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> void batchProcess(List<T> records, int start, int length, ListHandlerA<T> handler) {
        CollectionUtil.batchProcess(records, start, length, rows -> {
            handler.accept(rows);
            return true;
        });
    }

    /**
     * 批处理
     *
     * @param records 数据集合
     * @param size    长度,默认取绝对值
     * @param handler 回调函数
     * @param <T>
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> void batchProcess(List<T> records, int size, ListHandlerA<T> handler) {
        CollectionUtil.batchProcess(records, 0, Math.abs(size), handler);
    }

}
