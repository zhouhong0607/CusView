package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.example.macroz.myapplication.constant.RadioDefine;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/8/29 下午8:11
 * 修改人:   macroz
 * 修改时间: 2018/8/29 下午8:11
 * 修改备注:
 */
public class MatrixApiTextView extends BaseMatrixView {
    private float xValue, yValue, degreeValue;
    private int curSelectMethod;

    public MatrixApiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSubDraw(Canvas canvas) {
        //使用concat会影响canvas中的matrix值
        canvas.concat(mMatrix);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);


        //使用这个方法不影响canvas内部的matrix 值 
//        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }


    /**
     * matrix Translate 测试
     *
     * @param tx
     * @param ty
     */
    public void translate(float tx, float ty) {
        Matrix matrix = new Matrix();
        matrix.preTranslate(tx, ty);
        mMatrix.preConcat(matrix);
        Log.i(TAG, "matrix after translate() :  " + mMatrix.toShortString());
    }

    /**
     * matrix rotate 测试
     *
     * @param degree
     */
    public void rotate(float degree) {
        Matrix matrix = new Matrix();
        matrix.preRotate(degree);
        mMatrix.preConcat(matrix);
        Log.i(TAG, "matrix after rotate() :  " + mMatrix.toShortString());
    }

    /**
     * scale 测试
     *
     * @param sx
     * @param sy
     */
    public void scale(float sx, float sy) {
        Matrix matrix = new Matrix();
        matrix.preScale(sx, sy);
        mMatrix.preConcat(matrix);
        Log.i(TAG, "matrix after scale() :  " + mMatrix.toShortString());
    }

    /**
     * skew 测试
     *
     * @param sx
     * @param sy
     */
    public void skew(float sx, float sy) {
        Matrix matrix = new Matrix();
        matrix.preSkew(sx, sy);
        mMatrix.preConcat(matrix);
        Log.i(TAG, "matrix after skew() :  " + mMatrix.toShortString());
    }

    public void setSelectMethod(int method) {
        curSelectMethod = method;
    }

    //设置 xValue ，yValue ，degreeValue
    public void setValueX(float x) {
        this.xValue = x;
        Log.i(TAG, "current X :  " + this.xValue);
    }

    public void setValueY(float y) {
        this.yValue = y;
        Log.i(TAG, "current Y :  " + this.yValue);
    }

    public void setValueDegree(float degree) {
        this.degreeValue = degree;
        Log.i(TAG, "current Degree :  " + this.degreeValue);
    }

    @Override
    public void apply() {
        //选择对应的方法更新matrix
        switch (curSelectMethod) {
            case RadioDefine.RADIO_GROUP_METHOD_TRANSLATE:
                translate(xValue, yValue);
                Log.i(TAG, "apply translate  xValue :  " + this.xValue + " yValue :" + this.yValue);
                break;
            case RadioDefine.RADIO_GROUP_METHOD_ROTATE:
                rotate(degreeValue);
                Log.i(TAG, "apply rotate  degreeValue :  " + this.degreeValue);
                break;
            case RadioDefine.RADIO_GROUP_METHOD_SCALE:
                scale(xValue, yValue);
                Log.i(TAG, "apply scale  xValue :  " + this.xValue + " yValue :" + this.yValue);
                break;
            case RadioDefine.RADIO_GROUP_METHOD_SKEW:
                skew(xValue, yValue);
                Log.i(TAG, "apply skew  xValue :  " + this.xValue + " yValue :" + this.yValue);
                break;
        }
        super.apply();
    }

    @Override
    public void reset() {
        super.reset();
    }
}
