package com.avalon.holygrail.ss.filter;

import com.avalon.holygrail.filter.norm.FilterChain;
import com.avalon.holygrail.ss.util.ResultUtil;
import com.avalon.holygrail.ss.view.ExceptionView;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 默认异常过滤器
 * Created by 白超 on 2018-2-6.
 */
public class DefaultExceptionFilter extends ExceptionFilter {

    /**
     * 主键重复默认异常信息
     */
    public static final String DEFAULT_DUPLICATE_KEY_MESSAGE = "数据已存在,无法完成相关操作";
    /**
     * 违反数据完成性默认异常信息
     */
    public static final String DEFAULT_DATA_INTEGRITY_VIOLATION_MESSAGE = "您输入的字段过长,请重新输入";

    @Override
    public void doFilter(Throwable go, ExceptionView back, FilterChain filterChain) throws Exception {
        if (go instanceof UndeclaredThrowableException) {
            go = ((UndeclaredThrowableException) go).getUndeclaredThrowable();
        }
        if (go instanceof DuplicateKeyException) {
            back.setResultInfo(ResultUtil.createFail(DEFAULT_DUPLICATE_KEY_MESSAGE));
            filterChain.doFilter(go, back);
            return;
        }
        if (go instanceof DataIntegrityViolationException) {
            back.setResultInfo(ResultUtil.createFail(DEFAULT_DATA_INTEGRITY_VIOLATION_MESSAGE));
            filterChain.doFilter(go, back);
            return;
        }
        filterChain.doFilter(go, back);
    }
}
