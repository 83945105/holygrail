package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.exception.StatisticsException;
import com.avalon.holygrail.statistics.model.AdvancedStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * 平均值统计
 * Created by 白超 on 2018-3-10.
 */
public final class AverageValue extends AdvancedStatisticsFilter<BigDecimal> {

    private String totalValueName;

    private String totalCountName;

    private String valueCountName;

    /**
     * 统计方式
     * 1-使用TotalValue+TotalCount来计算
     * 2-使用ValueCount来计算
     */
    private int type;

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, String totalValueName, String totalCountName) {
        super(name, dataContainer);
        this.totalValueName = totalValueName;
        this.totalCountName = totalCountName;
        this.type = 1;
    }

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<DataContainer, DataContainer> formatter, String totalValueName, String totalCountName) {
        super(name, dataContainer, formatter);
        this.totalValueName = totalValueName;
        this.totalCountName = totalCountName;
        this.type = 1;
    }

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String totalValueName, String totalCountName) {
        super(name, dataContainer, scale, roundingMode);
        this.totalValueName = totalValueName;
        this.totalCountName = totalCountName;
        this.type = 1;
    }

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, Formatter<DataContainer, DataContainer> formatter, String totalValueName, String totalCountName) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.totalValueName = totalValueName;
        this.totalCountName = totalCountName;
        this.type = 1;
    }

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, String valueCountName) {
        super(name, dataContainer);
        this.valueCountName = valueCountName;
        this.type = 2;
    }

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<DataContainer, DataContainer> formatter, String valueCountName) {
        super(name, dataContainer, formatter);
        this.valueCountName = valueCountName;
        this.type = 2;
    }

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String valueCountName) {
        super(name, dataContainer, scale, roundingMode);
        this.valueCountName = valueCountName;
        this.type = 2;
    }

    public AverageValue(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, Formatter<DataContainer, DataContainer> formatter, String valueCountName) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.valueCountName = valueCountName;
        this.type = 2;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal averageValue = new BigDecimal(0);
        BigDecimal totalValue;
        Integer totalCount = 0;
        switch (this.type) {
            case 0:
                throw new StatisticsException("AverageValue 未指定type类型:" + this.getName());
            case 1:
                totalValue = (BigDecimal) value.getValue(this.totalValueName);
                if (totalValue == null) {
                    break;
                }
                totalCount = (Integer) value.getValue(this.totalCountName);
                if (totalCount == null || totalCount == 0) {
                    break;
                }
                averageValue = totalValue.divide(new BigDecimal(totalCount), 16, this.roundingMode);
                break;
            case 2:
                ValueCounts<Object> valueCounts = value.getValueCounts(this.valueCountName);
                if (valueCounts == null) {
                    break;
                }
                totalValue = new BigDecimal(0);
                for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        if (entry.getKey() instanceof BigDecimal) {
                            totalValue = totalValue.add((BigDecimal) entry.getKey());
                        } else {
                            totalValue = totalValue.add(new BigDecimal(entry.getKey().toString()));
                        }
                    }
                    totalCount += entry.getValue();
                }
                if (totalCount == 0) {
                    break;
                }
                averageValue = totalValue.divide(new BigDecimal(totalCount), 16, this.roundingMode);
                break;
            default:
                throw new StatisticsException("AverageValue type类型不正确:" + this.getName());
        }
        averageValue = averageValue.setScale(this.scale, this.roundingMode);
        this.setValue(this.getName(), averageValue);
        int hc = this.getValueCount(this.getName(), averageValue);
        this.setValueCount(this.getName(), averageValue, hc + count);
    }
}
