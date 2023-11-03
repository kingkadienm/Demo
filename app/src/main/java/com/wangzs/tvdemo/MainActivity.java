package com.wangzs.tvdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallbackProxy;
import com.hjq.http.listener.OnHttpListener;
import com.wangzs.tvdemo.adapter.ContentViewPagerAdapter;
import com.wangzs.tvdemo.bean.AppTypeBean;
import com.wangzs.tvdemo.http.api.AppGameTypeRequestApi;
import com.wangzs.tvdemo.http.model.HttpData;
import com.wangzs.tvdemo.presenter.TitlePresenter;
import com.wangzs.tvdemo.widgets.TabVerticalGridView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ViewTreeObserver.OnGlobalFocusChangeListener, OnHttpListener<Object> {

    private static final String TAG = "MainActivity";
    private TextView mOldTitle;

    private ArrayObjectAdapter mArrayObjectAdapter;

    private int mCurrentPageIndex = 0;
    private boolean isSkipTabFromViewPager = false;

    public ArrayObjectAdapter getArrayObjectAdapter() {
        return mArrayObjectAdapter;
    }

    public TabVerticalGridView getHorizontalGridView() {
        return mVerticalGridView;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    private long remainingTime = -1L;

     @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
//        Log.e(TAG, "onGlobalFocusChanged newFocus: " + newFocus);
//        Log.e(TAG, "onGlobalFocusChanged oldFocus: " + oldFocus);
        if (newFocus == null || oldFocus == null) {
            return;
        }
        if (newFocus.getId() == R.id.tv_main_title
                && oldFocus.getId() == R.id.tv_main_title) {
            ((TextView) newFocus).setTextColor(getResources().getColor(R.color.colorWhite));
            ((TextView) newFocus).getPaint().setFakeBoldText(true);
            ((TextView) oldFocus).setTextColor(getResources().getColor(R.color.colorWhite));
            ((TextView) oldFocus).getPaint().setFakeBoldText(false);
        } else if (newFocus.getId() == R.id.tv_main_title
                && oldFocus.getId() != R.id.tv_main_title) {
            ((TextView) newFocus).setTextColor(getResources().getColor(R.color.colorWhite));
            ((TextView) newFocus).getPaint().setFakeBoldText(true);
        } else if (newFocus.getId() != R.id.tv_main_title
                && oldFocus.getId() == R.id.tv_main_title) {
            ((TextView) oldFocus).setTextColor(getResources().getColor(R.color.colorBlue));
            ((TextView) oldFocus).getPaint().setFakeBoldText(true);
        }
    }




    @Override
    protected void onDestroy() {
        mVerticalGridView.removeOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
        getWindow().getDecorView().getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
        super.onDestroy();

    }

    private TabVerticalGridView mVerticalGridView;
    private MyViewPager mViewPager;
    private Group mGroup;

    public Group getGroup() {
        return mGroup;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    protected void initView() {
        mVerticalGridView = findViewById(R.id.hg_title);
        mViewPager = findViewById(R.id.vp_content);
        mGroup = findViewById(R.id.id_group);
        mViewPager.setOffscreenPageLimit(5);
        mArrayObjectAdapter = new ArrayObjectAdapter(new TitlePresenter());
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mArrayObjectAdapter);
        mVerticalGridView.setAdapter(itemBridgeAdapter);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(itemBridgeAdapter,
                FocusHighlight.ZOOM_FACTOR_XSMALL, false);
        initListener();
        initBroadCast();
        ImageView qrView = findViewById(R.id.qrView);

    }


    protected void initData() {

        EasyHttp.get(this)
                .api(new AppGameTypeRequestApi())
                .request(new HttpCallbackProxy<HttpData<List<AppTypeBean>>>(this) {
                    @Override
                    public void onHttpSuccess(HttpData<List<AppTypeBean>> result) {
                        List<AppTypeBean> dataBeans = result.getResult();

                        ArrayObjectAdapter adapter = getArrayObjectAdapter();
                        if (adapter != null) {
                            adapter.addAll(0, dataBeans);
                            initViewPager(dataBeans);
                            TabVerticalGridView horizontalGridView = getHorizontalGridView();
                            if (dataBeans.size() > 0) {
                                if (horizontalGridView != null) {
                                    horizontalGridView.requestFocus();
                                    horizontalGridView.setSelectedPositionSmooth(0);
                                    View positionView = horizontalGridView.getChildAt(0);
                                    if (positionView != null) {
                                        mOldTitle = positionView.findViewById(R.id.tv_main_title);
                                    }
                                }
                            } else if (dataBeans.size() > 0) {
                                if (getHorizontalGridView() != null) {
                                    horizontalGridView.setSelectedPositionSmooth(0);
                                    View position0 = horizontalGridView.getChildAt(0);
                                    if (position0 != null) {
                                        mOldTitle = position0.findViewById(R.id.tv_main_title);
                                    }
                                }
                            }
                        }
                    }
                });
//        EasyHttp.get(this)
//                .server("http://192.168.5.6:8080/")
//                .api("im/genUserSig?userID=wangzs")
//                .request(new HttpCallbackProxy<HttpData<String>>(this) {
//
//                    @Override
//                    public void onHttpSuccess(HttpData<String> result) {
//                        Log.e("=====", result.getResult());
//                        IMConfig.getInstance().loginIM("wangzs", result.getResult());
//                    }
//                });


    }


    private void initListener() {
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalFocusChangeListener(this);
        mVerticalGridView.addOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

    private void initBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

    }

    private boolean isFirstIn = true;

    private void initViewPager(List<AppTypeBean> dataBeans) {

        ContentViewPagerAdapter viewPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.setData(dataBeans);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected position: " + position);
                if (isFirstIn) {
                    isFirstIn = false;
                } else {
                    isSkipTabFromViewPager = true;
                }
                if (position != mCurrentPageIndex) {
//                    mCurrentPageIndex = position;
                    mVerticalGridView.setSelectedPosition(position);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setGoToNextPage(new MyViewPager.GoToNextPage() {
            @Override
            public void goToNetxPage() {
                mVerticalGridView.requestFocus();
            }
        });
    }


    private final OnChildViewHolderSelectedListener onChildViewHolderSelectedListener
            = new OnChildViewHolderSelectedListener() {
        @Override
        public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
            super.onChildViewHolderSelected(parent, child, position, subposition);
            if (child != null & position != mCurrentPageIndex) {
                Log.e(TAG, "onChildViewHolderSelected: 000 isSkipTabFromViewPager" + isSkipTabFromViewPager);
                TextView currentTitle = child.itemView.findViewById(R.id.tv_main_title);
                if (isSkipTabFromViewPager) {
                    Log.e(TAG, "onChildViewHolderSelected: 111");
                    if (mOldTitle != null) {
                        Log.e(TAG, "onChildViewHolderSelected: 222");

                        mOldTitle.setTextColor(getResources().getColor(R.color.colorWhite));
                        Paint paint = mOldTitle.getPaint();
                        if (paint != null) {
                            paint.setFakeBoldText(false);
                            //viewpager切页标题不刷新，调用invalidate刷新
                            mOldTitle.invalidate();
                        }
                    }
                    currentTitle.setTextColor(getResources().getColor(R.color.colorBlue));
                    Paint paint = currentTitle.getPaint();
                    if (paint != null) {
                        paint.setFakeBoldText(true);
                        //viewpager切页标题不刷新，调用invalidate刷新
                        currentTitle.invalidate();
                    }
                }
                mOldTitle = currentTitle;
            }

            isSkipTabFromViewPager = false;
            Log.e(TAG, "onChildViewHolderSelected mViewPager != null: " + (mViewPager != null)
                    + " position:" + position);
            setCurrentItemPosition(position);

        }
    };

    private void setCurrentItemPosition(int position) {
        if (mViewPager != null && position != mCurrentPageIndex) {
            mCurrentPageIndex = position;
            mViewPager.setCurrentItem(position);
        }
    }


    @Override
    public void onHttpSuccess(Object result) {

    }

    @Override
    public void onHttpFail(Exception e) {

    }
}

