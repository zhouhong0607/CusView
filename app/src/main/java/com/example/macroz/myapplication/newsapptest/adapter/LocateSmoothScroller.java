package com.example.macroz.myapplication.newsapptest.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:   处理RecyclerView 中的滚动定位效果，smooth定位到自定位置 item
 * todo zh 根据滚动的距离计算滚动的时间
 * 创建人:   macroz
 * 创建时间: 2018/11/26 下午3:59
 * 修改人:   macroz
 * 修改时间: 2018/11/26 下午3:59
 * 修改备注:
 */
public class LocateSmoothScroller extends LinearSmoothScroller {

    private final String TAG = this.getClass().getSimpleName();
    //滚动时间150ms
    private static final int SCROLL_TIME = 400;
    private List<LocateListener> mLocateListeners;

    public LocateSmoothScroller(Context context) {
        super(context);
        mLocateListeners = new ArrayList<>();
    }

    //计算滚动的位置
    @Override
    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
        Log.d(TAG, "calculateDtToFit ,  viewStart " + viewStart + " viewEnd: " + viewEnd +
                " boxStart: " + boxStart + " boxEnd: " + boxEnd + " snapPreference: " + snapPreference);
        return -viewStart;
    }

//    //计算滚动的速度(滚动 1000像素 需要的时间 ms)
//    @Override
//    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//        return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
//    }

    @Override
    protected int calculateTimeForScrolling(int dx) {
        return SCROLL_TIME;
    }

    public void addLocateListener(LocateListener listener) {
        mLocateListeners.add(listener);
    }

    public void removeLocateListener(LocateListener listener) {
        mLocateListeners.remove(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (LocateListener listener : mLocateListeners) {
            listener.onStart();
        }
        Log.w(TAG, "onStart: ");
    }


    @Override
    protected void onStop() {
        super.onStop();
        for (LocateListener listener : mLocateListeners) {
            listener.onStop();
        }
        Log.w(TAG, "onStop: ");
    }

    public interface LocateListener {
        void onStart();

        void onStop();
    }

}
