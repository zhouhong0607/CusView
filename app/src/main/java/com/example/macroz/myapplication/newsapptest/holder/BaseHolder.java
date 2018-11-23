package com.example.macroz.myapplication.newsapptest.holder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/23 上午11:50
 * 修改人:   macroz
 * 修改时间: 2018/11/23 上午11:50
 * 修改备注:
 */
public class BaseHolder<D> extends RecyclerView.ViewHolder {
    private Context mContext;
    private D mData;

    public BaseHolder(Context context, ViewGroup parent, @LayoutRes int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId, parent, false));
        mContext = context;
    }

    public void onBindHolder(D data, int pos) {
        mData = data;
    }

    public D getData() {
        return mData;
    }

    protected Context getContext() {
        return mContext;
    }
}
