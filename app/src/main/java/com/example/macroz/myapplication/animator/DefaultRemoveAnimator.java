package com.example.macroz.myapplication.animator;

import android.support.v4.view.ViewCompat;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/28 上午10:21
 * 修改人:   macroz
 * 修改时间: 2018/11/28 上午10:21
 * 修改备注:
 */
public class DefaultRemoveAnimator extends AbsItemAnimator {


    @Override
    public boolean prepareAnimator(ChangeInfo info) {
        return true;
    }

    @Override
    public void animatorImp(ChangeInfo info, SimpleVpaListener... listeners) {
        ViewCompat.animate(info.oldHolder.itemView).setDuration(getDuration())
                .alpha(0).translationX(-info.oldHolder.itemView.getMeasuredWidth()).setListener(listeners[0]).start();
    }

    @Override
    public long getDuration() {
        return 120;
    }
}
