package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsBigDecimalFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 最小值统计
 * Created by 白超 on 2018/3/9.
 */
public final class MinValue<T> extends StatisticsBigDecimalFilter<T> {

    public MinValue(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public MinValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter) {
        super(name, dataContainer, formatter);
    }

    public MinValue(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, rawDataHandler, scale, roundingMode);
    }

    public MinValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, formatter, scale, roundingMode);
    }

    @Override
    public void doStatistics(BigDecimal value, int count) throws Exception {
        BigDecimal hv = this.getValue(this.getName());
        if (hv == null) {
            hv = new BigDecimal(Integer.MAX_VALUE);
        }
        if (value == null) {
            value = new BigDecimal(0);
        }
        this.doStatistics(hv, value, count);
    }

    @Override
    public void doStatistics(BigDecimal oldValue, BigDecimal newValue, int count) {
        BigDecimal minValue;
        if (newValue.compareTo(oldValue) == -1) {
            minValue = newValue;
        }else {
            minValue = oldValue;
        }
        this.setValue(this.getName(), minValue);
        int hc = this.getValueCount(this.getName(), minValue);
        this.setValueCount(this.getName(), minValue, hc + count);
    }

}
