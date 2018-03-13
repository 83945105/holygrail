package com.avalon.holygrail.statistics.norm;

/**
 * 统计
 * Created by 白超 on 2018-3-13.
 */
public interface Statistics<V> {

    /**
     * 执行统计
     *
     * @param value 统计值
     * @param count 统计值数量
     */
    void doStatistics(V value, int count) throws Exception;
}
