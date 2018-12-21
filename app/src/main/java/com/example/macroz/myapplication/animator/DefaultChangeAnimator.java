package com.example.macroz.myapplication.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/28 上午10:21
 * 修改人:   macroz
 * 修改时间: 2018/11/28 上午10:21
 * 修改备注:
 */
public class DefaultChangeAnimator extends AbsItemAnimator {

    @Override
    public boolean prepareAnimator(ChangeInfo info) {
        final float prevTranslationX = ViewCompat.getTranslationX(info.oldHolder.itemView);
        final float prevTranslationY = ViewCompat.getTranslationY(info.oldHolder.itemView);
        final float prevAlpha = ViewCompat.getAlpha(info.oldHolder.itemView);
        int deltaX = (int) (info.toX - info.fromX - prevTranslationX);
        int deltaY = (int) (info.toY - info.fromY - prevTranslationY);
        ViewCompat.setTranslationX(info.oldHolder.itemView, prevTranslationX);
        ViewCompat.setTranslationY(info.oldHolder.itemView, prevTranslationY);
        ViewCompat.setAlpha(info.oldHolder.itemView, prevAlpha);
        if (info.newHolder != null) {
            ViewCompat.setTranslationX(info.newHolder.itemView, -deltaX);
            ViewCompat.setTranslationY(info.newHolder.itemView, -deltaY);
            ViewCompat.setAlpha(info.newHolder.itemView, 0);
        }
        return true;
    }

    @Override
    public void animatorImp(ChangeInfo info, SimpleVpaListener... listeners) {
        final RecyclerView.ViewHolder holder = info.oldHolder;
        final View view = holder == null ? null : holder.itemView;
        final RecyclerView.ViewHolder newHolder = info.newHolder;
        final View newView = newHolder != null ? newHolder.itemView : null;
        if (view != null) {
            final ViewPropertyAnimatorCompat oldViewAnim = ViewCompat.animate(view).setDuration(
                    getDuration());
            oldViewAnim.translationX(info.toX - info.fromX);
            oldViewAnim.translationY(info.toY - info.fromY);
            oldViewAnim.alpha(0).setListener(listeners[0]).start();
        }
        if (newView != null) {
            final ViewPropertyAnimatorCompat newViewAnimation = ViewCompat.animate(newView);
            newViewAnimation.translationX(0).translationY(0).setDuration(getDuration()).
                    alpha(1).setListener(listeners[1]).start();
        }
    }


}
