package com.avalon.holygrail.statistics.bean;

import com.avalon.holygrail.statistics.exception.StatisticsException;
import com.avalon.holygrail.statistics.model.*;
import com.avalon.holygrail.statistics.norm.DataContainer;
import com.avalon.holygrail.statistics.norm.Formatter;
import com.avalon.holygrail.statistics.norm.RawDataHandler;
import com.avalon.holygrail.util.CollectionUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * 统计
 * Created by 白超 on 2018/3/9.
 */
public final class Statistics<T> {

    @FunctionalInterface
    public interface Group<T> {
        String apply(T record);
    }

    public Statistics() {
        this.init();
    }

    /**
     * 统计数据容器
     */
    private List<StatisticsData> statisticsDataList = new ArrayList<>();
    /**
     * 分组统计数据容器
     */
    private List<Map<String, StatisticsData>> statisticsDataMapList = new ArrayList<>();

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
     * 高阶统计过滤器
     */
    private List<List<SeniorStatisticsFilter>> seniorStatisticsFilterList = new ArrayList<>();

    /**
     * 高阶统计过滤链
     */
    private SeniorFilterChain seniorFilterChain = new SeniorFilterChain();

    /**
     * 最终统计过滤器
     */
    private List<List<FinalStatisticsFilter>> finalStatisticsFilterList = new ArrayList<>();

    /**
     * 最终统计过滤链
     */
    private FinalFilterChain finalFilterChain = new FinalFilterChain();

    /**
     * 汇总过滤器
     */
    private List<List<CollectStatisticsFilter>> collectStatisticsFilterList = new ArrayList<>();

    /**
     * 汇总过滤链
     */
    private CollectFilterChain collectFilterChain = new CollectFilterChain();

    /**
     * 用于记录获取最新统计所需参数的下标
     */
    private int index = -1;

    private void init() {
        this.index++;
        this.statisticsDataList.add(this.index, new StatisticsData());
        this.statisticsDataMapList.add(this.index, new LinkedHashMap<>());

        this.basicStatisticsFilterList.add(this.index, new ArrayList<>());
        this.advancedStatisticsFilterList.add(this.index, new ArrayList<>());
        this.seniorStatisticsFilterList.add(this.index, new ArrayList<>());
        this.finalStatisticsFilterList.add(this.index, new ArrayList<>());
        this.collectStatisticsFilterList.add(this.index, new ArrayList<>());
    }

    private StatisticsData getStatisticsData() {
        return this.statisticsDataList.get(this.index);
    }

    private StatisticsData getStatisticsData(int index) {
        return this.statisticsDataList.get(index);
    }

    private Map<String, StatisticsData> getStatisticsDataMap() {
        return this.statisticsDataMapList.get(this.index);
    }

    private Map<String, StatisticsData> getStatisticsDataMap(int index) {
        return this.statisticsDataMapList.get(index);
    }

    private List<BasicStatisticsFilter<T, Object>> getBasicFilters() {
        return this.basicStatisticsFilterList.get(this.index);
    }

    private List<AdvancedStatisticsFilter> getAdvancedFilters() {
        return this.advancedStatisticsFilterList.get(this.index);
    }

    private List<SeniorStatisticsFilter> getSeniorFilters() {
        return this.seniorStatisticsFilterList.get(this.index);
    }

    private List<FinalStatisticsFilter> getFinalFilters() {
        return this.finalStatisticsFilterList.get(this.index);
    }

    private List<CollectStatisticsFilter> getCollectFilters() {
        return this.collectStatisticsFilterList.get(this.index);
    }

    /**
     * 开始统计
     *
     * @param records 数据
     * @return
     */
    public Statistics<T> start(Collection<T> records) throws Exception {
        this.basicFilterChain.setFilters(this.getBasicFilters());
        Iterator<T> iterator = records.iterator();
        StatisticsData container = this.getStatisticsData();
        while (iterator.hasNext()) {
            this.basicFilterChain.doFilter(iterator.next(), container);
        }
        List<AdvancedStatisticsFilter> advancedStatisticsFilters = this.getAdvancedFilters();
        if (advancedStatisticsFilters.size() > 0) {
            this.advancedFilterChain.setFilters(this.getAdvancedFilters());
            this.advancedFilterChain.doFilter(container, container);
        }
        List<SeniorStatisticsFilter> seniorStatisticsFilters = this.getSeniorFilters();
        if (seniorStatisticsFilters.size() > 0) {
            this.seniorFilterChain.setFilters(this.getSeniorFilters());
            this.seniorFilterChain.doFilter(container, container);
        }
        List<FinalStatisticsFilter> finalStatisticsFilters = this.getFinalFilters();
        if (finalStatisticsFilters.size() > 0) {
            this.finalFilterChain.setFilters(finalStatisticsFilters);
            this.finalFilterChain.doFilter(container, container);
        }
        List<CollectStatisticsFilter> collectStatisticsFilters = this.getCollectFilters();
        if (collectStatisticsFilters.size() > 0) {
            this.collectFilterChain.setFilters(collectStatisticsFilters);
            this.collectFilterChain.doFilter(CollectionUtil.newArrayList(container), container);
        }
        this.init();
        return this;
    }

    /**
     * 分组统计
     *
     * @param records 数据
     * @param group   分组回调,需要返回标识key
     * @return
     * @throws Exception
     */
    public Statistics<T> groupStart(Collection<T> records, Group<T> group) throws Exception {
        StatisticsData previousContainer;//上一次统计存储容器
        Map<String, StatisticsData> previousContainers;//上一次分组统计结果
        //获取上一次统计的结果,作为这次统计的参数,如果没有上次,则新建
        if (this.index > 0) {
            previousContainer = this.getStatisticsData(this.index - 1);
            previousContainers = this.getStatisticsDataMap(this.index - 1);
        } else {
            previousContainer = new StatisticsData();
            previousContainers = new LinkedHashMap<>();
        }
        //获取最新的容器
        Map<String, StatisticsData> containers = this.getStatisticsDataMap();
        //构建基础统计过滤器
        this.basicFilterChain.setFilters(this.getBasicFilters());
        Iterator<T> iterator = records.iterator();
        StringBuilder key = new StringBuilder();
        T record;
        //执行基础统计
        StatisticsData container;//容器
        while (iterator.hasNext()) {
            record = iterator.next();
            key.replace(0, key.length(), group.apply(record));
            container = containers.get(key.toString());
            if (container == null) {
                container = new StatisticsData();
                containers.put(key.toString(), container);
            }
            this.basicFilterChain.doFilter(record, container);
        }
        //基础数据统计完毕
        //准备进阶+高阶统计
        StatisticsData param;//参数
        List<AdvancedStatisticsFilter> advancedStatisticsFilters = this.getAdvancedFilters();
        List<SeniorStatisticsFilter> seniorStatisticsFilters = this.getSeniorFilters();
        List<FinalStatisticsFilter> finalStatisticsFilters = this.getFinalFilters();
        List<CollectStatisticsFilter> collectStatisticsFilters = this.getCollectFilters();
        for (Map.Entry<String, StatisticsData> entry : containers.entrySet()) {
            //尝试获取上一次统计结果
            param = previousContainers.get(entry.getKey());
            //上一次分组统计没找到,使用单次统计的
            if (param == null) {
                param = new StatisticsData().merge(previousContainer);
            }
            //合并上次统计结果+本次基础统计结果作为参数,如果key相同,最新的优先
            container = entry.getValue();
            if (advancedStatisticsFilters.size() > 0) {
                param.merge(container);
                this.advancedFilterChain.setFilters(this.getAdvancedFilters());
                this.advancedFilterChain.doFilter(param, container);
            }
            if (seniorStatisticsFilters.size() > 0) {
                param.merge(container);
                this.seniorFilterChain.setFilters(this.getSeniorFilters());
                this.seniorFilterChain.doFilter(param, container);
            }
            if (finalStatisticsFilters.size() > 0) {
                param.merge(container);
                this.finalFilterChain.setFilters(finalStatisticsFilters);
                this.finalFilterChain.doFilter(param, container);
            }
            container.merge(param);
        }
        if (collectStatisticsFilters.size() > 0) {
            this.collectFilterChain.setFilters(collectStatisticsFilters);
            this.collectFilterChain.doFilter(containers.values(), null);
        }
        this.init();
        return this;
    }

    /**
     * 获取统计结果
     *
     * @return
     */
    public StatisticsData getResult() {
        return this.getStatisticsData(this.index - 1);
    }

    /**
     * 获取一组统计结果集合
     *
     * @return
     */
    public Map<String, StatisticsData> getResults() {
        return this.getStatisticsDataMap(this.index - 1);
    }

    /**
     * 添加一个String类型值
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addStringValue(String name, RawDataHandler<T, String> handler) {
        this.getBasicFilters().add(new StringValue(name, this.getStatisticsData(), handler));
        return this;
    }

    /**
     * 添加一个String类型值
     *
     * @param formatterName 统计名回调
     * @param handler       原始数据回调
     * @return
     */
    public Statistics<T> addStringValue(StatisticsFilter.FormatterName<T, String> formatterName, RawDataHandler<T, String> handler) {
        this.getBasicFilters().add(new StringValue(formatterName, this.getStatisticsData(), handler));
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
        this.getBasicFilters().add(new StringValue(name, this.getStatisticsData(), formatter));
        return this;
    }

    /**
     * 添加一个Integer类型值
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addIntegerValue(String name, RawDataHandler<T, Integer> handler) {
        this.getBasicFilters().add(new IntegerValue(name, this.getStatisticsData(), handler));
        return this;
    }

    /**
     * 添加一个Integer类型值
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addIntegerValue(String name, Formatter<T, Integer> formatter) {
        this.getBasicFilters().add(new IntegerValue(name, this.getStatisticsData(), formatter));
        return this;
    }

    /**
     * 添加一个BigDecimal类型值
     *
     * @param name    统计名
     * @param handler 原始数据回调
     * @return
     */
    public Statistics<T> addBigDecimalValue(String name, RawDataHandler<T, BigDecimal> handler) {
        this.getBasicFilters().add(new BigDecimalValue(name, this.getStatisticsData(), handler));
        return this;
    }

    /**
     * 添加一个BigDecimal类型值
     *
     * @param name      统计名
     * @param formatter 格式化回调
     * @return
     */
    public Statistics<T> addBigDecimalValue(String name, Formatter<T, BigDecimal> formatter) {
        this.getBasicFilters().add(new BigDecimalValue(name, this.getStatisticsData(), formatter));
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
        this.getBasicFilters().add(new TotalValue(name, this.getStatisticsData(), handler));
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
        this.getBasicFilters().add(new TotalValue(name, this.getStatisticsData(), formatter));
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
        this.getBasicFilters().add(new TotalCount(name, this.getStatisticsData(), handler));
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
        this.getBasicFilters().add(new TotalCount(name, this.getStatisticsData(), formatter));
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
        this.getBasicFilters().add(new MaxValue(name, this.getStatisticsData(), handler));
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
        this.getBasicFilters().add(new MaxValue(name, this.getStatisticsData(), formatter));
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
        this.getBasicFilters().add(new MinValue(name, this.getStatisticsData(), handler));
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
        this.getBasicFilters().add(new MinValue(name, this.getStatisticsData(), formatter));
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
        this.getBasicFilters().add(new BigDecimalValueCount(name, this.getStatisticsData(), handler));
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
        this.getBasicFilters().add(new BigDecimalValueCount(name, this.getStatisticsData(), formatter));
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
        this.getAdvancedFilters().add(new AverageValue(name, this.getStatisticsData(), totalValueName, totalCountName));
        return this;
    }

    /**
     * 添加一个平均值统计
     *
     * @param name           统计名
     * @param valueCountName 依赖的总次数统计名
     * @return
     * @throws StatisticsException
     */
    public Statistics<T> addAverageValue(String name, String valueCountName) {
        this.getAdvancedFilters().add(new AverageValue(name, this.getStatisticsData(), valueCountName));
        return this;
    }

    /**
     * 添加一个平均值统计
     *
     * @param formatterName  统计名回调
     * @param valueCountName 依赖的总次数统计名
     * @return
     * @throws StatisticsException
     */
    public Statistics<T> addAverageValue(StatisticsFilter.FormatterName<DataContainer, BigDecimal> formatterName, String valueCountName) {
        this.getAdvancedFilters().add(new AverageValue(formatterName, this.getStatisticsData(), valueCountName));
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
        this.getAdvancedFilters().add(new MedianValue(name, this.getStatisticsData(), valueCountName));
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
        this.getAdvancedFilters().add(new ModeValue(name, this.getStatisticsData(), valueCountName));
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
    public Statistics<T> addStandardDeviation(String name, String valueCountName) {
        this.getAdvancedFilters().add(new StandardDeviation(name, this.getStatisticsData(), valueCountName));
        return this;
    }

    /**
     * 添加一个标准分统计
     *
     * @param name                       统计名
     * @param originalValueName          原值值名称
     * @param referAverageScoreName      参考范围平均值名称
     * @param referStandardDeviationName 参考范围标准差名称
     * @return
     */
    public Statistics<T> addStandardScore(String name, String originalValueName, String referAverageScoreName, String referStandardDeviationName) {
        this.getSeniorFilters().add(new StandardScore(name, this.getStatisticsData(), originalValueName, referAverageScoreName, referStandardDeviationName));
        return this;
    }

    /**
     * 添加一个标准分统计
     *
     * @param name                       统计名
     * @param originalValueName          原值值名称
     * @param referAverageScoreName      参考范围平均值名称
     * @param referStandardDeviationName 参考范围标准差名称
     * @return
     */
    public Statistics<T> addStandardScore(String name, String originalValueName, String referAverageScoreName, String referStandardDeviationName, Formatter<DataContainer, DataContainer> formatter) {
        this.getSeniorFilters().add(new StandardScore(name, this.getStatisticsData(), originalValueName, referAverageScoreName, referStandardDeviationName, formatter));
        return this;
    }

    /**
     * 添加一个得分率统计
     *
     * @param name              统计名
     * @param originalValueName 原值值名称
     * @param totalValueName    总值名称
     * @return
     */
    public Statistics<T> addScoreRate(String name, String originalValueName, String totalValueName) {
        this.getSeniorFilters().add(new ScoreRate(name, this.getStatisticsData(), originalValueName, totalValueName));
        return this;
    }

    /**
     * 添加一个超均率统计
     *
     * @param name                  统计名
     * @param originalValueName     原值值名称
     * @param referAverageScoreName 参考范围平均值名称
     * @return
     */
    public Statistics<T> addHyperAverageRate(String name, String originalValueName, String referAverageScoreName) {
        this.getSeniorFilters().add(new HyperAverageRate(name, this.getStatisticsData(), originalValueName, referAverageScoreName));
        return this;
    }

    /**
     * 添加一个排名统计
     *
     * @param name                统计名
     * @param originalValueName   原值值名称
     * @param referValueCountName 参考值集合名称
     * @return
     */
    public Statistics<T> addRank(String name, String originalValueName, String referValueCountName) {
        this.getFinalFilters().add(new Rank(name, this.getStatisticsData(), originalValueName, referValueCountName));
        return this;
    }

    /**
     * 添加汇总值汇总到值次数
     *
     * @param name            汇总名
     * @param targetValueName 目标数据名称
     * @return
     */
    public Statistics<T> addCollectValueToValueCount(String name, String targetValueName) {
        this.getCollectFilters().add(new CollectValueToValueCount(name, this.getStatisticsData(), targetValueName));
        return this;
    }

    /**
     * 添加汇总值汇总到值次数
     *
     * @param name            汇总名
     * @param targetValueName 目标数据名称
     * @param formatter       格式化回调函数
     * @return
     */
    public Statistics<T> addCollectValueToValueCount(String name, String targetValueName, Formatter<Collection<DataContainer>, Collection<DataContainer>> formatter) {
        this.getCollectFilters().add(new CollectValueToValueCount(name, this.getStatisticsData(), targetValueName, formatter));
        return this;
    }

}
