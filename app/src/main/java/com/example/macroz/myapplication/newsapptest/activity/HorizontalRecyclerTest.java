package com.example.macroz.myapplication.newsapptest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.animator.RecyclerAnimatorManager;
import com.example.macroz.myapplication.animator.RecyclerItemAnimator;
import com.example.macroz.myapplication.animator.ScaleAddAnimator;
import com.example.macroz.myapplication.newsapptest.adapter.HorizontalAdapter;
import com.example.macroz.myapplication.animator.StartSnapHelper;
import com.example.macroz.myapplication.newsapptest.view.HorizontalPullLayout;
import com.example.macroz.myapplication.newsapptest.view.PullLayoutConfig;
import com.example.macroz.myapplication.newsapptest.view.RightLottieRecyclerView;

/**
 * 横滑类型的RecyclerView
 */
public class HorizontalRecyclerTest extends AppCompatActivity {
    private static final String TAG = HorizontalRecyclerTest.class.getSimpleName();
    private RightLottieRecyclerView mLottieRecyclerView;
    private RecyclerView mRecyclerView;
    private HorizontalAdapter mHoriAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hori_recycler_test);
        initRecycler();
        initOthers();
    }


    private void initRecycler() {
        mLottieRecyclerView = findViewById(R.id.test_right_lottie_recycler);
        mRecyclerView = mLottieRecyclerView.getRecyclerView();
        //布局管理
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(lm);
        //Adapter
        mHoriAdapter = new HorizontalAdapter(this);
        mRecyclerView.setAdapter(mHoriAdapter);
        //配置动画
        RecyclerItemAnimator animator = new RecyclerItemAnimator();
        animator.setAddAnimator(new ScaleAddAnimator());
        mRecyclerView.setItemAnimator(animator);
        //自动定位
        RecyclerAnimatorManager animatorManager = new RecyclerAnimatorManager(mRecyclerView);
        animatorManager.setAutoLocate(true);
        mLottieRecyclerView.setAnimatorManager(animatorManager);
        //配置lottie
        PullLayoutConfig config = new PullLayoutConfig.Builder()
                .maxDis(500)
                .limit(240)
                .threshold(0.978f)
                .bounceRatio(0.3f)
                .style(HorizontalPullLayout.STYLE_STICKY)
                .rightLottie("news_reader_viewmore4.json").build();
        mLottieRecyclerView.applyConfig(config);
    }


    private void initOthers() {
        //在当前第一条的位置 后面 插入新数据
        final EditText editText = findViewById(R.id.input_location_edit);
        Button mInsertButton = findViewById(R.id.insert_button);
        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.getLayoutManager().getItemCount();
                mHoriAdapter.insertToPos(pos, new Object());

            }
        });
    }


}
