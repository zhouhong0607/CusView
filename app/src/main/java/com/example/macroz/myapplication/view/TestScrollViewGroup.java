package com.example.macroz.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/8/10 下午6:53
 * 修改人:   macroz
 * 修改时间: 2018/8/10 下午6:53
 * 修改备注:
 */
public class TestScrollViewGroup extends ViewGroup {

    public TestScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View view=getChildAt(0);
        view.measure(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view=getChildAt(0);
        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
    }

}
