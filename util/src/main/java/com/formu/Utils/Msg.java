package com.formu.Utils;

/**
 * Created by weiqiang
 */


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class Msg<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private Msg(int status) {
        this.status = status;
    }

    private Msg(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private Msg(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private Msg(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess() {
        return this.status == 0;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }


    public static <T> Msg<T> createBySuccess() {
        return new Msg<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Msg<T> createBySuccessMessage(String msg) {
        return new Msg<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> Msg<T> createBySuccess(T data) {
        return new Msg<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> Msg<T> createBySuccess(String msg, T data) {
        return new Msg<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> Msg<T> createByError() {
        return new Msg<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }


    public static <T> Msg<T> createByErrorMessage(String errorMessage) {
        return new Msg<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> Msg<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new Msg<T>(errorCode, errorMessage);
    }
}