package com.bihai.servicevideo;
/*
 *@author bihai-ui
 *@create 2020-12-08 16:38
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class SpringBootApplicationVideo {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationVideo.class,args);
    }
}