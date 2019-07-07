package pub.avalon.holygrail.response.views;

import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 分页视图
 *
 * @author 白超
 */
public class LimitView extends MessageView {

    public static final String LIMIT_KEY = "limit";

    protected Limit limit;

    public LimitView(Integer code, ResultInfo resultInfo) {
        super(code, resultInfo);
    }

    public LimitView(Integer code, ResultInfo resultInfo, Limit limit) {
        super(code, resultInfo);
        this.limit = new PageViewLimit(limit.getTotal(), limit.getCurrentPage(), limit.getPageSize());
    }

    public LimitView(Integer code, ResultInfo resultInfo, long total, long currPageNum, long pageSize) {
        super(code, resultInfo);
        this.limit = new PageViewLimit(total, currPageNum, pageSize);
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    private final class PageViewLimit implements Limit {
        private long total;
        private long currentPage = 1L;
        private long pageSize = 1L;

        private PageViewLimit(long total, long currentPage, long pageSize) {
            this.setTotal(total);
            this.setCurrentPage(currentPage);
            this.setPageSize(pageSize);
        }

        @Override
        public long getTotal() {
            return this.total;
        }

        @Override
        public void setTotal(Long total) {
            this.total = (total == null || total <= 0) ? 0 : total;
        }

        @Override
        public long getCurrentPage() {
            return this.currentPage;
        }

        @Override
        public void setCurrentPage(Long currentPage) {
            this.currentPage = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
        }

        @Override
        public long getPageSize() {
            return this.pageSize;
        }

        @Override
        public void setPageSize(Long pageSize) {
            this.pageSize = (pageSize == null || pageSize <= 0) ? 1 : pageSize;
        }

        @Override
        public long getPageCount() {
            if (this.total <= 0) {
                return 1L;
            }
            if (this.total % this.pageSize == 0) {
                return this.total / this.pageSize;
            }
            if (total % pageSize > 0) {
                return this.total / this.pageSize + 1;
            }
            return 1L;
        }

    }

}
