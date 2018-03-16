package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.filter.norm.FilterChain;
import com.avalon.holygrail.statistics.model.FinalStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;

import java.util.List;

/**
 * 最终统计过滤链
 * Created by 白超 on 2018-3-13.
 */
public class FinalFilterChain<V> implements FilterChain<DataContainer, DataContainer<V>> {

    protected List<FinalStatisticsFilter<V>> filters;

    protected int index = 0;

    public FinalFilterChain() {
    }

    public FinalFilterChain(List<FinalStatisticsFilter<V>> filters) {
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

    public void setFilters(List<FinalStatisticsFilter<V>> filters) {
        this.filters = filters;
    }
}
