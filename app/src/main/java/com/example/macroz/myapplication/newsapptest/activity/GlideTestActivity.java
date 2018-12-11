package com.example.macroz.myapplication.newsapptest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.bean.LabelBean;
import com.example.macroz.myapplication.newsapptest.view.LabelSelectLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GlideTestActivity extends AppCompatActivity {

    private LabelSelectLayout mLabelSelectLayout;
    private List<LabelBean> mBeanList;

    private ImageView glideImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_layout_test);
        mLabelSelectLayout = findViewById(R.id.test_flex_layout);
        glideImgView = findViewById(R.id.glide_test_img);
        initData();

        mLabelSelectLayout.setOnSelectItemListener(new LabelSelectLayout.OnSelectItemListener() {
            @Override
            public void onSelectItem(LabelBean labelBean) {
                Toast.makeText(GlideTestActivity.this, labelBean.getLabel(), Toast.LENGTH_SHORT).show();
            }
        });

        mLabelSelectLayout.addChildren(mBeanList);

        testGlide();
    }

    private void initData() {
        mBeanList = new ArrayList<>();
        String pattern = "标签1标签1标签1标签1标签3标签2标签1";
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            LabelBean bean = new LabelBean();
            bean.setLabel(pattern.substring(0, random.nextInt(10) + 1));
            bean.setEnable(true);
            mBeanList.add(bean);
        }
        mBeanList.get(0).setEnable(false);

    }

    private void testGlide() {
        String uri = "http://bjnewsrec-cv.ws.126.net/three467wuE40I9Jtct7fyPFgLQoTW3dCLS2gWkjf3xs0B2ybbksb1540134205248.jpg";
        String gifUri = "http://p1.pstatp.com/large/166200019850062839d3";

        RequestOptions op = new RequestOptions().error(R.drawable.icon_heart_selected).placeholder(R.drawable.icon_heart_unselected).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).load(gifUri).apply(op).into(glideImgView);
    }

}