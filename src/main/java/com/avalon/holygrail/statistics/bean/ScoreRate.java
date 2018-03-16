package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.SeniorStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 得分率
 * Created by 白超 on 2018/3/13.
 */
public final class ScoreRate extends SeniorStatisticsFilter<BigDecimal> {

    /**
     * 原始值名称
     */
    private String originalValueName;

    /**
     * 总值名称
     */
    private String totalValueName;

    public ScoreRate(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, String totalValueName) {
        super(name, dataContainer);
        this.originalValueName = originalValueName;
        this.totalValueName = totalValueName;
    }

    public ScoreRate(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, String totalValueName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, formatter);
        this.originalValueName = originalValueName;
        this.totalValueName = totalValueName;
    }

    public ScoreRate(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String totalValueName) {
        super(name, dataContainer, scale, roundingMode);
        this.originalValueName = originalValueName;
        this.totalValueName = totalValueName;
    }

    public ScoreRate(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String totalValueName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.originalValueName = originalValueName;
        this.totalValueName = totalValueName;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal scoreRate = new BigDecimal(0);
        Object ov = value.getValue(this.originalValueName);
        Object tv = value.getValue(this.totalValueName);
        for (int i = 1; i > 0; i--) {
            if (ov == null) {
                break;
            }
            if (tv == null) {
                break;
            }
            BigDecimal originalValue;
            if (ov instanceof BigDecimal) {
                originalValue = (BigDecimal) ov;
            } else {
                originalValue = new BigDecimal(ov.toString());
            }
            BigDecimal totalValue;
            if (tv instanceof BigDecimal) {
                totalValue = (BigDecimal) tv;
            } else {
                totalValue = new BigDecimal(tv.toString());
            }
            if (totalValue.compareTo(new BigDecimal(0)) == 0) {
                break;
            }
            scoreRate = originalValue.divide(totalValue, 16, this.roundingMode);
        }
        scoreRate = scoreRate.setScale(this.scale, this.roundingMode);
        this.setValue(this.getName(), scoreRate);
        int hc = this.getValueCount(this.getName(), scoreRate);
        this.setValueCount(this.getName(), scoreRate, hc + count);
    }
}
