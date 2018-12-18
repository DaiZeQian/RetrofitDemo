package com.dzq.net;

/**
 * Created by admin on 2018/12/17.
 * <p>
 * 基础接收类
 * 需要根据需求看是否通用 如果不通用可以恢复成原始数据
 */

public class BaseRespose<T> {

    private String code;
    private String msg;
    private T data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
