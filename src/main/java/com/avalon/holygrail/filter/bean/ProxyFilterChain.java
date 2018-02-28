package com.avalon.holygrail.filter.bean;

import com.avalon.holygrail.filter.norm.Filter;
import com.avalon.holygrail.filter.norm.FilterChain;

import java.util.List;

/**
 * 代理过滤器链
 * Created by 白超 on 2018-2-5.
 */
public class ProxyFilterChain<T, V> implements FilterChain<T, V> {

    protected FilterChain filterChain;

    protected List<Filter> filters;

    protected int index = 0;

    public ProxyFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public ProxyFilterChain(FilterChain filterChain, List<Filter> filters) {
        if (filterChain == null) {
            throw new NullPointerException("原始 FilterChain 不能为空");
        }
        this.filterChain = filterChain;
        this.filters = filters;
    }

    @Override
    public void doFilter(T go, V back) throws Exception {
        if (this.filters == null || this.index == this.filters.size()) {
            this.filterChain.doFilter(go, back);
            return;
        }
        this.filters.get(this.index++).doFilter(go, back, this);
    }
}
