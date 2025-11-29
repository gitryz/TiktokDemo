package com.example.tiktokdemo.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tiktokdemo.R;
import com.example.tiktokdemo.ui.recyclerview.bean.FeedItem;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;        // 主图片
    public TextView tvTitle;           // 标题
    public ImageView ivUserAvatar;     // 用户头像
    public TextView tvUsername;        // 用户名
    public TextView tvLikeCount;       // 点赞数
    private ImageView ivLike;          // 点赞图标
    public ImageViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imgView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        ivUserAvatar = itemView.findViewById(R.id.iv_user_avatar);
        tvUsername = itemView.findViewById(R.id.tv_username);
        tvLikeCount = itemView.findViewById(R.id.tv_like_count);
        ivLike = itemView.findViewById(R.id.iv_like);
    }

    public void bindData(FeedItem feedItem) {
        // 设置主图片
        imageView.setImageResource(feedItem.getImageResId());
//        // 动态设置图片高度
//        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
//                // 根据图片宽度计算高度（保持宽高比）
//                int width = imageView.getWidth();
//                // 这里可以根据实际图片的宽高比来计算
//                // 示例：假设图片宽高比为 3:4
//                ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                params.height = (int) (width * 1.2f); // 可根据实际需求调整比例
//                imageView.setLayoutParams(params);
//                return true;
//            }
//        });

        // 设置标题
        tvTitle.setText(feedItem.getTitle());
        // 设置用户头像（动态加载）
        ivUserAvatar.setImageResource(feedItem.getAvatarResId());
        // 设置用户名
        tvUsername.setText(feedItem.getUsername());
        // 设置点赞数（将整数转换为字符串）
        tvLikeCount.setText(String.valueOf(feedItem.getLikeCount()));
        // 设置点赞状态
        updateLikeStatus(feedItem.isLiked());

//        监听点赞按钮
        ivLike.setOnClickListener(v -> {
            boolean newLikeStatus = !feedItem.isLiked();
            feedItem.setLiked(newLikeStatus);

            // 更新点赞数
            if (newLikeStatus) {
                feedItem.setLikeCount(feedItem.getLikeCount() + 1);
            } else {
                feedItem.setLikeCount(feedItem.getLikeCount() - 1);
            }

            tvLikeCount.setText(String.valueOf(feedItem.getLikeCount()));
            updateLikeStatus(newLikeStatus);
        });
    }

    private void updateLikeStatus(boolean isLiked) {
        if (isLiked) {
            ivLike.setImageResource(R.drawable.like_icon);
        } else {
            ivLike.setImageResource(R.drawable.untap_like_icon);
        }
    }
}

