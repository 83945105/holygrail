package pub.avalon.holygrail.response.views;

import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 分页视图
 *
 * @author 白超
 */
public class PageView extends MessageView {

    public static final String LIMIT_KEY = "limit";

    protected Limit limit;

    public PageView(ResultInfo resultInfo) {
        super(resultInfo);
    }

    public PageView(ResultInfo resultInfo, Limit limit) {
        super(resultInfo);
        this.limit = new PageViewLimit(limit.getTotal(), limit.getCurrentPage(), limit.getPageSize());
    }

    public PageView(ResultInfo resultInfo, int total, int currPageNum, int pageSize) {
        super(resultInfo);
        this.limit = new PageViewLimit(total, currPageNum, pageSize);
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    private final class PageViewLimit implements Limit {
        private int total;
        private int currentPage = 1;
        private int pageSize = 1;

        private PageViewLimit(int total, int currentPage, int pageSize) {
            this.setTotal(total);
            this.setCurrentPage(currentPage);
            this.setPageSize(pageSize);
        }

        @Override
        public Integer getTotal() {
            return this.total;
        }

        @Override
        public Integer getCurrentPage() {
            return this.currentPage;
        }

        @Override
        public Integer getPageSize() {
            return this.pageSize;
        }

        @Override
        public Integer getPageCount() {
            if (this.total % this.pageSize == 0) {
                return this.total / this.pageSize;
            } else if (total % pageSize > 0) {
                return this.total / this.pageSize + 1;
            } else {
                return 1;
            }
        }

        private void setTotal(int total) {
            this.total = total > 0 ? total : 0;
        }

        private void setCurrentPage(int currentPage) {
            this.currentPage = currentPage > 0 ? currentPage : 1;
        }

        private void setPageSize(int pageSize) {
            this.pageSize = pageSize > 0 ? pageSize : 1;
        }

    }

}
