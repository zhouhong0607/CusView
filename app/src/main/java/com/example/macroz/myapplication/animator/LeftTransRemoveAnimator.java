package com.example.macroz.myapplication.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/28 下午1:17
 * 修改人:   macroz
 * 修改时间: 2018/11/28 下午1:17
 * 修改备注:
 */
public class LeftTransRemoveAnimator extends AbsItemAnimator {


    @Override
    public boolean prepareAnimator(ChangeInfo info) {
        return true;
    }

    @Override
    public void animatorImp(ChangeInfo info, SimpleVpaListener... listeners) {
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(info.oldHolder.itemView);
        //移除非Footer使用左移动画
        animation.setDuration(getDuration())
                .alpha(0).translationX(-info.oldHolder.itemView.getMeasuredWidth()).setListener(listeners[0]).start();

    }

    @Override
    public long getDuration() {
        return 120;
    }

}
