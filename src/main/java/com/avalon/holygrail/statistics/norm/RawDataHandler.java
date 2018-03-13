package com.avalon.holygrail.statistics.norm;

/**
 * 原始数据
 * Created by 白超 on 2018/3/9.
 */
@FunctionalInterface
public interface RawDataHandler<T, V> {

    /**
     * 获取原始值
     *
     * @param record 原始数据
     * @return
     */
    V apply(T record);

}
