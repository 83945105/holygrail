package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsStringFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 记录String类型值
 * Created by 白超 on 2018/3/12.
 */
public final class StringValue<T> extends StatisticsStringFilter<T> {

    public StringValue(String name, DataContainer<String> dataContainer, RawDataHandler<T, String> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public StringValue(String name, DataContainer<String> dataContainer, Formatter<T, String> formatter) {
        super(name, dataContainer, formatter);
    }

    public StringValue(FormatterName<T, String> formatterName, DataContainer<String> dataContainer, RawDataHandler<T, String> rawDataHandler) {
        super(formatterName, dataContainer, rawDataHandler);
    }

    public StringValue(FormatterName<T, String> formatterName, DataContainer<String> dataContainer, Formatter<T, String> formatter) {
        super(formatterName, dataContainer, formatter);
    }

    @Override
    public void doStatistics(String oldValue, String newValue, int count) {
        this.setValue(this.getName(), newValue);
        int hc = this.getValueCount(this.getName(), newValue);
        this.setValueCount(this.getName(), newValue, hc + count);
    }

}
