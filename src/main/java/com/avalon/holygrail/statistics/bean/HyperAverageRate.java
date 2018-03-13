package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.SeniorStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 超均率
 * Created by 白超 on 2018/3/13.
 */
public class HyperAverageRate extends SeniorStatisticsFilter<BigDecimal> {

    /**
     * 原始值名称
     */
    private String originalValueName;

    /**
     * 参考范围平均分
     */
    private BigDecimal averageScore;

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, BigDecimal averageScore) {
        super(name, dataContainer);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
    }

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, Formatter<DataContainer, DataContainer> formatter, String originalValueName, BigDecimal averageScore) {
        super(name, dataContainer, formatter);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
    }

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, BigDecimal averageScore) {
        super(name, dataContainer, scale, roundingMode);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
    }

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, Formatter<DataContainer, DataContainer> formatter, String originalValueName, BigDecimal averageScore) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal hyperAverageRate = new BigDecimal(0);
        Object oc = value.getValue(this.originalValueName);
        for (int i = 1; i > 0; i--) {
            if (this.averageScore == null || this.averageScore.compareTo(new BigDecimal(0)) == 0) {
                break;
            }
            if (oc == null) {
                break;
            }
            if (oc instanceof BigDecimal) {
                hyperAverageRate = (BigDecimal) oc;
            } else {
                hyperAverageRate = new BigDecimal(oc.toString());
            }
            hyperAverageRate = hyperAverageRate.subtract(this.averageScore).divide(this.averageScore, this.scale, this.roundingMode);
        }
        this.setValue(this.getName(), hyperAverageRate.setScale(this.scale, this.roundingMode));
    }
}
