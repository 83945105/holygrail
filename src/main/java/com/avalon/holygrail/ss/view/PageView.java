package com.avalon.holygrail.ss.view;

import com.avalon.holygrail.ss.norm.Limit;
import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 分页视图
 */
public class PageView extends MessageView {

    protected PageViewLimit limit;

    public PageView(ResultInfo resultInfo) {
        super(resultInfo);
    }

    public PageView(int total, int currPageNum, int pageSize, int totalPage) {
        this.limit = new PageViewLimit(total, currPageNum, pageSize, totalPage);
    }

    public PageView(Limit limit) {
        this.limit = new PageViewLimit(limit);
    }

    public PageView(ResultInfo resultInfo, int total, int currPageNum, int pageSize, int totalPage) {
        super(resultInfo);
        this.limit = new PageViewLimit(total, currPageNum, pageSize, totalPage);
    }

    public PageView(ResultInfo resultInfo, Limit limit) {
        super(resultInfo);
        this.limit = new PageViewLimit(limit);
    }

    public Limit getLimit() {
        return this.limit;
    }

    public void setLimit(Limit limit) {
        this.limit = new PageViewLimit(limit);
    }

    private static class PageViewLimit implements Limit {

        private int total;
        private int currPageNum;
        private int pageSize;
        private int totalPage;

        public PageViewLimit(Limit limit) {
            this.total = limit.getTotal();
            this.currPageNum = limit.getCurrPageNum();
            this.pageSize = limit.getPageSize();
            this.totalPage = limit.getTotalPage();
        }

        public PageViewLimit(int total, int currPageNum, int pageSize, int totalPage) {
            this.total = total;
            this.currPageNum = currPageNum;
            this.pageSize = pageSize;
            this.totalPage = totalPage;
        }

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

        @Override
        public String getSql() {
            return null;
        }
    }
}
