package com.avalon.holygrail.util;

/**
 * 算法工具
 *
 * @author 白超
 * @date 2018-1-28
 */
public class AlgorithmicUtil {

    private static final int MIN_CALCULATE_NUMBER = 2;

    /**
     * 根据数字计算出字符串
     * 规则同Excel列名,如一个集合区间为[A-Z],那么传入num数字得到的结果如下
     * 1  => A
     * 2  => B
     * 26 => Z
     * 27 => AA
     * 29 => AC
     *
     * @param num        数字
     * @param collection 集合
     * @return 字符串
     */
    public static String calculateStrByNumber(int num, Object[] collection) {
        if (collection.length < MIN_CALCULATE_NUMBER) {
            throw new RuntimeException("collection length must great and then 2");
        }
        return AlgorithmicUtil.calculateStrByNumber(num, collection, new StringBuilder()).toString();
    }

    public static StringBuilder calculateStrByNumber(int num, Object[] collection, StringBuilder rs) {
        if (num < collection.length) {
            return rs.insert(0, collection[num - 1]);
        }
        int idx = num % collection.length - 1;
        rs.insert(0, collection[idx >= 0 ? idx : idx + collection.length]);
        num = (num - idx) / collection.length;
        return AlgorithmicUtil.calculateStrByNumber(num, collection, rs);
    }

    /**
     * calculateStrByNumber的反向计算
     *
     * @param str        字符串
     * @param collection 集合
     * @return 数字
     */
    public static Long calculateNumByString(String str, Object[] collection) {
        if (collection.length < MIN_CALCULATE_NUMBER) {
            throw new RuntimeException("collection length must great and then 2");
        }
        String[] strs = str.trim().split("");
        Long rs = 0L;
        for (int i = 0; i < strs.length; i++) {
            for (int j = 0; j < collection.length; j++) {
                if (!strs[i].equals(collection[j])) {
                    continue;
                }
                if (i != strs.length - 1) {
                    rs += collection.length * (strs.length - i - 1) * (j + 1);
                } else {
                    rs += (j + 1);
                }
                break;
            }
        }
        return rs;
    }

}
