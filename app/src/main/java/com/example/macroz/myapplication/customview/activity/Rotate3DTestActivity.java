package com.example.macroz.myapplication.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.animation.Rotate3dAnimation;

public class Rotate3DTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3_dtest);
        ImageView imageView = findViewById(R.id.rotate_test_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rotate3dAnimation animation = new Rotate3dAnimation(Rotate3DTestActivity.this, 0, 180, v.getMeasuredWidth() / 2, v.getMeasuredHeight() / 2, 1, false);
                animation.setDuration(4000);
                animation.setFillAfter(true);
                animation.setInterpolator(new LinearInterpolator());
                v.startAnimation(animation);

            }
        });
    }
}
