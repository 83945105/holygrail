package com.avalon.holygrail.filter.model;

import com.avalon.holygrail.filter.norm.Filter;
import com.avalon.holygrail.filter.norm.FilterChain;

import java.util.List;

/**
 * 过滤链实现
 * Created by 白超 on 2018/2/8.
 */
public abstract class FilterChainAbstract<T, V> implements FilterChain<T, V> {

    protected List<Filter<T, V>> filters;

    protected int index = 0;

    public FilterChainAbstract() {
    }

    public FilterChainAbstract(List<Filter<T, V>> filters) {
        this.filters = filters;
    }

    public FilterChainAbstract(List<Filter<T, V>> filters, int index) {
        this.filters = filters;
        this.index = index;
    }

    @Override
    public void doFilter(T go, V back) throws Exception {
        if(this.filters == null || this.index == this.filters.size()) {
            return;
        }
        this.filters.get(index++).doFilter(go, back, this);
    }

    public List<Filter<T, V>> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter<T, V>> filters) {
        this.filters = filters;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
