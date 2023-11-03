package com.wangzs.tvdemo.http.api;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;

public class AppInfoRequestApi implements IRequestApi {
    @NonNull
    @Override
    public String getApi() {
        return "app/appGameList";
    }


    private int pageNo;
    private int pageSize;

    private Integer appType;

    public AppInfoRequestApi setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public AppInfoRequestApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public AppInfoRequestApi setAppType(Integer appType) {
        this.appType = appType;
        return this;
    }
}
