package com.bihai.serviceedu.client;
/*
 *@author bihai-ui
 *@create 2020-12-18 21:10
 */

import com.bihai.common_utils.ResultData;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-video",fallback = VideoFileDegradeFeignClient.class)
@Component
public interface VideoClient {

    @DeleteMapping("/serviceVideo/aliyun/{videoId}")
    ResultData removeVideo(@PathVariable(name = "videoId") String videoId);

    @DeleteMapping("/serviceVideo/aliyun/videoIdList")
    ResultData removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);



}