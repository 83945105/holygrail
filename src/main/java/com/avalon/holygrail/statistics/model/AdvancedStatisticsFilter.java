package com.avalon.holygrail.statistics.model;

import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;

import java.math.RoundingMode;

/**
 * 进阶统计
 * 依赖于基础统计
 * Created by 白超 on 2018-3-10.
 */
public abstract class AdvancedStatisticsFilter<V> extends StatisticsFilter<DataContainer, V, DataContainer> {

    /**
     * 小数位数
     */
    protected int scale = 2;
    /**
     * 小数模式
     */
    protected RoundingMode roundingMode = RoundingMode.HALF_UP;

    /**
     * 格式化数据
     */
    private Formatter<DataContainer, DataContainer> formatter = (record, filter) -> filter.doStatistics(record, 1);

    public AdvancedStatisticsFilter(String name, DataContainer<V> dataContainer) {
        super(name, dataContainer);
    }

    public AdvancedStatisticsFilter(String name, DataContainer<V> dataContainer, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer);
        this.formatter = formatter;
    }

    public AdvancedStatisticsFilter(String name, DataContainer<V> dataContainer, int scale, RoundingMode roundingMode) {
        super(name, dataContainer);
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    public AdvancedStatisticsFilter(String name, DataContainer<V> dataContainer, int scale, RoundingMode roundingMode, Formatter<DataContainer, DataContainer> formatter) {
        super(name, dataContainer);
        this.scale = scale;
        this.roundingMode = roundingMode;
        this.formatter = formatter;
    }

    @Override
    public void doStatistics(DataContainer go, DataContainer<V> back) throws Exception {
        this.formatter.accept(go, this);
    }
}
