package com.example.macroz.myapplication.newsapptest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.bean.AdItemBean;
import com.example.macroz.myapplication.newsapptest.holder.BaseHolder;
import com.example.macroz.myapplication.newsapptest.holder.NormalVerticalHolder;
import com.example.macroz.myapplication.newsapptest.holder.RotateAdHolder;

import java.util.List;

/**
 * 类描述:   recycler 垂直类型的adapter(新闻 feed流类型)
 * 创建人:   macroz
 * 创建时间: 2018/11/23 上午11:04
 * 修改人:   macroz
 * 修改时间: 2018/11/23 上午11:04
 * 修改备注:
 */
public class VerticalAdapter extends RecyclerView.Adapter<BaseHolder> {

    private static final String TAG = VerticalAdapter.class.getSimpleName();
    private Context mContext;
    private List<Object> mDataList;


    public VerticalAdapter(Context context) {
        mContext = context;
        initDefaultData();
    }

    private void initDefaultData() {
        for (int i = 0; i < 20; i++) {
            String s = "item " + i;
            mDataList.add(s);
        }
    }

    public VerticalAdapter(Context context, List<Object> dataList) {
        mDataList = dataList;
        mContext = context;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemTypeDefine.ROTATE_3D_AD) {
            return new RotateAdHolder(mContext,parent);
        }
        return new NormalVerticalHolder(mContext,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.onBindHolder(mDataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) instanceof AdItemBean) {
            return ItemTypeDefine.ROTATE_3D_AD;
        }
        return ItemTypeDefine.NORMAL_VERTICAL;
    }


}
