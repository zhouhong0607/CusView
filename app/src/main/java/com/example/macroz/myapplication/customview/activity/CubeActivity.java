package com.example.macroz.myapplication.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.macroz.myapplication.R;

public class CubeActivity extends AppCompatActivity {

    private final static String TAG="CubeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        View view1=findViewById(R.id.child_1);
        View view2=findViewById(R.id.child_2);
        View view3=findViewById(R.id.child_3);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"click View 1");
            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"click View 2");
            }
        });

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"click View 3");
            }
        });


    }
}
