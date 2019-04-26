package com.example.macroz.myapplication.utils;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final String ELLIPSIS = "...";

    private static String regEx = "[\\u4e00-\\u9fa5]"; //unicode编码，汉字范围

    /**
     * URL编码
     */
    public static String URLEncode(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (Exception e) {
            }
        }
        return "";
    }

    /**
     * 获取整除结果
     */
    public static float getPercent(int itemNum, long allVoteNum) {
        return getPercent(itemNum, allVoteNum, "0.00");
    }

    /**
     * 获取整除结果
     */
    public static float getPercent(int itemNum, long allVoteNum, String format) {
        String percent = getStringPercent(itemNum, allVoteNum, format);
        if (TextUtils.isEmpty(percent)) {
            return 0F;
        }
        return Float.parseFloat(percent);
    }

    public static String getStringPercent(float percent, String format) {
        try {
            DecimalFormat df = new DecimalFormat(format);//格式化小数，不足的补0
            return df.format(percent);
        } catch (Exception e) {

        }
        return null;
    }

    public static String getStringPercent(int itemNum, long allVoteNum, String format) {
        if (allVoteNum == 0) {
            return null;
        }
        float p = (float) itemNum / allVoteNum;
        try {
            DecimalFormat df = new DecimalFormat(format);//格式化小数，不足的补0
            return df.format(p);
        } catch (Exception e) {

        }
        return null;
    }


    /**
     * 转成String
     */
    public static String toString(Object param) {
        return param != null ? param.toString() : "";
    }

    /**
     * String 转换为 Json
     */
    public static JSONObject StringToJson(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return new JSONObject(str);
            } catch (JSONException e) {
            }
        }
        return null;
    }

    /**
     * 查找 regex 在 string 中出现的次数
     */
    public static int findRegexCount(StringBuilder string, String regex) {
        Pattern pt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher mc = pt.matcher(string);
        int count = 0;
        while (mc.find()) {
            ++count;
        }
        if (count % 2 != 0) {
            return count;
        }

        replaceSpace(string, regex);
        return count;
    }

    public static void replaceSpace(StringBuilder str, String tag) {
        Matcher mc = Pattern.compile(tag).matcher(str);
        mc = Pattern.compile(tag).matcher(str);
        while (mc.find()) {
            int index;
            try {
                index = mc.start();
            } catch (Exception e1) {
                break;
            }

            while (true) {
                try {
                    if (--index < 0) {
                        break;
                    }

                    char s = str.charAt(index);
                    boolean isspace = isSpaceChar(s);
                    if (isspace) {
                        str.replace(index, index + 1, "");
                        mc = Pattern.compile(tag).matcher(str);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    /**
     * 判断空白字符串
     */
    private static boolean isSpaceChar(char str) {
        String regex = "^\\s*$";
        Pattern pt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher mac = pt.matcher(String.valueOf(str));
        return mac.find();
    }

    /**
     * 字符转integer 默认为0
     */
    public static int getInteger(final String str) {
        return getInteger(str, 0);
    }

    /**
     * 字符转integer
     */
    public static int getInteger(final String str, int defaultInt) {
        int rst = defaultInt;
        if (!TextUtils.isEmpty(str)) {
            try {
                rst = Integer.valueOf(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return rst;
    }

    /**
     * 字符转float
     */
    public static float getFloat(String str, float defaultFloat) {
        float rst = defaultFloat;
        if (!TextUtils.isEmpty(str)) {
            try {
                rst = Float.parseFloat(str);
            } catch (NumberFormatException e) {

            }
        }
        return rst;
    }

    /**
     * trim 全角半角空格
     */
    public static String trimAllSpace(String str) {
        return str == null ? "" : str.replaceAll("^[\\s　]*|[\\s　]*$", "");
    }

    public static String buildRedString(Context context, String redStr, boolean isNightTheme) {
        StringBuilder stb = new StringBuilder();
        String color = "#ea413c";
        if (context != null && isNightTheme) {
            color = "#992a28";
        }
        stb.append("<font color='");
        stb.append(color + "'>");
        stb.append(redStr);
        stb.append("</font>");
        return stb.toString();
    }


    /**
     * 将RGB颜色值转换为十六进制
     *
     * @param rgb "r, g, b"
     * @return
     */
    public static String RGBToHSX(String rgb) {
        String[] rgbs = rgb != null ? rgb.split(",") : null;
        if (rgbs != null && rgbs.length == 3) {
            return "#" + toHexValue(rgbs[0]) + toHexValue(rgbs[1]) + toHexValue(rgbs[2]);
        }
        return null;
    }

    public static String toHexValue(String value) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(Integer.valueOf(value)));
        if (sb.length() < 2) {
            sb.append("0");
        }
        return sb.toString().toUpperCase(Locale.US);
    }


    public static String flowFormate(int KB) {
        if (KB <= 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat(".# M ");
        return df.format(KB / 1024f);
    }

    public static String list2String(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.substring(0, sb.length() - separator.length());
    }

    /**
     * 截取字符串，使其长度为len个字符（一个汉字为两个字符，结果为长度最接近len的合法的字符串）
     */
    public static String subString(String str, int charLen) {
        if (charLen == 0) {
            return "";
        }
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            int chiNum = 0;
            int engNum = 0;
            int i = 0;
            byte[] bytes = str.getBytes("GBK");
            int n = bytes.length;
            if (n <= charLen) {
                return str;
            }
            int end = 0;
            byte b;
            while (i < n && chiNum * 2 + engNum <= charLen) {
                end = i;
                b = bytes[i];
                if (b >= 0) {
                    engNum++;
                } else {
                    chiNum++;
                    i++;
                }
                i++;
            }
            return new String(bytes, 0, end, "GBK");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * 获取字符串的字符数 1汉字->2字符 1英文->1字符
     */
    public static int getCharsetLength(String str) {
        try {
            return str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            return 0;
        }
    }

    /**
     * * 数字转字符串
     * 3200 32.2万 3.1亿 7万
     */
    public static String getNumberText(Context context, String number) {
        String ret = "";
        if (TextUtils.isEmpty(number) || context == null) {
            return ret;
        }
        try {
            int count = Integer.parseInt(number);
            ret = getNumberText(count);
        } catch (NumberFormatException e) {
        }
        return ret;
    }


    /**
     * 数字转字符串
     * 3200 32.2万 3.1亿
     *
     * @param num
     */
    public static String getNumberText(int num) {
        StringBuilder sb = new StringBuilder();
        int front = 0;
        int behind = 0;
        if (num <= 0) {
            front = 0;
        } else if (num >= 10000 * 10000) {
            num /= (1000 * 10000);
            behind = num % 10;
            front = num / 10;
            sb.append("亿");
        } else if (num >= 10000) {
            num /= 1000;
            behind = num % 10;
            front = num / 10;
            sb.append("万");
        } else {
            front = num;
        }
        if (behind != 0) {
            sb.insert(0, behind);
            sb.insert(0, ".");
        }
        sb.insert(0, front);
        return sb.toString();
    }

    /**
     * 字符串转long 默认为0
     */
    public static long getLong(final String str) {
        return getLong(str, 0);
    }

    public static long getLong(final String str, long defaultLong) {
        return !TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str) ? Long.parseLong(str) : defaultLong;
    }
}
