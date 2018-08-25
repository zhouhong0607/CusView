package com.example.macroz.myapplication.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.customview.view.PolygonView;

public class PolyToPolyTestActivity extends AppCompatActivity {
    private PolygonView mPolygonView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly_to_poly_test);
        mPolygonView = findViewById(R.id.polygon_view);
        initBottom();
    }

    private void initBottom() {
        RadioGroup radioGroup = findViewById(R.id.polygon_select_bottom_layout);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int select = 1;
                switch (checkedId) {
                    case R.id.radio_select_one:
                        select = 1;
                        break;
                    case R.id.radio_select_two:
                        select = 2;
                        break;
                    case R.id.radio_select_three:
                        select = 3;
                        break;
                    case R.id.radio_select_four:
                        select = 4;
                        break;
                }
                mPolygonView.select(select);
            }
        });
        radioGroup.check(R.id.radio_select_one);
    }
}
