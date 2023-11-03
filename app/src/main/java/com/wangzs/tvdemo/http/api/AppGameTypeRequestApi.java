package com.wangzs.tvdemo.http.api;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestCache;
import com.hjq.http.model.CacheMode;

public class AppGameTypeRequestApi implements IRequestApi, IRequestCache {
    @NonNull
    @Override
    public String getApi() {
        return "app/appGameTypeList";
    }


    @NonNull
    @Override
    public CacheMode getCacheMode() {
        // 设置优先使用缓存
        return CacheMode.USE_CACHE_FIRST;
    }

    /**
     * 设置缓存 10分钟
     *
     * @return
     */
    @Override
    public long getCacheTime() {
        return 1000 * 60 * 10;
    }
}
