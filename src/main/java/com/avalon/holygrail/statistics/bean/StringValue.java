package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsStringFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 记录String类型值
 * Created by 白超 on 2018/3/12.
 */
public class StringValue<T> extends StatisticsStringFilter<T> {

    public StringValue(String name, DataContainer<String> dataContainer, RawDataHandler<T, String> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public StringValue(String name, DataContainer<String> dataContainer, Formatter<T, String> formatter) {
        super(name, dataContainer, formatter);
    }

    @Override
    public void doStatistics(String oldValue, String newValue, int count) {
        for (; count > 1; count--) {
            newValue += newValue;
        }
        this.setValue(this.getName(), newValue);
    }

}
