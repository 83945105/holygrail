package com.avalon.holygrail.statistics.norm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 数据容器
 * Created by 白超 on 2018/3/12.
 */
public interface DataContainer<T> {

    /**
     * 设置数据
     *
     * @param name
     * @param value
     */
    void setValue(String name, T value);

    /**
     * 获取数据
     *
     * @param name
     * @return
     */
    T getValue(String name);

    /**
     * 设置数据出现次数
     *
     * @param name
     * @param value
     * @param count
     */
    void setValueCount(String name, T value, int count);

    /**
     * 获取数据值出现次数
     *
     * @param name
     * @param value
     * @return
     */
    int getValueCount(String name, T value);

    /**
     * 获取指定的值出现次数集合
     *
     * @param name
     * @return
     */
    ValueCounts<T> getValueCounts(String name);

    /**
     * 获取数据值出现次数集合
     *
     * @return
     */
    Collection<ValueCounts<T>> getValueCounts();

    /**
     * 合并指定的值出现次数集合
     *
     * @param names
     * @return
     */
    ValueCounts<?> mergeValueCounts(String... names);


    final class ValueCounts<K> extends HashMap<K, Integer> {

        @Override
        public String toString() {
            Iterator<Entry<K, Integer>> i = entrySet().iterator();
            if (!i.hasNext())
                return "{}";

            StringBuilder sb = new StringBuilder();
            sb.append('{');
            for (; ; ) {
                Entry<K, Integer> e = i.next();
                K key = e.getKey();
                Integer value = e.getValue();
                sb.append(key == this ? "(this Map)" : key);
                sb.append('=');
                sb.append(value == null ? 0 : value);
                if (!i.hasNext())
                    return sb.append('}').toString();
                sb.append(',').append(' ');
            }
        }
    }
}
