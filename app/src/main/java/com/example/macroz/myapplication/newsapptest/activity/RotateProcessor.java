package com.example.macroz.myapplication.newsapptest.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.adapter.RotateAdAdapter;
import com.example.macroz.myapplication.newsapptest.view.RotateAdView;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/10/31 下午5:21
 * 修改人:   macroz
 * 修改时间: 2018/10/31 下午5:21
 * 修改备注:
 */
public class RotateProcessor {

    private static final String TAG = "RotateProcessor";

    private ArrayMap<RotateAdTest.AdItemBean, RecordBean> mPositionCacheMap;

    private static volatile RotateProcessor mProcessor;

    private RotateProcessor() {
        mPositionCacheMap = new ArrayMap<>();
    }

    public static RotateProcessor getInstance() {
        if (mProcessor == null) {
            synchronized (RotateProcessor.class) {
                if (mProcessor == null) {
                    mProcessor = new RotateProcessor();
                }
            }
        }
        return mProcessor;
    }


    public void dealAdRotate(RecyclerView recyclerView) {
        if (null != recyclerView.getLayoutManager() && recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (null != linearLayoutManager) {
                int fPos = linearLayoutManager.findFirstVisibleItemPosition();
                int lPos = linearLayoutManager.findLastVisibleItemPosition();
                Log.w(TAG, "first visible position: " + fPos + "    last visible position: " + lPos);
                for (int i = fPos; i <= lPos; i++) {
                    View view = linearLayoutManager.findViewByPosition(i);
                    if (view != null) {
                        RotateAdView rotateAdView = (RotateAdView) view.findViewById(R.id.rotate_ad_image);
                        if (null != rotateAdView) {
                            //todo zh holder  拿到数据
                            RotateAdAdapter.RotateAdHolder holder = (RotateAdAdapter.RotateAdHolder) recyclerView.getChildViewHolder(view);
                            RotateAdTest.AdItemBean itemBean = holder.getItemBean();

                            float disToTop = computeAdViewDisToTop(rotateAdView);
                            //可以滚动的区域
                            float scrollDistance = recyclerView.getMeasuredHeight() - rotateAdView.getMeasuredHeight();

                            //拿到缓存的位置数据
                            RecordBean recordBean = mPositionCacheMap.get(itemBean);
                            if (recordBean == null) {
                                int type;
                                if (disToTop > 0 && disToTop < scrollDistance) {
                                    type = RecordBean.TYPE_CENTER;
                                } else {
                                    type = RecordBean.TYPE_NORMAL;
                                }
                                recordBean = new RecordBean(disToTop, type);
                                mPositionCacheMap.put(itemBean, recordBean);
                            }

                            float progress = computeProcess(disToTop, scrollDistance, recordBean);
                            Log.w(TAG, "progress: " + progress);
                            rotateAdView.setProgress(progress);
                        }
                    }
                }
            }
        }
    }


    //计算广告View 到RecyclerView顶端的距离
    public float computeAdViewDisToTop(RotateAdView rotateAdView) {
        float toTop = rotateAdView.getTop() + ((View) rotateAdView.getParent()).getTop() +
                ((View) rotateAdView.getParent().getParent()).getTop() + ((View) rotateAdView.getParent().getParent().getParent()).getTop();
        return toTop;
    }


    /**
     * 计算当前的进度
     *
     * @param curDisToTop 当前滚动的位置
     * @param recordBean  holder出现时候的起始位置
     * @return
     */
    public float computeProcess(float curDisToTop, float scrollDistance, RecordBean recordBean) {

        float progress;
        if (recordBean.getType() == RecordBean.TYPE_NORMAL) {
            progress = (scrollDistance - curDisToTop) / scrollDistance;
        } else {
            //以第一次记录的位置作为分界点, 从该点向上 滚动到顶 0~1 , 从该点向下滚到底 0~1
            float recordDistance = recordBean.getFirstShowPosition();
            if (curDisToTop < recordDistance) {
                //todo zh  除 0 判断
                progress = (recordDistance - curDisToTop) / recordDistance;
            } else {
                //todo zh  除 0 判断
                progress = (curDisToTop - recordDistance) / (scrollDistance - recordDistance);
            }
        }
        progress = progress > 1 ? 1 : progress;
        progress = progress < 0 ? 0 : progress;
        return progress;
    }

    /**
     * 记录holder首次出现的位置,根据该位置判断翻转类型
     */
    private static class RecordBean {

        //普通情况,界面中只进行一次翻转
        private final static int TYPE_NORMAL = 0;
        //首次出现在 界面中, 进行两次翻转
        private final static int TYPE_CENTER = 1;

        float firstShowPosition;

        int type;

        public RecordBean(float firstShowPosition, int type) {
            this.firstShowPosition = firstShowPosition;
            this.type = type;
        }

        public float getFirstShowPosition() {
            return firstShowPosition;
        }

        public int getType() {
            return type;
        }
    }

    //清除缓存的数据
    public void clearData() {
        mPositionCacheMap.clear();
    }
}
