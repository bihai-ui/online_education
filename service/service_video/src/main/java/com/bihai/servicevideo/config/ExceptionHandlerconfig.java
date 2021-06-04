package com.bihai.servicevideo.config;
/*
 *@author bihai-ui
 *@create 2020-12-17 15:47
 */

import com.bihai.common_utils.ResultData;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
public class ExceptionHandlerconfig {

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