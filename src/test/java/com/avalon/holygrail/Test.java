package com.avalon.holygrail;

import com.avalon.holygrail.ss.filter.ExceptionFilter;
import com.avalon.holygrail.ss.filter.ExceptionFilterChain;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) {

        ExceptionFilterChain filterChain = new ExceptionFilterChain();


        filterChain.doFilter(null, null);
    }
}
