package com.wangzs.tvdemo.http.api;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;

public class VodVideoRequestApi implements IRequestApi {
    @NonNull
    @Override
    public String getApi() {
        return "vod/vodVideoList";
    }


    private int pageNo;
    private int pageSize;

    private Integer appType;

    public VodVideoRequestApi setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public VodVideoRequestApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public VodVideoRequestApi setAppType(Integer appType) {
        this.appType = appType;
        return this;
    }
}
