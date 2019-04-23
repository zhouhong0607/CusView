package com.example.macroz.myapplication.utils

import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue

import com.example.macroz.myapplication.activity.BaseApplication

/**
 *
 */
class ScreenUtils {

    companion object {
        /**
         * 当前是否横屏
         *
         * @return
         */
        fun isLandscape(): Boolean {
            return BaseApplication.instance?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE;
        }

        fun dp2px(dp: Float): Float {
            return dp2px(BaseApplication.instance?.resources, dp)
        }

        fun dp2px(resources: Resources?, dp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources?.displayMetrics)
        }
    }

}
