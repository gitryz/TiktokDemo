package com.example.tiktokdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tiktokdemo.R;
import com.example.tiktokdemo.ui.recyclerview.adapter.StaggeredAdapter;
import com.example.tiktokdemo.ui.recyclerview.bean.FeedItem;
import com.example.tiktokdemo.util.FeedDataGenerator;

import java.util.List;
import java.util.Random;

public class ExperienceFragment extends Fragment {
    private RecyclerView recyclerView;
    private StaggeredAdapter adapter;
    private boolean isLoading = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int PAGE_SIZE = 10;
    public ExperienceFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experience,container,false);
        initViews(view);
        setupRecyclerView();
        setupSwipeRefresh();
//        recyclerView = view.findViewById(R.id.recycler_view);
//        List<FeedItem> feedItems = FeedDataGenerator.generateFeedList(20);
//        StaggeredAdapter staggeredAdapter = new StaggeredAdapter(requireContext(), feedItems);
//        //2列垂直瀑布流
//        StaggeredGridLayoutManager staggeredLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredLayoutManager);
//        //创建Adapter并设置给RecyclerView
//        recyclerView.setAdapter(staggeredAdapter);
////        feedItems = new ArrayList<>();
////        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
    }

    private void setupRecyclerView() {
        // 设置瀑布流布局
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // 初始化适配器
        List<FeedItem> initialData = FeedDataGenerator.generateInitialData(PAGE_SIZE);
        adapter = new StaggeredAdapter(getContext(), initialData);
        recyclerView.setAdapter(adapter);

        // 设置上拉加载更多监听
        adapter.setOnLoadMoreListener(new StaggeredAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isLoading) {
                    loadMoreData();
                }
            }
        });
    }

    private void setupSwipeRefresh() {

        // 设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void loadMoreData() {
        isLoading = true;

        // 模拟网络延迟
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<FeedItem> moreData = FeedDataGenerator.generateMoreData(PAGE_SIZE);
                adapter.addData(moreData);
                isLoading = false;
            }
        }, 2000); // 延迟1秒模拟加载
    }

    private void refreshData() {
        // 模拟网络延迟
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<FeedItem> newData = FeedDataGenerator.refreshData(PAGE_SIZE);
                adapter.refreshData(newData);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500); // 延迟1秒模拟刷新
    }
}


