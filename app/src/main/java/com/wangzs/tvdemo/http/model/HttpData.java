package com.wangzs.tvdemo.http.model;


import androidx.annotation.Nullable;

import okhttp3.Headers;


public class HttpData<T> {

    /** 响应头 */
    @Nullable
    private Headers responseHeaders;

    /** 返回码 */
    private int code;
    /** 提示语 */
    private String message;
    /** 数据 */
    @Nullable
    private T result;

    public void setResponseHeaders(@Nullable Headers responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    @Nullable
    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public T getResult() {
        return result;
    }

    /**
     * 是否请求成功
     */
    public boolean isRequestSuccess() {
        return code == 200;
    }

    /**
     * 是否 Token 失效
     */
    public boolean isTokenInvalidation() {
        return code == 1001;
    }
}