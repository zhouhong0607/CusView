package com.example.macroz.myapplication.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/28 上午10:21
 * 修改人:   macroz
 * 修改时间: 2018/11/28 上午10:21
 * 修改备注:
 */
public class DefaultAddAnimator extends AbsItemAnimator {
    @Override
    public boolean prepareAnimator(ChangeInfo info) {
        ViewCompat.setAlpha(info.oldHolder.itemView, 0);
        return true;
    }

    @Override
    public void animatorImp(ChangeInfo info, SimpleVpaListener... listeners) {
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(info.oldHolder.itemView);
        animation.alpha(1).setListener(listeners[0]).start();
    }

    @Override
    public long getDuration() {
        return 120;
    }
}
