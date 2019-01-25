package com.example.macroz.myapplication.newsapptest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.holder.BaseHolder;
import com.example.macroz.myapplication.newsapptest.holder.NormalHorizontalHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:  recycler  横滑类型的adapter
 * 创建人:   macroz
 * 创建时间: 2018/11/23 上午10:41
 * 修改人:   macroz
 * 修改时间: 2018/11/23 上午10:41
 * 修改备注:
 */
public class HorizontalAdapter extends RecyclerView.Adapter<BaseHolder<Object>> {

    private List<Object> mDataList;
    private Context mContext;

    public HorizontalAdapter(Context context) {
        mDataList = new ArrayList<>();
        mContext = context;
        initDefaultData();
    }

    private void initDefaultData() {
        for (int i = 0; i < 20; i++) {
            String s = "item " + i;
            mDataList.add(s);
        }
    }

    public HorizontalAdapter(Context context, List<Object> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public BaseHolder<Object> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NormalHorizontalHolder(mContext, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<Object> holder, int position) {
        holder.onBindHolder(mDataList.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public void insertToPos(int pos, Object data) {
        mDataList.add(pos, data);
        notifyItemInserted(pos);
    }

    @Override
    public int getItemViewType(int position) {
        return ItemTypeDefine.NORMAL_HORIZONTAL;
    }


}
