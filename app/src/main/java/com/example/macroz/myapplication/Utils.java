package com.example.macroz.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utils {
    public static Bitmap getBitmap(Resources res, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        int mPicWidth = options.outWidth;
        int mPicHeight = options.outHeight;

        int exp = 500;
        int reSize = mPicHeight / 500 > mPicWidth / 500 ? mPicWidth / 500 : mPicHeight / 500;
        options.inSampleSize = reSize + 2;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);

        return bitmap;
    }


    public static Bitmap getIcon(Resources res, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        return bitmap;
    }


    /**
     * 判断字符串是否是数字
     *
     * @param s
     * @return
     */
    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("-?[0-9]+.*[0-9]*");
        else
            return false;
    }

    /**
     * 字符串转数字
     *
     * @param s
     * @return
     */
    public static float String2Num(String s) {
        if (isNumeric(s)) {
            return Float.valueOf(s);
        } else {
            return 0.0f;
        }
    }


}
