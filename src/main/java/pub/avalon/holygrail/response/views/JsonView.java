package pub.avalon.holygrail.response.views;

import com.fasterxml.jackson.core.type.TypeReference;
import pub.avalon.beans.Limit;

import java.util.List;
import java.util.Map;

/**
 * @author 白超
 * @date 2018/6/3
 */
public interface JsonView extends DataView {

    /**
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
     * @return
     */
    Object getRecord();

    /**
     * 获取存储于record的对象
     * 一般存储者放入的是非Map对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    <T> T getRecord(TypeReference<T> typeReference);

    /**
     * 获取存储于record的对象并转为指定的类型
     * 一般存储者放入的是非Map对象
     *
     * @param returnType
     * @param <T>
     * @return
     */
    <T> T getRecord(Class<T> returnType);

    /**
     * 获取存储于records的对象
     * 一般存储者放入的是Map对象
     *
     * @return
     */
    Map<String, Object> getRecords();

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    <T> T getRecords(TypeReference<T> typeReference);

    /**
     * 获取存储于records的对象并转为指定的类型
     * 一般存储者放入的是Map对象
     *
     * @param returnType
     * @param <T>
     * @return
     */
    <T> T getRecords(Class<T> returnType);

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @return
     */
    List<Map<String, Object>> getRows();

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    <T> T getRows(TypeReference<T> typeReference);

    /**
     * 获取存储于rows的对象
     * 一般存储者放入的是集合对象
     *
     * @param beanType
     * @param <T>
     * @return
     */
    <T> List<T> getRows(Class<T> beanType);

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @return
     */
    Limit getLimit();

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    <T extends Limit> T getLimit(TypeReference<T> typeReference);

    /**
     * 获取存储于limit的对象
     * 一般存储者放入的是分页对象
     *
     * @param returnType
     * @param <T>
     * @return
     */
    <T extends Limit> T getLimit(Class<T> returnType);

}
