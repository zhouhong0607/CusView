package com.example.macroz.myapplication.newsapptest.view;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.animator.RecyclerAnimatorManager;
import com.example.macroz.myapplication.utils.DataUtils;
import com.example.macroz.myapplication.utils.ViewUtils;

/**
 * 类描述:   右侧可拉出Lottie的RecyclerView
 * 获取到RecyclerView   {@link #getRecyclerView()}
 * 获取到LottieView     {@link #getLottieView()}
 * 创建人:   macroz
 * 创建时间: 2018/12/19 上午10:27
 * 修改人:   macroz
 * 修改时间: 2018/12/19 上午10:27
 * 修改备注:
 */
public class RightLottieRecyclerView extends HorizontalPullLayout {


    /**
     * 震动时长
     */
    private static final long VIBRATE_DURATION = 50L;

    private RecyclerView mRecyclerView;
    private LottieAnimationView mLottieView;
    private RecyclerAnimatorManager mAnimatorManager;
    private String mRightLottieSrc;
    private String mRightLottieSrcNight;
    private PullLayoutConfig mConfig;

    /**
     * 保证只触发一次震动
     */
    private boolean vibrateTrigger = false;

    //配置 RecyclerView fling 到结尾弹出的 lottie比例(0~1) , 设置0 关闭弹出效果
    private boolean bounceEnable;
    private float bounceRatio;

    //是否有用户触摸屏幕
    private boolean isInTouching = false;

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //fling到结尾,触发弹出效果
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && !recyclerView.canScrollHorizontally(1)
                    && !isInTouching) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (lm.findLastCompletelyVisibleItemPosition() == lm.getItemCount() - 1) {
                    triggerBounce();
                }
            }
        }
    };

    public RightLottieRecyclerView(Context context) {
        this(context, null);
    }

    public RightLottieRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightLottieRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mCenterView instanceof RecyclerView && mRightExtraView instanceof LottieAnimationView) {
            mRecyclerView = (RecyclerView) mCenterView;
            mLottieView = (LottieAnimationView) mRightExtraView;
            return;
        }
        if (!(mCenterView instanceof RecyclerView)) {
            //recyclerView 创建
            mRecyclerView = new RecyclerView(getContext());
        }
        if (!(mRightExtraView instanceof LottieAnimationView)) {
            //LottieView 创建
            mLottieView = new LottieAnimationView(getContext());
        }
        setViews(mRecyclerView, null, mLottieView);
    }


    /**
     * 配置Lottie
     *
     * @param config
     */
    public void applyConfig(final PullLayoutConfig config) {
        vibrateTrigger = false;
        mConfig = config;
        if (mLottieView == null && DataUtils.dataValid(config.getRightLottieFile())) {
            return;
        }
        //防止holder复用时 之前的listener影响 ,这里clear下
        clearOnDragListeners();
        setMaxDragDistance(config.getMaxDragDis());
        setDragLimit(config.getDragLimit());
        setDragThreshold(config.getDragThreshold());
        if (DataUtils.dataValid(config.getDragListener())) {
            addOnDragListener(config.getDragListener());
        }

        if (DataUtils.dataValid(config.getCenterLayoutParams())) {
            mRecyclerView.setLayoutParams(config.getCenterLayoutParams());
        }

        //配置了View 和 lottie文件的默认打开 开关
        setRightDragEnable(true);
        mRightLottieSrc = config.getRightLottieFile();
        mRightLottieSrcNight = config.getRightLottieFileNight();
        syncRightLottieSrc();
        ViewUtils.setViewVisible(mLottieView);
        //lottie 文件 的宽高比是不确定的 ,  View和文件的 宽高比不同Lottie会产生变形,这里可以通过lp手动设置宽度,高度取决于横滑卡片的高度
        if (DataUtils.dataValid(config.getRightLayoutParams())) {
            mLottieView.setLayoutParams(config.getRightLayoutParams());
        }
        addOnDragListener(new HorizontalPullLayout.SimpleOnDragListener() {
            @Override
            public void onDragProgressUpdate(float progress, int curState) {
                progress = progress > 1f ? 1f : progress;
                if (mLottieView != null) {
                    mLottieView.setProgress(progress);
                }
                checkToPerformVibrate(progress);
            }
        });
        //配置 fling弹出效果
        initTriggerBounce(config.getBounceRatio());
        //配置摆放模式
        if (getStyle() != config.getStyle()) {
            setStyle(config.getStyle());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isInTouching = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isInTouching = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置fling结尾的弹出效果
     */
    private void initTriggerBounce(float ratio) {
        if (ratio > 0f && !bounceEnable) {
            bounceEnable = true;
            bounceRatio = ratio;
            bounceRatio = bounceRatio <= 1f ? bounceRatio : 1f;
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
        if (ratio <= 0f) {
            mRecyclerView.removeOnScrollListener(mScrollListener);
            bounceEnable = false;
        }
    }

    /**
     * 触发弹出效果
     */
    public void triggerBounce() {
//        NTLog.d(TAG,"triggerBounce() -> doRelease()");
        float transX = mDragLimit * bounceRatio;
        setState(STATE_PULL_LEFT);
        updateTransX(-transX);
        doRelease();
    }

    /**
     * 震动效果
     */
    private void checkToPerformVibrate(float progress) {
        if (mConfig == null || !mConfig.isVibrate()) {
            return;
        }

        if (progress < mConfig.getDragThreshold()) {
            vibrateTrigger = false;
        }

        /**
         * 越界，没触发过震动 触发一次震动
         */
        if (progress > mConfig.getDragThreshold() && !vibrateTrigger) {
            Vibrator vibrator = (Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE);
            if (DataUtils.dataValid(vibrator)) {
                vibrator.vibrate(VIBRATE_DURATION);
            }
            vibrateTrigger = true;
        }
    }

    public void setAnimatorManager(RecyclerAnimatorManager animatorManager) {
        mAnimatorManager = animatorManager;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public LottieAnimationView getLottieView() {
        return mLottieView;
    }

    private void syncRightLottieSrc() {
        mLottieView.setAnimation(mRightLottieSrc);
    }

}
