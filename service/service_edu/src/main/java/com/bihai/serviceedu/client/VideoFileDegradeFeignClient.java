package com.bihai.serviceedu.client;
/*
 *@author bihai-ui
 *@create 2020-12-18 21:55
 */

import com.bihai.common_utils.ResultData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoFileDegradeFeignClient implements VideoClient{

    @Override
    public ResultData removeVideo(String videoId) {
        return ResultData.error().message("time out");
    }


    @Override
    public ResultData removeVideoList(List videoIdList) {
        return ResultData.error().message("time out");
    }
}