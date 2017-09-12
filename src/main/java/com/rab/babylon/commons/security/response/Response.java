package com.rab.babylon.commons.security.response;

import org.apache.http.HttpStatus;

/**
 * 系统响应
 */
public class Response<T>{

    //响应状态码
    private Integer code;
    //信息
    private String msg;
    //业务结果
    private Boolean success;
    //响应数据
    private T result;

    public Response(){

    }

    public Response(String msg, T result){
        this.code = HttpStatus.SC_OK;
        this.success = true;
        this.msg = msg;
        this.result = result;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public Boolean getSuccess(){
        return success;
    }

    public void setSuccess(Boolean success){
        this.success = success;
    }

    public T getResult(){
        return result;
    }

    public void setResult(T result){
        this.result = result;
    }
}
