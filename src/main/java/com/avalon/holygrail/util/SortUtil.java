package com.avalon.holygrail.util;

import java.util.List;

/**
 * 排序
 * Created by 白超 on 2018-1-22.
 */
public class SortUtil {

    public interface CompareA<T> {
        /**
         * 比较俩个参数大小
         *
         * @param left  左边的参数
         * @param right 右边的参数
         * @return left > right = true,从小到大排序, left < right = true,从大到小排序
         */
        boolean apply(T left, T right);
    }

    /**
     * 冒泡排序
     * left > right = true,从小到大排序
     * left < right = true,从大到小排序
     *
     * @param targets 排序对象
     * @param compare 比较函数
     * @param <T>
     * @return
     */
    public static <T> T[] bubbleSort(T[] targets, CompareA<T> compare) {
        int next = targets.length - 1;//循环排序次数
        T left;
        T right;
        for (int i = 0; i < next; i++) {
            int maxIndex = next - i;//当前循环排序最大下标
            for (int j = 0; j < maxIndex; j++) {
                left = targets[j];
                right = targets[j + 1];
                if (compare.apply(left, right)) {
                    targets[j] = right;
                    targets[j + 1] = left;
                }
            }
        }
        return targets;
    }

    /**
     * 冒泡排序
     * left > right = true,从小到大排序
     * left < right = true,从大到小排序
     *
     * @param targets 排序对象
     * @param compare 比较函数
     * @param <T>
     * @return
     */
    public static <T> List<T> bubbleSort(List<T> targets, CompareA<T> compare) {
        int next = targets.size() - 1;//循环排序次数
        T left;
        T right;
        for (int i = 0; i < next; i++) {
            int maxIndex = next - i;//当前循环排序最大下标
            for (int j = 0; j < maxIndex; j++) {
                left = targets.get(j);
                right = targets.get(j + 1);
                if (compare.apply(left, right)) {
                    targets.set(j, right);
                    targets.set(j + 1, left);
                }
            }
        }
        return targets;
    }

    public interface CompareB<T> {
        /**
         * 比较俩个参数大小
         *
         * @param current 当前元素
         * @param target  比较元素
         * @return current < target = true,从小到大排序, current > target = true,从大到小排序
         */
        boolean apply(T current, T target);
    }

    /**
     * 插入排序
     * left < right = true,从小到大排序
     * left > right = true,从大到小排序
     *
     * @param targets 排序对象
     * @param compare 比较函数
     * @param <T>
     * @return
     */
    public static <T> T[] insertSort(T[] targets, CompareB<T> compare) {
        int len = targets.length;
        T current;
        int j;
        for (int i = 1; i < len; i++) {
            current = targets[i];//当前元素,从第二个元素开始
            for (j = i; j > 0 && compare.apply(current, targets[j - 1]); j--) {
                targets[j] = targets[j - 1];
            }
            targets[j] = current;
        }
        return targets;
    }

    /**
     * 插入排序
     * left < right = true,从小到大排序
     * left > right = true,从大到小排序
     *
     * @param targets 排序对象
     * @param compare 比较函数
     * @param <T>
     * @return
     */
    public static <T> List<T> insertSort(List<T> targets, CompareB<T> compare) {
        int len = targets.size();
        T current;
        int j;
        for (int i = 1; i < len; i++) {
            current = targets.get(i);//当前元素,从第二个元素开始
            for (j = i; j > 0 && compare.apply(current, targets.get(j - 1)); j--) {
                targets.set(j, targets.get(j - 1));
            }
            targets.set(j, current);
        }
        return targets;
    }

}
