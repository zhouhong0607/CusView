package com.example.macroz.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.Utils;

public class MyFoldLayout extends ViewGroup {

    private final String TAG = "MyFoldLayout";

    private static final int FOLD_NUM = 4;

    //折叠因子
    private float mFactor;
    //折叠的块数
    private int foldNum;

    //bitmap 折叠后高度
    private int mFoldHeight;
    //折叠矩阵
    private Matrix[] matrices;

    //用于拦截子View图像
    private Canvas mInvertCanvas;
    private Bitmap mContainerBitmap;

    private GestureDetector mGestureDetector;

    private Paint mShaderPaint;

    public MyFoldLayout(Context context) {
        this(context, null);

    }

    public MyFoldLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFoldLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }

    public MyFoldLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
        //测量完宽高后 更新参数
        updateFoldInfo();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //总宽度
        int mWidth = getMeasuredWidth();
        //原始每块的高度
        int mPerHeight = getMeasuredHeight() / foldNum;

        //拦截子View图像 保存在 mContainerBitmap中
        super.dispatchDraw(mInvertCanvas);

        //重新绘制拦截后的图像
        for (int j = 0; j < foldNum; j++) {
            canvas.save();
            //每块画布的位置
            canvas.concat(matrices[j]);
            canvas.clipRect(0, j * mPerHeight, mWidth, (j + 1) * mPerHeight);
            canvas.drawBitmap(mContainerBitmap, 0, 0,null);
            canvas.restore();
        }

        drawShadow(canvas);
    }


    private void drawShadow(Canvas canvas) {
        Bitmap bitmap=Utils.getIcon(getResources(),R.drawable.telangpuicon);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mShaderPaint);
//        canvas.drawCircle(bitmap.getWidth()/2,bitmap.getHeight()/2,bitmap.getHeight()/2,mShaderPaint);
    }

    //初始化各项参数
    private void init(Context context) {
        mFactor = 1.0f;
        foldNum = FOLD_NUM;
        matrices = new Matrix[foldNum];
        mInvertCanvas = new Canvas();
        mGestureDetector = new GestureDetector(context, new FoldOnGestureListener());
        mShaderPaint = new Paint();

    }


    private Shader getShader(int i) {
        Shader shader;
        switch (i) {
            case 1:
                //BitmapShader
                Bitmap bitmap= Utils.getIcon(getResources(), R.drawable.telangpuicon);
                shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                break;
            case 2:
                //ComposeShader
                Bitmap heart=Utils.getIcon(getResources(),R.drawable.icon_heart_unselected);
                BitmapShader bitmapShader=new BitmapShader(heart, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                LinearGradient linearGradient=new LinearGradient(0,getMeasuredHeight(),getMeasuredWidth(),getMeasuredHeight(),Color.RED,Color.YELLOW, Shader.TileMode.CLAMP);
                shader=new ComposeShader(bitmapShader,linearGradient, PorterDuff.Mode.MULTIPLY);
                break;
            case 3:
                //LinearGradient
                int[] colors={Color.RED,Color.GREEN,Color.YELLOW};
                float[] positions={0f,0.5f,1.0f};
                shader=new LinearGradient(0,getMeasuredHeight(),getMeasuredWidth(),getMeasuredHeight(), colors,positions, Shader.TileMode.CLAMP);
                break;
            case 4:
                //RadialGradient
                shader=new RadialGradient(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2,Color.RED,Color.GREEN, Shader.TileMode.CLAMP);
                break;
            default:
                //SweepGradient
                int[] colors1={Color.RED,Color.GREEN,Color.YELLOW};
                float[] positions1={0.25f,0.5f,0.75f};
               shader=new SweepGradient(getMeasuredWidth()/2,getMeasuredHeight()/2,colors1,positions1);
                break;
        }
        return shader;
    }

    //跟新折叠因子
    public void setFactor(float mFactor) {
        if (mFactor > 1.0) {
            mFactor = 1.0f;
        } else if (mFactor <= 0.01f) {
            mFactor = 0.01f;
        }
        this.mFactor = mFactor;
        updateFoldInfo();
        invalidate();
    }

    public float getFactor() {
        return this.mFactor;
    }

    //更新各种参数
    private void updateFoldInfo() {
        //总高度
        int mHeight = getMeasuredHeight();
        //总宽度
        int mWidth = getMeasuredWidth();
        //每块原始高度
        int perHeight = mHeight / foldNum;
        //每块折叠后的高度
        int perFoldHeight = (int) (mFactor * mHeight / foldNum);
        //每块折叠后 经过勾股定理算出的 收缩宽度, 这里进行压缩，这个值太大会不显示（原因未明）
        int perOffsetWidth = (int) (Math.sqrt(perHeight * perHeight - perFoldHeight * perFoldHeight) * 0.1);
        //原始每块 各点位置 （左上、右上、右下、左下）
        float[] src = new float[8];
        //折叠后每块  各点位置(左上、右上、右下、左下)
        float[] dst = new float[8];

        //计算 源矩阵 与目标矩阵坐标 ，设置到对应的matrix中
        for (int i = 0; i < foldNum; i++) {
            matrices[i] = new Matrix();

            //  左上
            src[0] = 0;
            src[1] = i * perHeight;
            //  右上
            src[2] = mWidth;
            src[3] = src[1];
            //  右下
            src[4] = mWidth;
            src[5] = src[1] + perHeight;
            //  左下
            src[6] = 0;
            src[7] = src[5];

            boolean isEven = i % 2 == 0;

            //左上
            dst[0] = isEven ? 0 : perOffsetWidth;
            dst[1] = i * perFoldHeight;

            //右上
            dst[2] = isEven ? mWidth : mWidth - perOffsetWidth;
            dst[3] = dst[1];

            //右下
            dst[4] = isEven ? mWidth - perOffsetWidth : mWidth;
            dst[5] = dst[3] + perFoldHeight;

            //左下
            dst[6] = isEven ? perOffsetWidth : 0;
            dst[7] = dst[5];

            //!!!! 注意 矩形上边 等于 下边的时候 会失效  （矩形变成线的时候）
            matrices[i].setPolyToPoly(src, 0, dst, 0, 4);

        }
        //创建一个 当前View大小的Bitmap
        mContainerBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //链接 bitmap与canvas
        mInvertCanvas.setBitmap(mContainerBitmap);

        //更新着色器
        Shader shader = getShader(1);
        mShaderPaint.setShader(shader);

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return mGestureDetector.onTouchEvent(event);
//    }

    private class FoldOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "disY: " + distanceY);
            int height = MyFoldLayout.this.getMeasuredHeight();
            float factorOffset = -distanceY / height;
            float factor = mFactor + factorOffset;
            Log.d(TAG, "setFactor: " + factor);
            MyFoldLayout.this.setFactor(factor);
            return true;
        }
    }
}
