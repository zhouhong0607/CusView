package com.example.macroz.myapplication.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;



/**
 * Created by zhaonan on 16/5/3.
 */
public class ViewUtils {

    /**
     * 找到view在屏幕上的bottom
     *
     * @param view
     * @return
     */
    public static int findViewBottomOnScreen(View view) {
        int[] locOnSrc = new int[2];
        view.getLocationOnScreen(locOnSrc);
        return locOnSrc[1] + view.getHeight();
    }

    /**
     * 找到view在屏幕上的Top
     *
     * @param view
     * @return
     */
    public static int findViewTopOnScreen(View view) {
        int[] locOnSrc = new int[2];
        view.getLocationOnScreen(locOnSrc);
        return locOnSrc[1];
    }

    /**
     * touch事件是否落在了view中
     *
     * @param event
     * @param view
     * @return
     */
    public static boolean isTouchEventInView(MotionEvent event, View view) {
        if (event == null) {
            return false;
        }
        Rect viewRect = new Rect();
        int[] locOnSrc = new int[2];
        view.getLocationOnScreen(locOnSrc);
        viewRect.set(locOnSrc[0], locOnSrc[1], locOnSrc[0] + view.getWidth(), locOnSrc[1] + view.getHeight());
        return viewRect.contains((int) event.getRawX(), (int) event.getRawY());
    }

    /**
     * 播放2个view淡入淡出的交叉动画
     *
     * @param ctx
     * @param toView   要显示的view
     * @param fromView 要隐藏的view
     */
    public static void playViewExchangeAni(Context ctx, final View toView, final View fromView) {
        if (toView == fromView || ctx == null) {
            return;
        }

        // 处理要消失view的过渡动画
        if (fromView != null) {
            fromView.clearAnimation();
            Animation hideAnimation = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_out);
            hideAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fromView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            hideAnimation.setFillAfter(false);
            fromView.startAnimation(hideAnimation);
        }

        // 处理要显示view的过渡动画
        if (toView != null) {
            toView.clearAnimation();
            Animation showAnimation = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_in);
            toView.setVisibility(View.VISIBLE);
            showAnimation.setFillAfter(false);
            toView.startAnimation(showAnimation);
        }
    }

    public static void startAnimation(View view, Animation animation) {
        if (view != null) {
            view.startAnimation(animation);
        }
    }

    public static void clearAnimation(View view) {
        if (view != null) {
            view.clearAnimation();
        }
    }

    public static <T> T findView(View parent, @IdRes int id) {
        if (parent != null) {
            //noinspection unchecked
            return (T) parent.findViewById(id);
        }
        return null;
    }

    public static void setViewStatus(TextView tv, int visibleStatus, CharSequence text) {
        setViewVisibility(tv, visibleStatus);
        setViewText(tv, text);
    }

    public static void setViewStatus(TextView tv, int visibleStatus, String text, int translationY) {
        setViewStatus(tv, visibleStatus, text);
        setViewTranslationY(tv, translationY);
    }

    public static void setViewStatus(TextView tv, int visibleStatus, float alpha, float translationY) {
        setViewTranslationY(tv, translationY);
        setViewAlpha(tv, alpha);
        setViewVisibility(tv, visibleStatus);
    }

    public static void setViewText(TextView tv, CharSequence text) {
        if (tv == null) {
            return;
        }
        if (text == null) {
            text = "";
        }
        if (!text.equals(tv.getText())) {
            tv.setText(text);
        }
    }

    public static void setViewText(TextView textView, String text) {
        setViewText(textView, text, false);
    }

    public static void setViewText(TextView textView, String text, boolean nullGone) {
        if (textView != null) {
            if (text == null) {
                text = "";
            }
            if (!text.equals(textView.getText())) {
                textView.setText(text);
            }
            if (nullGone) {
                setViewVisibility(textView, DataUtils.dataValid(text) ? View.VISIBLE : View.GONE);
            }
        }
    }

    public static void setViewText(View parent, @IdRes int id, String str) {
        setViewText(parent, id, str, false);
    }

    public static void setViewText(View parent, @IdRes int id, String str, boolean nullGone) {
        if (parent != null) {
            View view = parent.findViewById(id);
            if (view instanceof TextView) {
                setViewText((TextView) view, str, nullGone);
            }
        }
    }

    public static void setViewVisible(View parent, @IdRes int id) {
        if (parent != null) {
            setViewVisible(parent.findViewById(id));
        }
    }

    public static void setViewVisible(View view) {
        setViewVisibility(view, View.VISIBLE);
    }

    public static void setViewVisible(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                setViewVisibility(view, View.VISIBLE);
            }
        }
    }

    public static void setViewInvisible(View parent, @IdRes int id) {
        if (parent != null) {
            setViewInvisible(parent.findViewById(id));
        }
    }

    public static void setViewInvisible(View view) {
        setViewVisibility(view, View.INVISIBLE);
    }

    public static void setViewGone(View parent, @IdRes int id) {
        if (parent != null) {
            setViewGone(parent.findViewById(id));
        }
    }

    public static void setViewGone(View view) {
        setViewVisibility(view, View.GONE);
    }

    public static void setViewGone(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                setViewVisibility(view, View.GONE);
            }
        }
    }

    public static void setViewVisibility(View parent, @IdRes int id, int visibility) {
        if (parent != null) {
            setViewVisibility(parent.findViewById(id), visibility);
        }
    }

    public static void setViewVisibility(View view, int visibility) {
        if (view != null) {
            if (view.getVisibility() != visibility) {
                view.setVisibility(visibility);
            }
        }
    }

    public static void setViewVisibility(View view, boolean isVisibility) {
        if (view == null) {
            return;
        }
        if (isVisibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static boolean isViewVisible(View view) {
        if (view == null) {
            return false;
        }
        return View.VISIBLE == view.getVisibility();
    }

    public static boolean isViewInvisible(View view) {
        if (view == null) {
            return false;
        }
        return View.INVISIBLE == view.getVisibility();
    }

    public static boolean isViewGone(View view) {
        if (view == null) {
            return false;
        }
        return View.GONE == view.getVisibility();
    }

    public static void setViewAlpha(View view, float alpha) {
        if (view != null) {
            view.setAlpha(alpha);
        }
    }

    public static void setViewScale(View view, float scale) {
        if (view != null) {
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    }

    public static void setViewTranslationY(View view, float translationY) {
        if (view != null) {
            view.setTranslationY(translationY);
        }
    }


    public static void setOnClickListener(View parent, @IdRes int id, View.OnClickListener listener) {
        if (parent != null) {
            setOnClickListener(parent.findViewById(id), listener);
        }
    }

    public static void setOnClickListener(View view, View.OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    public static void replaceHolderWithRealView(View placeHolder, View realView) {
        if (placeHolder == null || realView == null) {
            return;
        }
        realView.setLayoutParams(placeHolder.getLayoutParams());
        ViewGroup parent = (ViewGroup) placeHolder.getParent();
        int indexInParent = parent.indexOfChild(placeHolder);
        parent.removeView(placeHolder);
        parent.addView(realView, indexInParent);
    }

    /**
     * 判断一个view是否完全可见
     */
    public static boolean isFullyVisible(View view) {
        if (view == null) {
            return false;
        }
        Rect rect = new Rect();
        boolean visible = view.getGlobalVisibleRect(rect);
        return visible && Math.abs(rect.bottom - rect.top) >= view.getHeight() && Math.abs(rect.right - rect.left) >= view.getWidth();
    }

    /**
     * 判断一个view暴露出的面积是否大于指定的曝光比例
     * 如果rateHeight = 50 说明暴露出来的view大于或者等于view高度的50%
     */
    public static boolean isRateExposed(View view, int rateHeight) {
        if (view == null || rateHeight < 0) {
            return false;
        }
        Rect rect = new Rect();
        boolean visible = view.getGlobalVisibleRect(rect);
        return visible && Math.abs(rect.bottom - rect.top) >= view.getHeight() * rateHeight * 0.01;
    }


    public static boolean isExposed(View view) {
        if (view == null) {
            return false;
        }
        Rect rect = new Rect();
        boolean visible = view.getGlobalVisibleRect(rect);
        return visible;
    }

    public static GradientDrawable createRectangleDrawable(Context context,
                                                           int contentColor, int strokeColor, int strokeWidth, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setColor(contentColor);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static @ColorInt
    int applyAlpha(final int color, final float alpha) {
        final int newAlpha = (int) (Color.alpha(color) * alpha);
        return Color.argb(newAlpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * 清除view焦点
     *
     * @param view
     */
    public static void clearViewFocus(View view) {
        if (view == null) {
            return;
        }
        view.clearFocus();
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
    }
}
