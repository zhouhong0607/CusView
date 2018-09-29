package com.example.macroz.myapplication.newsapptest.view;

import android.content.Context;
import android.support.constraint.motion.MotionLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/9/25 下午8:29
 * 修改人:   macroz
 * 修改时间: 2018/9/25 下午8:29
 * 修改备注:
 */
public class MyMotionLayout  extends MotionLayout{
    public MyMotionLayout(Context context) {
        super(context);
    }

    public MyMotionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMotionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int axes, int type) {
        return super.onStartNestedScroll(child, target, axes, type);
    }



    @Override
    public void onStopNestedScroll(View target, int type) {
        super.onStopNestedScroll(target, type);
    }



    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }
}
