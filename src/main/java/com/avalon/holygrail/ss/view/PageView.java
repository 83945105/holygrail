package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.bean.PageSupport;
import com.avalon.holygrail.ss.norm.Limit;
import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 分页视图
 */
public class PageView extends MessageView {

    public static final String LIMIT_KEY = "limit";

    protected Limit limit;

    public PageView(ResultInfo resultInfo) {
        super(resultInfo);
    }

    public PageView(ResultInfo resultInfo, Limit limit) {
        super(resultInfo);
        this.limit = limit;
    }

    public PageView(ResultInfo resultInfo, int total, int currPageNum, int pageSize) {
        super(resultInfo);
        this.limit = new PageSupport(total, currPageNum, pageSize);
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

}
