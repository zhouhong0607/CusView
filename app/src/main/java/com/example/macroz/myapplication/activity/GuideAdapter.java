package com.example.macroz.myapplication.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macroz.myapplication.R;

import java.util.List;

/**
 * 类描述:   主页面 导航 recyclerView 的adapter
 * 创建人:   macroz
 * 创建时间: 2018/8/14 下午3:05
 * 修改人:   macroz
 * 修改时间: 2018/8/14 下午3:05
 * 修改备注:
 */
public class GuideAdapter extends RecyclerView.Adapter {
    private List<GuideBean> mList;
    private ClickGuideListener mClickGuideListener;

    public GuideAdapter(List<GuideBean> list ,ClickGuideListener listener) {
        mList = list;
        mClickGuideListener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_holder_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GuideBean guideBean = mList.get(position);
        final ViewHolder vh = (ViewHolder) holder;

        vh.mTitleView.setText(guideBean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickGuideListener != null) {
                    mClickGuideListener.onClickGuideView(vh, guideBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleView = itemView.findViewById(R.id.guide_item_title);
        }
    }

    interface ClickGuideListener {
        void onClickGuideView(ViewHolder holder, GuideBean guideBean);
    }

}
