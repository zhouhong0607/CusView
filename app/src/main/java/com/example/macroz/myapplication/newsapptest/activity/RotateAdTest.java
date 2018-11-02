package com.example.macroz.myapplication.newsapptest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.adapter.RotateAdAdapter;

import java.util.ArrayList;
import java.util.List;

public class RotateAdTest extends AppCompatActivity {

    private static final String TAG = "RotateAdTest";
    private List<Object> dataList;
     int scrollY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_ad_test);


        RecyclerView recyclerView = findViewById(R.id.rotate_test_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
        recyclerView.setAdapter(new RotateAdAdapter(this, dataList));


        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "onGlobalLayout: ");
            }
        });


//        recyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                Log.d(TAG, "onScrollChanged: ");
//            }
//        });

//        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                Log.d(TAG, "onPreDraw: ");
//                return true;
//            }
//        });

        recyclerView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                Log.d(TAG, "onDraw: ");
            }
        });

        recyclerView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                Log.d(TAG, "onGlobalFocusChanged  oldFocus: " + (oldFocus == null ? "null" : oldFocus.getClass()) + "  new Focus: " + (newFocus == null ? "null" : newFocus.getClass()));
            }
        });


        recyclerView.getViewTreeObserver().addOnTouchModeChangeListener(new ViewTreeObserver.OnTouchModeChangeListener() {
            @Override
            public void onTouchModeChanged(boolean isInTouchMode) {
                Log.d(TAG, "onTouchModeChanged  isInTouchMode : " + isInTouchMode);
            }
        });

        recyclerView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            @Override
            public void onWindowAttached() {
                Log.d(TAG, "onWindowAttached: ");
            }

            @Override
            public void onWindowDetached() {
                Log.d(TAG, "onWindowDetached: ");
            }
        });

        recyclerView.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                Log.d(TAG, "onWindowFocusChanged  hasFocus: " + hasFocus);
            }
        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                Log.i(TAG, "onScrollStateChanged:  " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                Log.i(TAG, "onScrolled  " + " dy: " + dy);
                RotateProcessor.getInstance().dealAdRotate(recyclerView);
                scrollY += dy;
                Log.d(TAG, "scrollY: " + scrollY);
                Log.d(TAG, "RecyclerView scrollY: " + recyclerView.getScrollY());
            }
        });


    }

    public static class ProgressBean {
        float progress;
        int dir;

        public float getProgress() {
            return progress;
        }

        public void setProgress(float progress) {
            this.progress = progress;
        }

        public int getDir() {
            return dir;
        }

        public void setDir(int dir) {
            this.dir = dir;
        }
    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add(new Object());
        }
        dataList.add(3, new AdItemBean());
        dataList.add(10, new AdItemBean());

    }

    public static class AdItemBean {
    }

}
