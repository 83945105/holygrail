package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.norm.DataContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计容器
 * Created by 白超 on 2018/3/9.
 */
public final class StatisticsData<V> extends HashMap<String, V> implements DataContainer<V> {

    private String key;

    private HashMap<String, ValueCounts<V>> valueCounts = new HashMap<>();

    private ArrayList<StatisticsData> statisticsDataList = new ArrayList<>();

    @Override
    public void setValue(String name, V value) {
        if (value instanceof StatisticsData) {
            this.statisticsDataList.add((StatisticsData) value);
        }
        this.put(name, value);
    }

    @Override
    public V getValue(String name) {
        return this.get(name);
    }

    public ArrayList<StatisticsData> getValues() {
        return this.statisticsDataList;
    }

    @Override
    public void setValueCount(String name, V value, int count) {
        ValueCounts<V> vcs = this.valueCounts.get(name);
        if (vcs == null) {
            vcs = new ValueCounts<>();
            this.valueCounts.put(name, vcs);
        }
        vcs.put(value, count);
    }

    @Override
    public int getValueCount(String name, V value) {
        ValueCounts<V> vcs = this.valueCounts.get(name);
        if (vcs == null) {
            return 0;
        }
        return vcs.get(value) == null ? 0 : vcs.get(value);
    }

    @Override
    public ValueCounts<V> getValueCounts(String name) {
        return this.valueCounts.get(name) == null ? new ValueCounts<>() : this.valueCounts.get(name);
    }

    @Override
    public ValueCounts<?> getValueCounts(String... names) {
        ValueCounts vcs = new ValueCounts();
        for (String name : names) {
            for (Map.Entry<V, Integer> vc : this.getValueCounts(name).entrySet()) {
                vcs.put(vc.getKey(), vc.getValue());
            }
        }
        return vcs;
    }

    public StatisticsData<V> merge(StatisticsData<Object> statisticsData) {
        if (statisticsData == null) {
            return this;
        }
        for (Entry<String, Object> entry : statisticsData.entrySet()) {
            this.put(entry.getKey(), (V) entry.getValue());
        }
        for (Entry<String, ValueCounts<Object>> entry : statisticsData.valueCounts.entrySet()) {
            for (Entry<Object, Integer> valueCount : entry.getValue().entrySet()) {
                this.setValueCount(entry.getKey(), (V) valueCount.getKey(), valueCount.getValue());
            }
        }
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<StatisticsData> getStatisticsDataList() {
        return this.statisticsDataList;
    }
}
