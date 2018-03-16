package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsValueCount;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

/**
 * 统计String类型值次数
 * Created by 白超 on 2018-3-12.
 */
public final class StringValueCount<T> extends StatisticsValueCount<T, String> {

    public StringValueCount(String name, DataContainer<String> dataContainer, RawDataHandler<T, String> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public StringValueCount(String name, DataContainer<String> dataContainer, Formatter<T, String> formatter) {
        super(name, dataContainer, formatter);
    }

}
