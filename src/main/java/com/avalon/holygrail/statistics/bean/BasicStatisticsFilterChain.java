package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.filter.norm.FilterChain;
import com.avalon.holygrail.statistics.model.BasicStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;

import java.util.List;

/**
 * 统计过滤链
 * Created by 白超 on 2018-3-12.
 */
public class BasicStatisticsFilterChain<T, V> implements FilterChain<T, DataContainer<V>> {

    protected List<BasicStatisticsFilter<T, V>> filters;

    protected int index = 0;

    public BasicStatisticsFilterChain() {
    }

    public BasicStatisticsFilterChain(List<BasicStatisticsFilter<T, V>> filters) {
        this.filters = filters;
    }

    @Override
    public void doFilter(T go, DataContainer<V> back) throws Exception {
        if (this.filters == null || this.index == this.filters.size()) {
            this.index = 0;
            return;
        }
        this.filters.get(this.index++).doFilter(go, back, this);
    }

    public void setFilters(List<BasicStatisticsFilter<T, V>> filters) {
        this.filters = filters;
    }
}
