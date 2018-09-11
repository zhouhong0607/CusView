package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/9/11 下午5:49
 * 修改人:   macroz
 * 修改时间: 2018/9/11 下午5:49
 * 修改备注:
 */
public class CubeViewPager extends MyViewPager {

    private final String TAG = "CubeViewPager";

    private Matrix mMatrix;
    private Camera mCamera;

    public CubeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mCamera = new Camera();
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {

        ArrayList<ItemInfo> items = getItems();

        Log.e(TAG, "scrollX: " + getScrollX());
        float perChildWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        Log.e(TAG, "perChildWidth: " + perChildWidth);
        int childCount = getChildCount();
        Log.e(TAG, "getChildCount(): " + childCount);
        Log.e(TAG, "items(): " + items.size());
        float curScrollX = getScrollX();
        for (int i = 0; i < childCount; i++) {
            MyViewPager.ItemInfo ii=infoForAnyChild(getChildAt(i));
            int position=ii.position;

            if (curScrollX < (position - 1) * perChildWidth || curScrollX > (position + 1) * perChildWidth) {
                continue;
            }


            canvas.save();
            mCamera.save();

            //左方的View  翻  0 ~ -90
            if (curScrollX >= position * perChildWidth && curScrollX < (position + 1) * perChildWidth) {
                float degree = -(curScrollX - position * perChildWidth) / perChildWidth * 90.0f;
                mCamera.rotateY(degree);
                mCamera.getMatrix(mMatrix);
                //移动matrix旋转中心
                float rotateX = (position + 1) * perChildWidth + getPaddingLeft();
                float rotateY = getMeasuredHeight() / 2.0f;
                mMatrix.preTranslate(-rotateX, -rotateY);
                mMatrix.postTranslate(rotateX, rotateY);

                canvas.concat(mMatrix);
                drawChild(canvas, getChildAt(i), getDrawingTime());

                Log.e(TAG, "i: " + position + "左边 degree: " + degree);

            }

            //右方的View   翻 90~ 0
            if (curScrollX < position * perChildWidth && curScrollX > (position - 1) * perChildWidth) {
                float degree = (position * perChildWidth - curScrollX) / perChildWidth * 90.0f;
                mCamera.rotateY(degree);
                mCamera.getMatrix(mMatrix);
                //移动matrix旋转中心
                float rotateX = position * perChildWidth + getPaddingLeft();
                float rotateY = getMeasuredHeight() / 2.0f;
                mMatrix.preTranslate(-rotateX, -rotateY);
                mMatrix.postTranslate(rotateX, rotateY);

                canvas.concat(mMatrix);
                drawChild(canvas, getChildAt(i), getDrawingTime());
                Log.e(TAG, "i: " + position + "右边 degree: " + degree);
            }

            mCamera.restore();
            canvas.restore();
        }

    }


}
