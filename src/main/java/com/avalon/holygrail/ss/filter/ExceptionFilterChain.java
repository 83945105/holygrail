package com.avalon.holygrail.ss.filter;

import com.avalon.holygrail.filter.norm.FilterChain;
import com.avalon.holygrail.ss.view.ExceptionView;

import java.util.List;

/**
 * 异常过滤链
 * Created by 白超 on 2018-2-5.
 */
public class ExceptionFilterChain implements FilterChain<Throwable, ExceptionView> {

    private List<ExceptionFilter> filters;

    private int index = 0;

    @Override
    public void doFilter(Throwable go, ExceptionView back) throws Exception {
        if (this.filters == null || this.index == this.filters.size()) {
            return;
        }
        this.filters.get(index++).doFilter(go, back, this);
    }
}
