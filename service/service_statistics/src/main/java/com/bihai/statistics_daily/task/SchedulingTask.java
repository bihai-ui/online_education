package com.bihai.statistics_daily.task;
/*
 *@author bihai-ui
 *@create 2021-01-08 19:01
 */

import com.bihai.statistics_daily.service.StatisticsDailyService;
import com.bihai.statistics_daily.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SchedulingTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //每日12点0分1秒执行
    @Scheduled(cron = "1 0 0 * * ?")
    public void dailySaveStatisticsTask(){
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.createStatisticsByDay(day);
    }

}