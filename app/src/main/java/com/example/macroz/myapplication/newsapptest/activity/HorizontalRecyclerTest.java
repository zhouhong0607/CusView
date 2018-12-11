package com.example.macroz.myapplication.newsapptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.adapter.AddItemAnimator;
import com.example.macroz.myapplication.newsapptest.adapter.HorizontalAdapter;
import com.example.macroz.myapplication.newsapptest.adapter.LocateSmoothScroller;
import com.example.macroz.myapplication.newsapptest.view.HorizontalPullLayout;

/**
 * 横滑类型的RecyclerView
 */
public class HorizontalRecyclerTest extends AppCompatActivity {
    private static final String TAG = HorizontalRecyclerTest.class.getSimpleName();
    private HorizontalPullLayout mHorizontalPullLayout;
    private RecyclerView mRecyclerView;
    private HorizontalAdapter mHoriAdapter;
    private LocateSmoothScroller scroller;
    private LinearLayoutManager lm;

    private LottieAnimationView leftLottieView, rightLottieView;
    //当前滚动停止的位置
    private int curPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hori_recycler_test);

        initHorizontalPullLayout();
        initRecycler();
        initOthers();
    }

    private void initHorizontalPullLayout() {
        mHorizontalPullLayout = findViewById(R.id.test_horizontal_pull_layout);
        mHorizontalPullLayout.setMaxDragDistance(500);
        mHorizontalPullLayout.setDragLimit(240);
        mHorizontalPullLayout.setDragThreshold(0.978f);
        mHorizontalPullLayout.setLeftDragEnable(true);
        mHorizontalPullLayout.setRightDragEnable(true);
        leftLottieView = findViewById(R.id.test_left_lottie);
        rightLottieView = findViewById(R.id.test_right_lottie);


        HorizontalPullLayout.LayoutParams lp = new HorizontalPullLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        rightLottieView.setLayoutParams(lp);

        mHorizontalPullLayout.addOnDragListener(new HorizontalPullLayout.SimpleOnDragListener() {

            @Override
            public void onDragProgressUpdate(float progress, int curState) {
                leftLottieView.setProgress(progress);
                rightLottieView.setProgress(progress);
            }
        });
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.test_horizontal_recycler);
        lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setItemAnimator(new AddItemAnimator());
        scroller = new LocateSmoothScroller(this);
//        scroller.addLocateListener(new LocateSmoothScroller.LocateListener() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onStop() {
//
//            }
//        });


        mHoriAdapter = new HorizontalAdapter(this);
        mRecyclerView.setAdapter(mHoriAdapter);
//        dealRecyclerPosition(mRecyclerView);
    }


    private void initOthers() {
        //在当前第一条的位置 后面 插入新数据
        final EditText editText = findViewById(R.id.input_location_edit);
        Button mInsertButton = findViewById(R.id.insert_button);
        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int insertPos = Integer.valueOf(editText.getText().toString());
                    smoothScrollTo(insertPos);
                    insertPos++;
                    curPos = insertPos;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 定位到指定位置
     *
     * @param pos
     */
    public void smoothScrollTo(int pos) {
        scroller.setTargetPosition(pos);
        lm.startSmoothScroll(scroller);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHoriAdapter.insertToPos(curPos, new Object());
                Log.d(TAG, " position : " + curPos + "  插入一条数据");
            }
        }, 400);

    }

    /**
     * 处理 滚动结束后 smooth停止的位置
     *
     * @param recyclerView
     */
//    private void dealRecyclerPosition(RecyclerView recyclerView) {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                Log.w(TAG, "onScrollStateChanged  state: " + newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
//                    int pos = lm.findFirstVisibleItemPosition();
//                    Log.w(TAG, "当前可见item  ：" + pos);
//                    View view = lm.findViewByPosition(pos);
//                    int left = Math.abs(view.getLeft());
//                    Log.w(TAG, "view  left  " + left);
//                    if (left == 0) {
//                        Log.w(TAG, "滚动完成  left=0 不执行滚动 ");
//                        return;
//                    }
//                    int width = view.getMeasuredWidth();
//                    Log.w(TAG, "view 宽 " + width);
//
//                    if (left >= width / 2) {
//                        pos++;
//                    }
//                    Log.w(TAG, "滚动完成  定位 position : " + pos);
//
//                    smoothScrollTo(pos);
//
//                    curPos = pos + 1;
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.d(TAG, "onScrolled   dx: " + dx);
//            }
//        });
//    }

}
