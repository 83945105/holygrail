package pub.avalon.holygrail.response.views;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.JsonResultInfo;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.utils.JsonUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
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
    public ResultInfo getResultInfo() {
        if (this.resultInfo == null) {
            Object resultInfo = this.get(MessageView.RESULT_INFO_PARAM);
            if (resultInfo == null) {
                return null;
            }
            if (!(resultInfo instanceof Map)) {
                return null;
            }
            JsonResultInfo jsonResultInfo = new JsonResultInfo();
            for (Object o : ((Map) resultInfo).entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                jsonResultInfo.put(String.valueOf(entry.getKey()), entry.getValue());
            }
            this.resultInfo = jsonResultInfo;
        }
        return this.resultInfo;
    }

    public Object getRecord() {
        return this.get(ModelView.RECORD_KEY);
    }

    public <T> T getRecord(Class<T> clazz) {
        Object obj = getRecord();
        if (obj == null) {
            return null;
        }
        return TypeUtils.cast(obj, clazz, ParserConfig.getGlobalInstance());
    }

    public Map<?, ?> getRecords() {
        Object records = this.get(ModelView.RECORDS_KEY);
        if (records instanceof Map) {
            return (Map<?, ?>) records;
        }
        return null;
    }

    public <T> T getRecords(Class<T> clazz) {
        Map<?, ?> records = getRecords();
        return TypeUtils.cast(records, clazz, ParserConfig.getGlobalInstance());
    }

    public <T> T getRecords(TypeReference<T> typeReference) {
        Map<?, ?> records = getRecords();
        return JsonUtil.parseObject("", typeReference);
    }

    public static void main(String[] args) {
        TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {
        };
        Map<String, Integer> map = new JsonView().getRecords(typeReference);
    }

    public Collection<?> getRows() {
        return (Collection<?>) this.get(LimitDataView.ROWS_KEY);
    }

    public <T> Collection<T> getRows(Function<Object, T> formatterRow) {
        Collection<?> rows = getRows();
        if (rows == null) {
            return new ArrayList<>(0);
        }
        ArrayList<T> list = new ArrayList<>(rows.size());
        rows.forEach(obj -> list.add(formatterRow.apply(obj)));
        return list;
    }

    public <T> Collection<T> getRows(Class<T> clazz) {
        return getRows(row -> TypeUtils.cast(row, clazz, ParserConfig.getGlobalInstance()));
    }

    public Limit getLimit() {
        if (this.limit == null) {
            Object obj = this.get(LimitView.LIMIT_KEY);
            if (obj == null) {
                return null;
            }
            this.limit = TypeUtils.cast(obj, JsonPagination.class, ParserConfig.getGlobalInstance());
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

    private static class JsonPagination implements Limit {

        private Integer total;

        private Integer currentPage;

        private Integer pageSize;

        private Integer pageCount;

        @Override
        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        @Override
        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        @Override
        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        @Override
        public Integer getPageCount() {
            return pageCount;
        }

        public void setPageCount(Integer pageCount) {
            this.pageCount = pageCount;
        }
    }

}
