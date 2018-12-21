package com.example.macroz.myapplication.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;

import com.example.macroz.myapplication.mainactivity.BaseApplication;

/**
 *
 */
public class ScreenUtils {
    /**
     * 当前是否横屏
     *
     * @return
     */
    public static boolean isLandscape() {
        return BaseApplication.getInstance().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static float dp2px(float dp) {
        return dp2px(BaseApplication.getInstance().getResources(), dp);
    }

    public static float dp2px(Resources resources, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

}
