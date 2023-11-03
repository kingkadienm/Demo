package com.wangzs.tvdemo.presenter.row;

import android.annotation.SuppressLint;

import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;

import com.blankj.utilcode.util.ToastUtils;
import com.wangzs.tvdemo.base.BaseListRowPresenter;
import com.wangzs.tvdemo.bean.AppInfoBean;


public class TypeZeroListRowPresenter extends BaseListRowPresenter {
    @SuppressLint("RestrictedApi")
    @Override
    protected void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final ViewHolder rowViewHolder = (ViewHolder) holder;

        rowViewHolder.getGridView().setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_ITEM);
        setOnItemViewClickedListener(new BaseOnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder,
                                      Object item, RowPresenter.ViewHolder rowViewHolder, Object row) {

                if (item instanceof AppInfoBean) {
                    ToastUtils.showLong(((AppInfoBean) item).getAppName());
                }

            }
        });


    }
}
