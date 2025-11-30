package com.example.tiktokdemo.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiktokdemo.R;
import com.example.tiktokdemo.ui.recyclerview.bean.FeedItem;
import com.example.tiktokdemo.ui.recyclerview.viewholder.ImageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StaggeredAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private LayoutInflater mInflater;
    private int[] imgIds;
    private List<FeedItem> feedItems;
    private List<Integer> heights;
    private OnLoadMoreListener onLoadMoreListener;
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    public StaggeredAdapter(Context context,  List<FeedItem> feedItems) {
        mInflater = LayoutInflater.from(context);
//        this.heights = new ArrayList<>();
//        for (int i = 0; i < imgIds.length; i++) {
//            this.heights.add((int) (300 + Math.random() * 300));// ignore_security_alert [ByDesign7.4]WeakPRNG
//        }
        this.feedItems = feedItems;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
//        控制 Item 高度
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//        lp.height = heights.get(position);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 自适应内容高度
        holder.itemView.setLayoutParams(lp);
        FeedItem item = feedItems.get(position);
////         设置主图片
//        holder.imageView.setBackgroundResource(item.getImageResId());
        //      绑定其他数据
        holder.bindData(item);
        // 触发加载更多（滑动到倒数第3个item时触发）
        if (position == feedItems.size() - 3 && onLoadMoreListener != null) {
            onLoadMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    // 添加新数据（上拉加载更多）
    public void addData(List<FeedItem> newItems) {
        if (newItems != null && !newItems.isEmpty()) {
            int startPosition = feedItems.size();
            feedItems.addAll(newItems);
            notifyItemRangeInserted(startPosition, newItems.size());
        }
    }

    // 刷新数据（下拉刷新）
    public void refreshData(List<FeedItem> newItems) {
        if (newItems != null) {
            feedItems.clear();
            feedItems.addAll(newItems);
            notifyDataSetChanged();
        }
    }

    // 获取当前数据
    public List<FeedItem> getData() {
        return feedItems;
    }
}
