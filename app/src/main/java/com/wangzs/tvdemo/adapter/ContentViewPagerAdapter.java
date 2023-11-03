package com.wangzs.tvdemo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.wangzs.tvdemo.bean.AppTypeBean;
import com.wangzs.tvdemo.fragment.ContentFragment;

import java.util.List;


public class ContentViewPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static final String TAG = "ContentViewPagerAdapter";

    private List<AppTypeBean> dataBeans;

    public ContentViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //    @NonNull
    //    @Override
    //    public Fragment createFragment(int position) {
    //        return ContentFragment.newInstance(position, dataBeans.get(position).getTabCode());
    //    }
    //
    //    @Override
    //    public int getItemCount() {
    //        return dataBeans == null ? 0 : dataBeans.size();
    //
    //    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ContentFragment.newInstance(position, dataBeans.get(position).getAppType());
    }


    public void setData(List<AppTypeBean> dataBeans) {
        this.dataBeans = dataBeans;
    }


    @Override
    public int getCount() {
        return dataBeans == null ? 0 : dataBeans.size();
    }
}
