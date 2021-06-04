package com.bihai.servicevideo.service.impl;
/*
 *@author bihai-ui
 *@create 2020-12-17 13:33
 */

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.bihai.common_utils.exception.OlineEducationException;
import com.bihai.servicevideo.config.DefaultClient;
import com.bihai.servicevideo.config.OssMessageConfigure;
import com.bihai.servicevideo.service.AliyunVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AliyunVideoServiceImpl implements AliyunVideoService {
    @Autowired
    private DefaultClient defaultClient;

    @Autowired
    private OssMessageConfigure ossMessageConfigure;


    @Override
    public List<String> getPlayInfo(String id){
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        ArrayList<String> list = new ArrayList<>();

        try {
            //设置请求参数
            request.setVideoId(id);

            response = defaultClient.initVodClient().getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
                list.add(playInfo.getPlayURL());
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");

        return list;
    }

    @Override
    public String getVideoPlayAuth(String id) {
        //初始化客户端、请求对象和相应对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        String playAuth = null;

        try {

            //设置请求参数
            request.setVideoId(id);
            //获取请求响应
            response = defaultClient.initVodClient().getAcsResponse(request);

            //输出请求结果
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            playAuth = response.getPlayAuth();

            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");

        return playAuth;
    }

    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ossMessageConfigure.getKeyid(),
                    ossMessageConfigure.getKeysecret(),
                    title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new OlineEducationException(20001, errorMessage);
                }

            }

            return videoId;
        } catch (IOException e) {
            throw new OlineEducationException(20001, "guli vod 服务上传失败");
        }
    }

    @Override
    public void removeVideo(String videoId) {
        try{
            DefaultAcsClient client = defaultClient.initVodClient();

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        }catch (ClientException e){
            throw new OlineEducationException(20001, "视频删除失败");
        }
    }

    @Override
    public void removeVideoList(List<String> videoIdList) {
        try {
            //初始化
            DefaultAcsClient client = defaultClient.initVodClient();

            //创建请求对象
            //一次只能批量删20个
            String str = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(str);
            //获取响应
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");

        } catch (ClientException e) {
            throw new OlineEducationException(20001, "视频删除失败");
        }
    }

}

