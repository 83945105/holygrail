package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.FinalStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.util.SortUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;

/**
 * 排名
 * Created by 白超 on 2018/3/14.
 */
public class Rank extends FinalStatisticsFilter<Integer> {

    /**
     * 原始值名称
     */
    private String originalValueName;

    /**
     * 参考值次数名称
     */
    private String referValueCountName;

    public Rank(String name, DataContainer<Integer> dataContainer, String originalValueName, String referValueCountName) {
        super(name, dataContainer);
        this.originalValueName = originalValueName;
        this.referValueCountName = referValueCountName;
    }

    public Rank(String name, DataContainer<Integer> dataContainer, String originalValueName, String referValueCountName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, formatter);
        this.originalValueName = originalValueName;
        this.referValueCountName = referValueCountName;
    }

    public Rank(String name, DataContainer<Integer> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String referValueCountName) {
        super(name, dataContainer, scale, roundingMode);
        this.originalValueName = originalValueName;
        this.referValueCountName = referValueCountName;
    }

    public Rank(String name, DataContainer<Integer> dataContainer, int scale, RoundingMode roundingMode, String originalValueName, String referValueCountName, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer, scale, roundingMode, formatter);
        this.originalValueName = originalValueName;
        this.referValueCountName = referValueCountName;
    }

    @Override
    public void doStatistics(DataContainer value, int count) throws Exception {
        Object ov = value.getValue(this.originalValueName);
        ValueCounts<Object> vcs = value.getValueCounts(this.referValueCountName);
        if (ov == null) {
            return;
        }
        if (vcs == null || vcs.size() == 0) {
            return;
        }
        ArrayList<BigDecimal> values = new ArrayList<>();
        BigDecimal v;
        for (Map.Entry<Object, Integer> entry : vcs.entrySet()) {
            if (entry.getKey() instanceof BigDecimal) {
                v = (BigDecimal) entry.getKey();
            } else {
                v = new BigDecimal(entry.getKey().toString());
            }
            for (int i = 0; i < entry.getValue(); i++) {
                values.add(v);
            }
        }
        SortUtil.bubbleSort(values, (left, right) -> left.compareTo(right) == -1);//从大到小排序
        BigDecimal cv;
        if (ov instanceof BigDecimal) {
            cv = (BigDecimal) ov;
        } else {
            cv = new BigDecimal(ov.toString());
        }
        int cp;
        for (int i = 0; i < values.size(); i++) {
            cp = cv.compareTo(values.get(i));
            // = 当前比较值
            if (cp == 1 || cp == 0) {
                this.setValue(this.getName(), i + 1);
                break;
            }
            //小于最后一个数
            if(i == values.size() - 1) {
                this.setValue(this.getName(), i + 2);
                break;
            }
        }
    }
}
