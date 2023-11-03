package com.wangzs.tvdemo.bean;


public class AppTypeBean {
    private Long id;

    //游戏描述
    private String description;

    //游戏类型
    private int appType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    @Override
    public String toString() {
        return "AppTypeBean{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", appType=" + appType +
                '}';
    }
}
