package pub.avalon.holygrail.response.views;

import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页数据视图
 *
 * @author 白超
 */
public class LimitDataView extends LimitView {

    public static final String ROWS_KEY = "rows";

    /**
     * 结果集
     */
    private Collection<?> rows = new ArrayList<>();

    public LimitDataView(Integer code, ResultInfo resultInfo) {
        super(code, resultInfo);
    }

    public LimitDataView(Integer code, ResultInfo resultInfo, Limit limit) {
        super(code, resultInfo, limit);
    }

    public LimitDataView(Integer code, ResultInfo resultInfo, Collection<?> rows) {
        super(code, resultInfo);
        this.rows = rows;
    }

    public LimitDataView(Integer code, ResultInfo resultInfo, Limit limit, Collection<?> rows) {
        super(code, resultInfo, limit);
        this.rows = rows;
    }

    public LimitDataView(Integer code, ResultInfo resultInfo, long total, long currentPage, long pageSize, Collection<?> rows) {
        super(code, resultInfo, total, currentPage, pageSize);
        this.rows = rows;
    }

    public Collection<?> getRows() {
        return rows;
    }

    /**
     * 结果集
     */
    public void setRows(Collection<?> rows) {
        this.rows = rows;
    }
}
