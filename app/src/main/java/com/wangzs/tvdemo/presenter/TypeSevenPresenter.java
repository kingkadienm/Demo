package com.wangzs.tvdemo.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.BaseGridView;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.Presenter;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.wangzs.tvdemo.R;
import com.wangzs.tvdemo.TestManager;
import com.wangzs.tvdemo.UpdateGameVideo;
import com.wangzs.tvdemo.bean.TypeSeven;
import com.wangzs.tvdemo.widgets.focus.MyItemBridgeAdapter;
import com.wangzs.tvdemo.bean.AppInfoBean;

import java.util.List;


public class TypeSevenPresenter extends Presenter {

    private Context mContext;
    private ViewHolder vh;


    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.type_seven_layout, parent, false);
        return new ViewHolder(view);
    }

    public ViewHolder getVh() {
        return vh;
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof TypeSeven) {
            vh = (ViewHolder) viewHolder;
            vh.updatePresenter(((TypeSeven) item).getWidgetsBeanList(), mContext);
        }

    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private final HorizontalGridView mHorizontalGridView;
        private GridLayoutManager mGridLayoutManager;
        private ArrayObjectAdapter mArrayObjectAdapter;

        public ArrayObjectAdapter getmArrayObjectAdapter() {
            return mArrayObjectAdapter;
        }

        @SuppressLint("RestrictedApi")
        public ViewHolder(View view) {
            super(view);
            mHorizontalGridView = view.findViewById(R.id.hg_seven);
            mHorizontalGridView.setNumRows(2);
            mGridLayoutManager = new GridLayoutManager(view.getContext(),
                    2, GridLayoutManager.HORIZONTAL, false);
            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 0 ? 2 : 1;
                }
            });
            mHorizontalGridView.setFocusScrollStrategy(BaseGridView.FOCUS_SCROLL_ITEM);
            mHorizontalGridView.setLayoutManager(mGridLayoutManager);

        }

        private void updatePresenter(List<AppInfoBean> widgetsBeanList, Context mContext) {
            mArrayObjectAdapter = new ArrayObjectAdapter(new TypeSevenContentPresenter());

            MyItemBridgeAdapter myItemBridgeAdapter = new MyItemBridgeAdapter(mArrayObjectAdapter) {
                @Override
                public OnItemViewClickedListener getOnItemViewClickedListener() {
                    return new OnItemViewClickedListener() {
                        @Override
                        public void onItemClicked(View focusView,
                                                  Presenter.ViewHolder itemViewHolder,
                                                  Object item) {
                            if (item instanceof AppInfoBean) {
                                ToastUtils.showLong(((AppInfoBean) item).getAppName());
                            }
                        }
                    };
                }

                @Override
                public OnItemFocusChangedListener getOnItemFocusChangedListener() {
                    return new OnItemFocusChangedListener() {
                        @Override
                        public void onItemFocusChanged(View focusView, Presenter.ViewHolder itemViewHolder,
                                                       Object item, boolean hasFocus, int pos) {
                            UpdateGameVideo updateGameVideo = TestManager.getInstance().getUpdateGameVideo();
                             if (updateGameVideo != null) {
                                if (item instanceof AppInfoBean) {
                                    updateGameVideo.onUpdateVideo((AppInfoBean) item);
                                }
                            }
                        }
                    };
                }
            };
            mHorizontalGridView.setAdapter(myItemBridgeAdapter);
            mArrayObjectAdapter.addAll(0, widgetsBeanList);
        }
    }
}
