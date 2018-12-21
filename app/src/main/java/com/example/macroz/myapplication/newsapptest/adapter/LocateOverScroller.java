package com.example.macroz.myapplication.newsapptest.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

import java.lang.reflect.Field;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/12/13 下午4:56
 * 修改人:   macroz
 * 修改时间: 2018/12/13 下午4:56
 * 修改备注:
 */
public class LocateOverScroller extends OverScroller {

    private static final String TAG = "LocateOverScroller";

    private RecyclerView mRecyclerView;

    public LocateOverScroller(Context context, RecyclerView recyclerView) {
        super(context, dInterpolator);
        mRecyclerView = recyclerView;
    }


    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        super.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        motifyDistance();
    }


    private void motifyDistance() {
        Log.w(TAG, "修改  distance");
        if (mRecyclerView == null) {
            return;
        }


        try {
            Class<?> clazz = this.getClass().getSuperclass();
            Field[] fields = clazz.getDeclaredFields();
            Object splineOverScrollerX = null;
            for (Field field : fields) {
                field.setAccessible(true);
                if ("mScrollerX".equals(field.getName())) {
                    field.setAccessible(true);
                    splineOverScrollerX = field.get(this);
                    Log.w(TAG, "拿到mScrollerX 对象 ");
                }
            }

            if (splineOverScrollerX == null) {
                return;
            }


            Class<?> sClazz = splineOverScrollerX.getClass();
            Field[] sFields = clazz.getDeclaredFields();

            Field mFinalField = null;

            for (Field sField : sFields) {
                if ("mFinal".equals(sField.getName())) {
                    mFinalField = sField;
                    break;
                }
            }

//            mFinalField


            //修改 值
            LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            int firPos = lm.findFirstVisibleItemPosition();
            View view = lm.findViewByPosition(firPos);
            int width = view.getMeasuredWidth();
//            int sig = Math.abs()


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static final Interpolator dInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

}
