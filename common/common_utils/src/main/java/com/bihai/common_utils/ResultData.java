package com.bihai.common_utils;
/*
 *@author bihai-ui
 *@create 2020-12-01 21:50
 */


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ResultData {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data;

    private ResultData(){

    }

    public static ResultData  success(){
        ResultData data = new ResultData();
        data.setSuccess(true);
        data.setCode(Code.success);
        data.setMessage("成功");

        return data;
    }

    public static ResultData error(){
        ResultData data = new ResultData();
        data.setSuccess(false);
        data.setCode(Code.error);
        data.setMessage("失败");
        return data;
    }

    //链式编程
    public ResultData code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultData message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultData data(Map<String,Object> map){
        this.setData(map);
        return this;
    }





}