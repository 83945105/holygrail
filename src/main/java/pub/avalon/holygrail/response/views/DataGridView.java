package pub.avalon.holygrail.response.views;

import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * EasyUI DataGrid表格视图
 *
 * @author 白超
 */
public class DataGridView extends LimitDataView {

    /**
     * 脚注
     */
    private Collection<?> footer = new ArrayList<>();

    public DataGridView(Integer code, ResultInfo resultInfo) {
        super(code, resultInfo);
    }

    public DataGridView(Integer code, ResultInfo resultInfo, Collection<?> rows) {
        super(code, resultInfo, rows);
    }

    public DataGridView(Integer code, ResultInfo resultInfo, long total, long currentPage, long pageSize, Collection<?> rows) {
        super(code, resultInfo, total, currentPage, pageSize, rows);
    }

    public DataGridView(Integer code, ResultInfo resultInfo, Limit limit, Collection<?> rows) {
        super(code, resultInfo, limit, rows);
    }

    public DataGridView(Integer code, ResultInfo resultInfo, Collection<?> rows, Collection<?> footer) {
        super(code, resultInfo, rows);
        this.footer = footer;
    }

    public DataGridView(Integer code, ResultInfo resultInfo, long total, long currentPage, long pageSize, Collection<?> rows, Collection<?> footer) {
        super(code, resultInfo, total, currentPage, pageSize, rows);
        this.footer = footer;
    }

    public DataGridView(Integer code, ResultInfo resultInfo, Limit limit, Collection<?> rows, Collection<?> footer) {
        super(code, resultInfo, limit, rows);
        this.footer = footer;
    }

    public Collection<?> getFooter() {
        return footer;
    }

    /**
     * 脚注
     */
    public void setFooter(Collection<?> footer) {
        this.footer = footer;
    }

}
