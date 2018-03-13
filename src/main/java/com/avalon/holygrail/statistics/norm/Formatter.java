package com.avalon.holygrail.statistics.norm;

/**
 * 格式化数据
 * Created by 白超 on 2018/3/12.
 */
public interface Formatter<T, V> {

    void accept(T record, Statistics<V> filter) throws Exception;

}
