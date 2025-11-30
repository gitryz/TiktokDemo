package com.example.tiktokdemo.ui.recyclerview.viewholder;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
        final int[] imageWidth = new int[1];
        final int[] imageHeight = new int[1];
        // 设置主图片
        // 设置用户头像
        if(feedItem.isNetworkImage()){
            Glide.with(itemView.getContext())
                    .load(feedItem.getImageUrl())
//                    .placeholder(R.drawable.image1) // 占位图
//                    .error(R.drawable.image1)       // 错误时显示
                    .centerCrop()
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            imageWidth[0] = resource.getIntrinsicWidth();
                            imageHeight[0] = resource.getIntrinsicHeight();
                            return false;
                        }
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            imageHeight[0] = dpToPx(600);
                            return false;
                        }
                    })
                    .into(imageView);
            Glide.with(itemView.getContext())
                    .load(feedItem.getAvatarUrl())
//                    .placeholder(R.drawable.avator_1)
//                    .error(R.drawable.avator_1)
                    .circleCrop() // 圆形头像
                    .into(ivUserAvatar);
        }else{
            imageView.setImageResource(feedItem.getImageResId());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(itemView.getContext().getResources(), feedItem.getImageResId(), options);
            imageWidth[0] = options.outWidth;
            imageHeight[0] = options.outHeight;
            ivUserAvatar.setImageResource(feedItem.getAvatarResId());
        }

        // 动态设置图片高度
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                // 根据图片宽度计算高度（保持宽高比）
                int viewWidth = imageView.getWidth();
                if (viewWidth == 0) {
                    // 如果View还没有测量完成，使用父容器的宽度
                    viewWidth = itemView.getWidth() -
                            itemView.getPaddingLeft() - itemView.getPaddingRight();
                }

                // 根据图片原始宽高比计算高度
                float aspectRatio = (float) imageHeight[0] / imageWidth[0];
                int targetHeight = (int) (viewWidth * aspectRatio);

                // 设置最大最小高度限制
                int minHeight = dpToPx(260); // 最小150dp
                int maxHeight = dpToPx(600); // 最大600dp

                if (targetHeight < minHeight) {
                    targetHeight = minHeight;
                } else if (targetHeight > maxHeight) {
                    targetHeight = maxHeight;
                }

                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.height = targetHeight;
                imageView.setLayoutParams(params);
                return true;
            }
        });

        // 设置标题
        tvTitle.setText(feedItem.getTitle());
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

    private int dpToPx(int dp) {
        return (int) (dp * itemView.getContext().getResources().getDisplayMetrics().density);
    }
}
