package com.example.macroz.myapplication.newsapptest.activity;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.view.HorizontalPullLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HorizontalPullTest extends AppCompatActivity {

    private static final String TAG = HorizontalPullTest.class.getSimpleName();

    private HorizontalPullLayout mHorizontalPullLayout;


    String fileName = "news_reader_viewmore.json";
    String wsDir = Environment.getExternalStorageDirectory().getPath() + "/Tencent/MicroMsg/Download/";
    String rootDir=Environment.getExternalStorageDirectory().getPath()+"/";
    String lottiePath = wsDir + fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_pull_test);
        ViewStub viewStub = findViewById(R.id.pull_view_stub);
        viewStub.setLayoutResource(R.layout.abs_horizontal_pull_layout);
        mHorizontalPullLayout = (HorizontalPullLayout) viewStub.inflate();

        mHorizontalPullLayout.setMaxDragDistance(1000);
        mHorizontalPullLayout.setDragLimit(240);
        mHorizontalPullLayout.setDragThreshold(0.978f);
        //recyclerView部分
        final RecyclerView recyclerView = findViewById(R.id.news_horizontal_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_recycler_item, parent, false)) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });


        //Lottie部分
        final LottieAnimationView lottieAnimationView = findViewById(R.id.news_horizontal_lottie_right);
        lottieAnimationView.setAnimation(fileName);
//        loadLottieFile(lottieAnimationView);
        mHorizontalPullLayout.addOnDragListener(new HorizontalPullLayout.SimpleOnDragListener() {
            @Override
            public void onDragProgressUpdate(float progress, int curState) {
                Log.d(TAG, "progress: " + progress);
                progress = progress <= 1 ? progress : 1;
                lottieAnimationView.setProgress(progress);
            }

            @Override
            public void onDragOver() {
                Toast.makeText(HorizontalPullTest.this, "XXXX", Toast.LENGTH_SHORT).show();
            }
        });


        Log.d(TAG, "Environment.getExternalStorageDirectory() : " + Environment.getExternalStorageDirectory());
        Log.d(TAG, "Environment.getDataDirectory() : " + Environment.getDataDirectory());
        Log.d(TAG, "Environment.getDownloadCacheDirectory() : " + Environment.getDownloadCacheDirectory());
        Log.d(TAG, "Environment.getRootDirectory() : " + Environment.getRootDirectory());
        Log.d(TAG, "Environment.getExternalStorageState() : " + Environment.getExternalStorageState());


//        ViewGroup.LayoutParams lottieLp = new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.MATCH_PARENT);
//        lottieAnimationView.setLayoutParams(lottieLp);
//        lottieAnimationView.setImageAssetsFolder("images/");
//        lottieAnimationView.setAnimation("news_reader_viewmore.json");
//        lottieAnimationView.loop(true);
//        lottieAnimationView.setProgress(0.5f);


        final   TextView fileNameText = findViewById(R.id.cur_lottie_name_text);
        fileNameText.setText(fileName);

        final EditText editText = findViewById(R.id.lottie_file_edit);

        Button loadButton = findViewById(R.id.load_lottie_button);
        Button deleteButton = findViewById(R.id.delete_lottie_button);
        Button updateButton = findViewById(R.id.update_lottie_name_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLottieFile(lottieAnimationView);
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


        ThreadLocal threadLocal=new ThreadLocal();
    }

    //


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
