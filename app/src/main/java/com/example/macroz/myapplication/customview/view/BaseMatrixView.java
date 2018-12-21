package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.utils.Utils;

import java.text.DecimalFormat;

/**
 * 类描述:   封装基本matrix功能
 * 1. 右上角绘制matrix信息 {@link #drawMatrixValueText()}
 * 2. 设置测试图片到中心位置 {@link #setCenter()}
 * 3. 重置View{@link #reset()}
 * 创建人:   macroz
 * 创建时间: 2018/8/25 下午5:33
 * 修改人:   macroz
 * 修改时间: 2018/8/25 下午5:33
 * 修改备注:
 */
public abstract class BaseMatrixView extends View {
    protected final String TAG = getClass().getSimpleName();
    //读取
    protected Matrix mMatrix;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected Paint mPaint;

    protected boolean mCenterFlag = false;

    public BaseMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载测试图片
        mBitmap = Utils.getBitmap(getResources(), R.drawable.page1);
        mPaint = new Paint();
        mMatrix = new Matrix();
        mCanvas = new Canvas();
    }

    @Override
    final protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() : ");
        mCanvas = canvas;
        mCanvas.save();
        //将图片调整到中间
        if (mCenterFlag) {
            adjustToCenter();
        }
        //交给子View去绘制自己的图形
        onSubDraw(mCanvas);

        //在右上角绘制当前 matrix 取值
        Matrix printMatrix = drawMatrixValueText();
        //log 打印当前matrix
        printMatrix(printMatrix);
    }

    /**
     * 子View调用这个方法绘制
     *
     * @param canvas
     */
    protected abstract void onSubDraw(Canvas canvas);

    /**
     * 将当前matrix值绘制到屏幕右上角
     *
     * @return 返回记录的 matrix信息
     */
    private Matrix drawMatrixValueText() {
        //将当前canvas矩阵的matrix记录下
        Matrix printMatrix = mCanvas.getMatrix();
        //恢复 canvas坐标  到view起始位置
        mCanvas.restore();
        //创建 矩形和文字的画笔
        Paint recPaint = new Paint();
        recPaint.setColor(Color.BLACK);
        recPaint.setStyle(Paint.Style.FILL);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(35);

        //在矩形里面绘制文字
        String value = getMatrixString(printMatrix);
        String[] valueArray = value.split("\n");

        Rect textRec = new Rect();
        //获取text的范围
        textPaint.getTextBounds(value, 0, value.length(), textRec);

        float textWidth = Math.abs(textRec.right - textRec.left);
        float textHeight = Math.abs(textRec.bottom - textRec.top);


        //在右上角绘制一个黑色的矩形 width X height 像素
        float width = textWidth / 3.0f + 50;  //+ 50  前后留个像素的余地
        float height = textHeight * 3 + 50;

        float left, top, right, bottom;
        left = getMeasuredWidth() - width;
        top = 0;
        right = getMeasuredWidth();
        bottom = height;

        mCanvas.drawRect(left, top, right, bottom, recPaint);

        //绘制第一行
        mCanvas.drawText(valueArray[0], left + 10, top + textHeight + 10, textPaint);
        //绘制第二行
        mCanvas.drawText(valueArray[1], left + 10, top + textHeight * 2 + 20, textPaint);
        //绘制第三行
        mCanvas.drawText(valueArray[2], left + 10, top + textHeight * 3 + 30, textPaint);
        return printMatrix;
    }

    /**
     * 将矩阵转换成三行三列的字符串，小数点保留2位
     *
     * @return
     */
    private String getMatrixString(Matrix printMatrix) {
        if (printMatrix == null) {
            return "";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        StringBuilder sb = new StringBuilder(64);
        float[] values = new float[9];
        printMatrix.getValues(values);
        sb.append('[');
        sb.append(decimalFormat.format(values[0]));
        sb.append(" , ");
        sb.append(decimalFormat.format(values[1]));
        sb.append(" , ");
        sb.append(decimalFormat.format(values[2]));
        sb.append("]\n[");
        sb.append(decimalFormat.format(values[3]));
        sb.append(" , ");
        sb.append(decimalFormat.format(values[4]));
        sb.append(" , ");
        sb.append(decimalFormat.format(values[5]));
        sb.append("]\n[");
        sb.append(decimalFormat.format(values[6]));
        sb.append(" , ");
        sb.append(decimalFormat.format(values[7]));
        sb.append(" , ");
        sb.append(decimalFormat.format(values[8]));
        sb.append(']');
        return sb.toString();
    }

    private void printMatrix(Matrix printMatrix) {
        Log.d(TAG, "matrix: " + printMatrix.toShortString());
    }


    public void setCenter() {
        mCenterFlag = true;
        invalidate();
    }

    /**
     * 调整bitmap 显示在屏幕中间
     */
    private void adjustToCenter() {
        mCanvas.translate(getMeasuredWidth() / 2 - mBitmap.getWidth() / 2, getMeasuredHeight() / 2 - mBitmap.getHeight() / 2);
    }


    /**
     * 刷新界面
     */
    public void apply() {
        invalidate();
    }

    public void reset() {
        mCenterFlag = false;
        mMatrix.reset();
        invalidate();
        Log.d(TAG, "reset() ");
    }

}
