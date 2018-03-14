package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 统计BigDecimal类型
 * Created by 白超 on 2018-3-12.
 */
public abstract class StatisticsBigDecimalFilter<T> extends BasicStatisticsFilter<T, BigDecimal> {

    /**
     * 小数位数
     */
    protected int scale = 2;
    /**
     * 小数模式
     */
    protected RoundingMode roundingMode = RoundingMode.HALF_UP;

    public StatisticsBigDecimalFilter(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler) {
        super(name, dataContainer, rawDataHandler);
    }

    public StatisticsBigDecimalFilter(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter) {
        super(name, dataContainer, formatter);
    }

    public StatisticsBigDecimalFilter(String name, DataContainer<BigDecimal> dataContainer, RawDataHandler<T, BigDecimal> rawDataHandler, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, rawDataHandler);
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    public StatisticsBigDecimalFilter(String name, DataContainer<BigDecimal> dataContainer, Formatter<T, BigDecimal> formatter, int scale, RoundingMode roundingMode) {
        super(name, dataContainer, formatter);
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    /**
     * 执行过滤
     *
     * @param oldValue 旧值
     * @param newValue 新值
     * @param count    新值数量
     */
    public abstract void doStatistics(BigDecimal oldValue, BigDecimal newValue, int count);

    @Override
    public void doStatistics(BigDecimal value, int count) throws Exception {
        BigDecimal hv = this.getValue(this.getName());
        if (hv == null) {
            hv = new BigDecimal(0);
        }
        if (value == null) {
            value = new BigDecimal(0);
        }
        this.doStatistics(hv, value, count);
    }
}
