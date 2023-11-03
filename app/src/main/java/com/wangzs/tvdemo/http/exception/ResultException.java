package com.wangzs.tvdemo.http.exception;

import androidx.annotation.NonNull;

import com.hjq.http.exception.HttpException;
import com.wangzs.tvdemo.http.model.HttpData;


public final class ResultException extends HttpException {

    private final HttpData<?> mData;

    public ResultException(String message, HttpData<?> data) {
        super(message);
        mData = data;
    }

    public ResultException(String message, Throwable cause, HttpData<?> data) {
        super(message, cause);
        mData = data;
    }

    @NonNull
    public HttpData<?> getHttpData() {
        return mData;
    }
}