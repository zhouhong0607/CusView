package com.example.macroz.myapplication.newsapptest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.activity.RotateAdTest;
import com.example.macroz.myapplication.newsapptest.view.RotateAdView;

import java.util.List;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/10/31 下午3:10
 * 修改人:   macroz
 * 修改时间: 2018/10/31 下午3:10
 * 修改备注:
 */
public class RotateAdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RotateAdAdapter.class.getSimpleName();

    public static final int HOLDER_TYPE_NORMAL = 0;
    public static final int HOLDER_TYPE_ROTATE_AD = 1;

    private List<Object> dataList;
    private Context mContext;

    public RotateAdAdapter(Context context, List<Object> list) {
        mContext = context;
        this.dataList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HOLDER_TYPE_ROTATE_AD) {
            return new RotateAdHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item_rotate_ad, parent, false));
        }
        return new NormalHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item_normal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOLDER_TYPE_ROTATE_AD) {
            RotateAdHolder rotateAdHolder = (RotateAdHolder) holder;
            rotateAdHolder.mItemBean = (RotateAdTest.AdItemBean) dataList.get(position);
            bindAdHolder(rotateAdHolder);

        } else {
            bindNormalHolder((NormalHolder) holder);
        }

    }

    //绑定广告holder
    private void bindAdHolder(RotateAdHolder holder) {
        final RotateAdView rotateAdView = holder.mRotateAdView;
        rotateAdView.setDrawPlan(RotateAdView.DRAW_PLAN_B);
        Bitmap defaultBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ad_2);
        Bitmap aBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ad_tianmao);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ad_3);
        rotateAdView.setDefaultBitmap(defaultBitmap);
        rotateAdView.setaBitmap(aBitmap);
        rotateAdView.setbBitmap(bitmap);

//        final RotateAdTest.ProgressBean progressBean = new RotateAdTest.ProgressBean();
//        progressBean.setProgress(0f);
//        progressBean.setDir(1);
//
//        rotateAdView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (progressBean.getProgress() >= 1) {
//                    progressBean.setDir(-1);
//                } else if (progressBean.getProgress() <= 0) {
//                    progressBean.setDir(1);
//                }
//
//                float curProgress = progressBean.getProgress();
//                curProgress += 0.05f * progressBean.getDir();
//
//                curProgress = curProgress < 0 ? 0 : curProgress;
//                curProgress = curProgress > 1 ? 1 : curProgress;
//
//                rotateAdView.setProgress(curProgress);
//                progressBean.setProgress(curProgress);
//
//                Log.d(TAG, "cur  progress: " + curProgress);
//            }
//        });
    }

    private void bindNormalHolder(NormalHolder holder) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        position == 3 || position == 15 ||
        if (dataList.get(position) instanceof RotateAdTest.AdItemBean) {
            return HOLDER_TYPE_ROTATE_AD;
        }
        return HOLDER_TYPE_NORMAL;
    }

    public static class NormalHolder extends RecyclerView.ViewHolder {
        public NormalHolder(View itemView) {
            super(itemView);
        }
    }


    public static class RotateAdHolder extends RecyclerView.ViewHolder {
        private RotateAdView mRotateAdView;

        private RotateAdTest.AdItemBean mItemBean;


        public RotateAdHolder(View itemView) {
            super(itemView);
            mRotateAdView = itemView.findViewById(R.id.rotate_ad_image);
        }

        public RotateAdTest.AdItemBean getItemBean() {
            return mItemBean;
        }

        public void setItemBean(RotateAdTest.AdItemBean itemBean) {
            mItemBean = itemBean;
        }
    }

}
