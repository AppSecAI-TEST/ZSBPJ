package com.lcc.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcc.activity.R;
import com.lcc.entity.CommentEntity;
import com.lcc.utils.DateUtils;
import com.lcc.view.CommentTextView;

import zsbpj.lccpj.utils.AppUtils;
import zsbpj.lccpj.view.recyclerview.adapter.LoadMoreRecyclerAdapter;

public class CommentsAdapter extends LoadMoreRecyclerAdapter<CommentEntity, CommentsAdapter.ViewHolder> {

    private Activity mActivity;
    private OnCommentItemClickListener onCommentItemClickListener;

    public CommentsAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comment_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(final ViewHolder holder, int position) {
        final CommentEntity entity = getItem(position);
        holder.mTextViewScreenName.setText(entity.getUser().getScreen_name());
        holder.mTextViewCreatedAt.setText(DateUtils.getDifference(entity.getCreated_at()));
        holder.mTextViewContent.setData(entity.getContent(), new
                CommentTextView.OnSpanonClickListener() {
                    @Override
                    public void onClick(String spanStr) {
                        onCommentItemClickListener.onClickAtFriend(spanStr);
                    }
                });
        setLikedCount(holder.mTextViewLikedCount, entity);
        if (entity.getLiked()) {
            holder.mImageViewThumbUp.setImageResource(R.mipmap.ic_thumb_up_red_18dp);
        } else {
            holder.mImageViewThumbUp.setImageResource(R.mipmap.ic_thumb_up_gray_18dp);
        }
        // TODO: 16/3/6 去加载一张图片 
        //AppUtils.loadSmallUserAvata(mActivity,entity.getUser(), holder.mImageViewAvatar);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommentItemClickListener.onItemClick(entity);
            }
        });

        holder.mImageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommentItemClickListener.onClickAvatar(entity.getUser().getId());
            }
        });

        holder.mImageViewThumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.thumb_up_scale);
                if (entity.getLiked()) {
                    entity.setLiked(false);
                    entity.setLiked_count(entity.getLiked_count() - 1);
                    holder.mImageViewThumbUp.setImageResource(R.mipmap.ic_thumb_up_gray_18dp);
                    holder.mImageViewThumbUp.startAnimation(animation);
                    onCommentItemClickListener.thumbDown(entity.getId());
                } else {
                    entity.setLiked_count(entity.getLiked_count() + 1);
                    entity.setLiked(true);
                    holder.mImageViewThumbUp.setImageResource(R.mipmap.ic_thumb_up_red_18dp);
                    holder.mImageViewThumbUp.startAnimation(animation);
                    onCommentItemClickListener.thumbUp(entity.getId());
                }
                setLikedCount(holder.mTextViewLikedCount, entity);
            }
        });
    }

    private void setLikedCount(TextView textView, CommentEntity entity) {
        int liked_count = entity.getLiked_count();
        if (liked_count > 0) {
            textView.setText(entity.getLiked_count() + "");
        } else {
            textView.setText("赞");
        }
    }

    public void setOnCommentItemClickListener(OnCommentItemClickListener onCommentItemClickListener) {
        this.onCommentItemClickListener = onCommentItemClickListener;
    }

    public interface OnCommentItemClickListener {
        void onItemClick(CommentEntity commentEntity);


        void onClickAvatar(int uid);


        void onClickAtFriend(String screen_name);


        void thumbUp(int id);


        void thumbDown(int id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageViewAvatar;
        private final TextView mTextViewScreenName;
        private final TextView mTextViewCreatedAt;
        private final CommentTextView mTextViewContent;
        private final TextView mTextViewLikedCount;
        private final ImageView mImageViewThumbUp;
        private final View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mImageViewThumbUp = (ImageView) view.findViewById(R.id.imageView_thumb_up);
            mImageViewAvatar = (ImageView) view.findViewById(R.id.imageView_avatar);
            mTextViewScreenName = (TextView) view.findViewById(R.id.textView_screen_name);
            mTextViewCreatedAt = (TextView) view.findViewById(R.id.textView_created_at);
            mTextViewContent = (CommentTextView) view.findViewById(R.id.textView_content);
            mTextViewLikedCount = (TextView) view.findViewById(R.id.textView_liked_count);
        }
    }
}
