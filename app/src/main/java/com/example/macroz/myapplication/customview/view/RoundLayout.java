package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.utils.ScreenUtils;
import com.example.macroz.myapplication.utils.StringUtil;

/**
 * 类描述:   圆角布局,  用于 ViewGroup需要实现圆角效果的 情况
 * <p>
 * 属性设置
 * app:rectangle_radius="4"                               所有圆角半径4dp
 * app:rectangle_radius_all="4,18,4,18,4,18,4,18"         数组取值对应, 左上角 : top _ left   右上角:top_right 左下角: bottom_left 右下角:bottom_right
 * <p>
 * <p>
 * 创建人:   macroz
 * 创建时间: 2019/3/14 下午2:13
 * 修改人:   macroz
 * 修改时间: 2019/3/14 下午2:13
 * 修改备注:
 */
public class RoundLayout extends FrameLayout {

    private float[] radius = new float[8];
    private Path mPath;
    private RectF mRect;

    public RoundLayout(@NonNull Context context) {
        this(context, null);
    }

    public RoundLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPath = new Path();
        mRect = new RectF();
        float recRadius;
        String recRadiusAll;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLayout);
        recRadius = typedArray.getFloat(R.styleable.RoundLayout_rectangle_radius, 0f);
        recRadiusAll = typedArray.getString(R.styleable.RoundLayout_rectangle_radius_all);
        typedArray.recycle();
        recRadius = ScreenUtils.Companion.dp2px(recRadius);
        for (int i = 0; i < radius.length; i++) {
            radius[i] = recRadius;
        }
        if (TextUtils.isEmpty(recRadiusAll)) {
            return;
        }
        String[] radiusDetails = recRadiusAll.split(",");
        for (int i = 0; i < radiusDetails.length; i++) {
            if (i >= 8) {
                break;
            }
            radius[i] = ScreenUtils.Companion.dp2px(StringUtil.getInteger(radiusDetails[i]));
        }
    }

    public void setCorner(int corner) {
        if (corner < 0) {
            return;
        }
        Log.d("RoundLayout", "setCorner() :" + corner);
        int val = (int) ScreenUtils.Companion.dp2px(corner);
        for (int i = 0; i < radius.length; i++) {
            radius[i] = val;
        }
        mPath.addRoundRect(mRect, radius, Path.Direction.CW);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("RoundLayout", "onSizeChanged()");
        mRect.left = getPaddingLeft();
        mRect.top = getPaddingTop();
        mRect.right = w - getPaddingRight();
        mRect.bottom = h - getPaddingBottom();
        mPath.addRoundRect(mRect, radius, Path.Direction.CW);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mPath);
        super.draw(canvas);
        canvas.restore();


        Log.d("RoundLayout", "draw()");
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.d("RoundLayout", "drawChild()");
        canvas.save();
        canvas.clipPath(mPath);
        boolean result = super.drawChild(canvas, child, drawingTime);
        canvas.restore();
        return result;
    }
}
