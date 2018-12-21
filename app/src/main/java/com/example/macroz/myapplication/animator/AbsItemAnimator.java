package com.example.macroz.myapplication.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/28 上午10:42
 * 修改人:   macroz
 * 修改时间: 2018/11/28 上午10:42
 * 修改备注:
 */
public abstract class AbsItemAnimator {
    /**
     * 动画初始状态设置
     *
     * @param info
     * @return  是否需要执行动画
     */
    public abstract boolean prepareAnimator(ChangeInfo info);

    /**
     * 动画的执行实现
     *
     * @param info        要执行动画的holder信息
     * @param listeners   对应 holder的动画监听回调
     */
    public abstract void animatorImp(ChangeInfo info, SimpleVpaListener... listeners);

    /**
     * 动画时长
     *
     * @return
     */
    public long getDuration() {
        return 250;
    }


    /**
     * 恢复到初始状态
     *
     * @param v
     */
    public void clear(View v) {
        ViewCompat.setAlpha(v, 1);
        ViewCompat.setScaleY(v, 1);
        ViewCompat.setScaleX(v, 1);
        ViewCompat.setTranslationY(v, 0);
        ViewCompat.setTranslationX(v, 0);
        ViewCompat.setRotation(v, 0);
        ViewCompat.setRotationY(v, 0);
        ViewCompat.setRotationX(v, 0);
        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null).setStartDelay(0);
    }

    /**
     * 封装执行动画的holder信息
     */
    public static class ChangeInfo {
        public RecyclerView.ViewHolder oldHolder, newHolder;
        public int fromX, fromY, toX, toY;

        public ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        public ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder,
                          int fromX, int fromY, int toX, int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        @Override
        public String toString() {
            return "ChangeInfo{" +
                    "oldHolder=" + oldHolder +
                    ", newHolder=" + newHolder +
                    ", fromX=" + fromX +
                    ", fromY=" + fromY +
                    ", toX=" + toX +
                    ", toY=" + toY +
                    '}';
        }
    }

    /**
     * 动画回调
     */
    public static class SimpleVpaListener implements ViewPropertyAnimatorListener {
        @Override
        public void onAnimationStart(View view) {
        }

        @Override
        public void onAnimationEnd(View view) {
        }

        @Override
        public void onAnimationCancel(View view) {
        }
    }

}
