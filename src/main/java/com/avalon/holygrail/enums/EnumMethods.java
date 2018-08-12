package com.avalon.holygrail.enums;

import java.util.function.Function;

/**
 * 枚举方法
 *
 * @author 白超
 * @date 2018/6/13
 */
public interface EnumMethods {

    /**
     * 获取值
     *
     * @return
     */
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

    /**
     * 目标枚举值是否包含指定值
     *
     * @param value
     * @param target
     * @return
     */
    static boolean contains(Object value, EnumMethods[] target) {
        for (EnumMethods e : target) {
            if (e.equalsTo(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取枚举指定值对象
     *
     * @param value
     * @param target
     * @param <T>
     * @return
     */
    static <T extends EnumMethods> T getEnumMethods(EnumMethods[] target, Object value) {
        for (EnumMethods e : target) {
            if (e.equalsTo(value)) {
                return (T) e;
            }
        }
        return null;
    }

    /**
     * 获取枚举指定值对象
     *
     * @param value
     * @param formatter
     * @param target
     * @param <T>
     * @return
     */
    static <T extends EnumMethods> T getEnumMethods(EnumMethods[] target, Function<EnumMethods, Object> formatter, Object value) {
        for (EnumMethods e : target) {
            Object v = formatter.apply(e);
            if (value.equals(v)) {
                return (T) e;
            }
        }
        return null;
    }

}
