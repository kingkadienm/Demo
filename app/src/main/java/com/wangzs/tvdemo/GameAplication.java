package com.wangzs.tvdemo;

import android.app.Application;

import com.hjq.http.EasyConfig;
import com.tencent.mmkv.MMKV;
import com.wangzs.tvdemo.http.model.RequestHandler;
import com.wangzs.tvdemo.http.model.RequestServer;

import okhttp3.OkHttpClient;

public class GameAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        MMKV.initialize(this);
        EasyConfig.with(okHttpClient)
                // 是否打印日志
                .setLogEnabled(false)
                // 设置服务器配置（必须设置）
                .setServer(new RequestServer())
                // 设置请求处理策略（必须设置）
                .setHandler(new RequestHandler(this))
                // 设置请求重试次数
                .setRetryCount(0)
                // 添加全局请求参数
                //.addParam("token", "6666666")
                // 添加全局请求头
                .addHeader("User-Agent", "Android_App")
                // 启用配置
                .into();
     }
}
