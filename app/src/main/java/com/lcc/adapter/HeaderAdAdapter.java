package com.lcc.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import zsbpj.lccpj.frame.ImageManager;


public class HeaderAdAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> ivList; // ImageView的集合
    private List<String> adList; // 广告链接
    private int count = 1; // 广告的数量

    public HeaderAdAdapter(Context context, List<String> adList, List<ImageView> ivList) {
        super();
        this.mContext = context;
        this.adList = adList;
        this.ivList = ivList;
        if(ivList != null && ivList.size() > 0){
            count = ivList.size();
        }
    }

    @Override
    public int getCount() {
        if (count == 1) {
            return 1;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % count;
        // 先移除在添加，更新图片在container中的位置（把iv放至container末尾）
        ImageView iv = ivList.get(newPosition);
        String url = adList.get(newPosition);
        container.removeView(iv);
        ImageManager.getInstance().loadUrlImage(mContext, url, iv);
        container.addView(iv);
        return iv;
    }
}
