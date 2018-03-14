package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.AdvancedStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * 标准差
 * Created by 白超 on 2018/3/12.
 */
public class StandardDeviation extends AdvancedStatisticsFilter<BigDecimal> {

    private String valueCountName;

    public StandardDeviation(String name, DataContainer<BigDecimal> dataContainer, String valueCountName) {
        super(name, dataContainer);
        this.valueCountName = valueCountName;
    }

    public StandardDeviation(String name, DataContainer<BigDecimal> dataContainer, Formatter<DataContainer, DataContainer> formatter, String valueCountName) {
        super(name, dataContainer, formatter);
        this.valueCountName = valueCountName;
    }

    public StandardDeviation(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String valueCountName) {
        super(name, dataContainer, scale, roundingMode);
        this.valueCountName = valueCountName;
    }

    public StandardDeviation(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, Formatter<DataContainer, DataContainer> formatter, String valueCountName) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.valueCountName = valueCountName;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal standardDeviation = new BigDecimal(0);
        ValueCounts<Object> valueCounts = value.getValueCounts(this.valueCountName);
        for (int i = 1; i > 0; i--) {
            if (valueCounts == null) {
                break;
            }
            //计算平均值
            BigDecimal avg = new BigDecimal(0);
            int tc = 0;//总数
            for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
                tc += entry.getValue();
                for (int j = 0; j < entry.getValue(); j++) {
                    if (entry.getKey() instanceof BigDecimal) {
                        avg = avg.add((BigDecimal) entry.getKey());
                        continue;
                    }
                    avg = avg.add(new BigDecimal(entry.getKey().toString()));
                }
            }
            if (tc == 0) {
                break;
            }
            BigDecimal total = new BigDecimal(tc);
            avg = avg.divide(total, 16, this.roundingMode);
            //计算标准差
            //标准差= sqrt( ( (A - avg) * (A - avg) + (B - avg) * (B - avg) + ... + (N - avg) * (N - avg) ) / N )
            BigDecimal v;
            for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
                for (int j = 0; j < entry.getValue(); j++) {
                    if (entry.getKey() instanceof BigDecimal) {
                        v = ((BigDecimal) entry.getKey()).subtract(avg);
                    } else {
                        v = new BigDecimal(entry.getKey().toString()).subtract(avg);
                    }
                    standardDeviation = standardDeviation.add(v.multiply(v));
                }
            }
            standardDeviation = standardDeviation.divide(total, 16, this.roundingMode);
            standardDeviation = new BigDecimal(Math.sqrt(standardDeviation.doubleValue()));
        }
        standardDeviation = standardDeviation.setScale(this.scale, this.roundingMode);
        this.setValue(this.getName(), standardDeviation);
        int hc = this.getValueCount(this.getName(), standardDeviation);
        this.setValueCount(this.getName(), standardDeviation, hc + count);
    }
}
