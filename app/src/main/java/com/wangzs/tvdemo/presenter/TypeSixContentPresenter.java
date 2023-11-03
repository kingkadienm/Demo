package com.wangzs.tvdemo.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wangzs.tvdemo.R;
import com.wangzs.tvdemo.bean.AppInfoBean;


public class TypeSixContentPresenter extends Presenter {

    private Context mContext;

    private static final String TAG = "TypeSixContentPresenter";

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_six_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof AppInfoBean) {
            ViewHolder vh = (ViewHolder) viewHolder;
            Glide.with(mContext)
                    .load(((AppInfoBean) item).getAppCover())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.bg_shape_default))
                    .into(vh.mIvPoster);
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

        private final TextView mTvDesc;
        private final ImageView mIvPoster;

        public ViewHolder(View view) {
            super(view);
            mTvDesc = view.findViewById(R.id.tv_desc);
            mIvPoster = view.findViewById(R.id.iv_poster);
        }
    }

}
