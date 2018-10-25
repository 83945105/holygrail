package pub.avalon.holygrail.response.views;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import pub.avalon.beans.Pagination;
import pub.avalon.holygrail.response.beans.JsonResultInfo;
import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;

import java.util.*;
import java.util.function.Function;

/**
 * @author 白超
 * @date 2018/6/3
 */
public class JsonView extends LinkedHashMap<String, Object> implements DataView {

    private ResultInfo resultInfo;
    private Limit limit;

    @Override
    public Integer getCode() {
        Object code = this.get(MessageView.CODE);
        return code == null ? null : Integer.parseInt(code.toString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResultInfo getResultInfo() {
        if (this.resultInfo == null) {
            Map<String, Object> map = (Map) this.get(MessageView.RESULT_INFO_PARAM);
            if (map == null) {
                return null;
            }
            JsonResultInfo resultInfo = new JsonResultInfo();
            resultInfo.putAll(map);
            this.resultInfo = resultInfo;
        }
        return this.resultInfo;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getRecord() {
        return (Map<String, Object>) this.get(ModelView.RECORD_KEY);
    }

    public <T> T getRecord(Class<T> clazz) {
        Object obj = this.get(ModelView.RECORD_KEY);
        if (obj == null) {
            return null;
        }
        return TypeUtils.cast(obj, clazz, ParserConfig.getGlobalInstance());
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getRecords() {
        Map<String, Object> records = (Map<String, Object>) this.get(ModelView.RECORDS_KEY);
        if (records == null) {
            return new HashMap<>(0);
        }
        return records;
    }

    @SuppressWarnings("unchecked")
    public Collection<Map<String, Object>> getRows() {
        return (Collection<Map<String, Object>>) this.get(LimitDataView.ROWS_KEY);
    }

    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> getRows(Function<Map<String, Object>, T> formatterRow) {
        Collection<Map<String, Object>> rows = (Collection<Map<String, Object>>) this.get(LimitDataView.ROWS_KEY);
        if (rows == null) {
            return new ArrayList<>(0);
        }
        ArrayList<T> results = new ArrayList<>(rows.size());
        rows.forEach(row -> results.add(formatterRow.apply(row)));
        return results;
    }

    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> getRows(Class<T> clazz) {
        return getRows(row -> TypeUtils.cast(row, clazz, ParserConfig.getGlobalInstance()));
    }

    public Limit getLimit() {
        if (this.limit == null) {
            Object obj = this.get(LimitView.LIMIT_KEY);
            if (obj == null) {
                return null;
            }
            this.limit = TypeUtils.cast(obj, Pagination.class, ParserConfig.getGlobalInstance());
        }
        return this.limit;
    }

    public <T extends Limit> T getLimit(Class<T> clazz) {
        Object obj = this.get(LimitView.LIMIT_KEY);
        if (obj == null) {
            return null;
        }
        return TypeUtils.cast(obj, clazz, ParserConfig.getGlobalInstance());
    }
}
