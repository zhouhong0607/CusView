package com.example.macroz.myapplication.newsapptest.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.bean.LabelBean;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述:   哈姆雷特选择标签的布局
 * 创建人:   macroz
 * 创建时间: 2018/8/22 上午11:07
 * 修改人:   macroz
 * 修改时间: 2018/8/22 上午11:07
 * 修改备注:
 */
public class LabelSelectLayout extends FlexboxLayout implements View.OnClickListener {
    private final String TAG = "LabelSelectLayout";

    //记录每个View对应的Bean
    private Map<View, LabelBean> mBeanMap;
    //记录每个View 摆放的起始点
    private Map<View, Point> mPointMap;
    private OnSelectItemListener mOnSelectItemListener;
    private TextView curSelectView;

    public LabelSelectLayout(Context context) {
        this(context, null);
    }

    public LabelSelectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelSelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBeanMap = new HashMap<>();
        mPointMap = new HashMap<>();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure()");

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int curX = getPaddingLeft();
        int curY = getPaddingTop();
        //curTop 表示当前摆放的 行高
        int curTop = curY;
        //记录下一次摆放的起始点 X
        int nextX = curX;
        //记录下一次换行时候的 起始点 ,用于更新curTop
        int nextY = curY;

        int childMarginLeft = 0, childMarginRight = 0, childMarginTop = 0, childMarginBottom = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            if (lp != null) {
                childMarginLeft = lp.leftMargin;
                childMarginRight = lp.rightMargin;
                childMarginTop = lp.topMargin;
                childMarginBottom = lp.bottomMargin;
            }
            //当前这行摆放不下了换一行摆放
            if (curX + childView.getMeasuredWidth() + childMarginLeft + childMarginRight > maxWidth - getPaddingRight()) {
                //换行顶格摆放
                curX = getPaddingLeft();
                //更新当前行高
                curTop = nextY;
            }
            //摆放点留出 marginLeft和marginTop的距离
            curX += childMarginLeft;
            curY = curTop + childMarginTop;

            //更新下个摆放点
            // 根据当前摆放的View的宽和margin值 ，设置下一个View 摆放的 X轴起始点
            nextX = curX + childView.getMeasuredWidth() + childMarginRight;
            //设置下一行 Y 的起始点 为当前行 curTop起始点 加上 当前行中 最高的View所占的空间
            nextY = curTop + childView.getMeasuredHeight() + childMarginTop + childMarginBottom > nextY ?
                    curTop + childView.getMeasuredHeight() + childMarginTop + childMarginBottom : nextY;
            //记录需要摆放的位置
            mPointMap.put(childView, new Point(curX, curY));
            //重置 子View的各项 margin 值
            childMarginLeft = 0;
            childMarginRight = 0;
            childMarginTop = 0;
            childMarginBottom = 0;
            //更新下一个View 摆放的 X轴 起始点
            curX = nextX;
        }

        //如果是Exactly 如 match_parent 设置为 maxHeight, 否则设置高度为当前需要的高度
        maxHeight = heightMode == MeasureSpec.EXACTLY ? maxHeight : nextY + getPaddingBottom();


        setMeasuredDimension(maxWidth, maxHeight);
    }

    //根据 mPointMap 摆放对应的子View
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout()");
        for (View view : mPointMap.keySet()) {
            Point point = mPointMap.get(view);
            view.layout(point.x, point.y, point.x + view.getMeasuredWidth(), point.y + view.getMeasuredHeight());
        }
    }

    /**
     * 根据bean添加标签
     *
     * @param dataList
     */
    public void addChildren(List<LabelBean> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }

        for (LabelBean bean : dataList) {
            TextView textView = new TextView(getContext());
            //设置标签内容
            textView.setText(bean.getLabel());
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(20, 10, 20, 10);
            MarginLayoutParams lp = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 20;
            lp.topMargin = 10;
            textView.setLayoutParams(lp);

            //设置是否可点击
            textView.setEnabled(bean.isEnable());


            textView.setOnClickListener(this);
            //添加到布局中
            addView(textView);

            mBeanMap.put(textView, bean);

        }
        refreshTheme();
    }


    private void refreshTheme() {
        for (View view : mBeanMap.keySet()) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextColor(textView.isEnabled() ? Color.BLACK : Color.GRAY);
                textView.setBackgroundResource(R.drawable.newsapp_flex_label_selector);

            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            if (curSelectView != null) {
                //重置当前选择的View
                curSelectView.setSelected(false);
                curSelectView.setTextColor(Color.BLACK);
            }
            curSelectView = (TextView) v;
            curSelectView.setTextColor(Color.WHITE);
            curSelectView.setSelected(true);

            LabelBean bean = mBeanMap.get(v);
            if (mOnSelectItemListener != null && bean != null) {
                mOnSelectItemListener.onSelectItem(bean);
            }
        }
    }

    public void setOnSelectItemListener(OnSelectItemListener onSelectItemListener) {
        mOnSelectItemListener = onSelectItemListener;
    }


    public interface OnSelectItemListener {
        /**
         * 点击对应标签回调
         */
        void onSelectItem(LabelBean labelBean);
    }


}
