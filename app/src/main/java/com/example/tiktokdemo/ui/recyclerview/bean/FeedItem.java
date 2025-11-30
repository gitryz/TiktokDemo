package com.example.tiktokdemo.ui.recyclerview.bean;

public class FeedItem {
    private int imageResId;      // 主图片资源 ID
    private String imageUrl;     // 网络图片URL
    private String title;        // 标题文本
    private int avatarResId;     // 用户头像资源 ID
    private String userAvatarUrl;     // 网络头像URL
    private String username;     // 用户名
    private int likeCount;       // 点赞数
    private boolean isLiked; // 点赞状态
    private boolean isNetworkImage;
    public FeedItem(int imageResId, String title, int avatarResId, String username, int likeCount) {
        this.imageResId = imageResId;
        this.title = title;
        this.avatarResId = avatarResId;
        this.username = username;
        this.likeCount = likeCount;
        this.isLiked = false;
        this.isNetworkImage = false;
    }

    public FeedItem(String imageUrl, String title, String userAvatarUrl, String username, int likeCount) {
        this.imageUrl = imageUrl;
        this.userAvatarUrl = userAvatarUrl;
        this.title = title;
        this.username = username;
        this.likeCount = likeCount;
        this.isLiked = false;
        this.isNetworkImage = true;
    }
    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public String getUsername() {
        return username;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getAvatarUrl() {
        return userAvatarUrl;
    }
    public boolean isNetworkImage() {
        return isNetworkImage;
    }
}