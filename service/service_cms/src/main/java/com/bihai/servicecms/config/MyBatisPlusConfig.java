package com.bihai.servicecms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.bihai.common_utils.ResultData;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/*
 *@author bihai-ui
 *@create 2020-12-01 19:40
 */
@Configuration
@MapperScan(basePackages = "com.bihai.servicecms.mapper")
@ComponentScan(basePackages = "com.bihai")
public class MyBatisPlusConfig {


    /**
     * SQL 执行性能分析插件
     * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
     */
/*    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {

        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);//ms，超过此处设置的ms则sql不执行
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }*/

    /*
    * 配置元数据处理器，用于数据填充
    * */
    @Bean
    public MetaObjectHandler getMetaObjectHandler(){
        return new MetaObjectHandler() {

            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("gmtCreate", new Date(), metaObject);
                this.setFieldValByName("gmtModified", new Date(), metaObject);
                this.setFieldValByName("isDeleted",0,metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("gmtModified", new Date(), metaObject);
            }
        };
    }


    //配分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    //配置逻辑删除插件
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();

    }


    /**
     * 统一异常处理类
     */
    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        @ResponseBody
        public ResultData error(RuntimeException e){
            e.printStackTrace();
            return ResultData.error().message(e.getMessage());
        }

    }




}

