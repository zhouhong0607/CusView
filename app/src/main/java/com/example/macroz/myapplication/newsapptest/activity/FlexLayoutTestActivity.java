package com.example.macroz.myapplication.newsapptest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.newsapptest.bean.LabelBean;
import com.example.macroz.myapplication.newsapptest.view.LabelSelectLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlexLayoutTestActivity extends AppCompatActivity {

    private LabelSelectLayout mLabelSelectLayout;
    private List<LabelBean> mBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_layout_test);
        mLabelSelectLayout = findViewById(R.id.test_flex_layout);
        initData();

        mLabelSelectLayout.setOnSelectItemListener(new LabelSelectLayout.OnSelectItemListener() {
            @Override
            public void onSelectItem(LabelBean labelBean) {
                Toast.makeText(FlexLayoutTestActivity.this, labelBean.getLabel(), Toast.LENGTH_SHORT).show();
            }
        });

        mLabelSelectLayout.addChildren(mBeanList);


    }

    private void initData() {
        mBeanList=new ArrayList<>();
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


}
