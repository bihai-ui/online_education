package com.bihai.serviceunceter;
/*
 *@author bihai-ui
 *@create 2020-12-28 16:15
 */


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class UncenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UncenterApplication.class,args);
    }
}