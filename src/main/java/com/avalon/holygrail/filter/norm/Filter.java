package com.avalon.holygrail.filter.norm;

/**
 * 过滤器
 * Created by 白超 on 2018-2-5.
 */
public interface Filter<T, V> {

    void doFilter(T go, V back, FilterChain filterChain);
}
