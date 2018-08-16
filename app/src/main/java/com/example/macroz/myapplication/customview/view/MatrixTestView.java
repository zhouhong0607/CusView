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
import com.example.macroz.myapplication.Utils;
import com.example.macroz.myapplication.constant.MatrixMethodDefine;

import java.text.DecimalFormat;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/8/14 下午2:49
 * 修改人:   macroz
 * 修改时间: 2018/8/14 下午2:49
 * 修改备注:
 */
public class MatrixTestView extends View {

    private static final String TAG = "MatrixTestView";

    private Matrix mMatrix;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;


    private boolean centerFlag = false;

    private int selectMethod = MatrixMethodDefine.MATRIX_TEST_METHOD_DEFAULT;


    public MatrixTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = Utils.getBitmap(getResources(), R.drawable.page1);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw() : ");
        mCanvas = canvas;
        mCanvas.save();

        if (centerFlag) {
            adjustToCenter();
        }
        //选择对应的测试
        selectMethodAndProcess();

        //根据当前 canvas的matrix绘制图像
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

        //在右上角绘制当前 matrix 取值
        drawMatrixValueText();
    }


    /**
     * pre操作影响叠加
     * post操作不受之前变换的影响
     */
    private void testPre() {

        Matrix matrix1=new Matrix();
        //首先旋转30°
        matrix1.preRotate(30);
        //之后前乘位移(500，0)
        matrix1.preTranslate(500f,0);
        //将变换应用到canvas中
        mCanvas.concat(matrix1);


        //测试canvas的对应方法， 说明canvas操作对应于前乘
//        mCanvas.rotate(30);
//        mCanvas.translate(500f,0);

    }

    //测试后乘
    private void testPost() {
        Matrix matrix1=new Matrix();
        //首先旋转30°
        matrix1.preRotate(30);
        //之后后乘位移(500，0)
        matrix1.postTranslate(500f,0);
        //将变换应用到canvas中
        mCanvas.concat(matrix1);
    }

    /**
     * 测试基于图片中心 的 rotate方法
     * 测试结果两种方法效果一样
     */
    private void testRotateByPoint() {
        Matrix matrix = new Matrix();
        float degree = 60;
//        matrix.preRotate(degree,mBitmap.getWidth()/4,mBitmap.getHeight()/4);
//        matrix.preRotate(degree,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//        matrix.postRotate(degree,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
        matrix.postRotate(degree, mBitmap.getWidth() / 4, mBitmap.getHeight() / 4);
        mCanvas.concat(matrix);
    }


    /**
     * 测试canvas的concat()方法，
     * 实验结果cancat()相当于matrix的preConcat(),也就是前乘，之前的变换会影响之后cancat的矩阵
     * （例如，之前canvas调用scale(0.5,0.5)方法 ，之后如果concat 一个 translate(1000,1000)的矩阵，
     * 实际效果只有translate(500,500)  因为之前的scale(0.5,0.5)影响到了后面的translate(1000,1000)    ）
     * 如果希望实际效果 translate(1000,1000),那么可以使用 matrix的 postConcat()后乘的方法。这样canvas之前的matrix变换不会影响到后面concat的矩阵
     */
    private void testCanvasConcat() {
        //canvas 进行一些变换
        Log.e(TAG, "original : " + mCanvas.getMatrix().toShortString());
        mCanvas.translate(300f, 400f);
        Log.e(TAG, "translate (300,400) : " + mCanvas.getMatrix().toShortString());
        mCanvas.scale(0.8f, 0.9f);
        Log.e(TAG, "scale (0.8,0.9) : " + mCanvas.getMatrix().toShortString());
        mCanvas.rotate(30f);
        Log.e(TAG, "rotate 30 : " + mCanvas.getMatrix().toShortString());

        //创建 translate 矩阵 ，测试canvas  contact方法
        mMatrix = mCanvas.getMatrix();
        //构造一个 200，300的translate矩阵
        Matrix transMatrix = new Matrix();
        transMatrix.preTranslate(200, 300);
        mCanvas.concat(transMatrix);
        Log.e(TAG, "canvas concat : " + mCanvas.getMatrix().toShortString());

        Matrix preMatrix = new Matrix(mMatrix);
        Matrix postMatrix = new Matrix(mMatrix);
        preMatrix.preConcat(transMatrix);
        Log.e(TAG, "pre concat : " + preMatrix.toShortString());
        postMatrix.postConcat(transMatrix);
        Log.e(TAG, "post concat : " + postMatrix.toShortString());
    }


    /**
     * 设置 选定要 测试的方法
     *
     * @param m
     */
    public void setSelectMethod(int m) {
        this.selectMethod = m;
    }

    /**
     * 选择对应方法  进行matrix变换
     */
    private void selectMethodAndProcess() {
        switch (selectMethod) {
            case MatrixMethodDefine.MATRIX_TEST_METHOD_PRE:
                testPre();
                break;
            case MatrixMethodDefine.MATRIX_TEST_METHOD_POST:
                testPost();
                break;
            case MatrixMethodDefine.MATRIX_TEST_METHOD_CANVAS_CONCAT:
                testCanvasConcat();
                break;
            case MatrixMethodDefine.MATRIX_TEST_METHOD_ROTATE_BY_POINT:
                testRotateByPoint();
                break;
        }
    }

    /**
     * 将矩阵转换成三行三列的字符串，小数点保留2位
     *
     * @return
     */
    public String getMatrixString() {
        if (mMatrix == null) {
            return "";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        StringBuilder sb = new StringBuilder(64);
        float[] values = new float[9];
        mMatrix.getValues(values);
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


    /**
     * 调整bitmap 显示在屏幕中间
     */
    private void adjustToCenter() {
        mCanvas.translate(getMeasuredWidth() / 2 - mBitmap.getWidth() / 2, getMeasuredHeight() / 2 - mBitmap.getHeight() / 2);
    }

    /**
     * 将当前matrix值绘制到屏幕右上角
     */
    private void drawMatrixValueText() {
        //将当前canvas矩阵的matrix记录下
        mMatrix = mCanvas.getMatrix();
        //回复 canvas到屏幕左上角
        mCanvas.restore();
        //创建 矩形和文字的画笔
        Paint recPaint = new Paint();
        recPaint.setColor(Color.BLACK);
        recPaint.setStyle(Paint.Style.FILL);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(35);


        //在矩形里面绘制文字
        String value = getMatrixString();
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
    }


    public void apply() {
        invalidate();
    }

    public void center() {
        centerFlag = true;
        invalidate();
    }

    public void reset() {
        centerFlag = false;
        selectMethod = MatrixMethodDefine.MATRIX_TEST_METHOD_DEFAULT;
        invalidate();
    }

}
