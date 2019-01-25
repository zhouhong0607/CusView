package com.example.macroz.myapplication.animator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * liujiakuo on 2018/12/14 10:56
 */
public class StartSnapHelper extends LinearSnapHelper {

    private static final String TAG = "StartSnapHelper";
    //每英寸滚动的时间
    private static final float SCROLL_TIME_PER_INCH = 50.0f;

    private float timePerInch = SCROLL_TIME_PER_INCH;
    private float dampingRatio = 1f;

    private OrientationHelper mHorizontalHelper, mVerticalHelper;
    private RecyclerView mRecyclerView;

    private boolean mIsFling = false;

    public StartSnapHelper() {

    }

    /**
     * 设置RecyclerView
     * @param recyclerView
     */
    public void bindRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }


    public void enableAutoLocate(boolean enable){
        if(mRecyclerView==null){
            return;
        }
        if(enable){
            attachToRecyclerView(mRecyclerView);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && mIsFling) {
                        //将个延时等待其他 addOnScrollListener处理完
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mIsFling = false;
                            }
                        }, 20);
                    }
                }
            });
        }else {
            attachToRecyclerView(null);
        }
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager layoutManager, View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    private int distanceToStart(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    @Override
    public boolean onFling(int velocityX, int velocityY) {
        mIsFling = true;
        return super.onFling(velocityX, velocityY);
    }

    /**
     * 滚动速度调整 设置每英寸滚动时间, 时间越短滚动速度越快
     *
     * @param timePerInch
     */
    public void setTimePerInch(float timePerInch) {
        this.timePerInch = timePerInch;
    }

    /**
     * 设置阻尼效果
     *
     * @param dampingRatio
     */
    public void setDampingRatio(float dampingRatio) {
        this.dampingRatio = dampingRatio;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        float vX = velocityX * 1f / dampingRatio;
        Log.d(TAG, "velocityX： " + velocityX + " after cut , vX: " + vX);
        return super.findTargetSnapPosition(layoutManager, (int) vX, velocityY);
    }

    /**
     * 手动 定位到pos
     *
     * @param pos
     */
    public void LocateToPosition(int pos) {
        if (mRecyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager == null || pos < 0 || pos >= layoutManager.getItemCount()) {
            return;
        }
        RecyclerView.SmoothScroller smoothScroller = this.createScroller(layoutManager);
        if (smoothScroller == null) {
            return;
        }
        smoothScroller.setTargetPosition(pos);
        layoutManager.startSmoothScroll(smoothScroller);
    }


    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {

            if (layoutManager.canScrollHorizontally()) {
                return findStartView(layoutManager, getHorizontalHelper(layoutManager));
            } else {
                return findStartView(layoutManager, getVerticalHelper(layoutManager));
            }
        }

        return super.findSnapView(layoutManager);
    }


    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               OrientationHelper helper) {
        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            //需要判断是否是最后一个Item，如果是最后一个则不让对齐，以免出现最后一个显示不完全。
            boolean isLastItem = ((LinearLayoutManager) layoutManager)
                    .findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1;

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null;
            }

            View child = layoutManager.findViewByPosition(firstChild);

            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                    && helper.getDecoratedEnd(child) > 0) {
                return child;
            } else {
                if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition()
                        == layoutManager.getItemCount() - 1) {
                    return null;
                } else {
                    return layoutManager.findViewByPosition(firstChild + 1);
                }
            }
        }

        return super.findSnapView(layoutManager);
    }

    /**
     * 创建自己的scroller 进行一些配置
     *
     * @param layoutManager
     * @return
     */
    @Nullable
    @Override
    protected RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView == null || !(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            protected void onTargetFound(View targetView, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                if (mRecyclerView != null) {
                    int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(), targetView);
                    if (snapDistances == null) {
                        return;
                    }
                    int dx = snapDistances[0];
                    int dy = snapDistances[1];
                    int time = this.calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                    if (time > 0) {
                        action.update(dx, dy, time, this.mDecelerateInterpolator);
                    }
                }
            }

            @Override
            protected int calculateTimeForScrolling(int dx) {
                int time = super.calculateTimeForScrolling(dx);
                Log.d(TAG, "time for scrolling : " + time);
                return time;
            }

            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return timePerInch / (float) displayMetrics.densityDpi;
            }
        };
    }

    public boolean isFling() {
        return mIsFling;
    }

    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;

    }
}

