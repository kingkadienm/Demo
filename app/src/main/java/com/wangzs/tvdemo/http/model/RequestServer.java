package com.wangzs.tvdemo.http.model;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestBodyStrategy;
import com.hjq.http.config.IRequestServer;
import com.hjq.http.model.*;
public class RequestServer implements IRequestServer {

    @NonNull
    @Override
    public String getHost() {
        return "http://119.255.235.41:8080/";
    }

    @NonNull
    @Override
    public IRequestBodyStrategy getBodyType() {
        // 参数以 Json 格式提交（默认是表单）
        return RequestBodyType.JSON;
    }

    @NonNull
    @Override
    public CacheMode getCacheMode() {
        // 只在请求失败才去读缓存
        return CacheMode.USE_CACHE_FIRST;
    }
    @NonNull
    @Override
    public long getCacheTime() {
        return 1000 * 60 * 10;
    }
}