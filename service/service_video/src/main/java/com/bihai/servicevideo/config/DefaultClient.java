package com.bihai.servicevideo.config;
/*
 *@author bihai-ui
 *@create 2020-12-17 13:34
 */

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultClient {
    @Autowired
    private OssMessageConfigure ossMessageConfigure;

    public  DefaultAcsClient initVodClient() throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ossMessageConfigure.getKeyid(),ossMessageConfigure.getKeysecret());
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}