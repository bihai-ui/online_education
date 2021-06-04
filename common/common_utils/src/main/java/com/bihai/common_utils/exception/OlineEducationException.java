package com.bihai.common_utils.exception;
/*
 *@author bihai-ui
 *@create 2020-12-17 15:46
 */


public class OlineEducationException extends RuntimeException{
    private Integer status;

    public OlineEducationException (Integer status,String message){
        super(message);
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }
}