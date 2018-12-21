package com.example.macroz.myapplication.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/28 上午10:21
 * 修改人:   macroz
 * 修改时间: 2018/11/28 上午10:21
 * 修改备注:
 */
public class DefaultMoveAnimator extends AbsItemAnimator {

    @Override
    public boolean prepareAnimator(ChangeInfo info) {
        final View view = info.oldHolder.itemView;
        info.fromX += ViewCompat.getTranslationX(info.oldHolder.itemView);
        info.fromY += ViewCompat.getTranslationY(info.oldHolder.itemView);
        int deltaX = info.toX - info.fromX;
        int deltaY = info.toY - info.fromY;
        if (deltaX == 0 && deltaY == 0) {
            return false;
        }
        if (deltaX != 0) {
            ViewCompat.setTranslationX(view, -deltaX);
        }
        if (deltaY != 0) {
            ViewCompat.setTranslationY(view, -deltaY);
        }
        return true;
    }

    @Override
    public void animatorImp(ChangeInfo info, SimpleVpaListener... listeners) {
        final View view = info.oldHolder.itemView;
        final int deltaX = info.toX - info.fromX;
        final int deltaY = info.toY - info.fromY;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view).setDuration(getDuration());
        if (deltaX != 0) {
            animation.translationX(0);
        }
        if (deltaY != 0) {
            animation.translationY(0);
        }
        animation.setListener(listeners[0]).start();
    }


}
