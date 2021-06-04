package com.bihai.statistics_daily.client;
/*
 *@author bihai-ui
 *@create 2021-01-08 17:01
 */

import com.bihai.common_utils.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "service-ucenter")
@Component
public interface UcenterClient {

    //每日注册数
    @GetMapping("/serviceunceter/ucenter-member/countregister/{day}")
    ResultData dailyregister(@PathVariable(name = "day") String date);

}