package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.macroz.myapplication.R;

/**
 * 类描述:   小飞机 转圈圈
 * 创建人:   macroz
 * 创建时间: 2018/8/14 下午9:19
 * 修改人:   macroz
 * 修改时间: 2018/8/14 下午9:19
 * 修改备注:
 */
public class PlaneRotateView extends View {

    private final String TAG="RotateView";

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    private Paint mDeafultPaint;


    public PlaneRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_plane, options);
        mMatrix = new Matrix();
        mDeafultPaint = new Paint();
        mDeafultPaint.setStyle(Paint.Style.STROKE);
        mDeafultPaint.setColor(Color.BLACK);
        mDeafultPaint.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);      // 平移坐标系


        //第一种方案
        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形

        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势

        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片

////      上面等效于以下两种   第一种
//        mMatrix.preTranslate(mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//        mMatrix.preRotate(degrees);
//        mMatrix.preTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight()/2);
////        第二种
//        mMatrix.postTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight()/2);
//        mMatrix.postRotate(degrees);
//        mMatrix.postTranslate(mBitmap.getWidth()/2,mBitmap.getHeight()/2);




        Log.e(TAG,"post Rotate: "+ mMatrix.toShortString() );

        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        Log.e(TAG,"post Translate: "+ mMatrix.toShortString() );





        //第二种方案



        // 获取当前位置的坐标以及趋势的矩阵
//        measure.getMatrix(measure.getLength() * currentValue, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
//
//        Log.e(TAG,"get Matrix: "+ mMatrix.toShortString() );
//
//        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
//        Log.e(TAG,"pre Translate : "+ mMatrix.toShortString() );



        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头

        invalidate();



    }
}
