package com.avalon.holygrail.filter.norm;

/**
 * 过滤器链
 * Created by 白超 on 2018-2-5.
 */
public interface FilterChain<T, V> {

    void doFilter(T go, V back) throws Exception;
}
