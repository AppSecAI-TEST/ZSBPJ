package com.lcc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcc.activity.R;
import com.lcc.entity.TravelingEntity;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.ImageManager;


public class TravelingAdapter extends SBaseListAdapter<TravelingEntity> {

    private boolean isNoData;
    private int mHeight;
    public static final int ONE_SCREEN_COUNT = 7;

    public TravelingAdapter(Context context) {
        super(context);
    }

    public TravelingAdapter(Context context, List<TravelingEntity> list) {
        super(context, list);
    }

    // 设置数据
    public void setData(List<TravelingEntity> list) {
        clearAll();
        addALL(list);

        isNoData = false;
        if (list.size() == 1 && list.get(0).isNoData()) {
            // 暂无数据布局
            isNoData = list.get(0).isNoData();
            mHeight = list.get(0).getHeight();
        } else {
            // 添加空数据
            if (list.size() < ONE_SCREEN_COUNT) {
                addALL(createEmptyList(ONE_SCREEN_COUNT - list.size()));
            }
        }
        notifyDataSetChanged();
    }

    // 创建不满一屏的空数据
    public List<TravelingEntity> createEmptyList(int size) {
        List<TravelingEntity> emptyList = new ArrayList<>();
        if (size <= 0) return emptyList;
        for (int i=0; i<size; i++) {
            emptyList.add(new TravelingEntity());
        }
        return emptyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 暂无数据
        if (isNoData) {
            convertView = mInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            RelativeLayout rootView = ButterKnife.findById(convertView, R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

        // 正常数据
        final ViewHolder holder;
        if (convertView != null && convertView instanceof LinearLayout) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_travel, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        TravelingEntity entity = getItem(position);

        holder.llRootView.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(entity.getType())) {
            holder.llRootView.setVisibility(View.INVISIBLE);
            return convertView;
        }

        holder.tvTitle.setText(entity.getFrom() + entity.getTitle() + entity.getType());
        holder.tvRank.setText("排名：" + entity.getRank());
        ImageManager.getInstance().loadUrlImage(mContext, entity.getImage_url(), holder.ivImage);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ll_root_view)
        LinearLayout llRootView;
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_rank)
        TextView tvRank;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
