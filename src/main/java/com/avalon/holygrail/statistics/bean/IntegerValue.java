package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsIntegerFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 记录Integer类型值
 * Created by 白超 on 2018/3/12.
 */
public class IntegerValue<T> extends StatisticsIntegerFilter<T> {

    public IntegerValue(String name, DataContainer<Integer> dataContainer, RawDataHandler<T, Integer> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public IntegerValue(String name, DataContainer<Integer> dataContainer, Formatter<T, Integer> formatter) {
        super(name, dataContainer, formatter);
    }

    @Override
    public void doStatistics(Integer oldValue, Integer newValue, int count) {
        for (; count > 0; count--) {
            newValue += newValue;
        }
        this.setValue(this.getName(), newValue);
    }
}
