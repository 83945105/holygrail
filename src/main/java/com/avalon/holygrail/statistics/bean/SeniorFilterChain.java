package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.filter.norm.FilterChain;
import com.avalon.holygrail.statistics.model.SeniorStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;

import java.util.List;

/**
 * 高阶过滤链
 * Created by 白超 on 2018/3/13.
 */
public class SeniorFilterChain<V> implements FilterChain<DataContainer, DataContainer<V>> {

    protected List<SeniorStatisticsFilter<V>> filters;

    protected int index = 0;

    public SeniorFilterChain() {
    }

    public SeniorFilterChain(List<SeniorStatisticsFilter<V>> filters) {
        this.filters = filters;
    }

    @Override
    public void doFilter(DataContainer go, DataContainer<V> back) throws Exception {
        if (this.filters == null || this.index == this.filters.size()) {
            this.index = 0;
            return;
        }
        this.filters.get(this.index++).doFilter(go, back, this);
    }

    public void setFilters(List<SeniorStatisticsFilter<V>> filters) {
        this.filters = filters;
    }
}
