package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pub.avalon.beans.DataBaseType;
import pub.avalon.beans.Limit;
import pub.avalon.beans.Pagination;
import pub.avalon.holygrail.response.beans.JacksonResultInfo;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.beans.ResultInfoRealization;
import pub.avalon.holygrail.response.beans.User;
import pub.avalon.holygrail.response.utils.DataViewUtil;
import pub.avalon.holygrail.response.utils.ResultUtil;
import pub.avalon.holygrail.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 白超
 * @date 2018/6/3
 */
public class JacksonView extends AbstractJsonView {

    private TreeNode treeNode;

    public JacksonView(TreeNode treeNode) {
        if (treeNode == null) {
            throw new RuntimeException("JacksonView Constructor arg treeNode is null.");
        }
        this.treeNode = treeNode;
    }

    private Integer code;
    private ResultInfo resultInfo;
    private String recordJson;
    private Map<String, Object> record;
    private Map<Type, Object> recordTypeReferenceCache;
    private Map<Class, Object> recordClassReferenceCache;
    private String recordsJson;
    private Map<String, Object> records;
    private Map<Type, Object> recordsTypeReferenceCache;
    private Map<Class, Object> recordsClassReferenceCache;
    private String rowsJson;
    private List<Map<String, Object>> rows;
    private Map<Type, Object> rowsTypeReferenceCache;
    private Map<Class, Object> rowsClassReferenceCache;
    private Limit limit;
    private String limitJson;
    private Map<Type, Object> limitTypeReferenceCache;
    private Map<Class, Object> limitClassReferenceCache;
    private final static TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {
    };
    private final static TypeReference<List<Map<String, Object>>> LIST_MAP_TYPE_REFERENCE = new TypeReference<List<Map<String, Object>>>() {
    };
    private final static TypeReference<JacksonPagination> LIMIT_TYPE_REFERENCE = new TypeReference<JacksonPagination>() {
    };

    @Override
    public Integer getCode() {
        if (this.code != null) {
            return this.code;
        }
        this.code = ((IntNode) this.treeNode.get(MessageView.CODE)).intValue();
        return this.code;
    }

    @Override
    public ResultInfo getResultInfo() {
        if (this.resultInfo != null) {
            return this.resultInfo;
        }
        this.resultInfo = new JacksonResultInfo(this.treeNode.get(MessageView.RESULT_INFO_PARAM));
        return this.resultInfo;
    }

    private void initRecordJson() {
        if (this.recordJson == null) {
            TreeNode treeNode = this.treeNode.get(ModelView.RECORD_KEY);
            if (treeNode instanceof ObjectNode) {
                this.recordJson = treeNode.toString();
            } else {
                this.recordJson = "{}";
            }
        }
    }

    @Override
    public Map<String, Object> getRecord() {
        if (this.record != null) {
            return this.record;
        }
        this.initRecordJson();
        this.record = JsonUtil.parseObject(this.recordJson, MAP_TYPE_REFERENCE);
        return this.record;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRecord(TypeReference<T> typeReference) {
        if (this.recordTypeReferenceCache == null) {
            this.recordTypeReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object record = this.recordTypeReferenceCache.get(typeReference.getType());
        if (record == null) {
            this.initRecordJson();
            record = JsonUtil.parseObject(this.recordJson, typeReference);
            this.recordTypeReferenceCache.put(typeReference.getType(), record);
        }
        return (T) record;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRecord(Class<T> clazz) {
        if (this.recordClassReferenceCache == null) {
            this.recordClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object record = this.recordClassReferenceCache.get(clazz);
        if (record == null) {
            this.initRecordJson();
            record = JsonUtil.parseObject(this.recordJson, clazz);
            this.recordClassReferenceCache.put(clazz, record);
        }
        return (T) record;
    }

    private void initRecordsJson() {
        if (this.recordsJson == null) {
            TreeNode treeNode = this.treeNode.get(ModelView.RECORDS_KEY);
            if (treeNode instanceof ObjectNode) {
                this.recordsJson = treeNode.toString();
            } else {
                this.recordsJson = "{}";
            }
        }
    }

    @Override
    public Map<String, Object> getRecords() {
        if (this.records != null) {
            return this.records;
        }
        this.initRecordsJson();
        this.records = JsonUtil.parseObject(this.recordsJson, MAP_TYPE_REFERENCE);
        return this.records;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRecords(TypeReference<T> typeReference) {
        if (this.recordsTypeReferenceCache == null) {
            this.recordsTypeReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object records = this.recordsTypeReferenceCache.get(typeReference.getType());
        if (records == null) {
            this.initRecordsJson();
            records = JsonUtil.parseObject(this.recordsJson, typeReference);
            this.recordsTypeReferenceCache.put(typeReference.getType(), records);
        }
        return (T) records;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRecords(Class<T> clazz) {
        if (this.recordsClassReferenceCache == null) {
            this.recordsClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object records = this.recordsClassReferenceCache.get(clazz);
        if (records == null) {
            this.initRecordsJson();
            records = JsonUtil.parseObject(this.recordsJson, clazz);
            this.recordsClassReferenceCache.put(clazz, records);
        }
        return (T) records;
    }

    private void initRowsJson() {
        if (this.rowsJson == null) {
            TreeNode treeNode = this.treeNode.get(LimitDataView.ROWS_KEY);
            if (treeNode instanceof ArrayNode) {
                this.rowsJson = treeNode.toString();
            } else {
                this.rowsJson = "[]";
            }
        }
    }

    @Override
    public List<Map<String, Object>> getRows() {
        if (this.rows != null) {
            return this.rows;
        }
        this.initRowsJson();
        this.rows = JsonUtil.parseObject(this.rowsJson, LIST_MAP_TYPE_REFERENCE);
        return this.rows;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRows(TypeReference<T> typeReference) {
        if (this.rowsTypeReferenceCache == null) {
            this.rowsTypeReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object rows = this.rowsTypeReferenceCache.get(typeReference.getType());
        if (rows == null) {
            this.initRowsJson();
            rows = JsonUtil.parseObject(this.rowsJson, typeReference);
            this.rowsTypeReferenceCache.put(typeReference.getType(), rows);
        }
        return (T) rows;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getRows(Class<T> clazz) {
        if (this.rowsClassReferenceCache == null) {
            this.rowsClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object rows = this.rowsClassReferenceCache.get(clazz);
        if (rows == null) {
            TreeNode treeNode = this.treeNode.get(LimitDataView.ROWS_KEY);
            if (treeNode instanceof ArrayNode) {
                rows = new ArrayList<>(treeNode.size());
                for (JsonNode jsonNode : (ArrayNode) treeNode) {
                    ((ArrayList) rows).add(JsonUtil.parseObject(jsonNode.toString(), clazz));
                }
                this.rowsClassReferenceCache.put(clazz, rows);
            } else {
                return new ArrayList<>(0);
            }
        }
        return (List<T>) rows;
    }

    private void initLimitJson() {
        if (this.limitJson == null) {
            TreeNode treeNode = this.treeNode.get(LimitView.LIMIT_KEY);
            if (treeNode instanceof ObjectNode) {
                this.limitJson = treeNode.toString();
            } else {
                this.limitJson = "{}";
            }
        }
    }

    @Override
    public Limit getLimit() {
        if (this.limit != null) {
            return this.limit;
        }
        this.initLimitJson();
        this.limit = JsonUtil.parseObject(this.limitJson, LIMIT_TYPE_REFERENCE);
        return this.limit;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Limit> T getLimit(TypeReference<T> typeReference) {
        if (this.limitTypeReferenceCache == null) {
            this.limitTypeReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object limit = this.limitTypeReferenceCache.get(typeReference.getType());
        if (limit == null) {
            this.initLimitJson();
            limit = JsonUtil.parseObject(this.limitJson, typeReference);
            this.limitTypeReferenceCache.put(typeReference.getType(), limit);
        }
        return (T) limit;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Limit> T getLimit(Class<T> clazz) {
        if (this.limitClassReferenceCache == null) {
            this.limitClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object limit = this.limitClassReferenceCache.get(clazz);
        if (limit == null) {
            this.initLimitJson();
            limit = JsonUtil.parseObject(this.limitJson, clazz);
            this.limitClassReferenceCache.put(clazz, limit);
        }
        return (T) limit;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId("666");
        List<User> list = new ArrayList<>();
        list.add(user);
        Pagination pagination = new Pagination(DataBaseType.MYSQL, 100, 1, 20);
        DataView dataView = DataViewUtil.getModelViewSuccess(10, "666", list, pagination);
        ResultInfoRealization rr = (ResultInfoRealization) dataView.getResultInfo();
        rr.addResultDetail(() -> ResultUtil.createError("666"));
        String json = JsonUtil.toJsonString(dataView);
        DataView dv = JsonUtil.parseObject(json, new TypeReference<DataView>() {
        });
        long begin = System.nanoTime();

        if (dv instanceof JacksonView) {

            Limit limit = ((JacksonView) dv).getLimit(JacksonPagination.class);
            System.out.println(limit);

        }

        long use = System.nanoTime() - begin;
        System.out.println(use + " = " + use / (1000 * 1000));
    }

    private static class JacksonPagination implements Limit {

        private Integer total;

        private Integer currentPage;

        private Integer pageSize;

        private Integer pageCount;

        @Override
        public Integer getTotal() {
            return total;
        }

        @Override
        public void setTotal(Integer total) {
            this.total = total;
        }

        @Override
        public Integer getCurrentPage() {
            return currentPage;
        }

        @Override
        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        @Override
        public Integer getPageSize() {
            return pageSize;
        }

        @Override
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
