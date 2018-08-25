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
public class MatrixPrePostTestView extends BaseMatrixView {

    private int selectMethod = MatrixMethodDefine.MATRIX_TEST_METHOD_DEFAULT;

    public MatrixPrePostTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSubDraw(Canvas canvas) {
        //选择对应的测试
        selectMethodAndProcess();

        //根据当前 canvas的matrix绘制图像
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);
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
     * pre操作影响叠加
     * post操作不受之前变换的影响
     */
    private void testPre() {
        Log.e(TAG, "******************testPre()*******************");
        Matrix matrix1 = new Matrix();
        //首先旋转30°
        matrix1.preRotate(30);
        //之后前乘位移(500，0)
        matrix1.preTranslate(500f, 0);
        //将变换应用到canvas中
        mCanvas.concat(matrix1);


        //测试canvas的对应方法， 说明canvas操作对应于前乘
//        mCanvas.rotate(30);
//        mCanvas.translate(500f,0);

    }

    //测试后乘
    private void testPost() {
        Log.e(TAG, "******************testPost()*******************");
        Matrix matrix1 = new Matrix();
        //首先旋转30°
        matrix1.preRotate(30);
        //之后后乘位移(500，0)
        matrix1.postTranslate(500f, 0);
        //将变换应用到canvas中
        mCanvas.concat(matrix1);
    }

    /**
     * 测试基于图片中心 的 rotate方法
     * 测试结果两种方法效果一样
     */
    private void testRotateByPoint() {
        Log.e(TAG, "******************testRotateByPoint()*******************");
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
        Log.e(TAG, "******************testCanvasConcat()*******************");
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

    public void reset() {
        super.reset();
        selectMethod = MatrixMethodDefine.MATRIX_TEST_METHOD_DEFAULT;
    }

}
