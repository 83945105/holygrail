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
public final class HyperAverageRate extends SeniorStatisticsFilter<BigDecimal> {

    /**
     * 原始值名称
     */
    private String originalValueName;

    /**
     * 参考范围平均分
     */
    private String referAverageScoreName;

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, String referAverageScoreName) {
        super(name, dataContainer);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
    }

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, String referAverageScoreName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, formatter);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
    }

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String referAverageScoreName) {
        super(name, dataContainer, scale, roundingMode);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
    }

    public HyperAverageRate(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String referAverageScoreName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal hyperAverageRate = new BigDecimal(0);
        Object oc = value.getValue(this.originalValueName);
        Object rAvg = value.getValue(this.referAverageScoreName);
        for (int i = 1; i > 0; i--) {
            if (oc == null) {
                break;
            }
            if (rAvg == null) {
                break;
            }
            if (oc instanceof BigDecimal) {
                hyperAverageRate = (BigDecimal) oc;
            } else {
                hyperAverageRate = new BigDecimal(oc.toString());
            }
            BigDecimal referAverageScore;
            if (rAvg instanceof BigDecimal) {
                referAverageScore = (BigDecimal) rAvg;
            } else {
                referAverageScore = new BigDecimal(rAvg.toString());
            }
            if (referAverageScore.compareTo(new BigDecimal(0)) == 0) {
                break;
            }
            hyperAverageRate = hyperAverageRate.subtract(referAverageScore).divide(referAverageScore, 16, this.roundingMode);
        }
        hyperAverageRate = hyperAverageRate.setScale(this.scale, this.roundingMode);
        this.setValue(this.getName(), hyperAverageRate);
        int hc = this.getValueCount(this.getName(), hyperAverageRate);
        this.setValueCount(this.getName(), hyperAverageRate, hc + count);
    }
}
