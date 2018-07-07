package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.Limit;
import com.avalon.holygrail.ss.norm.ResultInfo;

import java.util.Collection;
import java.util.Map;

/**
 * 异常视图
 */
public class ExceptionView extends ModelView {

    public ExceptionView(ResultInfo resultInfo) {
        super(resultInfo);
    }

    public ExceptionView(ResultInfo resultInfo, Collection<?> rows) {
        super(resultInfo, rows);
    }

    public ExceptionView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows) {
        super(resultInfo, total, currPage, pageSize, rows);
    }

    public ExceptionView(ResultInfo resultInfo, Limit limit, Collection<?> rows) {
        super(resultInfo, limit, rows);
    }

    public ExceptionView(ResultInfo resultInfo, Map<?, ?> records) {
        super(resultInfo, records);
    }

    public ExceptionView(ResultInfo resultInfo, Collection<?> rows, Map<?, ?> records) {
        super(resultInfo, rows, records);
    }

    public ExceptionView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Map<?, ?> records) {
        super(resultInfo, total, currPage, pageSize, rows, records);
    }

    public ExceptionView(ResultInfo resultInfo, Limit limit, Collection<?> rows, Map<?, ?> records) {
        super(resultInfo, limit, rows, records);
    }

    public ExceptionView(ResultInfo resultInfo, Object record) {
        super(resultInfo, record);
    }

    public ExceptionView(ResultInfo resultInfo, Collection<?> rows, Object record) {
        super(resultInfo, rows, record);
    }

    public ExceptionView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Object record) {
        super(resultInfo, total, currPage, pageSize, rows, record);
    }

    public ExceptionView(ResultInfo resultInfo, Limit limit, Collection<?> rows, Object record) {
        super(resultInfo, limit, rows, record);
    }

    public ExceptionView(ResultInfo resultInfo, Map<?, ?> records, Object record) {
        super(resultInfo, records, record);
    }

    public ExceptionView(ResultInfo resultInfo, Collection<?> rows, Map<?, ?> records, Object record) {
        super(resultInfo, rows, records, record);
    }

    public ExceptionView(ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Map<?, ?> records, Object record) {
        super(resultInfo, total, currPage, pageSize, rows, records, record);
    }

    public ExceptionView(ResultInfo resultInfo, Limit limit, Collection<?> rows, Map<?, ?> records, Object record) {
        super(resultInfo, limit, rows, records, record);
    }

    public ExceptionView(ResultInfo resultInfo, Map<?, ?> records, Limit limit) {
        super(resultInfo, records, limit);
    }
}
