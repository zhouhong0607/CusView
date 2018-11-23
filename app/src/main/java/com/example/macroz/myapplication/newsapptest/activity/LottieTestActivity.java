package com.example.macroz.myapplication.newsapptest.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LottieTestActivity extends AppCompatActivity {

    private float progress = 0f;

    String fileName = "news_reader_viewmore.json";
    String wsDir = Environment.getExternalStorageDirectory().getPath() + "/Tencent/MicroMsg/Download/";
    String rootDir = Environment.getExternalStorageDirectory().getPath() + "/";
    String lottiePath = wsDir + fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_test);
        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.test_lottie_view);
        animationView.setImageAssetsFolder("images/");
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


        final TextView fileNameText = findViewById(R.id.cur_lottie_name_text);
        fileNameText.setText(fileName);

        final EditText editText = findViewById(R.id.lottie_file_edit);

        Button loadButton = findViewById(R.id.load_lottie_button);
        Button deleteButton = findViewById(R.id.delete_lottie_button);
        Button updateButton = findViewById(R.id.update_lottie_name_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLottieFile(animationView);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLottieFile();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = editText.getText().toString();
                lottiePath = wsDir + fileName;
                fileNameText.setText(fileName);
            }
        });

        List<String> s = new ArrayList<>();


    }

    private void loadLottieFile(LottieAnimationView view) {
        if (view == null) return;

        File file = new File(lottiePath);

        if (!file.exists()) {
            Toast.makeText(this, "文件不存在: ", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder sb = new StringBuilder();

        try {
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isR = new InputStreamReader(is);
            BufferedReader bR = new BufferedReader(isR);

            String line;
            while ((line = bR.readLine()) != null) {
                sb.append(line);
            }

            is.close();
            isR.close();
            bR.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: ", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "文件加载成功: ", Toast.LENGTH_SHORT).show();
        view.setAnimationFromJson(sb.toString());
    }

    private void deleteLottieFile() {
        File file = new File(lottiePath);
        if (file.exists() && file.delete()) {
            Toast.makeText(this, "删除成功: ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败: ", Toast.LENGTH_SHORT).show();
        }
    }

}
