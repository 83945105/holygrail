package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsValueCount;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 统计Integer类型值次数
 * Created by 白超 on 2018-3-12.
 */
public class IntegerValueCount<T> extends StatisticsValueCount<T, Integer> {

    public IntegerValueCount(String name, DataContainer<Integer> dataContainer, RawDataHandler<T, Integer> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public IntegerValueCount(String name, DataContainer<Integer> dataContainer, Formatter<T, Integer> formatter) {
        super(name, dataContainer, formatter);
    }

}
