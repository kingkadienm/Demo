package com.wangzs.tvdemo.http.model;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2020/10/07
 *    desc   : 统一接口列表数据结构
 */
public class HttpPageData<T> extends HttpData<HttpPageData.ListBean<T>> {

    public static class ListBean<T> {

        /** 当前页码 */
        private int pageNo;
        /** 页大小 */
        private int pageSize;
        /** 总数量 */
        private int totalCount;
        /** 数据 */
        private List<T> data;

        /**
         * 判断是否是最后一页
         */
        public boolean isLastPage() {
            return Math.ceil((float) totalCount / pageSize) <= pageNo;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public int getPageNo() {
            return pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public List<T> getData() {
            return data;
        }
    }
}