package com.example.macroz.myapplication.newsapptest.view;

import android.support.annotation.IntDef;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.utils.ScreenUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类描述:   右侧lottie的一些配置
 * 创建人:   macroz
 * 创建时间: 2018/12/19 上午11:03
 * 修改人:   macroz
 * 修改时间: 2018/12/19 上午11:03
 * 修改备注:
 */
public class PullLayoutConfig {
    //拉动距离配置
    private float maxDragDis;     //配置最大可以拉出的距离  像素值
    private float dragLimit;        //配置 计算 progress的 最大距离,  会根据当前拉出的像素/该值  计算progress
    private float dragThreshold;    //配置 跳转的阈值 , 大于该值 会回调onDragOver() 方法  取值(0~1  对应 progress)
    private float bounceRatio;     //配置 RecyclerView fling 到结尾弹出的 lottie比例(0~1) , 设置0 关闭弹出效果

    private boolean vibrate;       //配置跳转震动

    /**
     * {@link HorizontalPullLayout#STYLE_FOLLOW}
     * {@link HorizontalPullLayout#STYLE_STICKY}
     */
    private int style;

    /**
     * {@link HorizontalPullLayout.SimpleOnDragListener}
     * 处理 lottie更新 和跳转
     */
    private HorizontalPullLayout.OnDragListener mDragListener;
    //左、 右侧lottie文件以及其夜间
    private String leftLottieFile, rightLottieFile, leftLottieFileNight, rightLottieFileNight;

    //左侧 右侧 布局参数 ,  部分lottie 文件 不同的宽高比 ,  View和文件的 比例不同Lottie会产生变形,这里可以通过lp手动设置宽度
    private HorizontalPullLayout.LayoutParams leftLayoutParams, rightLayoutParams, centerLayoutParams;

    public PullLayoutConfig() {
    }

    public float getMaxDragDis() {
        return maxDragDis;
    }

    public void setMaxDragDis(float maxDragDis) {
        this.maxDragDis = maxDragDis;
    }

    public float getDragLimit() {
        return dragLimit;
    }

    public void setDragLimit(float dragLimit) {
        this.dragLimit = dragLimit;
    }

    public float getDragThreshold() {
        return dragThreshold;
    }

    public void setDragThreshold(float dragThreshold) {
        this.dragThreshold = dragThreshold;
    }

    public HorizontalPullLayout.OnDragListener getDragListener() {
        return mDragListener;
    }

    public void setDragListener(HorizontalPullLayout.OnDragListener dragListener) {
        mDragListener = dragListener;
    }

    public String getRightLottieFile() {
        return rightLottieFile;
    }

    public void setRightLottieFile(String rightLottieFile) {
        this.rightLottieFile = rightLottieFile;
    }

    public String getLeftLottieFile() {
        return leftLottieFile;
    }

    public void setLeftLottieFile(String leftLottieFile) {
        this.leftLottieFile = leftLottieFile;
    }

    public HorizontalPullLayout.LayoutParams getLeftLayoutParams() {
        return leftLayoutParams;
    }

    public void setLeftLayoutParams(HorizontalPullLayout.LayoutParams leftLayoutParams) {
        this.leftLayoutParams = leftLayoutParams;
    }

    public HorizontalPullLayout.LayoutParams getRightLayoutParams() {
        return rightLayoutParams;
    }

    public void setRightLayoutParams(HorizontalPullLayout.LayoutParams rightLayoutParams) {
        this.rightLayoutParams = rightLayoutParams;
    }

    public HorizontalPullLayout.LayoutParams getCenterLayoutParams() {
        return centerLayoutParams;
    }

    public void setCenterLayoutParams(HorizontalPullLayout.LayoutParams centerLayoutParams) {
        this.centerLayoutParams = centerLayoutParams;
    }

    public float getBounceRatio() {
        return bounceRatio;
    }

    public void setBounceRatio(float bounceRatio) {
        this.bounceRatio = bounceRatio;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(@Style int style) {
        this.style = style;
    }

    public String getLeftLottieFileNight() {
        return leftLottieFileNight;
    }

    public void setLeftLottieFileNight(String leftLottieFileNight) {
        this.leftLottieFileNight = leftLottieFileNight;
    }

    public String getRightLottieFileNight() {
        return rightLottieFileNight;
    }

    public void setRightLottieFileNight(String rightLottieFileNight) {
        this.rightLottieFileNight = rightLottieFileNight;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public static class Builder {
        private float maxDragDis;     //配置最大可以拉出的距离  像素值
        private float dragLimit;        //配置 计算 progress的 最大距离,  会根据当前拉出的像素/该值  计算progress
        private float dragThreshold;    //配置 跳转的阈值 , 大于该值 会回调onDragOver() 方法  取值(0~1  对应 progress)
        private HorizontalPullLayout.OnDragListener mDragListener;
        private String leftLottieFile, rightLottieFile, leftLottieFileNight, rightLottieFileNight;
        private HorizontalPullLayout.LayoutParams leftLayoutParams, rightLayoutParams, centerLayoutParams;
        private float bounceRatio;     //配置 RecyclerView fling 到结尾弹出的 lottie比例(0~1) , 设置0 关闭弹出效果
        private int style;
        private boolean vibrate;       //配置跳转震动

        public Builder() {
        }

        /**
         * 提供一套默认配置
         *
         * @param useLargeLottie
         */
        public Builder(boolean useLargeLottie) {
            maxDis(ScreenUtils.dp2px(150))    //配置最大可以拉出的距离  像素值
                    .limit(ScreenUtils.dp2px(53))       //配置 计算 progress的 最大距离,  会根据当前拉出的像素/该值  计算progress
                    .style(HorizontalPullLayout.STYLE_STICKY)
                    .bounceRatio(0.3f)
                    .threshold(0.97f)                    //配置 跳转的阈值 , 大于该值 会回调onDragOver() 方法  取值(0~1  对应 progress)
                    .vibrate(true)
                    .rightLottie(useLargeLottie ?                               //配置 右侧lottie动画 日间
                            "lottie/news_base_horizontal_list_more_large.json" :
                            "lottie/news_base_horizontal_list_more_small.json")
                    .rightLottieNight(useLargeLottie ?                          //配置 右侧lottie动画 夜间
                            "lottie/night_news_base_horizontal_list_more_small.json" :
                            "lottie/night_news_base_horizontal_list_more_small.json")
                    .rightLP(new HorizontalPullLayout.LayoutParams((int) ScreenUtils.dp2px(53), HorizontalPullLayout.LayoutParams.MATCH_PARENT));
        }

        //设置能拉出的最大距离
        public Builder maxDis(float maxDragDis) {
            this.maxDragDis = maxDragDis;
            return this;
        }

        //设置计算progress的限制, progress=当前拉出像素/limit
        public Builder limit(float dragLimit) {
            this.dragLimit = dragLimit;
            return this;
        }

        //设置跳转的阈值,大于该阈值回调dragOver方法执行界面跳转
        public Builder threshold(float dragThreshold) {
            this.dragThreshold = dragThreshold;
            return this;
        }

        //设置跳转的震动效果
        public Builder vibrate(boolean v) {
            this.vibrate = v;
            return this;
        }

        //配置界面跳转回调
        public Builder listener(HorizontalPullLayout.OnDragListener listener) {
            this.mDragListener = listener;
            return this;
        }

        //左侧lottie文件
        public Builder leftLottie(String leftLottieFile) {
            this.leftLottieFile = leftLottieFile;
            return this;
        }

        public Builder leftLottieNight(String leftLottieFileNight) {
            this.leftLottieFileNight = leftLottieFileNight;
            return this;
        }

        //右侧lottie文件
        public Builder rightLottie(String rightLottieFile) {
            this.rightLottieFile = rightLottieFile;
            return this;
        }

        public Builder rightLottieNight(String rightLottieFileNight) {
            this.rightLottieFileNight = rightLottieFileNight;
            return this;
        }

        /**
         * 左侧lottieView  的 LayoutParams (可以设置 {@link android.view.Gravity#CENTER_VERTICAL} 垂直居中 )
         *
         * @param leftLayoutParams
         * @return
         */
        public Builder leftLP(HorizontalPullLayout.LayoutParams leftLayoutParams) {
            this.leftLayoutParams = leftLayoutParams;
            return this;
        }

        /**
         * 右侧lottieView  的 LayoutParams (可以设置 {@link android.view.Gravity#CENTER_VERTICAL} 垂直居中 )
         *
         * @param rightLayoutParams
         * @return
         */
        public Builder rightLP(HorizontalPullLayout.LayoutParams rightLayoutParams) {
            this.rightLayoutParams = rightLayoutParams;
            return this;
        }

        /**
         * 中心RecyclerView 的 LayoutParams
         *
         * @param centerLp
         * @return
         */
        public Builder centerLP(HorizontalPullLayout.LayoutParams centerLp) {
            this.centerLayoutParams = centerLp;
            return this;
        }

        /**
         * 配置 RecyclerView fling 到结尾弹出的 lottie比例(0~1) , 设置0 关闭弹出效果
         *
         * @param ratio
         * @return
         */
        public Builder bounceRatio(float ratio) {
            this.bounceRatio = ratio;
            return this;
        }

        /**
         * {@link HorizontalPullLayout#STYLE_FOLLOW}
         * {@link HorizontalPullLayout#STYLE_STICKY}
         *
         * @param style
         * @return
         */
        public Builder style(@Style int style) {
            this.style = style;
            return this;
        }

        public PullLayoutConfig build() {
            PullLayoutConfig config = new PullLayoutConfig();
            config.setMaxDragDis(this.maxDragDis);
            config.setDragLimit(this.dragLimit);
            config.setDragThreshold(this.dragThreshold);
            config.setDragListener(this.mDragListener);
            config.setLeftLottieFile(this.leftLottieFile);
            config.setRightLottieFile(this.rightLottieFile);
            config.setLeftLottieFileNight(this.leftLottieFileNight);
            config.setRightLottieFileNight(this.rightLottieFileNight);
            config.setLeftLayoutParams(this.leftLayoutParams);
            config.setCenterLayoutParams(this.centerLayoutParams);
            config.setRightLayoutParams(this.rightLayoutParams);
            config.setBounceRatio(this.bounceRatio);
            config.setStyle(this.style);
            config.setVibrate(this.vibrate);
            return config;
        }
    }

    @IntDef({HorizontalPullLayout.STYLE_FOLLOW, HorizontalPullLayout.STYLE_STICKY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

}
