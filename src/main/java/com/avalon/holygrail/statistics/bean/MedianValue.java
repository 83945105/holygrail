package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.AdvancedStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.util.SortUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;

/**
 * 中位数统计
 * Created by 白超 on 2018/3/12.
 */
public class MedianValue extends AdvancedStatisticsFilter<BigDecimal> {

    private String valueCountName;

    public MedianValue(String name, DataContainer<BigDecimal> dataContainer, String valueCountName) {
        super(name, dataContainer);
        this.valueCountName = valueCountName;
    }

    public MedianValue(String name, DataContainer<BigDecimal> dataContainer, Formatter<DataContainer, DataContainer> formatter, String valueCountName) {
        super(name, dataContainer, formatter);
        this.valueCountName = valueCountName;
    }

    public MedianValue(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, String valueCountName) {
        super(name, dataContainer, scale, roundingMode);
        this.valueCountName = valueCountName;
    }

    public MedianValue(String name, DataContainer<BigDecimal> dataContainer, int scale, RoundingMode roundingMode, Formatter<DataContainer, DataContainer> formatter, String valueCountName) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.valueCountName = valueCountName;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        BigDecimal medianValue = new BigDecimal(0);
        ValueCounts<Object> valueCounts = value.getValueCounts(this.valueCountName);
        for (int i = 1; i > 0; i--) {
            if (valueCounts == null) {
                break;
            }
            ArrayList<BigDecimal> values = new ArrayList<>();
            for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
                for (int j = 0; j < entry.getValue(); j++) {
                    if (entry.getKey() instanceof BigDecimal) {
                        values.add((BigDecimal) entry.getKey());
                    } else {
                        values.add(new BigDecimal(entry.getKey().toString()));
                    }
                }
            }
            SortUtil.bubbleSort(values, (left, right) -> left.compareTo(right) == 1);
            int size = values.size();
            if (size == 0) {
                break;
            }
            if (size % 2 == 0) {//偶数
                medianValue = values.get(size / 2 - 1)
                        .add(values.get(size / 2))
                        .divide(new BigDecimal(2), 16, this.roundingMode);
                break;
            }
            //奇数
            medianValue = values.get((size - 1) / 2);
        }
        medianValue = medianValue.setScale(this.scale, this.roundingMode);
        this.setValue(this.getName(), medianValue);
        int hc = this.getValueCount(this.getName(), medianValue);
        this.setValueCount(this.getName(), medianValue, hc + count);
    }

}
