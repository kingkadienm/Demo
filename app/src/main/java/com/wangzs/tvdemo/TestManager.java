package com.wangzs.tvdemo;


import com.blankj.utilcode.util.LogUtils;
import com.wangzs.tvdemo.bean.AppInfoBean;

/**
 * Author: wangzs<br>
 * Created Date: 2023/10/30 16:01<br>
 */
public class TestManager implements UpdateGameVideo{

    private static TestManager singleton;

    public TestManager() {
    }

    public static TestManager getInstance() {
        if (singleton == null) {
            synchronized (TestManager.class) {
                if (singleton == null) {
                    singleton = new TestManager();
                }
            }
        }
        return singleton;
    }

    private UpdateGameVideo updateGameVideo;

    public UpdateGameVideo getUpdateGameVideo() {
        return updateGameVideo;
    }

    public void setUpdateGameVideo(UpdateGameVideo updateGameVideo) {
        this.updateGameVideo = updateGameVideo;
    }

    @Override
    public void onUpdateVideo(AppInfoBean appInfoBean) {
        LogUtils.e("manage  " , appInfoBean.toString());
//        if (updateGameVideo != null) {
//            updateGameVideo.onUpdateVideo(appInfo);
//        }
    }
}
