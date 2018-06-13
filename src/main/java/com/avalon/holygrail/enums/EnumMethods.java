package com.avalon.holygrail.enums;

/**
 * 枚举方法
 * Created by 白超 on 2018/6/13.
 */
public interface EnumMethods {

    Object getValue();

    /**
     * 比较枚举实际值是否相等
     *
     * @param value
     * @return
     */
    default boolean equalsTo(Object value) {
        return this.getValue().equals(value);
    }

    /**
     * 比较枚举实际值是否相等
     *
     * @param value
     * @param target
     * @return
     */
    static boolean equalsTo(Object value, EnumMethods target) {
        return target.equalsTo(value);
    }

    static boolean contains(Object value, EnumMethods[] target) {
        for (EnumMethods e : target) {
            if (e.equalsTo(value)) {
                return true;
            }
        }
        return false;
    }
}
