package com.example.macroz.myapplication.newsapptest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.R;

public class LottieTestActivity extends AppCompatActivity {

    private float progress = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_test);
        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setImageAssetsFolder("images/");
//        animationView.setAnimation("push_test_animator.json");
        animationView.setAnimation("news_reader_viewmore.json");
        animationView.loop(true);
        animationView.playAnimation();

        Button button = findViewById(R.id.lottie_button_add_progress);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress += 0.1;
                if (progress > 1f) {
                    progress = 0f;
                }
                animationView.setProgress(progress);
            }
        });

    }
}
