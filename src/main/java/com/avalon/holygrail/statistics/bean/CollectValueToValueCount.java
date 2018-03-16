package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.CollectStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 汇总为ArrayList集合
 * Created by 白超 on 2018/3/15.
 */
public class CollectValueToValueCount extends CollectStatisticsFilter<Object> {

    /**
     * 目标值的名称
     */
    private String targetValueName;

    public CollectValueToValueCount(String name, DataContainer<Object> dataContainer, String targetValueName) {
        super(name, dataContainer);
        this.targetValueName = targetValueName;
    }

    public CollectValueToValueCount(String name, DataContainer<Object> dataContainer, String targetValueName, Formatter<Collection<DataContainer>, Collection<DataContainer>> formatter) {
        super(name, dataContainer, formatter);
        this.targetValueName = targetValueName;
    }

    public CollectValueToValueCount(FormatterName<Collection<DataContainer>, Object> formatterName, DataContainer<Object> dataContainer, String targetValueName) {
        super(formatterName, dataContainer);
        this.targetValueName = targetValueName;
    }

    public CollectValueToValueCount(FormatterName<Collection<DataContainer>, Object> formatterName, DataContainer<Object> dataContainer, String targetValueName, Formatter<Collection<DataContainer>, Collection<DataContainer>> formatter) {
        super(formatterName, dataContainer, formatter);
        this.targetValueName = targetValueName;
    }

    @Override
    public void doStatistics(Collection<DataContainer> value, int count) throws Exception {
        //取出汇总的所有值存入ValueCount
        Object v;
        int oc;
        Iterator<DataContainer> iterator = value.iterator();
        ArrayList<DataContainer> cs = new ArrayList<>();
        while (iterator.hasNext()) {
            cs.add(iterator.next());
        }
        for (DataContainer container : cs) {
            v = container.getValue(targetValueName);//获取单个容器指定值
            for (DataContainer c : cs) {
                oc = c.getValueCount(this.getName(), v);
                c.setValueCount(this.getName(), v, oc + count);
            }
        }
    }
}
