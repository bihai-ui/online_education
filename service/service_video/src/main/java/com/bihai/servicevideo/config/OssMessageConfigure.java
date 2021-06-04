package com.bihai.servicevideo.config;
/*
 *@author bihai-ui
 *@create 2020-12-08 16:50
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.oss.file")
@Component
@Data
@ComponentScan(basePackages = "com.bihai")
public class OssMessageConfigure {

    private String keyid;
    private String keysecret;

}