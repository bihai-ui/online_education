package com.bihai.serviceedu.exception;
/*
 *@author bihai-ui
 *@create 2020-12-14 11:01
 */


public class DeleteException extends RuntimeException {
    private String message;

    public DeleteException (String message){
        super(message);
        this.message = message;
    }


}