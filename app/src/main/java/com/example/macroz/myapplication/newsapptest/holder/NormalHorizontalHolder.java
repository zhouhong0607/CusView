package com.example.macroz.myapplication.newsapptest.holder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.holder.BaseHolder;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/23 下午12:06
 * 修改人:   macroz
 * 修改时间: 2018/11/23 下午12:06
 * 修改备注:
 */
public class NormalHorizontalHolder extends BaseHolder<Object> {
    private TextView tv;

    public NormalHorizontalHolder(Context context, ViewGroup parent) {
        this(context, parent, R.layout.motion_recycler_item);
    }

    public NormalHorizontalHolder(Context context, ViewGroup parent, int layoutId) {
        super(context, parent, layoutId);
        tv = itemView.findViewById(R.id.textView);
    }

    @Override
    public void onBindHolder(Object data, int pos) {
        super.onBindHolder(data, pos);
        tv.setText("item :" + (pos + 1));
    }
}
