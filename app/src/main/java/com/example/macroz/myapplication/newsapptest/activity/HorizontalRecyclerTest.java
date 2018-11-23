package com.example.macroz.myapplication.newsapptest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.adapter.HorizontalAdapter;
import com.example.macroz.myapplication.newsapptest.view.HorizontalPullLayout;

/**
 * 横滑类型的RecyclerView
 */
public class HorizontalRecyclerTest extends AppCompatActivity {
    private static final String TAG = HorizontalRecyclerTest.class.getSimpleName();
    private HorizontalPullLayout mHorizontalPullLayout;
    private RecyclerView mRecyclerView;
    private HorizontalAdapter mHoriAdapter;
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
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.test_horizontal_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mHoriAdapter = new HorizontalAdapter(this);
        mRecyclerView.setAdapter(mHoriAdapter);
        dealRecyclerPosition(mRecyclerView);
    }


    private void initOthers() {
        //在当前第一条的位置 后面 插入新数据
        Button mInsertButton = findViewById(R.id.insert_button);
        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHoriAdapter.insertToPos(curPos + 1, new Object());
                Log.d(TAG, " position : " + (curPos + 1) + "  插入一条数据");
            }
        });
    }

    /**
     * 处理 滚动结束后 smooth停止的位置
     *
     * @param recyclerView
     */
    private void dealRecyclerPosition(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int pos = lm.findFirstVisibleItemPosition();
                    Log.d(TAG, "滚动完成  定位 position : " + pos);
                    recyclerView.smoothScrollToPosition(pos);
                    LinearSmoothScroller linearSmoothScroller =
                            new LinearSmoothScroller(recyclerView.getContext()) {
                                @Override
                                protected int calculateTimeForScrolling(int dx) {
//                                    int time = super.calculateTimeForScrolling(dx);
                                    int time = 200;
                                    Log.d(TAG, "滚动时间 : " + time);
                                    return time;

                                }
                            };
                    linearSmoothScroller.setTargetPosition(pos);
                    lm.startSmoothScroll(linearSmoothScroller);
                    curPos = pos;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

}
