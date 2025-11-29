package com.example.tiktokdemo.ui.recyclerview.bean;

public class UserBean {
    // 用户名
    private String name;
    // 简介
    private String desc;
    // 头像资源ID
    private int avatarResId;

    // 构造方法
    public UserBean(String name, String desc, int avatarResId) {
        this.name = name;
        this.desc = desc;
        this.avatarResId = avatarResId;
    }

    // Getter和Setter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
    }

}
