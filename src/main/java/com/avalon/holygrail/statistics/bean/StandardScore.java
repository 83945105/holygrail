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
     * 参考范围平均分名称
     */
    private String referAverageScoreName;

    /**
     * 参考范围标准差名称
     */
    private String referStandardDeviationName;

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, String referAverageScoreName, String referStandardDeviationName) {
        super(name, dataContainer);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
        this.referStandardDeviationName = referStandardDeviationName;
    }

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, String originalValueName, String referAverageScoreName, String referStandardDeviationName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, formatter);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
        this.referStandardDeviationName = referStandardDeviationName;
    }

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String referAverageScoreName, String referStandardDeviationName) {
        super(name, dataContainer, scale, roundingMode);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
        this.referStandardDeviationName = referStandardDeviationName;
    }

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String referAverageScoreName, String referStandardDeviationName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.originalValueName = originalValueName;
        this.referAverageScoreName = referAverageScoreName;
        this.referStandardDeviationName = referStandardDeviationName;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal standardScore = new BigDecimal(0);
        Object oc = value.getValue(this.originalValueName);
        Object rAvg = value.getValue(this.referAverageScoreName);
        Object rsd = value.getValue(this.referStandardDeviationName);
        for (int i = 1; i > 0; i--) {
            if (oc == null) {
                break;
            }
            if (rAvg == null) {
                break;
            }
            if (rsd == null) {
                break;
            }
            BigDecimal originalValue;
            if (oc instanceof BigDecimal) {
                originalValue = (BigDecimal) oc;
            } else {
                originalValue = new BigDecimal(oc.toString());
            }
            BigDecimal referAverageScore;
            if (rAvg instanceof BigDecimal) {
                referAverageScore = (BigDecimal) rAvg;
            } else {
                referAverageScore = new BigDecimal(rAvg.toString());
            }
            BigDecimal referStandardDeviation;
            if (rsd instanceof BigDecimal) {
                referStandardDeviation = (BigDecimal) rsd;
            } else {
                referStandardDeviation = new BigDecimal(rsd.toString());
            }
            if (referStandardDeviation.compareTo(new BigDecimal(0)) == 0) {
                break;
            }
            standardScore = originalValue.subtract(referAverageScore).divide(referStandardDeviation, 16, this.roundingMode);
        }
        standardScore = standardScore.setScale(this.scale, this.roundingMode);
        this.setValue(this.getName(), standardScore);
        int hc = this.getValueCount(this.getName(), standardScore);
        this.setValueCount(this.getName(), standardScore, hc + count);
    }
}
