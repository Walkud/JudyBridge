package com.walkud.judy.api;

/**
 * JudyBridge返回结果
 * Created by Zhuliya on 2018/8/10
 */
public class JudyResult<T> {

    private boolean success;//是否成功
    private String msg;//信息
    private T data;//返回数据

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
