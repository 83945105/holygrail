package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsBigDecimalFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 记录BigDecimal类型值
 * Created by 白超 on 2018/3/12.
 */
public final class BigDecimalValue<T> extends StatisticsBigDecimalFilter<T> {

    public BigDecimalValue(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public BigDecimalValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter) {
        super(name, dataContainer, formatter);
    }

    public BigDecimalValue(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, rawDataHandler, scale, roundingMode);
    }

    public BigDecimalValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, formatter, scale, roundingMode);
    }

    @Override
    public void doStatistics(BigDecimal oldValue, BigDecimal newValue, int count) {
        this.setValue(this.getName(), newValue);
        int hc = this.getValueCount(this.getName(), newValue);
        this.setValueCount(this.getName(), newValue, hc + count);
    }
}
