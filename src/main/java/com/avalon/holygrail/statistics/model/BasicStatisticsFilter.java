package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 基础统计过滤器
 *
 * @param <T> 原始数据类型
 * @param <V> 用于当前统计器数据类型
 */
public abstract class BasicStatisticsFilter<T, V> extends StatisticsFilter<T, V, V> {

    /**
     * 用于获取目标数据
     */
    private RawDataHandler<T, V> rawDataHandler = record -> null;

    /**
     * 格式化数据
     */
    private Formatter<T, V> formatter = (record, filter) -> filter.doStatistics(BasicStatisticsFilter.this.rawDataHandler.apply(record), 1);

    public BasicStatisticsFilter(String name, DataContainer<V> dataContainer, RawDataHandler<T, V> rawDataHandler) {
        super(name, dataContainer);
        this.rawDataHandler = rawDataHandler;
    }

    public BasicStatisticsFilter(String name, DataContainer<V> dataContainer, Formatter<T, V> formatter) {
        super(name, dataContainer);
        this.formatter = formatter;
    }

    public BasicStatisticsFilter(FormatterName<T, V> formatterName, DataContainer<V> dataContainer, RawDataHandler<T, V> rawDataHandler) {
        super(formatterName, dataContainer);
        this.rawDataHandler = rawDataHandler;
    }

    public BasicStatisticsFilter(FormatterName<T, V> formatterName, DataContainer<V> dataContainer, Formatter<T, V> formatter) {
        super(formatterName, dataContainer);
        this.formatter = formatter;
    }

    @Override
    public void doStatistics(T go, DataContainer<V> back) throws Exception {
        this.formatter.accept(go, this);
    }

}
