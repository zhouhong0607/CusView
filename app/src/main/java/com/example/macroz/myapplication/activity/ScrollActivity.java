package com.example.macroz.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.view.TestScrollViewGroup;

public class ScrollActivity extends AppCompatActivity {

    private LinearLayout layout;

    private Button scrollToBtn;

    private Button scrollByBtn;

    private ViewGroup mTestScrollViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        layout = (LinearLayout) findViewById(R.id.layout);
        scrollToBtn = (Button) findViewById(R.id.scroll_to_btn);
        scrollByBtn = (Button) findViewById(R.id.scroll_by_btn);
        scrollToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.scrollTo(-60, -100);
            }
        });
        scrollByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.scrollBy(-60, -100);
            }
        });

        mTestScrollViewGroup=(ViewGroup) findViewById(R.id.testscroll_viewgroup);
        mTestScrollViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTestScrollViewGroup.getScrollY()>500){
                    mTestScrollViewGroup.scrollBy(0,-30);
                }else {
                    mTestScrollViewGroup.scrollBy(0,30);
                }

            }
        });

    }
}
