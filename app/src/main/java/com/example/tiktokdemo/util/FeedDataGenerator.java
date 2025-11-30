package com.example.tiktokdemo.util;

import android.content.Context;

import com.example.tiktokdemo.R;
import com.example.tiktokdemo.ui.recyclerview.bean.FeedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FeedDataGenerator {
    private static int currentPage = 0;
    private static final Random random = new Random(); // ignore_security_alert [ByDesign7.4]WeakPRNG

    // 1. 姓氏和名字数据源（用于随机组合名称）
    static String[] familyNames = {"张", "李", "王", "赵", "刘", "陈", "杨", "黄", "周", "吴", "徐", "孙", "胡", "朱", "高", "林", "何", "郭", "马", "罗",
            "梁", "宋", "郑", "谢", "韩", "唐", "冯", "于", "董", "萧", "程", "曹", "袁", "邓", "许", "傅", "沈", "曾", "彭", "吕"};
    static String[] givenNames = {"明", "华", "强", "伟", "芳", "丽", "敏", "军", "杰", "娜", "涛", "超", "静", "艳", "鹏", "辉", "玲", "刚", "平", "燕",
            "峰", "霞", "龙", "红", "宇", "玉", "桂", "林", "丹", "梅", "建", "辉", "倩", "欣", "蕾", "晨", "晨", "璐", "鑫", "莹", "婷", "浩"};

    // 2. 描述模板（随机选择）
    static String[] titleTemplates = {
            "把生活调成自己喜欢的频道",
            "今日份温柔碎片已加载完毕",
            "藏在镜头里的诗意瞬间",
            "这一刻，风和我都是自由的",
            "是心情满分的一天！",
            "慢品人间烟火色",
            "一不小心，闯进了秋天的童话",
            "治愈我的，永远是这些小事",
            "无滤镜的快乐，谁懂啊！ ",
            "是心动啊，对着光发会儿呆",
            "不会还有人不知道这个宝藏地吧？",
            "跟着我拍，轻松拍出氛围感大片",
            "猜猜我在哪？",
            "第1张可以做头像吗？",
            "发现了一个只有1%的人知道的秘密基地",
            "听劝！这样拍真的好看吗？",
            "这个地方，我还能再去一百次！",
            "是谁的DNA动了？哦，是我的。",
            "秩序与美",
            "光影是最好的滤镜",
            "日常的仪式感",
            "一些碎片，一些日常"
    };

    // 主图片资源数组
    static int[] imgIds = new int[]{R.drawable.image1, R.drawable.avator_2, R.drawable.image2, R.drawable.image3, R.drawable.avator_3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6};

    static int[] avatarIds = new int[]{R.drawable.avator_1, R.drawable.avator_4, R.drawable.avator_5, R.drawable.avator_6, R.drawable.avator_7
            , R.drawable.avator_8, R.drawable.avator_9, R.drawable.avator_10, R.drawable.avator_11, R.drawable.avator_12, R.drawable.avator_13, R.drawable.avator_14};

    // 网络图片URL列表
    private static final String[] NETWORK_IMAGE_URLS = {
            "https://picsum.photos/400/600?random=",
            "https://picsum.photos/400/500?random=",
            "https://picsum.photos/400/700?random=",
            "https://picsum.photos/400/550?random=",
            "https://picsum.photos/400/650?random=",
            "https://picsum.photos/400/600?random=",
            "https://picsum.photos/400/580?random=",
            "https://picsum.photos/400/620?random="
    };

    private static final String[] NETWORK_AVATAR_URLS = {
            "https://i.pravatar.cc/150?img="
    };

    // 生成初始数据
    public static List<FeedItem> generateInitialData(int count) {
        currentPage = 0;
        return generateFeedList(count,false);
    }

    // 生成更多数据（分页加载）
    public static List<FeedItem> generateMoreData(int count) {
        currentPage++;
        return generateFeedList(count,true);
    }

    // 刷新数据
    public static List<FeedItem> refreshData(int count) {
        currentPage = 0;
        return generateFeedList(count,true);
    }

    public static List<FeedItem> generateFeedList(int count,boolean useNetworkImage) {
        List<FeedItem> feedList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            FeedItem item;
            if (useNetworkImage){
                item = new FeedItem(
                        getRandomNetworkImage(),
                        getRandomTitle(),
                        getRandomNetworkAvatar(),
                        generateRandomName(),
                        getRandomLikeCount()
                );
            }else {
                item = new FeedItem(
                        getRandomImage(),
                        getRandomTitle(),
                        getRandomAvatar(),
                        generateRandomName(),
                        getRandomLikeCount()
                );
            }
            feedList.add(item);
        }

        return feedList;
    }

    private static String generateRandomName() {
        String family = familyNames[random.nextInt(familyNames.length)];
        int nameLength = random.nextBoolean() ? 1 : 2;
        StringBuilder given = new StringBuilder();

        for (int i = 0; i < nameLength; i++) {
            given.append(givenNames[random.nextInt(givenNames.length)]);
        }

        return family + given.toString();
    }

    private static String getRandomTitle() {
        return titleTemplates[random.nextInt(titleTemplates.length)];
    }

    private static int getRandomImage() {
        return imgIds[random.nextInt(imgIds.length)];
    }

    private static int getRandomAvatar() {
        return avatarIds[random.nextInt(avatarIds.length)];
    }
    private static int getRandomLikeCount() {
        return random.nextInt(1000);
    }

    private static String getRandomNetworkImage(){
        return  NETWORK_IMAGE_URLS[random.nextInt(NETWORK_IMAGE_URLS.length)]+String.valueOf(random.nextInt(200));
    }

    private static String getRandomNetworkAvatar(){
        return NETWORK_AVATAR_URLS[0]+String.valueOf(random.nextInt(70));
//        return "https://i.pravatar.cc/150?img="+String.valueOf(random.nextInt(70));
    }
}