package com.wangzs.tvdemo.bean;

public class AppInfoBean extends BaseBean {
    //编号
    private Long id;
    //游戏名字
    private String appName;
    //游戏缩略图
    private String appCover;
    //游戏手柄示意图
    private String appOperate;
    //游戏描述
    private String description;
    //游戏缩略视频
    private String appVideo;
    //游戏类型
    private int appType;
    private boolean isBigPic;

    public boolean isBigPic() {
        return isBigPic;
    }

    public AppInfoBean setBigPic(boolean bigPic) {
        isBigPic = bigPic;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCover() {
        return appCover;
    }

    public void setAppCover(String appCover) {
        this.appCover = appCover;
    }

    public String getAppOperate() {
        return appOperate;
    }

    public void setAppOperate(String appOperate) {
        this.appOperate = appOperate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppVideo() {
        return appVideo;
    }

    public void setAppVideo(String appVideo) {
        this.appVideo = appVideo;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", appCover='" + appCover + '\'' +
                ", appOperate='" + appOperate + '\'' +
                ", description='" + description + '\'' +
                ", appVideo='" + appVideo + '\'' +
                ", appType=" + appType +
                ", isBigPic=" + isBigPic +
                '}';
    }
}
