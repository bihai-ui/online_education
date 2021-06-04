package com.bihai.statistics_daily.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.bihai.common_utils.ResultData;
import com.bihai.statistics_daily.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author bihai_ui
 * @since 2021-01-08
 */
@RestController
@RequestMapping("/statistics_daily/statistics-daily")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService service;

    @PostMapping("statisticsDaily/{day}")
    public ResultData createStatisticsByDate(@PathVariable(name = "day") String day) {
        service.createStatisticsByDay(day);
        return ResultData.success();
    }

    @GetMapping("statisticsDaily/{startTime}/{endTime}/{type}")
    public ResultData getChartsData(@PathVariable("startTime") String startTime,
                                    @PathVariable("endTime") String endTime,
                                    @PathVariable("type") String type){

        Map<String, Object> map = service.getChartData(startTime, endTime, type);
        return ResultData.success().data(map);
    }


}

