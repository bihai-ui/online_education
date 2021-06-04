package com.bihai.servicevideo.service;
/*
 *@author bihai-ui
 *@create 2020-12-17 13:33
 */

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AliyunVideoService {


    //获取播放地址
    List<String> getPlayInfo(String id);


    //获取播放凭证
    String getVideoPlayAuth(String id);


    //上传视频
    String uploadVideo(MultipartFile file);

    //删除视频
    void removeVideo(String videoId);

    //批量删除视频
    void removeVideoList(List<String> videoIdList);


}
