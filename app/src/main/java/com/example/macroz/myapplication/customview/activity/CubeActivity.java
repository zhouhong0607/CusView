package com.example.macroz.myapplication.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.customview.view.CubeLayout;

public class CubeActivity extends AppCompatActivity {

    private final static String TAG = "CubeActivity";
    private CubeLayout mCubeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        View view1 = findViewById(R.id.child_1);
        View view2 = findViewById(R.id.child_2);
        View view3 = findViewById(R.id.child_3);
        mCubeLayout = findViewById(R.id.cube_layout);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //父View拦截Move事件后  短时间内点击同一区域会产生click事件 ，滚动或者长按都不产生click事件 (正常情况下 长按  滑动后松开都会产生click事件)
                Log.e(TAG, "click View 1");
            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "click View 2");
            }
        });

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "click View 3");
            }
        });


        Button button = findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //父View拦截Move事件后  短时间内点击(或者长按 ，这点与imageView有区别 研究下 )同一区域会产生click事件 ，滚动不产生click事件
                Toast.makeText(CubeActivity.this, "Click", Toast.LENGTH_SHORT).show();
                mCubeLayout.scrollBy(0, 200);
            }
        });
    }
}
