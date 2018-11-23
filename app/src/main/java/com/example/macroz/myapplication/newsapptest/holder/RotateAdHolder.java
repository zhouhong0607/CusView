package com.example.macroz.myapplication.newsapptest.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.bean.AdItemBean;
import com.example.macroz.myapplication.newsapptest.view.RotateAdView;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/23 上午11:53
 * 修改人:   macroz
 * 修改时间: 2018/11/23 上午11:53
 * 修改备注:
 */
public class RotateAdHolder extends BaseHolder<AdItemBean> {
    private RotateAdView mRotateAdView;

    public RotateAdHolder(Context context, ViewGroup parent) {
        this(context,parent,R.layout.news_item_rotate_ad);
    }

    public RotateAdHolder(Context context, ViewGroup parent, int layoutId) {
        super(context, parent, layoutId);
        mRotateAdView = itemView.findViewById(R.id.rotate_ad_image);
    }

    @Override
    public void onBindHolder(AdItemBean data, int pos) {
        super.onBindHolder(data, pos);
        bindAdHolder();
    }

    //绑定广告holder
    private void bindAdHolder() {
        mRotateAdView.setDrawPlan(RotateAdView.DRAW_PLAN_B);
        Bitmap defaultBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ad_2);
        Bitmap aBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ad_tianmao);
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ad_3);
        mRotateAdView.setDefaultBitmap(defaultBitmap);
        mRotateAdView.setaBitmap(aBitmap);
        mRotateAdView.setbBitmap(bitmap);
    }
}
