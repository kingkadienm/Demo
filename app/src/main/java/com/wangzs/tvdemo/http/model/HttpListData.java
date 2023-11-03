package com.wangzs.tvdemo.http.model;

import java.util.List;

/**
 * Author: wangzs<br>
 * Created Date: 2023/10/30 11:46<br>
 */
public class HttpListData<T> extends HttpData<HttpListData.ListBean<T>> {

    public static class ListBean<T> {
        private List<T> data;

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }
    }
}
