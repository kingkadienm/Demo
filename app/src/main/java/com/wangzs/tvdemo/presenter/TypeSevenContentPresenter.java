package com.wangzs.tvdemo.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wangzs.tvdemo.R;
import com.wangzs.tvdemo.widgets.ImgConstraintLayout;
import com.wangzs.tvdemo.widgets.focus.MyFocusHighlightHelper;
import com.wangzs.tvdemo.bean.AppInfoBean;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/12/19.
 * ClassName  :
 * Description  :
 */
public class TypeSevenContentPresenter extends Presenter {
    public Context mContext;
    private MyFocusHighlightHelper.BrowseItemFocusHighlight mBrowseItemFocusHighlight;

    private static final String TAG = "TypeSevenContentPresenter";

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mBrowseItemFocusHighlight == null) {
            mBrowseItemFocusHighlight =
                    new MyFocusHighlightHelper
                            .BrowseItemFocusHighlight(FocusHighlight.ZOOM_FACTOR_MEDIUM, false);
        }
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_seven_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mClTypeSeven.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mBrowseItemFocusHighlight != null) {
                    mBrowseItemFocusHighlight.onItemFocused(v, hasFocus);
                }
                viewHolder.mClTypeSeven.onFocusChange(v, hasFocus);
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof AppInfoBean) {
            ViewHolder vh = (ViewHolder) viewHolder;
            if (((AppInfoBean) item).isBigPic()) {
                ImgConstraintLayout.LayoutParams params = (ImgConstraintLayout.LayoutParams) vh.mIvTypeSevenPoster.getLayoutParams();
                params.width = 408 * 2;
                params.height = 324 * 2;
                vh.mClTypeSeven.setLayoutParams(params);
                Glide.with(mContext).load(R.drawable.ic_launcher_background).into(vh.mIvTypeSevenPoster);
                Glide.with(mContext)
                        .load(((AppInfoBean) item).getAppVideo())
                        .apply(new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.bg_shape_default))
                        .into(vh.mIvTypeSevenPoster);
            } else {
                ImgConstraintLayout.LayoutParams params = (ImgConstraintLayout.LayoutParams) vh.mIvTypeSevenPoster.getLayoutParams();
                params.width = 408;
                params.height = 324;
                vh.mClTypeSeven.setLayoutParams(params);
                Glide.with(mContext).load(R.drawable.ic_launcher_background).into(vh.mIvTypeSevenPoster);
                Glide.with(mContext)
                        .load(((AppInfoBean) item).getAppCover())
                        .apply(new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.bg_shape_default))
                        .into(vh.mIvTypeSevenPoster);
            }

            String desc = ((AppInfoBean) item).getAppName();
            if (!TextUtils.isEmpty(desc)) {
                vh.mTvDesc.setText(desc);
            }
        }
    }


    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        final ImageView mIvTypeSevenPoster;
        final ImgConstraintLayout mClTypeSeven;
        private final TextView mTvDesc;

        public ViewHolder(View view) {
            super(view);
            mIvTypeSevenPoster = view.findViewById(R.id.iv_type_seven_poster);
            mClTypeSeven = view.findViewById(R.id.cl_type_seven);
            mTvDesc = view.findViewById(R.id.tv_desc);

        }
    }
}
