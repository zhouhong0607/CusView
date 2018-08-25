package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/8/20 上午9:20
 * 修改人:   macroz
 * 修改时间: 2018/8/20 上午9:20
 * 修改备注:
 */
public class CubeLayout extends ViewGroup {
    private final static String TAG = "CubeLayout";
    private Matrix mMatrix;
    private Camera mCamera;
    //    //当前显示的子View所对应的索引值，默认为0，第一个View
//    private int curIndex = 0;
    private GestureDetector mGestureDetector;


    public CubeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mCamera = new Camera();
        mGestureDetector = new GestureDetector(context, mGestureListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        //每个子View的高度
        int perChildHeight = getMeasuredHeight();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(0, i * perChildHeight, getMeasuredWidth(), (i + 1) * perChildHeight);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        float perChildHeight = getMeasuredHeight();
        int childCount = getChildCount();
        float curScrollY = getScrollY() % (childCount * perChildHeight);
        for (int i = 0; i < childCount; i++) {
            if (curScrollY < (i - 1) * perChildHeight || curScrollY > (i + 1) * perChildHeight) {
                continue;
            }

            canvas.save();
            mCamera.save();

            //上方的View  正翻  0 ~ 90
            if (curScrollY >= i * perChildHeight && curScrollY < (i + 1) * perChildHeight) {
                float degree = (curScrollY - i * perChildHeight) / perChildHeight * 90.0f;
                mCamera.rotateX(degree);
                mCamera.getMatrix(mMatrix);
                //移动matrix旋转中心
                float rotateX = getMeasuredWidth() / 2.0f;
                float rotateY = (i + 1) * perChildHeight;
                mMatrix.preTranslate(-rotateX, -rotateY);
                mMatrix.postTranslate(rotateX, rotateY);

                canvas.concat(mMatrix);
                drawChild(canvas, getChildAt(i), getDrawingTime());
            }

            //下方的View   正翻 -90~ 0
            if (curScrollY < i * perChildHeight && curScrollY > (i - 1) * perChildHeight) {
                float degree = (curScrollY - i * perChildHeight) / perChildHeight * 90.0f;
                mCamera.rotateX(degree);
                mCamera.getMatrix(mMatrix);
                //移动matrix旋转中心
                float rotateX = getMeasuredWidth() / 2.0f;
                float rotateY = i * perChildHeight;
                mMatrix.preTranslate(-rotateX, -rotateY);
                mMatrix.postTranslate(rotateX, rotateY);

                canvas.concat(mMatrix);
                drawChild(canvas, getChildAt(i), getDrawingTime());
            }

            mCamera.restore();
            canvas.restore();
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int maxScrollCount = getChildCount()-1;
            int height = getHeight();

            float scrollY = getScrollY() + distanceY;
            if (scrollY > maxScrollCount * height) {
                scrollY = maxScrollCount * height;
            }

            if (scrollY < 0) {
                scrollY = 0;
            }

            CubeLayout.this.scrollTo(0, (int) scrollY);
            return true;
        }
    };


}
