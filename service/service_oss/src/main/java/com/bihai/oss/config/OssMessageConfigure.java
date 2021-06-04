package com.bihai.oss.config;
/*
 *@author bihai-ui
 *@create 2020-12-08 16:50
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.oss.file")
@Component
@Data
public class OssMessageConfigure {

    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;


}