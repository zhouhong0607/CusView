package com.example.macroz.myapplication.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/28 上午11:05
 * 修改人:   macroz
 * 修改时间: 2018/11/28 上午11:05
 * 修改备注:
 */
public class ScaleAddAnimator extends AbsItemAnimator {

    private static final String TAG = "ScaleAddAnimator";

    @Override
    public boolean prepareAnimator(ChangeInfo info) {
        Log.d(TAG, "prepareAnimator()");
        ViewCompat.setAlpha(info.oldHolder.itemView, 0f);
        ViewCompat.setScaleX(info.oldHolder.itemView, 0.5f);
        ViewCompat.setScaleY(info.oldHolder.itemView, 0.5f);
        return true;
    }

    @Override
    public void animatorImp(ChangeInfo info, SimpleVpaListener... listeners) {
        Log.d(TAG, "animatorImp()");
        ViewPropertyAnimatorCompat animation = ViewCompat.animate(info.oldHolder.itemView);
        animation.setDuration(getDuration())
                .alpha(1)
                .scaleX(1f)
                .scaleY(1f)
                .setListener(listeners[0])
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    @Override
    public long getDuration() {
        return 300;
    }
}
