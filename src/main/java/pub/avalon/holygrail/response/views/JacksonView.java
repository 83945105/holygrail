package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.JacksonResultInfo;
import pub.avalon.holygrail.response.beans.ResultInfo;
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
public class JacksonView implements JsonView {

    private TreeNode treeNode;

    public JacksonView(TreeNode treeNode) {
        if (treeNode == null) {
            throw new RuntimeException("JacksonView Constructor arg treeNode is null.");
        }
        this.treeNode = treeNode;
    }

    private Integer code;
    private ResultInfo resultInfo;
    private Object record;
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

    @Override
    public Object getRecord() {
        if (this.record != null) {
            return this.record;
        }
        TreeNode treeNode = this.treeNode.get(ModelView.RECORD_KEY);
        if (treeNode == null || treeNode instanceof NullNode) {
            return null;
        }
        if (treeNode instanceof TextNode) {
            this.record = ((TextNode) treeNode).textValue();
        } else if (treeNode instanceof BooleanNode) {
            this.record = ((BooleanNode) treeNode).booleanValue();
        } else if (treeNode instanceof IntNode) {
            this.record = ((IntNode) treeNode).intValue();
        } else if (treeNode instanceof LongNode) {
            this.record = ((LongNode) treeNode).longValue();
        } else if (treeNode instanceof DoubleNode) {
            this.record = ((DoubleNode) treeNode).doubleValue();
        } else if (treeNode instanceof FloatNode) {
            this.record = ((FloatNode) treeNode).floatValue();
        } else if (treeNode instanceof BigIntegerNode) {
            this.record = ((BigIntegerNode) treeNode).bigIntegerValue();
        } else if (treeNode instanceof DecimalNode) {
            this.record = ((DecimalNode) treeNode).decimalValue();
        } else if (treeNode instanceof ShortNode) {
            this.record = ((ShortNode) treeNode).shortValue();
        } else {
            this.record = treeNode.toString();
        }
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
            record = getRecord();
            if (record == null) {
                return null;
            }
            if (record.getClass() == typeReference.getType()) {
                return (T) record;
            }
            record = JsonUtil.parseObject(record.toString(), typeReference);
            this.recordTypeReferenceCache.put(typeReference.getType(), record);
        }
        return (T) record;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRecord(Class<T> returnType) {
        if (this.recordClassReferenceCache == null) {
            this.recordClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object record = this.recordClassReferenceCache.get(returnType);
        if (record == null) {
            record = getRecord();
            if (record == null) {
                return null;
            }
            if (record.getClass() == returnType) {
                return (T) record;
            }
            record = JsonUtil.parseObject(record.toString(), returnType);
            this.recordClassReferenceCache.put(returnType, record);
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
        if (this.recordsJson == null) {
            return null;
        }
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
            if (this.recordsJson == null) {
                return null;
            }
            records = JsonUtil.parseObject(this.recordsJson, typeReference);
            this.recordsTypeReferenceCache.put(typeReference.getType(), records);
        }
        return (T) records;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRecords(Class<T> returnType) {
        if (this.recordsClassReferenceCache == null) {
            this.recordsClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object records = this.recordsClassReferenceCache.get(returnType);
        if (records == null) {
            this.initRecordsJson();
            if (this.recordsJson == null) {
                return null;
            }
            records = JsonUtil.parseObject(this.recordsJson, returnType);
            this.recordsClassReferenceCache.put(returnType, records);
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
        if (this.rowsJson == null) {
            return null;
        }
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
            if (this.rowsJson == null) {
                return null;
            }
            rows = JsonUtil.parseObject(this.rowsJson, typeReference);
            this.rowsTypeReferenceCache.put(typeReference.getType(), rows);
        }
        return (T) rows;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getRows(Class<T> beanType) {
        if (this.rowsClassReferenceCache == null) {
            this.rowsClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object rows = this.rowsClassReferenceCache.get(beanType);
        if (rows == null) {
            TreeNode treeNode = this.treeNode.get(LimitDataView.ROWS_KEY);
            if (treeNode instanceof ArrayNode) {
                rows = new ArrayList<>(treeNode.size());
                for (JsonNode jsonNode : (ArrayNode) treeNode) {
                    ((ArrayList) rows).add(JsonUtil.parseObject(jsonNode.toString(), beanType));
                }
                this.rowsClassReferenceCache.put(beanType, rows);
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
        if (this.limitJson == null) {
            return null;
        }
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
            if (this.limitJson == null) {
                return null;
            }
            limit = JsonUtil.parseObject(this.limitJson, typeReference);
            this.limitTypeReferenceCache.put(typeReference.getType(), limit);
        }
        return (T) limit;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Limit> T getLimit(Class<T> returnType) {
        if (this.limitClassReferenceCache == null) {
            this.limitClassReferenceCache = new ConcurrentHashMap<>(1);
        }
        Object limit = this.limitClassReferenceCache.get(returnType);
        if (limit == null) {
            this.initLimitJson();
            if (this.limitJson == null) {
                return null;
            }
            limit = JsonUtil.parseObject(this.limitJson, returnType);
            this.limitClassReferenceCache.put(returnType, limit);
        }
        return (T) limit;
    }

    private static class JacksonPagination implements Limit {

        private Long total;

        private Long currentPage;

        private Long pageSize;

        private Long pageCount;

        @Override
        public long getTotal() {
            return total;
        }

        @Override
        public void setTotal(Long total) {
            this.total = total;
        }

        @Override
        public long getCurrentPage() {
            return currentPage;
        }

        @Override
        public void setCurrentPage(Long currentPage) {
            this.currentPage = currentPage;
        }

        @Override
        public long getPageSize() {
            return pageSize;
        }

        @Override
        public void setPageSize(Long pageSize) {
            this.pageSize = pageSize;
        }

        @Override
        public long getPageCount() {
            return pageCount;
        }

        public void setPageCount(Long pageCount) {
            this.pageCount = pageCount;
        }
    }

}
