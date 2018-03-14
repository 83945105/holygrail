package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.AdvancedStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.util.SortUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * 众数值统计
 * Created by 白超 on 2018-3-11.
 */
public class ModeValue extends AdvancedStatisticsFilter<String> {

    private String valueCountName;

    public ModeValue(String name, DataContainer<String> dataContainer, String valueCountName) {
        super(name, dataContainer);
        this.valueCountName = valueCountName;
    }

    public ModeValue(String name, DataContainer<String> dataContainer, Formatter<DataContainer, DataContainer> formatter, String valueCountName) {
        super(name, dataContainer, formatter);
        this.valueCountName = valueCountName;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        StringBuilder modeValue = new StringBuilder();
        ValueCounts<Object> valueCounts = value.getValueCounts(this.valueCountName);
        for (int i = 1; i > 0; i--) {
            if (valueCounts == null) {
                modeValue.append("无,");
                break;
            }
            Iterator<Integer> iterator = valueCounts.values().iterator();
            ArrayList<Integer> counts = new ArrayList<>();
            while (iterator.hasNext()) {
                counts.add(iterator.next());
            }
            if (counts.size() == 0) {
                modeValue.append("无,");
                break;
            }
            SortUtil.bubbleSort(counts, (left, right) -> left < right);//从大到小排序
            int maxCount = counts.get(0);
            ArrayList<BigDecimal> modeValues = new ArrayList<>();
            for (Map.Entry<Object, Integer> entry : valueCounts.entrySet()) {
                if (entry.getValue() != maxCount) {
                    continue;
                }
                if (entry.getKey() instanceof BigDecimal) {
                    modeValues.add((BigDecimal) entry.getKey());
                    continue;
                }
                modeValues.add(new BigDecimal(entry.getKey().toString()));
            }
            SortUtil.bubbleSort(modeValues, (left, right) -> left.compareTo(right) == 1);
            for (BigDecimal bigDecimal : modeValues) {
                modeValue.append(bigDecimal.setScale(this.scale, this.roundingMode).doubleValue()).append(",");
            }
        }
        modeValue.replace(0, modeValue.length(), modeValue.substring(0, modeValue.length() - 1));
        this.setValue(this.getName(), modeValue.toString());
        int hc = this.getValueCount(this.getName(), modeValue.toString());
        this.setValueCount(this.getName(), modeValue.toString(), hc + count);
    }
}
