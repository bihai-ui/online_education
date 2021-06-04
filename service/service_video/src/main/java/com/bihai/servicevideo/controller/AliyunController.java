package com.bihai.servicevideo.controller;
/*
 *@author bihai-ui
 *@create 2020-12-17 13:34
 */

import com.baomidou.mybatisplus.extension.api.R;
import com.bihai.common_utils.ResultData;
import com.bihai.servicevideo.service.AliyunVideoService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/serviceVideo/aliyun")
//@CrossOrigin
public class AliyunController {

    @Autowired
    private AliyunVideoService aliyunVideoService;

    @GetMapping("playUrl/{id}")
    public ResultData getVideoPlayUrl(@PathVariable("id") String id){
        List<String> playInfo = aliyunVideoService.getPlayInfo(id);

        if(playInfo != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("urlList",playInfo);
            return ResultData.success().data(map);
        }
        return ResultData.error();
    }

    @GetMapping("playAuth/{id}")
    public ResultData getPlayAuth(@PathVariable("id") String id){
        String playAuth = aliyunVideoService.getVideoPlayAuth(id);
        if(playAuth != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("playAuth",playAuth);
            return ResultData.success().data(map);
        }
        return ResultData.error();
    }

    @PostMapping("upload")
    public ResultData uploadVideo( @RequestParam("file") MultipartFile file){
        String videoId = aliyunVideoService.uploadVideo(file);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",videoId);
        return  ResultData.success().data(map);
    }

    @DeleteMapping("{videoId}")
    public ResultData removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                                  @PathVariable String videoId){
        aliyunVideoService.removeVideo(videoId);
        System.out.println("视频删除成功");
        return ResultData.success().message("视频删除成功");
    }

    @DeleteMapping("videoIdList")
    public ResultData removeVideoList(@ApiParam(name = "videoIdList", value = "云端视频id", required = true)
                                      @RequestParam("videoIdList") List<String> videoIdList){

        aliyunVideoService.removeVideoList(videoIdList);
        System.out.println("视频删除成功");
        return ResultData.success().message("视频删除成功");
    }


}