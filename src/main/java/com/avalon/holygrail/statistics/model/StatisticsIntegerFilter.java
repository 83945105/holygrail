package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 统计Integer类型
 * Created by 白超 on 2018-3-12.
 */
public abstract class StatisticsIntegerFilter<T> extends BasicStatisticsFilter<T, Integer> {

    public StatisticsIntegerFilter(String name, DataContainer<Integer> dataContainer, RawDataHandler<T, Integer> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public StatisticsIntegerFilter(String name, DataContainer<Integer> dataContainer, Formatter<T, Integer> formatter) {
        super(name, dataContainer, formatter);
    }

    public abstract void doStatistics(Integer oldValue, Integer newValue, int count);

    @Override
    public void doStatistics(Integer value, int count) throws Exception {
        Integer hv = this.getValue(this.getName());
        if (hv == null) {
            hv = 0;
        }
        if (value == null) {
            value = 0;
        }
        this.doStatistics(hv, value, count);
    }
}
