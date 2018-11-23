package com.example.macroz.myapplication.newsapptest.holder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.macroz.myapplication.R;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/23 下午12:04
 * 修改人:   macroz
 * 修改时间: 2018/11/23 下午12:04
 * 修改备注:
 */
public class NormalVerticalHolder extends BaseHolder {
    public NormalVerticalHolder(Context context, ViewGroup parent) {
        this(context,parent, R.layout.news_item_normal);
    }

    public NormalVerticalHolder(Context context, ViewGroup parent, int layoutId) {
        super(context, parent, layoutId);
    }
}
