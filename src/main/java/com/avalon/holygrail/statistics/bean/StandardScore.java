package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.model.SeniorStatisticsFilter;
import com.avalon.holygrail.statistics.norm.DataContainer;

import java.math.BigDecimal;

/**
 * 标准分
 * Created by 白超 on 2018/3/12.
 */
public class StandardScore extends SeniorStatisticsFilter<BigDecimal> {

    public StandardScore(String name, DataContainer<BigDecimal> dataContainer) {
        super(name, dataContainer);
    }
}
