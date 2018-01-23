package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.Limit;
import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 分页视图
 */
public class PageView extends MessageView {

    protected Limit limit;

    public PageView(ResultInfo resultInfo) {
        super(resultInfo);
    }

    public PageView(int total, int currPageNum, int pageSize, int totalPage) {
        this.limit = new Limit() {
            @Override
            public int getTotal() {
                return total;
            }

            @Override
            public int getCurrPageNum() {
                return currPageNum;
            }

            @Override
            public int getPageSize() {
                return pageSize;
            }

            @Override
            public int getTotalPage() {
                return totalPage;
            }
        };
    }

    public PageView(Limit limit) {
        this.limit = limit;
    }

    public PageView(ResultInfo resultInfo, int total, int currPageNum, int pageSize, int totalPage) {
        super(resultInfo);
        this.limit = new Limit() {
            @Override
            public int getTotal() {
                return total;
            }

            @Override
            public int getCurrPageNum() {
                return currPageNum;
            }

            @Override
            public int getPageSize() {
                return pageSize;
            }

            @Override
            public int getTotalPage() {
                return totalPage;
            }
        };
    }

    public PageView(ResultInfo resultInfo, Limit limit) {
        super(resultInfo);
        this.limit = limit;
    }

    public Limit getLimit() {
        return this.limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }
}
