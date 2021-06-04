package com.bihai.statistics_daily.service;

import com.bihai.statistics_daily.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //生成每日统计数据
    void createStatisticsByDay(String day);

    Map<String,Object> getChartData(String beginTime,String endTime, String type);
}
