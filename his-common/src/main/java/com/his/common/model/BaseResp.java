package com.his.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据返回统一格式
 * @author Administrator
 *
 * @param <T>
 */
public class BaseResp<T> implements Serializable{
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息描述
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private long currentTime = new Date().getTime();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    /**
     *
     * @param code 错误码
     * @param message 信息
     * @param data 数据
     */
    public BaseResp(){
        this.code = ResultStatus.SUCCESS.getErrorCode();
        this.message = ResultStatus.SUCCESS.getErrorMsg();
    }
    public BaseResp(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.currentTime = System.currentTimeMillis();
    }
    
    public BaseResp(T data) {
        this.code = ResultStatus.SUCCESS.getErrorCode();
        this.message = ResultStatus.SUCCESS.getErrorMsg();
        this.data = data;
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * 不带数据的返回结果
     * @param resultStatus
     */
    public BaseResp(ResultStatus resultStatus) {
        this.code = resultStatus.getErrorCode();
        this.message = resultStatus.getErrorMsg();
        this.data = data;
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * 带数据的返回结果
     * @param resultStatus
     * @param data
     */
    public BaseResp(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getErrorCode();
        this.message = resultStatus.getErrorMsg();
        this.data = data;
        this.currentTime = System.currentTimeMillis();
    }

}
