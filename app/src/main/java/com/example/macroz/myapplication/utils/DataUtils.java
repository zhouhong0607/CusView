package com.example.macroz.myapplication.utils;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by taiwei on 17/1/9.
 */

public class DataUtils {

    public static <T> boolean dataValid(T... data) {
        return data != null && data.length > 0;
    }

    public static <T> boolean dataValid(List<T> data) {
        return data != null && !data.isEmpty();
    }

    public static <T> boolean dataValid(T data) {
        return data != null;
    }

    public static boolean dataValid(String data) {
        return !TextUtils.isEmpty(data);
    }

    public static boolean dataValid(String str1, String str2) {
        return dataValid(str1) && dataValid(str2);
    }

    public static <A, B> boolean dataValid(A data1, B data2) {
        return dataValid(data1) && dataValid(data2);
    }

    public static <A, B, C> boolean dataValid(A data1, B data2, C data3) {
        return dataValid(data1) && dataValid(data2) && dataValid(data3);
    }

    public static long getArrayItem(long[] data, int position) {
        if (!dataValid(data)) {
            return -Long.MAX_VALUE;
        }
        if (position >= data.length || position < 0) {
            return -Long.MAX_VALUE;
        }
        return data[position];
    }

    public static <T> T getListItem(List<T> data, int position) {
        if (!dataValid(data)) {
            return null;
        }
        if (position >= data.size() || position < 0) {
            return null;
        }
        return data.get(position);
    }

    public static <T> T getListLastItem(List<T> data) {
        return getListItem(data, dataValid(data) ? data.size() - 1 : -1);
    }

    public static <T> int getListSize(List<T> data) {
        if (!dataValid(data)) {
            return 0;
        }
        return data.size();
    }

    public static String getSafeStr(String str) {
        return getSafeStr(str, "");
    }

    public static String getSafeStr(String str, String defaultValue) {
        return dataValid(str) ? str : defaultValue;
    }

    public static float getSafeFloat(String str) {
        return getSafeFloat(str, 0f);
    }

    public static float getSafeFloat(String str, float defaultValue) {
        try {
            return dataValid(str) ? Float.parseFloat(str) : defaultValue;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static float getSafeFloat(Object obj) {
        return getSafeFloat(obj, 0f);
    }

    public static float getSafeFloat(Object obj, float defaultValue) {
        if (String.class.isInstance(obj)) {
            return getSafeFloat(String.valueOf(obj), defaultValue);
        }
        if (!Number.class.isInstance(obj)) {
            return defaultValue;
        }
        return ((Number) obj).floatValue();
    }

    public static int getSafeInt(String str) {
        return getSafeInt(str, 0);
    }

    public static int getSafeInt(String str, int defaultValue) {
        try {
            return dataValid(str) ? Integer.parseInt(str) : defaultValue;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static int getSafeInt(Object obj) {
        return getSafeInt(obj, 0);
    }

    public static int getSafeInt(Object obj, int defaultValue) {
        if (!Number.class.isInstance(obj)) {
            return defaultValue;
        }
        return ((Number) obj).intValue();
    }

    public static long getSafeLong(String str) {
        return getSafeLong(str, 0);
    }

    public static long getSafeLong(String str, long defaultValue) {
        try {
            return dataValid(str) ? Long.parseLong(str) : defaultValue;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static boolean safeEquals(String str1, String str2) {
        if (dataValid(str1, str2)) {
            return str1.equals(str2);
        }
        return false;
    }


}