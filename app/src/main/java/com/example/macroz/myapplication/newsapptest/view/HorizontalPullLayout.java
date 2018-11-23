package com.example.macroz.myapplication.newsapptest.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.example.macroz.myapplication.animation.SimpleAnimatorListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/9/28 下午2:12
 * 修改人:   macroz
 * 修改时间: 2018/9/28 下午2:12
 * 修改备注:
 */
public class HorizontalPullLayout extends ViewGroup {

    private final String TAG = HorizontalPullLayout.class.getSimpleName();

    //中心主View
    private View mCenterView;
    //右侧拉出的View
    private View mRightExtraView;
    //左侧拉出的View
    private View mLeftExtraView;

    //手势检测部分
    private PointF mLastActionPoint;
    private float xScrollDiff;

    //原始位置
    private static final int STATE_NORMAL = 0;
    //左拉出状态
    private static final int STATE_PULL_LEFT = 1;
    //右拉出状态
    private static final int STATE_PULL_RIGHT = 2;
    private int mState = STATE_NORMAL;

    //向左拉出开关
    private boolean mLeftDragEnable = true;
    //向右拉出开关
    private boolean mRightDragEnable = true;

    //动画相关
    private ValueAnimator mReturnAnimator;
    //动画时长
    private static final int ANIMATOR_DURATION = 200;

    //滚动的阈值
    private float mDragThreshold = 0.75f;
    //计算阈值的距离 , progress=1 时拉出的距离
    private float mDragLimit = Integer.MAX_VALUE >> 2;

    //允许拉出的最大距离(像素值)
    private float mMaxDragDistance = Integer.MAX_VALUE >> 2;

    private List<OnDragListener> mOnDragListeners = new ArrayList<>();

    public HorizontalPullLayout(Context context) {
        this(context, null);
    }

    public HorizontalPullLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public HorizontalPullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPullLayout();
    }

    private void initPullLayout() {
        mLastActionPoint = new PointF();
        mReturnAnimator = new ValueAnimator();
    }

    /**
     * 布局加载完成后  解析对应xml文件中子View
     * 子View数：
     * 1：centerView
     * 2：centerView rightExtraView
     * >=3：leftExtraView centerView rightExtraView
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            mCenterView = bindView(null);
            mLeftExtraView = bindView(null);
            mRightExtraView = bindView(null);
        } else if (getChildCount() == 1) {
            mCenterView = getChildAt(0);
            mLeftExtraView = bindView(null);
            mRightExtraView = bindView(null);
        } else if (getChildCount() == 2) {
            mCenterView = getChildAt(0);
            mRightExtraView = getChildAt(1);
            mLeftExtraView = bindView(null);
        } else {
            mLeftExtraView = getChildAt(0);
            mCenterView = getChildAt(1);
            mRightExtraView = getChildAt(2);
        }
    }

    /**
     * 手动设置子View
     *
     * @param center     中心View
     * @param leftExtra  左侧拉出View
     * @param rightExtra 右侧拉出View
     */
    public void setViews(View center, View leftExtra, View rightExtra) {
        removeAllViews();
        mLeftExtraView = bindView(leftExtra);
        mCenterView = bindView(center);
        mRightExtraView = bindView(rightExtra);
    }

    private View bindView(View sourceView) {
        if (sourceView == null) {
            sourceView = new View(getContext());
        }
        if (sourceView.getLayoutParams() == null) {
            sourceView.setLayoutParams(generateDefaultLayoutParams());
        }
        addView(sourceView);
        return sourceView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制CenterView的高度使用
        mCenterView.measure(widthMeasureSpec, getChildMeasureSpec(heightMeasureSpec,
                getPaddingTop() + getPaddingBottom(), mCenterView.getLayoutParams().height));
        int demandHeight = mCenterView.getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
        int spec = MeasureSpec.makeMeasureSpec(Math.max(demandHeight, MeasureSpec.getSize(heightMeasureSpec)), MeasureSpec.AT_MOST);
        mLeftExtraView.measure(getExtraViewWidthSpec(mLeftExtraView), spec);
        mRightExtraView.measure(getExtraViewWidthSpec(mRightExtraView), spec);
        super.onMeasure(widthMeasureSpec, spec);
        Log.i(TAG, "测量  mCenterView  width:  " + mCenterView.getMeasuredWidth() + " height " + mCenterView.getMeasuredHeight());
        Log.i(TAG, "测量  mRightExtraView  width:  " + mRightExtraView.getMeasuredWidth() + " height " + mRightExtraView.getMeasuredHeight());
        Log.i(TAG, "测量  mLeftExtraView  width:  " + mLeftExtraView.getMeasuredWidth() + " height " + mLeftExtraView.getMeasuredHeight());
    }

    private int getExtraViewWidthSpec(View extraView) {
        LayoutParams lp = extraView.getLayoutParams();
        if (lp == null) {
            lp = generateDefaultLayoutParams();
            extraView.setLayoutParams(lp);
        }
        if (lp.width == LayoutParams.WRAP_CONTENT || lp.width == LayoutParams.MATCH_PARENT) {
            return MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        } else {
            return MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mCenterView.layout(0, getPaddingTop(), mCenterView.getMeasuredWidth(), getPaddingTop() + mCenterView.getMeasuredHeight());
        //将extraView 放到 测量区域外部
        int leftViewMarginRight = 0;
        int rightViewMarginLeft = 0;
        if (checkLayoutParams(mLeftExtraView.getLayoutParams())) {
            leftViewMarginRight = ((MarginLayoutParams) mLeftExtraView.getLayoutParams()).rightMargin;
        }
        if (checkLayoutParams(mRightExtraView.getLayoutParams())) {
            rightViewMarginLeft = ((MarginLayoutParams) mRightExtraView.getLayoutParams()).leftMargin;
        }
        mRightExtraView.layout(getMeasuredWidth() + rightViewMarginLeft, 0, getMeasuredWidth()
                + mRightExtraView.getMeasuredWidth() + rightViewMarginLeft, mRightExtraView.getMeasuredHeight());
        mLeftExtraView.layout(-mLeftExtraView.getMeasuredWidth() - leftViewMarginRight,
                0, -leftViewMarginRight, mLeftExtraView.getMeasuredHeight());

        Log.i(TAG, "布局 mCenterView:" + " 0 , 0 " + mCenterView.getMeasuredWidth() + " , " + mCenterView.getMeasuredHeight());
        Log.i(TAG, "布局 mRightExtraView: " + getMeasuredWidth() + " , 0  " + (getMeasuredWidth() + mRightExtraView.getMeasuredWidth()) + " , " + mRightExtraView.getMeasuredHeight());
        Log.i(TAG, "布局 mLeftExtraView: " + (-mLeftExtraView.getMeasuredWidth()) + " , 0  , 0 " + mLeftExtraView.getMeasuredHeight());
    }

    /**
     * 更新子View的位置
     *
     * @param scrollX x方向移动量
     */
    private void updateChildrenScrollX(float scrollX) {

        if (!mLeftDragEnable && scrollX > 0) {
            scrollX = 0;
        }
        if (!mRightDragEnable && scrollX < 0) {
            scrollX = 0;
        }


        //左拉状态 transX不能 > 0 ,右拉状态 transX不能 <0
        if (mState == STATE_PULL_LEFT) {
            scrollX = scrollX > 0 ? scrollX : 0;
            scrollX = scrollX < mMaxDragDistance ? scrollX : mMaxDragDistance;
        } else if (mState == STATE_PULL_RIGHT) {
            scrollX = scrollX < 0 ? scrollX : 0;
            scrollX = scrollX > -mMaxDragDistance ? scrollX : -mMaxDragDistance;
        }

        // 添加阻尼效果
        float dampingRatio;
        if (mMaxDragDistance == 0) {
            dampingRatio = 1;
        } else {
            dampingRatio = Math.abs(getScrollX()) / mMaxDragDistance;
        }
        scrollX = getScrollX() + (scrollX - getScrollX()) * (1 - dampingRatio);

//        Log.e(TAG, "dampingRatio:  " + dampingRatio);
//        Log.e(TAG, "scrollX:  " + scrollX);

        scrollTo((int) scrollX, 0);
        //通知监听
        notifyOnDragProgressUpdate(getProgress(), mState);

        Log.d(TAG, "updateChildrenScrollX:  " + scrollX);
    }


    @Override
    public void scrollTo(int x, int y) {

        if (!mLeftDragEnable && x > 0) {
            x = 0;
        }
        if (!mRightDragEnable && x < 0) {
            x = 0;
        }


        //左拉状态 transX不能 > 0 ,右拉状态 transX不能 <0
        if (mState == STATE_PULL_LEFT) {
            x = x > 0 ? x : 0;
            x = x < (int) mMaxDragDistance ? x : (int) mMaxDragDistance;
        } else if (mState == STATE_PULL_RIGHT) {
            x = x < 0 ? x : 0;
            x = x > -(int) mMaxDragDistance ? x : -(int) mMaxDragDistance;
        }

//         添加阻尼效果
        float dampingRatio;
        if (mMaxDragDistance == 0) {
            dampingRatio = 1;
        } else {
            dampingRatio = Math.abs(getScrollX()) / mMaxDragDistance;
        }
        x = (int) (getScrollX() + (x - getScrollX()) * (1 - dampingRatio));

        Log.e(TAG, "dampingRatio:  " + dampingRatio);

        super.scrollTo(x, getScrollY());
        //通知监听
        notifyOnDragProgressUpdate(getProgress(), mState);

        Log.w(TAG, "scrollTo :  " + x);
    }

    public float getProgress() {
        if (mDragLimit == 0) {
            return 1f;
        }
        return Math.abs(getScrollX()) / mDragLimit;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent action : ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent action : ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "dispatchTouchEvent action : ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent action : ACTION_UP");
                break;

        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG, "mScroller.abortAnimation() 终止动画");
                break;
            case MotionEvent.ACTION_MOVE:
                xScrollDiff = mLastActionPoint.x - ev.getX();
                break;
            default:
                xScrollDiff = 0f;
        }
        mLastActionPoint.set(ev.getX(), ev.getY());

        // 如果需要滚到头连续拉出效果打开注释
        //防止子View不允许父 拦截事件，这里手动调一下拦截判断，给子View一个cancel
        if (!mCenterView.canScrollHorizontally(1) || !mCenterView.canScrollHorizontally(-1)) {
            if (onInterceptTouchEvent(ev)) {
                final long now = SystemClock.uptimeMillis();
                MotionEvent event = MotionEvent.obtain(now, now, MotionEvent.ACTION_CANCEL, 0f, 0f, 0);
                event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
                mCenterView.dispatchTouchEvent(event);
                event.recycle();
                return onTouchEvent(ev);
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e(TAG, "onInterceptTouchEvent action : ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e(TAG, "onInterceptTouchEvent action : ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e(TAG, "onInterceptTouchEvent action : ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "onInterceptTouchEvent action : ACTION_UP");
//                break;
//        }


        if (!mLeftDragEnable && !mRightDragEnable) {
            return super.onInterceptTouchEvent(ev);
        }

        //非0 位置 拦截所有事件
        if (getScrollX() != 0) {
            return true;
        }

        //0 位置时 , 仅拦截 向左拉出  向右拉出动作
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            //左拉( 1  检查向左滚动)
            if (!mCenterView.canScrollHorizontally(1) && xScrollDiff > 0 && mLeftDragEnable) {
                setState(STATE_PULL_LEFT);
                return true;
            }
            //右拉( -1 检查 向右滚动)
            if (!mCenterView.canScrollHorizontally(-1) && xScrollDiff < 0 && mRightDragEnable) {
                setState(STATE_PULL_RIGHT);
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e(TAG, "onTouchEvent action : ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e(TAG, "onTouchEvent action : ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e(TAG, "onTouchEvent action : ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "onTouchEvent action : ACTION_UP");
//                break;
//        }

        //防止不经过 拦截的move事件传过来
        if (mState == STATE_NORMAL) {
            return false;
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mReturnAnimator.cancel();
                break;
            case MotionEvent.ACTION_MOVE:
                //设置子View的位置
                scrollTo((int) (getScrollX() + xScrollDiff), 0);
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
        if (getProgress() >= mDragThreshold) {
            notifyOnDragOver();
        }

        mReturnAnimator.setIntValues(getScrollX(), 0);
        mReturnAnimator.setDuration(ANIMATOR_DURATION);
        mReturnAnimator.setInterpolator(new LinearInterpolator());
        mReturnAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollTo((int) animation.getAnimatedValue(), 0);
            }
        });
        mReturnAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //判断下trans 防止 cancel的时候回调设置状态normal
                if (getScrollX() == 0) {
                    setState(STATE_NORMAL);
                }
            }

        });
        mReturnAnimator.start();
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    private void setState(int state) {
        //拉出的时候不允许父View拦截事件
        if (state == STATE_PULL_LEFT || state == STATE_PULL_RIGHT) {
            requestDisallowInterceptTouchEvent(true);
        } else {
            requestDisallowInterceptTouchEvent(false);
        }


        if (mState != state) {
            notifyOnStateChanged(state);
        }
        mState = state;

        switch (state) {
            case STATE_NORMAL:
                Log.w(TAG, "STATE_NORMAL");
                break;
            case STATE_PULL_LEFT:
                Log.w(TAG, "STATE_PULL_LEFT");
                break;
            case STATE_PULL_RIGHT:
                Log.w(TAG, "STATE_PULL_RIGHT");
                break;
        }

    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return true;
    }

    public float getDragThreshold() {
        return mDragThreshold;
    }

    public void setDragThreshold(float dragThreshold) {
        mDragThreshold = dragThreshold > 0f ? dragThreshold : 0f;
    }

    public float getMaxDragDistance() {
        return mMaxDragDistance;
    }

    public void setMaxDragDistance(float maxDragDistance) {
        mMaxDragDistance = maxDragDistance > 0f ? maxDragDistance : 0f;
    }

    public float getDragLimit() {
        return mDragLimit;
    }

    public void setDragLimit(float dragLimit) {
        mDragLimit = dragLimit > 0f ? dragLimit : 0f;
    }

    public void setLeftDragEnable(boolean leftDragEnable) {
        mLeftDragEnable = leftDragEnable;
    }

    public void setRightDragEnable(boolean rightDragEnable) {
        mRightDragEnable = rightDragEnable;
    }

    public void addOnDragListener(OnDragListener onDragListener) {
        mOnDragListeners.add(onDragListener);
    }

    public void removeOnDragListener(OnDragListener onDragListener) {
        mOnDragListeners.remove(onDragListener);
    }

    private void notifyOnDragOver() {
        for (OnDragListener listener : mOnDragListeners) {
            listener.onDragOver();
        }
    }

    private void notifyOnDragProgressUpdate(float progress, int curState) {
        for (OnDragListener listener : mOnDragListeners) {
            listener.onDragProgressUpdate(progress, curState);
        }
    }

    private void notifyOnStateChanged(int state) {
        for (OnDragListener listener : mOnDragListeners) {
            listener.onStateChanged(state);
        }
    }

    public interface OnDragListener {
        /**
         * 拉出进度更新
         *
         * @param progress 当前拉出的距离与设置最大拉动距离的比值
         * @param curState 当前状态  左拉{@link #STATE_PULL_LEFT} 右拉{@link #STATE_PULL_RIGHT}
         * @return
         */
        void onDragProgressUpdate(float progress, int curState);

        /**
         * 释放时超过指定阈值
         *
         * @return
         */
        void onDragOver();

        /**
         * 状态变换时通知
         *
         * @param state
         */
        void onStateChanged(int state);


    }

    public static class SimpleOnDragListener implements OnDragListener {
        @Override
        public void onDragProgressUpdate(float progress, int curState) {

        }

        @Override
        public void onDragOver() {
        }

        @Override
        public void onStateChanged(int state) {

        }
    }
}
