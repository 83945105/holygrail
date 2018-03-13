package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsIntegerFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 总次数统计
 * Created by 白超 on 2018/3/9.
 */
public final class TotalCount<T> extends StatisticsIntegerFilter<T> {

    public TotalCount(String name, DataContainer<Integer> dataContainer, RawDataHandler<T, Integer> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public TotalCount(String name, DataContainer<Integer> dataContainer, Formatter<T, Integer> formatter) {
        super(name, dataContainer, formatter);
    }

    @Override
    public void doStatistics(Integer oldValue, Integer newValue, int count) {
        this.setValue(this.getName(), oldValue + newValue * count);
    }

}
