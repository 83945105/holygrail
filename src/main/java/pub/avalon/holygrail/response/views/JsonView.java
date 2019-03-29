package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.Limit;
import pub.avalon.holygrail.response.beans.ResultInfo;

import java.util.List;
import java.util.Map;

/**
 * @author 白超
 * @date 2019/3/25
 */
public class JsonView extends AbstractJsonView {

    @Override
    public Map<String, Object> getRecord() {
        return null;
    }

    @Override
    public <T> T getRecord(TypeReference<T> typeReference) {
        return null;
    }

    @Override
    public <T> T getRecord(Class<T> clazz) {
        return null;
    }

    @Override
    public Map<String, Object> getRecords() {
        return null;
    }

    @Override
    public <T> T getRecords(TypeReference<T> typeReference) {
        return null;
    }

    @Override
    public <T> T getRecords(Class<T> clazz) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getRows() {
        return null;
    }

    @Override
    public <T> T getRows(TypeReference<T> typeReference) {
        return null;
    }

    @Override
    public <T> List<T> getRows(Class<T> clazz) {
        return null;
    }

    @Override
    public Limit getLimit() {
        return null;
    }

    @Override
    public <T extends Limit> T getLimit(TypeReference<T> typeReference) {
        return null;
    }

    @Override
    public <T extends Limit> T getLimit(Class<T> clazz) {
        return null;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public ResultInfo getResultInfo() {
        return null;
    }
}
