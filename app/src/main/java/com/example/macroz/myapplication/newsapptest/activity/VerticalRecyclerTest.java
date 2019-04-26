package com.example.macroz.myapplication.newsapptest.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.activity.BaseActivity;
import com.example.macroz.myapplication.newsapptest.adapter.VerticalAdapter;
import com.example.macroz.myapplication.newsapptest.bean.AdItemBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VerticalRecyclerTest extends BaseActivity {
    private static final String TAG = HorizontalRecyclerTest.class.getSimpleName();
    private List<Object> dataList;
    private RotateProcessor mRotateProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_recycler_test);

        RecyclerView recyclerView = findViewById(R.id.test_vertical_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
        recyclerView.setAdapter(new VerticalAdapter(this, dataList));
        mRotateProcessor = new RotateProcessor(recyclerView);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mRotateProcessor.updateHeight(bottom - top);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mRotateProcessor.dealAdRotate(dy, false);
            }
        });

    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add(new Object());
        }
        dataList.add(3, new AdItemBean("3"));
        dataList.add(10, new AdItemBean("10"));
    }

}
