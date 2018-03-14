package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.filter.norm.Filter;
import com.avalon.holygrail.filter.norm.FilterChain;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Statistics;

import java.util.Collection;

/**
 * 统计过滤器
 * Created by 白超 on 2018-3-13.
 */
public abstract class StatisticsFilter<T, V, K> implements Filter<T, DataContainer<V>>, DataContainer<V>, Statistics<K> {

    /**
     * 统计名
     */
    private String name;

    /**
     * 数据容器
     */
    private DataContainer<V> dataContainer;

    /**
     * 统计结果
     */
    private V value;

    /**
     * 统计结果次数
     */
    private ValueCounts<V> valueCount = new ValueCounts<>();

    public StatisticsFilter(String name, DataContainer<V> dataContainer) {
        this.name = name;
        this.dataContainer = dataContainer;
    }

    public abstract void doStatistics(T go, DataContainer<V> back) throws Exception;

    @Override
    public void doFilter(T go, DataContainer<V> back, FilterChain filterChain) throws Exception {
        if (back != null) {
            this.dataContainer = back;
        }
        this.doStatistics(go, back);
        filterChain.doFilter(go, back);
    }

    /**
     * 获取统计字段名
     *
     * @return
     */
    public String getName() {
        return name;
    }

    @Override
    public void setValue(String name, V value) {
        this.value = value;
        this.dataContainer.setValue(name, value);
    }

    @Override
    public V getValue(String name) {
        return this.dataContainer.getValue(name);
    }

    @Override
    public void setValueCount(String name, V value, int count) {
        this.valueCount.put(value, count);
        this.dataContainer.setValueCount(name, value, count);
    }

    @Override
    public int getValueCount(String name, V value) {
        return this.dataContainer.getValueCount(name, value);
    }

    @Override
    public ValueCounts<V> getValueCounts(String name) {
        return this.dataContainer.getValueCounts(name);
    }

    @Override
    public ValueCounts<?> getValueCounts(String... names) {
        return this.dataContainer.getValueCounts(names);
    }
}
