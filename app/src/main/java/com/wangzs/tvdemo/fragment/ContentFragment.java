package com.wangzs.tvdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.Presenter;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.hjq.http.EasyHttp;
import com.hjq.http.lifecycle.ApplicationLifecycle;
import com.hjq.http.listener.HttpCallbackProxy;
import com.hjq.http.listener.OnHttpListener;
import com.wangzs.tvdemo.MainActivity;
import com.wangzs.tvdemo.R;
import com.wangzs.tvdemo.TestManager;
import com.wangzs.tvdemo.UpdateGameVideo;
import com.wangzs.tvdemo.base.BaseLazyLoadFragment;
import com.wangzs.tvdemo.bean.AppInfoBean;
import com.wangzs.tvdemo.bean.TypeSeven;
import com.wangzs.tvdemo.content.ContentPresenterSelector;
import com.wangzs.tvdemo.http.api.AppInfoRequestApi;
import com.wangzs.tvdemo.http.model.HttpPageData;
import com.wangzs.tvdemo.presenter.TypeSevenPresenter;
import com.wangzs.tvdemo.presenter.TypeSixContentPresenter;
import com.wangzs.tvdemo.widgets.TabVerticalGridView;

import java.util.ArrayList;
import java.util.List;


public class ContentFragment extends BaseLazyLoadFragment implements OnHttpListener<Object>, UpdateGameVideo {

    private static final String TAG = "ContentFragment";

    private static final String BUNDLE_KEY_POSITION = "bundleKeyPosition";
    private static final String BUNDLE_KEY_TAB_CODE = "bundleKeyTabCode";


    private AppInfoBean appInfoBean = new AppInfoBean();


    private TabVerticalGridView mVerticalGridView;
    private MainActivity mActivity;
    private View mRootView;

    private ProgressBar mPbLoading;
    private ArrayObjectAdapter mAdapter;

    private int mCurrentTabPosition;
    private int mCurrentTabCode;
    private List<AppInfoBean> sevenList;
    private ContentPresenterSelector presenterSelector;

    @Override
    public void onHttpSuccess(Object result) {

    }

    @Override
    public void onHttpFail(Exception e) {

    }


    public static ContentFragment newInstance(int position, int tabCode) {
        Log.e(TAG + " pos:" + position, "new Instance status: " + position + " tab:" + tabCode);
        ContentFragment fragment = new ContentFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_POSITION, position);
        bundle.putInt(BUNDLE_KEY_TAB_CODE, tabCode);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        mActivity = (MainActivity) context;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG + " pos:", "onCreate: ");
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mCurrentTabPosition = getArguments().getInt(BUNDLE_KEY_POSITION);
        mCurrentTabCode = getArguments().getInt(BUNDLE_KEY_TAB_CODE);
        Log.e(TAG + " pos:" + mCurrentTabPosition, " tabCode: " + mCurrentTabCode);


        TestManager.getInstance().setUpdateGameVideo(this);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_content, container, false);
            initView();
            initListener();
            fetchData();
        }
        return mRootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initView() {
        mPbLoading = mRootView.findViewById(R.id.pb_loading);
        mVerticalGridView = mRootView.findViewById(R.id.hg_content);
        mVerticalGridView.setTabView(mActivity.getHorizontalGridView());
        mVerticalGridView.setGroup(mActivity.getGroup());
        presenterSelector = new ContentPresenterSelector();

        mAdapter = new ArrayObjectAdapter(presenterSelector);
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mAdapter);
        mVerticalGridView.setAdapter(itemBridgeAdapter);

    }

    private void initListener() {
        mVerticalGridView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mVerticalGridView != null) {
            mVerticalGridView.removeOnScrollListener(onScrollListener);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint mCurrentTabPosition: " + mCurrentTabPosition
                + " isVisibleToUser:" + isVisibleToUser);
        if (!isVisibleToUser) {
            scrollToTop();
        }
    }

    @Override
    public void fetchData() {
        loadData();
    }


    private void loadData() {
        mPbLoading.setVisibility(View.VISIBLE);
        mVerticalGridView.setVisibility(View.INVISIBLE);

        EasyHttp.get(ApplicationLifecycle.getInstance())
                .api(new AppInfoRequestApi()
                        .setPageNo(1)
                        .setPageSize(999)
                        .setAppType(mCurrentTabCode)
                )
                .request(new HttpCallbackProxy<HttpPageData<AppInfoBean>>(this) {

                    @Override
                    public void onHttpSuccess(HttpPageData<AppInfoBean> result) {

                        HttpPageData.ListBean<AppInfoBean> appInfoBeanListBean = result.getResult();

                        if (appInfoBeanListBean == null || appInfoBeanListBean.getData() == null) {
                            return;
                        }
                        List<AppInfoBean> dataBeans = appInfoBeanListBean.getData();

                        if (mCurrentTabCode < 0) {
                            return;
                        }
                        List<AppInfoBean> sixList = new ArrayList<>();
                        sevenList = new ArrayList<>();
                        for (int i = 0; i < dataBeans.size(); i++) {
                            if (i < 4) {
                                sevenList.add(dataBeans.get(i));
                            } else {
                                sixList.add(dataBeans.get(i));
                            }
                        }

                        TypeSeven typeSeven = new TypeSeven();
                        sevenList.add(0, appInfoBean.setBigPic(true));
                        typeSeven.setWidgetsBeanList(sevenList);
                        addWithTryCatch(typeSeven);

                        ArrayObjectAdapter arrayObjectAdapterSix = new ArrayObjectAdapter(new TypeSixContentPresenter());
                        arrayObjectAdapterSix.addAll(0, sixList);
                        ListRow listRowSix = new ListRow(arrayObjectAdapterSix);
                        addWithTryCatch(listRowSix);

                        mPbLoading.setVisibility(View.GONE);
                        mVerticalGridView.setVisibility(View.VISIBLE);

                    }
                });
    }


    private void scrollToTop() {
        if (mVerticalGridView != null) {
            mVerticalGridView.scrollToPosition(0);
            if (mActivity.getGroup() != null && mActivity.getGroup().getVisibility() != View.VISIBLE) {
                mActivity.getGroup().setVisibility(View.VISIBLE);
            }
        }
    }


    private final RecyclerView.OnScrollListener onScrollListener
            = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                case RecyclerView.SCROLL_STATE_SETTLING:
                    Glide.with(mActivity).pauseRequests();
                    break;
                case RecyclerView.SCROLL_STATE_IDLE:
                    Glide.with(mActivity).resumeRequests();
            }
        }
    };


    private void addWithTryCatch(Object item) {
        try {
            if (!mVerticalGridView.isComputingLayout()) {
                mAdapter.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateVideo(AppInfoBean appInfoBean) {
        Presenter[] presenters = presenterSelector.getPresenters();
        for (Presenter presenter : presenters) {
            if (presenter instanceof TypeSevenPresenter) {
                TypeSevenPresenter typeSevenPresenter = (TypeSevenPresenter) presenter;
                TypeSevenPresenter.ViewHolder vh = typeSevenPresenter.getVh();
                ArrayObjectAdapter arrayObjectAdapter = vh.getmArrayObjectAdapter();
                AppInfoBean bean = (AppInfoBean) arrayObjectAdapter.get(0);
                bean.setAppName("哈哈哈");
                bean.setAppCover(appInfoBean.getAppCover());
                LogUtils.e("222222222", bean);
                arrayObjectAdapter.notifyItemRangeChanged(0,1,bean);
//                arrayObjectAdapter.notifyItemRangeChanged(0, 1);
                break;
            }
        }
    }
}
