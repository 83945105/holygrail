package com.avalon.holygrail.util;

import java.util.List;

/**
 * 排序
 * Created by 白超 on 2018-1-22.
 */
public class SortUtil {

    public interface Compare<T> {
        /**
         * 比较俩个参数大小
         * @param left 左边的参数
         * @param right 右边的参数
         * @return left > right = true,此时交换俩个参数位置
         */
        boolean apply(T left, T right);
    }

    /**
     * 冒泡排序
     *
     * @param targets 排序对象
     * @param compare 比较值,接收当前排序left和right的值,返回true表示left>right,此时交换位置
     * @param <T>
     * @return
     */
    public static <T> T[] bubbleSort(T[] targets, Compare<T> compare) {
        int next = targets.length - 1;//循环排序次数
        for (int i = 0; i < next; i++) {
            int maxIndex = next - i;//当前循环排序最大下标
            for (int j = 0; j < maxIndex; j++) {
                T left = targets[j];
                T right = targets[j + 1];
                if (compare.apply(left, right)) {//左边比右边大
                    targets[j] = right;
                    targets[j + 1] = left;
                }
            }
        }
        return targets;
    }

    /**
     * 冒泡排序
     *
     * @param targets 排序对象
     * @param compare 比较值,接收当前排序left和right的值,返回true表示left>right,此时交换位置
     * @param <T>
     * @return
     */
    public static <T> List<T> bubbleSort(List<T> targets, Compare<T> compare) {
        int next = targets.size() - 1;//循环排序次数
        for (int i = 0; i < next; i++) {
            int maxIndex = next - i;//当前循环排序最大下标
            for (int j = 0; j < maxIndex; j++) {
                T left = targets.get(j);
                T right = targets.get(j + 1);
                if (compare.apply(left, right)) {//左边比右边大
                    targets.set(j, right);
                    targets.set(j + 1, left);
                }
            }
        }
        return targets;
    }
}
