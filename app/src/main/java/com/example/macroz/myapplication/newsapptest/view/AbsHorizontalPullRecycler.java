package com.example.macroz.myapplication.newsapptest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/9/28 下午2:12
 * 修改人:   macroz
 * 修改时间: 2018/9/28 下午2:12
 * 修改备注:
 */
@Deprecated
public class AbsHorizontalPullRecycler extends RecyclerView {

    private final String TAG = AbsHorizontalPullRecycler.class.getSimpleName();

    //    //中心主View
//    private View mCenterView;
    //右侧拉出的View
    private View mRightExtraView;

    //    private PointF mDownPoint;
//    private PointF mMovePoint;
    private PointF mLastActionPoint;
    private float xDiff;

    //动画相关
    private ValueAnimator mReturnAnimator;
    //动画时长
    private static final int ANIMATOR_DURATION = 500;

    //允许拉出的最大距离(像素值)
    private static final float MAX_X_TRANSLATE = 500;

    public AbsHorizontalPullRecycler(Context context) {
        this(context, null);
    }

    public AbsHorizontalPullRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPullLayout(context);
    }

    private void initPullLayout(Context context) {
        mRightExtraView = new View(context);
        mLastActionPoint = new PointF();
        mReturnAnimator = new ValueAnimator();
    }

//    //强制使用横滑布局
//    @Override
//    public void setLayoutManager(LayoutManager layout) {
//        layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        super.setLayoutManager(layout);
//    }


//    private ViewGroup myParent() {
//        return (ViewGroup) getParent();
//    }

    /**
     * 设置子View
     *
     * @param rightExtra
     */
    public void setViews(View rightExtra) {
        if (rightExtra != null) {
            removeView(mRightExtraView);
            mRightExtraView = rightExtra;
            addView(mRightExtraView);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRightExtraView.measure(MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST), heightMeasureSpec);
        Log.d(TAG, "测量  mRightExtraView  width:  " + mRightExtraView.getMeasuredWidth() + " height " + mRightExtraView.getMeasuredHeight());


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //将extraView 放到 测量区域外部
        mRightExtraView.layout(getMeasuredWidth(), 0, getMeasuredWidth() + mRightExtraView.getMeasuredWidth(), mRightExtraView.getMeasuredHeight());
        Log.d(TAG, "布局 mRightExtraView: " + getMeasuredWidth() + " , 0  " + (getMeasuredWidth() + mRightExtraView.getMeasuredWidth()) + " , " + mRightExtraView.getMeasuredHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                xDiff = ev.getX() - mLastActionPoint.x;
                break;
            default:
                xDiff = 0f;
        }
        updatePoint(mLastActionPoint, ev);
        return super.dispatchTouchEvent(ev);
    }

    private void updatePoint(PointF pointF, MotionEvent ev) {
        pointF.x = ev.getX();
        pointF.y = ev.getY();
    }

    /**
     * 更新子View的位置
     *
     * @param transX x方向移动量
     */
    private void updateChildrenTransX(float transX) {
        //右侧拉出 ， translate距离必须 <=0
        float transValue = transX < 0 ? transX : 0;
        //最大拉出最大长度 MAX_X_TRANSLATE
        transValue = -Math.min(Math.abs(transValue), MAX_X_TRANSLATE);
        setTranslationX(transValue);
        mRightExtraView.setTranslationX(transValue);
        Log.d(TAG, "updateChildrenTransX:  " + transValue);
    }

    /**
     * 获取当前X方向 的translate值
     *
     * @return
     */
    private float getTransX() {
        return getTranslationX();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当前处于拖拽 或者处于恢复位置动画中  拦截事件
        if (getTransX() != 0) {
            return true;
        }
        //中心位置View 不能向左滚动 拦截事件
        if (!canScrollHorizontally(1) && xDiff < 0) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mReturnAnimator.cancel();
                break;
            case MotionEvent.ACTION_MOVE:
                //设置子View的位置
                updateChildrenTransX(getTransX() + xDiff);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                doRelease();
                break;
        }
        return true;
    }

    //恢复到起始的位置
    private void doRelease() {
        mReturnAnimator.setFloatValues(getTransX(), 0);
        mReturnAnimator.setDuration(ANIMATOR_DURATION);
        mReturnAnimator.setInterpolator(new LinearInterpolator());
        mReturnAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateChildrenTransX((float) animation.getAnimatedValue());
            }
        });
        mReturnAnimator.start();
    }

}
