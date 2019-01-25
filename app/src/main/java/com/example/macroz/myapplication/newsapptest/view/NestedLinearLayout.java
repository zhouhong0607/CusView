package com.example.macroz.myapplication.newsapptest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/12/27 下午8:02
 * 修改人:   macroz
 * 修改时间: 2018/12/27 下午8:02
 * 修改备注:
 */
public class NestedLinearLayout extends LinearLayout implements NestedScrollingParent  {

    private static final String TAG = "NestedLinearLayout";

    public NestedLinearLayout(Context context) {
        super(context);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//
//
//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        Log.d(TAG, "onStartNestedScroll:  nestedScrollAxes:  " + nestedScrollAxes);
//        return true;
//    }
//
//    @Override
//    public void onNestedScrollAccepted(View child, View target, int axes) {
//        Log.d(TAG, "onNestedScrollAccepted: ");
//    }
//
//    @Override
//    public void onStopNestedScroll(View child) {
//        Log.d(TAG, "onStopNestedScroll: ");
//    }
//
//    @Override
//    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        Log.d(TAG, "onNestedScroll: ");
//    }
//
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        Log.d(TAG, "onNestedPreScroll:  dx: " + dx + "  dy: " + dy);
//    }
//
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        Log.d(TAG, "onNestedPreFling:  velocityX: " + velocityX + " velocityY: " + velocityY);
//        return true;
//    }
//
//    @Override
//    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        Log.d(TAG, "onNestedFling: velocityY " + velocityY);
//        return true;
//    }
//
//    @Override
//    public int getNestedScrollAxes() {
//        Log.d(TAG, "getNestedScrollAxes: ");
//        return SCROLL_AXIS_HORIZONTAL | SCROLL_AXIS_VERTICAL;
//    }
}
