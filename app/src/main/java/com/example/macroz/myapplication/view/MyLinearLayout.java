package com.example.macroz.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.macroz.myapplication.R;


public class MyLinearLayout extends ViewGroup {

    private String orientation = "";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性的值,TypedArray获取到命名为MyLinearLayout的属性集合
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyLinearLayout);
        //从属性集合中拿到orientation1属性的值
        orientation = ta.getString(R.styleable.MyLinearLayout_orientation1);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int width = 0;
        int height = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            width = calCurWidth(width, child);
            height = calCurHeight(height, child);
        }
        setMeasuredDimension(width, height);
    }

    private int calCurWidth(int curWidth, View child) {
        if ("vertical".equals(orientation)) {
            if (child.getMeasuredWidth() > curWidth)
                curWidth = child.getMeasuredWidth();
        } else {
            curWidth += child.getMeasuredWidth();
        }
        return curWidth;
    }

    private int calCurHeight(int curHeight, View child) {
        if ("vertical".equals(orientation)) {
            curHeight += child.getMeasuredHeight();
        } else {
            if (child.getMeasuredHeight() > curHeight)
                curHeight = child.getMeasuredHeight();
        }
        return curHeight;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int curLeft = l;
        int curTop = t;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            view.layout(curLeft, curTop, curLeft + view.getMeasuredWidth(), curTop + view.getMeasuredHeight());
            //垂直摆放
            if ("vertical".equals(orientation)) {

                curTop += view.getMeasuredHeight();
            } else {
                //水平摆放
                curLeft += view.getMeasuredWidth();

            }

        }
    }


}
