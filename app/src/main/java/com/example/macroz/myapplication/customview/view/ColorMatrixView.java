package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.macroz.myapplication.R;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/15 下午2:36
 * 修改人:   macroz
 * 修改时间: 2018/11/15 下午2:36
 * 修改备注:
 */
public class ColorMatrixView extends View {
    private Paint mPaint;
    private Bitmap dstBmp, srcBmp;
    private RectF dstRect, srcRect;

    private Xfermode mXfermode;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.SRC_ATOP;

    public ColorMatrixView(Context context) {
        this(context, null);

    }

    public ColorMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG );
        dstBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.dst);
//        srcBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.src);
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.banner);
        mXfermode = new PorterDuffXfermode(mPorterDuffMode);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        int saveCount = canvas.saveLayer(srcRect, mPaint, Canvas.ALL_SAVE_FLAG);

//        testPortDuff(canvas);

        testColorMatrix(canvas);

        //还原画布
        canvas.restoreToCount(saveCount);
    }

    /**
     * 测试  PorterDuff.Mode
     *
     * @param canvas
     */
    private void testPortDuff(Canvas canvas) {
        //绘制目标图
        canvas.drawBitmap(dstBmp, null, dstRect, mPaint);
        //设置混合模式
        mPaint.setXfermode(mXfermode);
        //绘制源图
        canvas.drawBitmap(srcBmp, null, srcRect, mPaint);
        //清除混合模式
        mPaint.setXfermode(null);
    }

    private void testColorMatrix(Canvas canvas) {
        //绘制源图
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(0f, 0f, 0f, 0.5f);
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        canvas.drawBitmap(srcBmp, null, srcRect, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = w <= h ? w : h;
        int centerX = w / 2;
        int centerY = h / 2;
        int quarterWidth = width / 4;
//        srcRect = new RectF(centerX - quarterWidth, centerY - quarterWidth, centerX + quarterWidth, centerY + quarterWidth);
        srcRect = new RectF(centerX - srcBmp.getWidth() / 2, centerY - srcBmp.getHeight() / 2, centerX + srcBmp.getWidth() / 2, centerY + srcBmp.getHeight() / 2);
        dstRect = new RectF(centerX - quarterWidth, centerY - quarterWidth, centerX + quarterWidth, centerY + quarterWidth);
    }
}
