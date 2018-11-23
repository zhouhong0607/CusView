package com.example.macroz.myapplication.newsapptest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 类描述:   3D 翻转广告 跟随滚动 广告A 翻转到广告B
 * 创建人:   macroz
 * 创建时间: 2018/10/31 上午10:14
 * 修改人:   macroz
 * 修改时间: 2018/10/31 上午10:14
 * 修改备注:
 */
public class RotateAdView extends AppCompatImageView {
    private static final String TAG = "RotateAdView";

    //camera旋转方案
    public static final int DRAW_PLAN_A = 0;
    //多边形方案
    public static final int DRAW_PLAN_B = 1;

    private int mDrawPlan;

    //方案一旋转的时候用镜像图画
    private volatile Bitmap defaultBitmap, aBitmap, bBitmap, aMirrorBitmap;
    private Bitmap curDrawBitmap;
    private Camera mCamera;
    private Matrix mMatrix;
    //进度0~1 A翻到B
    private float progress;
    private Paint mPaint;

    public RotateAdView(Context context) {
        this(context, null);
    }

    public RotateAdView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateAdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mCamera = new Camera();
        mMatrix = new Matrix();
        mPaint = new Paint();
        mDrawPlan = DRAW_PLAN_A;
    }


    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setDefaultBitmap(Bitmap defaultBitmap) {
        this.defaultBitmap = defaultBitmap;
    }

    public void setaBitmap(Bitmap aBitmap) {
        if (mDrawPlan == DRAW_PLAN_A) {
            //对原始图做镜像处理
            this.aBitmap = aBitmap;
            aMirrorBitmap = mirrorImage(aBitmap);
        } else {
            this.aBitmap = aBitmap;
        }
    }

    public void setbBitmap(Bitmap bBitmap) {
        this.bBitmap = bBitmap;
    }


    public void setDrawPlan(int drawPlan) {
        mDrawPlan = drawPlan;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //A 图没的时候 画默认图
        if (aBitmap == null && defaultBitmap != null) {
            //画默认图
//            canvas.drawBitmap(defaultBitmap, 0, 0, mPaint);
            curDrawBitmap = defaultBitmap;
            setImageBitmap(defaultBitmap);
            super.onDraw(canvas);
            Log.d(TAG, "only  draw default bitmap  ");
            return;
        }
        //仅有 A图
        if (aBitmap != null && bBitmap == null) {
            //画A图
//            canvas.drawBitmap(aBitmap, 0, 0, mPaint);
            curDrawBitmap = aBitmap;
            setImageBitmap(aBitmap);
            super.onDraw(canvas);
            Log.d(TAG, "only  draw A bitmap");
            return;
        }

        if (!isAllBitmapReady()) {
            return;
        }

        //此时matrix会吧B图完全绘出来出现闪的情况 progress 0.5不做处理
        if (progress == 0.5) {
            return;
        }
        //A B 图都准备情况 做旋转操作
        canvas.save();
        mMatrix.reset();
        mCamera.save();


        if (mDrawPlan == DRAW_PLAN_A) {
            drawByPlanA(canvas);
        } else {
            drawByPlanB(canvas);
        }

        canvas.concat(mMatrix);
        super.onDraw(canvas);

        mCamera.restore();
        canvas.restore();

    }

    //所有资源都已准备好
    private boolean isAllBitmapReady() {
        if (mDrawPlan == DRAW_PLAN_A) {
            return aBitmap != null && bBitmap != null && aMirrorBitmap != null;
        } else {
            return aBitmap != null && bBitmap != null;
        }
    }

    //camera旋转方式
    private void drawByPlanA(Canvas canvas) {
//        mMatrix = canvas.getMatrix();
//        mCamera.save();
        //计算旋转角度
        float degree;
        if (progress < 0.5f) {
            degree = 180f - progress / 0.5f * 90;
            //水平镜像
            mMatrix.postScale(-1, 1);

        } else {
            degree = 90f - (progress - 0.5f) / 0.5f * 90f;
        }
        Log.d(TAG, "progress : " + progress + " degree: " + degree);
        mCamera.rotateY(degree);
        mCamera.getMatrix(mMatrix);
        //设置旋转中心
        float transX;
        if (progress == 1) {
            transX = 0;
        } else {
            transX = getMeasuredWidth() * (1f - progress);
        }
        float centerY = getMeasuredHeight() / 2f;

        //调整旋转坐标轴
        mMatrix.postTranslate(transX, 0);
        Log.d(TAG, "translate  X   : " + transX);
        //设置旋转中心
        mMatrix.preTranslate(0, -centerY);
        mMatrix.postTranslate(0, centerY);

        Bitmap drawBitamp = progress < 0.5f ? aMirrorBitmap : bBitmap;
        if (curDrawBitmap != drawBitamp) {
            curDrawBitmap = drawBitamp;
            setImageBitmap(drawBitamp);
        }

//        canvas.drawBitmap(drawBitamp, mMatrix, mPaint);
//        mCamera.restore();
    }

    //多边形方式
    private void drawByPlanB(Canvas canvas) {
//        mMatrix.reset();
        float[] src = new float[8];
        float[] dst = new float[8];

        //x方向收缩量
        float xCut;
        //y方向收缩量
        float yCut;

        if (progress < 0.5) {
            xCut = progress * getMeasuredWidth();
        } else {
            xCut = (1f - progress) * getMeasuredWidth();
        }
        //y最大缩减到 0.5
        float yMaxCutRatio = 0.5f;

        if (progress < 0.5) {
            yCut = progress * getMeasuredHeight() * yMaxCutRatio;
        } else {
            yCut = (1f - progress) * getMeasuredHeight() * yMaxCutRatio;
        }


        //源图位置
        src[0] = 0f;
        src[1] = 0f;

        src[2] = getMeasuredWidth();
        src[3] = 0f;

        src[4] = getMeasuredWidth();
        src[5] = getMeasuredHeight();

        src[6] = 0f;
        src[7] = getMeasuredHeight();

        //目标图位置
        if (progress < 0.5f) {
            //左侧边缩
            dst[0] = xCut;
            dst[1] = yCut;

            dst[2] = getMeasuredWidth() - xCut;
            dst[3] = 0;

            dst[4] = getMeasuredWidth() - xCut;
            dst[5] = getMeasuredHeight();

            dst[6] = xCut;
            dst[7] = getMeasuredHeight() - yCut;

        } else {
            //右侧边缩
            dst[0] = xCut;
            dst[1] = 0;

            dst[2] = getMeasuredWidth() - xCut;
            dst[3] = yCut;

            dst[4] = getMeasuredWidth() - xCut;
            dst[5] = getMeasuredHeight() - yCut;

            dst[6] = xCut;
            dst[7] = getMeasuredHeight();
        }


        mMatrix.setPolyToPoly(src, 0, dst, 0, 4);
        Bitmap drawBitamp = progress < 0.5f ? aBitmap : bBitmap;
        if (curDrawBitmap != drawBitamp) {
            curDrawBitmap = drawBitamp;
            setImageBitmap(drawBitamp);
        }
//        canvas.drawBitmap(drawBitamp, mMatrix, mPaint);
    }


    //bitmap水平镜像
    private Bitmap mirrorImage(Bitmap a) {
        int w = a.getWidth();
        int h = a.getHeight();
        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        return Bitmap.createBitmap(a, 0, 0, w, h, m, true);
    }

}
