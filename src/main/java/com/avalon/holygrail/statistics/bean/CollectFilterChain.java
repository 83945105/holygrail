package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.filter.norm.FilterChain;
import com.avalon.holygrail.statistics.model.CollectStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;

import java.util.Collection;
import java.util.List;

/**
 * 汇总过滤链
 * Created by 白超 on 2018-3-13.
 */
public class CollectFilterChain<V> implements FilterChain<Collection<DataContainer>, DataContainer<V>> {

    protected List<CollectStatisticsFilter<V>> filters;

    protected int index = 0;

    public CollectFilterChain() {
    }

    public CollectFilterChain(List<CollectStatisticsFilter<V>> filters) {
        this.filters = filters;
    }

    @Override
    public void doFilter(Collection<DataContainer> go, DataContainer<V> back) throws Exception {
        if (this.filters == null || this.index == this.filters.size()) {
            this.index = 0;
            return;
        }
        this.filters.get(this.index++).doFilter(go, back, this);
    }

    public void setFilters(List<CollectStatisticsFilter<V>> filters) {
        this.filters = filters;
    }
}
