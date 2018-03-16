package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.util.Collection;

/**
 * 汇总统计
 * 用于汇总分布于多个容器的数据
 * Created by 白超 on 2018-3-10.
 */
public abstract class CollectStatisticsFilter<V> extends StatisticsFilter<Collection<DataContainer>, V, Collection<DataContainer>> {

    /**
     * 格式化数据
     */
    private Formatter<Collection<DataContainer>, Collection<DataContainer>> formatter = (record, filter) -> filter.doStatistics(record, 1);

    public CollectStatisticsFilter(String name, DataContainer<V> dataContainer) {
        super(name, dataContainer);
    }

    public CollectStatisticsFilter(String name, DataContainer<V> dataContainer, Formatter<Collection<DataContainer>, Collection<DataContainer>> formatter) {
        super(name, dataContainer);
        this.formatter = formatter;
    }

    public CollectStatisticsFilter(FormatterName<Collection<DataContainer>, V> formatterName, DataContainer<V> dataContainer) {
        super(formatterName, dataContainer);
    }

    public CollectStatisticsFilter(FormatterName<Collection<DataContainer>, V> formatterName, DataContainer<V> dataContainer, Formatter<Collection<DataContainer>, Collection<DataContainer>> formatter) {
        super(formatterName, dataContainer);
        this.formatter = formatter;
    }

    @Override
    public void doStatistics(Collection<DataContainer> go, DataContainer<V> back) throws Exception {
        this.formatter.accept(go, this);
    }
}
