package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;

/**
 * Created by 白超 on 2018/3/13.
 */
public class SeniorStatisticsFilter<V> extends StatisticsFilter<DataContainer, V, V> {
    public SeniorStatisticsFilter(String name, DataContainer<V> dataContainer) {
        super(name, dataContainer);
    }

    @Override
    public void doStatistics(DataContainer go, DataContainer<V> back) throws Exception {

    }

    @Override
    public void doStatistics(V value, int count) throws Exception {

    }
}
