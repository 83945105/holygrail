package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.Limit;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 白超
 * @date 2018/6/3
 */
public abstract class AbstractJsonView implements DataView {

    abstract public Object getRecord();

    abstract public <T> T getRecord(Class<T> clazz);

    abstract public Map<?, ?> getRecords();

    abstract public <T> T getRecords(Class<T> clazz);

    abstract public <T> T getRecords(TypeReference<T> typeReference);

    abstract public Collection<?> getRows();

    abstract public <T> Collection<T> getRows(Function<Object, T> formatterRow);

    abstract public <T> Collection<T> getRows(Class<T> clazz);

    abstract public Limit getLimit();

    abstract public <T extends Limit> T getLimit(Class<T> clazz);

}
