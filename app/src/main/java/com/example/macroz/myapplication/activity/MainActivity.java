package com.example.macroz.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.constant.ActivityDefine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GuideAdapter.ClickGuideListener {

    private List<GuideBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView guideRecyclerView = findViewById(R.id.main_guide_recycler);
        guideRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        guideRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
        initData();
        guideRecyclerView.setAdapter(new GuideAdapter(mList, this));


    }


    /**
     * 初始化各个界面跳转数据
     */
    private void initData() {
        mList = new ArrayList<>();
        //Matrix test 界面
        mList.add(new GuideBean("Matrix 测试",ActivityDefine.MATRIX_TEST_ACTIVITY));
        //Camera test 界面
        mList.add(new GuideBean("Camera 测试",ActivityDefine.CAMERA_TEST_ACTIVITY));
        //小飞机  转圈圈
        mList.add(new GuideBean("小飞机",ActivityDefine.MATRIX_TEST_ROTATE));


    }

    /**
     * 点击后跳往对应页面
     *
     * @param holder
     * @param guideBean
     */
    @Override
    public void onClickGuideView(GuideAdapter.ViewHolder holder,final GuideBean guideBean) {

        holder.itemView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getStartIntent(guideBean.getClassName());
                if (intent != null) {
                    startActivity(intent);
                }
            }
        },150);


    }

    private Intent getStartIntent(String className) {
        Intent intent = null;
        try {
            Class clazz = Class.forName(className);
            intent = new Intent(this, clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return intent;
    }
}
