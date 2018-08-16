package com.example.macroz.myapplication.view;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.macroz.myapplication.R;

public class MyView extends View {
    private Paint mPaint;
    private Context mContext;

    private Point control1, control2, control3, control4;

    private int select = 1;

    private Bitmap bitmap;

    public void select(int index) {
        select = index;
    }

    public MyView(Context context) {
        super(context);
        init();
        mContext = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("AAA", String.valueOf(event.getAction()));

                switch (select) {
                    case 1:
                        control1.x = (int) event.getX() - 200;
                        control1.y = (int) event.getY() - 200;
                        break;
                    case 2:
                        control2.x = (int) event.getX() - 200;
                        control2.y = (int) event.getY() - 200;
                        break;
                    case 3:
                        control3.x = (int) event.getX() - 200;
                        control3.y = (int) event.getY() - 200;
                        break;
                    case 4:
                        control4.x = (int) event.getX() - 200;
                        control4.y = (int) event.getY() - 200;
                        break;

                }
                invalidate();
                return true;
            }
        });
        control1 = new Point();
        control2 = new Point();
        control3 = new Point();
        control4 = new Point();


        initBitmap();
        init();
        recording();
        initFoldDraw();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha((int) (0.9 * 255));


    }

    private void initBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.page1, options);

        int bitWid = options.outWidth;
        int bitHei = options.outHeight;

        int expWid = 500;
        int expHeight = 500;
        int resSize = bitWid / expWid < bitHei / expHeight ? bitWid / expWid : bitHei / expHeight;

        resSize -= 1;

        options.inSampleSize = resSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.page1, options);

        Log.e("AAA", "宽度:" + bitmap.getWidth() + "  高度:" + bitmap.getHeight() + " 大小  ：" + bitmap.getByteCount() / 1024 + "  KB");

        control1.x = 0;
        control1.y = 0;

        control2.x = 0 + bitmap.getWidth();
        control2.y = 0;

        control3.x = 0 + bitmap.getWidth();
        control3.y = 0 + bitmap.getHeight();

        control4.x = 0;
        control4.y = 0 + bitmap.getHeight();

    }
    float mFactor;
    int foldNum;
    int mHeight;
    int mWidth ;
    //bitmap 折叠后高度
    int mFoldHeight ;

    //每块
    int mPerHeight;
    int mPerFoldHeight ;
    Matrix[] matrices = new Matrix[foldNum];
    private void initFoldDraw(){
        //折叠因子
         mFactor = 0.8f;
        //折叠后的块数
         foldNum = 8;
        //bitmap 高度
         mHeight = bitmap.getHeight();
         mWidth = bitmap.getWidth();
        //bitmap 折叠后高度
         mFoldHeight = (int) (mHeight * mFactor);

        //每块
         mPerHeight = mHeight / foldNum;
         mPerFoldHeight = mFoldHeight / foldNum;

        //折叠后突出的宽度
        int mPerWidthOffset = (int) Math.sqrt(mPerHeight * mPerHeight - mPerFoldHeight * mPerFoldHeight) /2;

        //这个值过大 会不显示
//        mPerWidthOffset-=100;

         matrices = new Matrix[foldNum];
        //源 点
        float[] src = new float[8];
        //目标点
        float[] dst = new float[8];

        for (int i = 0; i < foldNum; i++) {
            matrices[i] = new Matrix();

            //  左上
            src[0] = 0;
            src[1] = i * mPerHeight;
            //  右上
            src[2] = mWidth;
            src[3] = src[1];
            //  右下
            src[4] = mWidth;
            src[5] = src[1] + mPerHeight;
            //  左下
            src[6] = 0;
            src[7] = src[5];

            boolean isEven = i % 2 == 0;

            //左上
            dst[0] = isEven ? 0 : mPerWidthOffset;
            dst[1] = i * mPerFoldHeight;

            //右上
            dst[2] = isEven ? mWidth : mWidth - mPerWidthOffset;
            dst[3] = dst[1];

            //右下
            dst[4] = isEven ? mWidth - mPerWidthOffset : mWidth;
            dst[5] = dst[3] + mPerFoldHeight;

            //左下
            dst[6] = isEven ? mPerWidthOffset : 0;
            dst[7] = dst[5];

            matrices[i].setPolyToPoly(src, 0, dst, 0, 4);

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Log.e("AAA", "onDraw");

        drawFoldView(canvas);


    }


    private void drawFoldView(Canvas canvas) {


        for (int j = 0; j < foldNum; j++) {
            canvas.save();
            //每块画布的位置
            canvas.concat(matrices[j]);
            canvas.clipRect(0, j * mPerHeight, mWidth , (j + 1) * mPerHeight);
            canvas.drawBitmap(bitmap,0,0,null);
            canvas.restore();


        }


    }


    private void bitmapTest(Canvas canvas) {

        canvas.translate(200, 200);

        int[] location = new int[2];
        getLocationOnScreen(location);
        Log.e("AAA", "Location on Screen   x=" + location[0] + "   y=" + location[1]);


//        canvas.drawCircle(control1.x,control1.y,10,mPaint);
//        canvas.drawCircle(control2.x,control2.y,10,mPaint);
//        canvas.drawCircle(control3.x,control3.y,10,mPaint);
//        canvas.drawCircle(control4.x,control4.y,10,mPaint);

        Matrix matrix = new Matrix();
//        matrix.preTranslate(200,200);
        float[] src = {0, 0, bitmap.getWidth(), 0, bitmap.getWidth(), bitmap.getHeight(), 0, bitmap.getHeight()};
        float[] dst = {control1.x, control1.y, control2.x, control2.y, control3.x, control3.y, control4.x, control4.y};
        matrix.setPolyToPoly(src, 0, dst, 0, 4);

//        RectF srcF=new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
//        RectF dstF=new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
//        matrix.setRectToRect(srcF,dstF,Matrix.ScaleToFit.FILL);


        canvas.drawBitmap(bitmap, matrix, mPaint);


//        canvas.drawBitmap(bitmap1,new Matrix(),mPaint);
//        Log.e("AAA","宽度:" +bitmap1.getWidth() +"  高度:"+ bitmap1.getHeight() +" 大小  ：" + bitmap1.getByteCount()/1024 +"  KB");

    }


    //矩阵测试
    private void matrixTest(Canvas canvas) {
        Matrix matrix = canvas.getMatrix();
        Log.e("AAA", matrix.toShortString());

        matrix.preTranslate(200, 200);

        Log.e("AAA", matrix.toShortString());

        canvas.setMatrix(matrix);

    }


    //填充测试
    private void testFill(Canvas canvas) {
        Path path = new Path();
        //内圈
//        path.addCircle(350,250,100, Path.Direction.CW);
//        path.addRect(new RectF(200,200,500,300), Path.Direction.CW);
        //外圈
        path.addRect(new RectF(100, 100, 600, 400), Path.Direction.CW);
        path.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(path, mPaint);

    }

    //贝塞尔曲线测试
    private void beiseirTest(Canvas canvas) {
        Point start, end;
        start = new Point(getMeasuredWidth() / 2 - 300, getMeasuredHeight() / 2);
        end = new Point(getMeasuredWidth() / 2 + 300, getMeasuredHeight() / 2);


        mPaint.setStrokeWidth(30);
        mPaint.setColor(Color.GRAY);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control1.x, control1.y, mPaint);
        canvas.drawPoint(control2.x, control2.y, mPaint);

        mPaint.setStrokeWidth(5);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, mPaint);
        canvas.drawLine(control1.x, control1.y, control2.x, control2.y, mPaint);
        canvas.drawLine(control2.x, control2.y, end.x, end.y, mPaint);

        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLUE);


//        canvas.drawPicture(mPicture,new Rect(0,0,mPicture.getWidth(),200));
//        Bitmap bitmap= BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_heart_unselected);

        Path path = new Path();

//        canvas.translate(getMeasuredWidth()/2,getMeasuredHeight()/2);

        //offset 方法只对之前路径有效，对之后的路径无效。 是一次临时移动， 与canvas 区分开
//        path.offset(getMeasuredWidth()/2,getMeasuredHeight()/2);


        //二阶贝塞尔曲线
        path.moveTo(start.x, start.y);
//        path.quadTo(control1.x,control1.y,getMeasuredWidth()/2+300,getMeasuredHeight()/2);


        //三届贝塞尔曲线
        path.cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);

    }

    private Picture mPicture;

    private void recording() {
        mPicture = new Picture();
        Canvas canvas = mPicture.beginRecording(500, 500);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(250, 250);
        canvas.drawCircle(0, 0, 100, paint);
        mPicture.endRecording();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
