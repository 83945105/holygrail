package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.StatisticsBigDecimalFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 最大值统计
 * Created by 白超 on 2018/3/9.
 */
public final class MaxValue<T> extends StatisticsBigDecimalFilter<T> {

    public MaxValue(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public MaxValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter) {
        super(name, dataContainer, formatter);
    }

    public MaxValue(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, rawDataHandler, scale, roundingMode);
    }

    public MaxValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, formatter, scale, roundingMode);
    }

    @Override
    public void doStatistics(BigDecimal oldValue, BigDecimal newValue, int count) {
        BigDecimal maxValue = new BigDecimal(0);
        for (int i = 1; i > 0; i--) {
            if (this.getValue(this.getName()) == null) {
                break;
            }
            if (newValue.compareTo(oldValue) == 1) {
                maxValue = newValue;
                break;
            }
        }
        this.setValue(this.getName(), maxValue);
        int hc = this.getValueCount(this.getName(), maxValue);
        this.setValueCount(this.getName(), maxValue, hc + count);
    }

}
