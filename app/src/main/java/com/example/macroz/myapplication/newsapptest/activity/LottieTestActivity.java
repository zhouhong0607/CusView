package com.example.macroz.myapplication.newsapptest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.R;

public class LottieTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_test);
        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setImageAssetsFolder("images/");
        animationView.setAnimation("push_test_animator.json");
        animationView.loop(true);
        animationView.playAnimation();
    }
}
