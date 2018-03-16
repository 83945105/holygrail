package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 统计String类型
 * Created by 白超 on 2018/3/12.
 */
public abstract class StatisticsStringFilter<T> extends BasicStatisticsFilter<T, String> {

    public StatisticsStringFilter(String name, DataContainer<String> dataContainer, RawDataHandler<T, String> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public StatisticsStringFilter(String name, DataContainer<String> dataContainer, Formatter<T, String> formatter) {
        super(name, dataContainer, formatter);
    }

    public StatisticsStringFilter(FormatterName<T, String> formatterName, DataContainer<String> dataContainer, RawDataHandler<T, String> rawDataHandler) {
        super(formatterName, dataContainer, rawDataHandler);
    }

    public StatisticsStringFilter(FormatterName<T, String> formatterName, DataContainer<String> dataContainer, Formatter<T, String> formatter) {
        super(formatterName, dataContainer, formatter);
    }

    public abstract void doStatistics(String oldValue, String newValue, int count);

    @Override
    public void doStatistics(String value, int count) throws Exception {
        String hv = this.getValue(this.getName());
        if (hv == null) {
            hv = "";
        }
        if (value == null) {
            value = "";
        }
        this.doStatistics(hv, value, count);
    }
}
