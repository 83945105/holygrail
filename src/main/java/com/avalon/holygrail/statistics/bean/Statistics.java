package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.exception.StatisticsException;
import com.avalon.holygrail.statistics.model.AdvancedStatisticsFilter;
import com.avalon.holygrail.statistics.model.BasicStatisticsFilter;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;

import java.math.BigDecimal;
import java.util.*;

/**
 * 统计
 * Created by 白超 on 2018/3/9.
 */
public final class Statistics<T> {

    public Statistics() {
        this.initFilters();
    }

    /**
     * 统计数据容器
     */
    private StatisticsData statisticsData = new StatisticsData();

    private int filterIndex = -1;

    private void initFilters() {
        this.filterIndex++;
        this.basicStatisticsFilterList.add(filterIndex, new ArrayList<>());
        this.advancedStatisticsFilterList.add(filterIndex, new ArrayList<>());
    }

    private List<BasicStatisticsFilter<T, Object>> getBasicFilters() {
        return this.basicStatisticsFilterList.get(this.filterIndex);
    }

    private List<AdvancedStatisticsFilter> getAdvancedFilters() {
        return this.advancedStatisticsFilterList.get(this.filterIndex);
    }

    /**
     * 基础统计过滤器
     */
    private List<List<BasicStatisticsFilter<T, Object>>> basicStatisticsFilterList = new ArrayList<>();

    /**
     * 基础统计过滤链
     */
    private BasicStatisticsFilterChain<T, Object> basicFilterChain = new BasicStatisticsFilterChain<>();

    /**
     * 进阶统计过滤器
     */
    private List<List<AdvancedStatisticsFilter>> advancedStatisticsFilterList = new ArrayList<>();

    /**
     * 进阶统计过滤链
     */
    private AdvancedFilterChain advancedFilterChain = new AdvancedFilterChain();

    /**
     * 开始统计
     *
     * @param records 数据
     * @return
     */
    public Statistics start(Collection<T> records) throws Exception {
        this.basicFilterChain.setFilters(this.getBasicFilters());
        Iterator<T> iterator = records.iterator();
        while (iterator.hasNext()) {
            this.basicFilterChain.doFilter(iterator.next(), null);
        }
        this.advancedFilterChain.setFilters(this.getAdvancedFilters());
        this.advancedFilterChain.doFilter(this.statisticsData, this.statisticsData);
        this.initFilters();
        return this;
    }

    public interface Group<T> {

        String apply(T record);
    }

    /**
     * 分组统计
     *
     * @param records 数据
     * @param group   分组回调,需要返回标识key
     * @return
     * @throws Exception
     */
    public Statistics groupStart(Collection<T> records, Group<T> group) throws Exception {
        this.basicFilterChain.setFilters(this.getBasicFilters());
        Iterator<T> iterator = records.iterator();
        StringBuilder key = new StringBuilder();
        T record;
        StatisticsData sc;
        while (iterator.hasNext()) {
            record = iterator.next();
            key.replace(0, key.length(), group.apply(record));
            sc = (StatisticsData) this.statisticsData.getValue(key.toString());
            if (sc == null) {
                sc = new StatisticsData();
                this.statisticsData.setValue(key.toString(), sc);
            }
            this.basicFilterChain.doFilter(record, sc);
        }
        ArrayList<StatisticsData> statisticsDataArrayList = this.statisticsData.getStatisticsDataList();
        for (StatisticsData statisticsData : statisticsDataArrayList) {
            this.advancedFilterChain.setFilters(this.getAdvancedFilters());
            this.advancedFilterChain.doFilter(statisticsData, statisticsData);
        }
        this.initFilters();
        return this;
    }

    /**
     * 获取统计结果
     *
     * @return
     */
    public Map<String, Object> getResult() {
        return this.statisticsData;
    }

    /**
     * 获取一组统计结果
     *
     * @return
     */
    public ArrayList<Map<String, Object>> getResults() {
        return this.statisticsData.getValues();
    }

    /**
     * 添加一个String类型值
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addStringValue(String name, RawDataHandler<T, String> handler) {
        this.getBasicFilters().add(new StringValue(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个String类型值
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addStringValue(String name, Formatter<T, String> formatter) {
        this.getBasicFilters().add(new StringValue(name, statisticsData, formatter));
        return this;
    }

    /**
     * 添加一个Integer类型值
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addIntegerValue(String name, RawDataHandler<T, String> handler) {
        this.getBasicFilters().add(new IntegerValue(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个Integer类型值
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addIntegerValue(String name, Formatter<T, String> formatter) {
        this.getBasicFilters().add(new IntegerValue(name, statisticsData, formatter));
        return this;
    }

    /**
     * 添加一个BigDecimal类型值
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addBigDecimalValue(String name, RawDataHandler<T, String> handler) {
        this.getBasicFilters().add(new BigDecimalValue(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个BigDecimal类型值
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addBigDecimalValue(String name, Formatter<T, String> formatter) {
        this.getBasicFilters().add(new BigDecimalValue(name, statisticsData, formatter));
        return this;
    }

    /**
     * 添加一个总值统计
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addTotalValue(String name, RawDataHandler<T, BigDecimal> handler) {
        this.getBasicFilters().add(new TotalValue(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个总值统计
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addTotalValue(String name, Formatter<T, BigDecimal> formatter) {
        this.getBasicFilters().add(new TotalValue(name, statisticsData, formatter));
        return this;
    }

    /**
     * 添加一个总次数统计
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addTotalCount(String name, RawDataHandler<T, Integer> handler) {
        this.getBasicFilters().add(new TotalCount(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个总次数统计
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addTotalCount(String name, Formatter<T, Integer> formatter) {
        this.getBasicFilters().add(new TotalCount(name, statisticsData, formatter));
        return this;
    }

    /**
     * 添加一个最大值统计
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addMaxValue(String name, RawDataHandler<T, BigDecimal> handler) {
        this.getBasicFilters().add(new MaxValue(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个最大值统计
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addMaxValue(String name, Formatter<T, BigDecimal> formatter) {
        this.getBasicFilters().add(new MaxValue(name, statisticsData, formatter));
        return this;
    }

    /**
     * 添加一个最小值统计
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addMinValue(String name, RawDataHandler<T, BigDecimal> handler) {
        this.getBasicFilters().add(new MinValue(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个最小值统计
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addMinValue(String name, Formatter<T, BigDecimal> formatter) {
        this.getBasicFilters().add(new MinValue(name, statisticsData, formatter));
        return this;
    }

    /**
     * 添加一个值对应出现次数统计
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addBigDecimalValueCount(String name, RawDataHandler<T, BigDecimal> handler) {
        this.getBasicFilters().add(new BigDecimalValueCount(name, statisticsData, handler));
        return this;
    }

    /**
     * 添加一个值对应出现次数统计
     *
     * @param name      统计名
     * @param formatter 原始数据回调
     * @return
     */
    public Statistics<T> addBigDecimalValueCount(String name, Formatter<T, BigDecimal> formatter) {
        this.getBasicFilters().add(new BigDecimalValueCount(name, statisticsData, formatter));
        return this;
    }


    /**
     * 添加一个平均值统计
     *
     * @param name           统计名
     * @param totalValueName 依赖的总值统计名
     * @param totalCountName 依赖的总数统计名
     * @return
     * @throws StatisticsException
     */
    public Statistics<T> addAverageValue(String name, String totalValueName, String totalCountName) {
        this.getAdvancedFilters().add(new AverageValue(name, statisticsData, totalValueName, totalCountName));
        return this;
    }

    /**
     * 添加一个中位数统计
     *
     * @param name           统计名
     * @param valueCountName 依赖的值次数统计名
     * @return
     * @throws StatisticsException
     */
    public Statistics<T> addMedianValue(String name, String valueCountName) {
        this.getAdvancedFilters().add(new MedianValue(name, statisticsData, valueCountName));
        return this;
    }

    /**
     * 添加一个众数统计
     *
     * @param name           统计名
     * @param valueCountName 依赖的值次数统计名
     * @return
     * @throws StatisticsException
     */
    public Statistics<T> addModeValue(String name, String valueCountName) {
        this.getAdvancedFilters().add(new ModeValue(name, statisticsData, valueCountName));
        return this;
    }

    /**
     * 添加一个标准差统计
     *
     * @param name           统计名
     * @param valueCountName 依赖的值次数统计名
     * @return
     * @throws StatisticsException
     */
    public Statistics<T> addStandardDeviation(String name, String valueCountName) throws StatisticsException {
        this.getAdvancedFilters().add(new StandardDeviation(name, statisticsData, valueCountName));
        return this;
    }
}
