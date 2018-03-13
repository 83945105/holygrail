package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.SeniorStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 标准分
 * Created by 白超 on 2018/3/12.
 */
public class StandardScore extends SeniorStatisticsFilter<BigDecimal> {

    /**
     * 原始值名称
     */
    private String originalValueName;

    /**
     * 参考范围平均分
     */
    private BigDecimal averageScore;

    /**
     * 参考范围标准差
     */
    private BigDecimal standardDeviation;

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, BigDecimal averageScore, BigDecimal standardDeviation) {
        super(name, dataContainer);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
        this.standardDeviation = standardDeviation;
    }

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, Formatter<DataContainer, DataContainer> formatter, String originalValueName, BigDecimal averageScore, BigDecimal standardDeviation) {
        super(name, dataContainer, formatter);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
        this.standardDeviation = standardDeviation;
    }

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, BigDecimal averageScore, BigDecimal standardDeviation) {
        super(name, dataContainer, scale, roundingMode);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
        this.standardDeviation = standardDeviation;
    }

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, Formatter<DataContainer, DataContainer> formatter, String originalValueName, BigDecimal averageScore, BigDecimal standardDeviation) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.originalValueName = originalValueName;
        this.averageScore = averageScore;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal standardScore = new BigDecimal(0);
        Object oc = value.getValue(this.originalValueName);
        for (int i = 1; i > 0; i--) {
            if(this.averageScore == null) {
                break;
            }
            if(this.standardDeviation == null || this.standardDeviation.compareTo(new BigDecimal(0)) == 0) {
                break;
            }
            if (oc == null) {
                break;
            }
            if (oc instanceof BigDecimal) {
                standardScore = (BigDecimal) oc;
            } else {
                standardScore = new BigDecimal(oc.toString());
            }
            standardScore = standardScore.subtract(this.averageScore).divide(this.standardDeviation, this.scale, this.roundingMode);
        }
        this.setValue(this.getName(), standardScore.setScale(this.scale, this.roundingMode));
    }
}
