package com.avalon.holygrail.util;

import java.util.ArrayList;
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
         * @return left < right = true,从大到小排序
         */
        boolean apply(T left, T right);
    }

    /**
     * 冒泡排序
     * left > right = true,从小到大排序
     * left < right = true,从大到小排序
     *
     * @param targets 排序对象
     * @param compare 比较值
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
     * left > right = true,从小到大排序
     * left < right = true,从大到小排序
     *
     * @param targets 排序对象
     * @param compare 比较值,接收当前排序left和right的值,返回true表示left>right,此时交换位置
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
                if (compare.apply(left, right)) {//左边比右边大
                    targets.set(j, right);
                    targets.set(j + 1, left);
                }
            }
        }
        return targets;
    }

    public static void main(String[] args) {
        ArrayList<Integer> r = new ArrayList<>();
        r.add(3);
        r.add(2);
        r.add(4);
        r.add(1);
        r = (ArrayList<Integer>) SortUtil.bubbleSort(r, (left, right) -> left > right);
        System.out.println(r);
    }

}
