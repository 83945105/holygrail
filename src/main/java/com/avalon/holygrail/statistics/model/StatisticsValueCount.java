package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 统计值次数
 * Created by 白超 on 2018/3/9.
 */
public abstract class StatisticsValueCount<T, V> extends BasicStatisticsFilter<T, V> {

    public StatisticsValueCount(String name, DataContainer<V> dataContainer, RawDataHandler<T, V> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public StatisticsValueCount(String name, DataContainer<V> dataContainer, Formatter<T, V> formatter) {
        super(name, dataContainer, formatter);
    }

    @Override
    public void doStatistics(V value, int count) throws Exception {
        int hc = this.getValueCount(this.getName(), value);
        this.setValueCount(this.getName(), value, hc + count);
    }
}
