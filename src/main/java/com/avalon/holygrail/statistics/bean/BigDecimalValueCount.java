package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsValueCount;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

import java.math.BigDecimal;

/**
 * 统计BigDecimal类型值次数
 * Created by 白超 on 2018/3/12.
 */
public class BigDecimalValueCount<T> extends StatisticsValueCount<T, BigDecimal> {

    public BigDecimalValueCount(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public BigDecimalValueCount(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter) {
        super(name, dataContainer, formatter);
    }
}
