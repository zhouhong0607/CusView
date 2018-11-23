package com.example.macroz.myapplication.newsapptest.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.bean.AdItemBean;
import com.example.macroz.myapplication.newsapptest.holder.BaseHolder;
import com.example.macroz.myapplication.newsapptest.view.RotateAdView;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 记录当前的滚动距离,用于删除item重置View初始状态,防止拉到顶部出现倾斜的情况
     */
    private int mScrollY;

    /**
     * 绑定的recyclerView
     */
    private RecyclerView mBindRecycler;

    /**
     * 单独设置个变量跟踪高度变化的情况
     */
    private int mRecyclerHeight;

    /**
     * 缩减View的滚动区间(目前不需要, 在需要调整滚动区间的时候 设置)
     */
    private final int TOP_OFFSET;
    private final int BOTTOM_OFFSET;

    /**
     * 起始位置在界面外,只进行一次翻转
     */
    private final static int TYPE_NORMAL = 0;
    /**
     * 起始位置在界面中,进行两次翻转
     */
    private final static int TYPE_CENTER = 1;

    /**
     * 记录每个广告对应的起始翻转位置,根据广告ID区分是否是同一个广告
     */
    private Map<String, RecordBean> mPositionCacheMap;

    public RotateProcessor(@NonNull RecyclerView view) {
        mBindRecycler = view;
        mPositionCacheMap = new HashMap<>();
        mScrollY = 0;
        TOP_OFFSET = 0;
        BOTTOM_OFFSET = 0;
    }

    /**
     * 更新当前记录的recyclerView的高度
     *
     * @param height
     */
    public void updateHeight(int height) {
        if (mRecyclerHeight != height) {
            Log.w(TAG, "update  RecyclerViewHeight : " + mRecyclerHeight + "  -> " + height);
            mRecyclerHeight = height;
            dealAdRotate(0, true);
        }
    }

    /**
     * RecyclerView滚动的过程中 计算翻转角度 设置View翻转
     *
     * @param scrollDy y滚动量
     * @param reset    重置bean
     *                 由于某些业务逻辑 , 存储的状态值会发生变化 ,
     *                 例如 deleteItem后 记录的首次出现的位置可能发生变化(上移一段距离)
     *                 以及 mBindRecycler 可能会发生多次notify , 重新测量布局, recyclerView的高度也会发生变化。
     *                 这里统一在变化后更新下存储的状态, 防止发生一些特殊情况,例如view滚动到顶部的时候 不能回归到起始位置,或者由于
     *                 recyclerView的高度发生变化 ,  导致翻转的类型 由 TYPE_NORMAL 与 TYPE_CENTER 发生切换
     *                 (注 , 某些情况下可能会出现异常情况 即使拉到顶部位置  mBindRecycler 的scrollY 可能不是 0 , 原因目前不详)
     */
    public void dealAdRotate(int scrollDy, boolean reset) {
        if (mBindRecycler == null || !(mBindRecycler.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }
        Log.w(TAG, "dealAdRotate   dy: " + scrollDy + " reset: " + reset);
        mScrollY += scrollDy;
        Log.i(TAG, "scroll Y: " + mScrollY);
        LinearLayoutManager lm = (LinearLayoutManager) mBindRecycler.getLayoutManager();
        int fPos = lm.findFirstVisibleItemPosition();
        int lPos = lm.findLastVisibleItemPosition();
        Log.d(TAG, "first visible position: " + fPos + "    last visible position: " + lPos);
        for (int i = fPos; i <= lPos; i++) {
            View view = lm.findViewByPosition(i);
            if (view == null) {
                continue;
            }
            RotateAdView rotateAdView = view.findViewById(R.id.rotate_ad_image);
            if (rotateAdView == null) {
                continue;
            }
            //可以滚动的区域
            float scrollDistance = mRecyclerHeight - rotateAdView.getHeight() - TOP_OFFSET - BOTTOM_OFFSET;
            if (scrollDistance <= 0) {
                Log.w(TAG, "scrollDistance <= 0 ");
                continue;
            }
            //计算当前View 到顶端的距离
            float curDisToTop = computeAdViewDisToTop(rotateAdView);
            float absDisToTop = curDisToTop + mScrollY;
            if (curDisToTop < 0) {
                //保持原来的状态
                curDisToTop = 0;
                Log.w(TAG, "curDisToTop < 0  reset To 0 ");
            }

            if (curDisToTop > scrollDistance) {
                curDisToTop = scrollDistance;
                Log.w(TAG, "curDisToTop > scrollDistance  reset To scrollDistance ");
            }
            Log.d(TAG, "curDisToTop : " + curDisToTop + "  scrollDistance : " + scrollDistance);
            //查询缓存状态
            BaseHolder holder = (BaseHolder) mBindRecycler.getChildViewHolder(view);
            AdItemBean itemBean = (AdItemBean) holder.getData();
            RecordBean recordBean = getCacheData(itemBean, curDisToTop, scrollDistance);
            if (reset) {
                if (absDisToTop > 0 && absDisToTop < scrollDistance) {
                    //重置下拉的最大距离 为起始状态,保证拉到顶部可以恢复到起始位置
                    recordBean.setType(TYPE_CENTER);
                    recordBean.setFirstShowPosition(absDisToTop);
                    Log.i(TAG, "reset TYPE_CENTER , firstDis: " + curDisToTop + mScrollY);
                    //计算滚动角度

                } else {
                    recordBean.setType(TYPE_NORMAL);
                    recordBean.setFirstShowPosition(scrollDistance);
                    Log.i(TAG, "reset TYPE_NORMAL , firstDis scrollDistance: " + scrollDistance);
                }
            }
            //计算滚动角度
            float progress = computeProcess(curDisToTop, scrollDistance, recordBean);
            Log.d(TAG, "progress: " + progress);
            rotateAdView.setProgress(progress);
        }
    }

    /**
     * 拿到缓存的位置数据
     *
     * @param itemBean
     * @param curDisToTop
     * @param scrollDistance
     * @return
     */
    private RecordBean getCacheData(AdItemBean itemBean, float curDisToTop, float scrollDistance) {
        if (itemBean == null) {
            return null;
        }
        String id = itemBean.getAdId();
        RecordBean recordBean = mPositionCacheMap.get(id);
        if (recordBean == null) {
            Log.d(TAG, "Ad ID : " + id);
            int type;
            if (curDisToTop > 0 && curDisToTop < scrollDistance) {
                type = TYPE_CENTER;
                recordBean = new RecordBean(curDisToTop, type);
                Log.d(TAG, "create new TYPE_CENTER Bean recordDistance : " + curDisToTop);
            } else {
                type = TYPE_NORMAL;
                recordBean = new RecordBean(scrollDistance, type);
                Log.d(TAG, "create new TYPE_NORMAL Bean recordDistance : " + scrollDistance);
            }
            mPositionCacheMap.put(id, recordBean);
        }
        return recordBean;
    }


    /**
     * 计算广告View 到RecyclerView顶端的距离
     * 这里限制布局层次小于100,防止特殊情况进入死循环
     *
     * @param rotateAdView
     * @return
     */
    private float computeAdViewDisToTop(RotateAdView rotateAdView) {
        float toTop = 0f;
        //防止死循环
        int i = 0;
        View v = rotateAdView;
        while (v != null && !(v instanceof RecyclerView) && i < 100) {
            toTop += v.getTop();
            if (v.getParent() instanceof View) {
                v = (View) v.getParent();
            }
            i++;
        }
        return toTop - TOP_OFFSET;
    }


    /**
     * 计算当前的进度
     *
     * @param curDisToTop 当前滚动的位置
     * @param recordBean  holder出现时候的起始位置
     * @return
     */
    private float computeProcess(float curDisToTop, float scrollDistance, RecordBean recordBean) {
        if (recordBean == null) {
            return 0;
        }
        Log.w(TAG, "computeProcess() curDisToTop: " + curDisToTop + " scrollDistance: " + scrollDistance);
        float progress;
        if (recordBean.getType() == TYPE_NORMAL) {
            Log.d(TAG, "TYPE_NORMAL ");
            progress = (scrollDistance - curDisToTop) / scrollDistance;
        } else {
            //以第一次记录的位置作为分界点, 从该点向上 滚动到顶 0~1 , 从该点向下滚到底 0~1
            float recordDistance = recordBean.getFirstShowPosition();
            Log.d(TAG, "TYPE_CENTER  recordDistance : " + recordDistance);
            if (curDisToTop < recordDistance) {
                Log.d(TAG, "TYPE_CENTER  curDisToTop < recordDistance ");
                progress = (recordDistance - curDisToTop) / recordDistance;
            } else {
                Log.d(TAG, "TYPE_CENTER   curDisToTop >= recordDistance");
                progress = (curDisToTop - recordDistance) / (scrollDistance - recordDistance);
            }
        }
        progress = progress > 1 ? 1 : progress;
        progress = progress < 0 ? 0 : progress;
        return progress;
    }

    /**
     * 更新数据后恢复 滚动距离 0
     *
     * @param scrollY
     */
    public void setScrollY(int scrollY) {
        Log.w(TAG, "setScrollY: " + scrollY);
        mScrollY = scrollY;
    }

    /**
     * 记录holder首次出现的位置,根据该位置判断翻转类型
     */
    private static class RecordBean {
        /**
         * 记录翻转的起始位置
         */
        float firstShowPosition;
        /**
         * 记录每个广告的翻转类型
         * {@link RotateProcessor#TYPE_NORMAL,RotateProcessor#TYPE_CENTER}
         */
        int type;

        public RecordBean(float firstShowPosition, int type) {
            this.firstShowPosition = firstShowPosition;
            this.type = type;
        }

        public float getFirstShowPosition() {
            return firstShowPosition;
        }

        public void setFirstShowPosition(float firstShowPosition) {
            this.firstShowPosition = firstShowPosition;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    /**
     * 清除缓存的数据
     */
    public void clearData() {
        mPositionCacheMap.clear();
        Log.w(TAG, "clearData()  ");
    }
}
