package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.Limit;

import java.util.List;
import java.util.Map;

/**
 * @author 白超
 * @date 2018/6/3
 */
public abstract class AbstractJsonView implements DataView {

    /**
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
     * @return
     */
    abstract public Map<String, Object> getRecord();

    /**
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    abstract public <T> T getRecord(TypeReference<T> typeReference);

    /**
     * 获取存储于record的对象并转为指定的类型
     * 一般存储者放入的是非Map对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    abstract public <T> T getRecord(Class<T> clazz);

    /**
     * 获取存储于records的对象
     * 一般存储者放入的是Map对象
     *
     * @return
     */
    abstract public Map<String, Object> getRecords();

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    abstract public <T> T getRecords(TypeReference<T> typeReference);

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    abstract public <T> T getRecords(Class<T> clazz);

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @return
     */
    abstract public List<Map<String, Object>> getRows();

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    abstract public <T> T getRows(TypeReference<T> typeReference);

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    abstract public <T> List<T> getRows(Class<T> clazz);

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @return
     */
    abstract public Limit getLimit();

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    abstract public <T extends Limit> T getLimit(TypeReference<T> typeReference);

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    abstract public <T extends Limit> T getLimit(Class<T> clazz);

}
