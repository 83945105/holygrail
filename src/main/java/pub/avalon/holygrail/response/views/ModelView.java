package pub.avalon.holygrail.response.views;

import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 模型视图
 *
 * @author 白超
 */
public class ModelView extends LimitDataView {

    public static final String RECORD_KEY = "record";
    public static final String RECORDS_KEY = "records";

    protected Object record = new HashMap<>();

    protected Map<?, ?> records = new HashMap<>();

    public ModelView(Integer code, ResultInfo resultInfo) {
        super(code, resultInfo);
    }

    public ModelView(Integer code, ResultInfo resultInfo, Collection<?> rows) {
        super(code, resultInfo, rows);
    }

    public ModelView(Integer code, ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows) {
        super(code, resultInfo, total, currPage, pageSize, rows);
    }

    public ModelView(Integer code, ResultInfo resultInfo, Limit limit, Collection<?> rows) {
        super(code, resultInfo, limit, rows);
    }

    public ModelView(Integer code, ResultInfo resultInfo, Map<?, ?> records) {
        super(code, resultInfo);
        this.records = records;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Collection<?> rows, Map<?, ?> records) {
        super(code, resultInfo, rows);
        this.records = records;
    }

    public ModelView(Integer code, ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Map<?, ?> records) {
        super(code, resultInfo, total, currPage, pageSize, rows);
        this.records = records;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Limit limit, Collection<?> rows, Map<?, ?> records) {
        super(code, resultInfo, limit, rows);
        this.records = records;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Object record) {
        super(code, resultInfo);
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Collection<?> rows, Object record) {
        super(code, resultInfo, rows);
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Object record) {
        super(code, resultInfo, total, currPage, pageSize, rows);
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Limit limit, Collection<?> rows, Object record) {
        super(code, resultInfo, limit, rows);
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Map<?, ?> records, Object record) {
        super(code, resultInfo);
        this.records = records;
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Collection<?> rows, Map<?, ?> records, Object record) {
        super(code, resultInfo, rows);
        this.records = records;
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, int total, int currPage, int pageSize, Collection<?> rows, Map<?, ?> records, Object record) {
        super(code, resultInfo, total, currPage, pageSize, rows);
        this.records = records;
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Limit limit, Collection<?> rows, Map<?, ?> records, Object record) {
        super(code, resultInfo, limit, rows);
        this.records = records;
        this.record = record;
    }

    public ModelView(Integer code, ResultInfo resultInfo, Map<?, ?> records, Limit limit) {
        super(code, resultInfo, limit);
        this.records = records;
    }

    public Map<?, ?> getRecords() {
        return records;
    }

    public void setRecords(Map<?, ?> records) {
        this.records = records;
    }

    public Object getRecord() {
        return record;
    }

    public void setRecord(Object record) {
        this.record = record;
    }
}
