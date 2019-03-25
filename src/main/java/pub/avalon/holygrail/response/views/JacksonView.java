package pub.avalon.holygrail.response.views;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.IntNode;
import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.JacksonResultInfo;
import pub.avalon.holygrail.response.beans.ResultDetail;
import pub.avalon.holygrail.response.beans.ResultInfo;
import pub.avalon.holygrail.response.beans.ResultInfoRealization;
import pub.avalon.holygrail.response.utils.DataViewUtil;
import pub.avalon.holygrail.response.utils.ResultUtil;
import pub.avalon.holygrail.utils.JsonUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

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
    private Limit limit;

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
//        return this.get(ModelView.RECORD_KEY);
        return null;
    }

    @Override
    public <T> T getRecord(Class<T> clazz) {
        Object obj = getRecord();
        if (obj == null) {
            return null;
        }
        return TypeUtils.cast(obj, clazz, ParserConfig.getGlobalInstance());
    }

    @Override
    public Map<?, ?> getRecords() {
/*        Object records = this.get(ModelView.RECORDS_KEY);
        if (records instanceof Map) {
            return (Map<?, ?>) records;
        }
        return null;*/
        return null;
    }

    @Override
    public <T> T getRecords(Class<T> clazz) {
        Map<?, ?> records = getRecords();
        return TypeUtils.cast(records, clazz, ParserConfig.getGlobalInstance());
    }

    @Override
    public <T> T getRecords(TypeReference<T> typeReference) {
        Map<?, ?> records = getRecords();
        return JsonUtil.parseObject("", typeReference);
    }

    public static void main(String[] args) {
        DataView dataView = DataViewUtil.getModelViewSuccess(10, "666", Collections.singletonMap("id", 111));
        ResultInfoRealization rr = (ResultInfoRealization) dataView.getResultInfo();
        rr.addResultDetail(new ResultDetail() {
            @Override
            public ResultInfo getResultInfo() {
                return ResultUtil.createError("");
            }
        });
        String json = JsonUtil.toJsonString(dataView);
        DataView dv = JsonUtil.parseObject(json, new TypeReference<DataView>() {
        });
        long begin = System.nanoTime();

        ResultInfo resultInfo = dv.getResultInfo();

        resultInfo = resultInfo.getResultDetails().iterator().next().getResultInfo();
        System.out.println(resultInfo.getResultCode());

        long use = System.nanoTime() - begin;
        System.out.println(use + " = " + use / (1000 * 1000));
    }

    @Override
    public Collection<?> getRows() {
//        return (Collection<?>) this.get(LimitDataView.ROWS_KEY);
        return null;
    }

    @Override
    public <T> Collection<T> getRows(Function<Object, T> formatterRow) {
        Collection<?> rows = getRows();
        if (rows == null) {
            return new ArrayList<>(0);
        }
        ArrayList<T> list = new ArrayList<>(rows.size());
        rows.forEach(obj -> list.add(formatterRow.apply(obj)));
        return list;
    }

    @Override
    public <T> Collection<T> getRows(Class<T> clazz) {
        return getRows(row -> TypeUtils.cast(row, clazz, ParserConfig.getGlobalInstance()));
    }

    @Override
    public Limit getLimit() {
/*        if (this.limit == null) {
            Object obj = this.get(LimitView.LIMIT_KEY);
            if (obj == null) {
                return null;
            }
            this.limit = TypeUtils.cast(obj, JsonPagination.class, ParserConfig.getGlobalInstance());
        }
        return this.limit;*/
        return null;
    }

    @Override
    public <T extends Limit> T getLimit(Class<T> clazz) {
/*        Object obj = this.get(LimitView.LIMIT_KEY);
        if (obj == null) {
            return null;
        }
        return TypeUtils.cast(obj, clazz, ParserConfig.getGlobalInstance());*/
        return null;
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
