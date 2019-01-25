package com.example.macroz.myapplication.mainactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.constant.ActivityDefine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GuideAdapter.ClickGuideListener {

    private List<GuideBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView guideRecyclerView = findViewById(R.id.main_guide_recycler);
        guideRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        guideRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
        initData();
        guideRecyclerView.setAdapter(new GuideAdapter(mList, this));

        tryToRequestPermission();
    }

    /**
     * 请求读写的权限
     */
    private void tryToRequestPermission() {
        int STORAGE_REQUEST_CODE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        for (String permission : PERMISSIONS_STORAGE) {
            int granted = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (granted != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        PERMISSIONS_STORAGE,
                        STORAGE_REQUEST_CODE
                );
            }
        }


    }

    /**
     * 初始化各个界面跳转数据
     */
    private void initData() {
        mList = new ArrayList<>();
        //Matrix Api 界面
        mList.add(new GuideBean("Matrix Api 测试", ActivityDefine.MATRIX_API_TEST_ACTIVITY));
        //Matrix pre post test 界面
        mList.add(new GuideBean("Matrix Pre/Post 测试", ActivityDefine.MATRIX_PRE_POST_TEST_ACTIVITY));
        //Camera test 界面
        mList.add(new GuideBean("Camera 测试", ActivityDefine.CAMERA_TEST_ACTIVITY));
        //小飞机  转圈圈
        mList.add(new GuideBean("小飞机", ActivityDefine.MATRIX_TEST_ROTATE));
        //poly to poly
        mList.add(new GuideBean("Polygon 测试", ActivityDefine.MATRIX_TEST_POLYGON));
        //折叠布局测试
        mList.add(new GuideBean("FoldLayout 测试", ActivityDefine.MATRIX_FOLDLAYOUT_TEST));
        //谷歌 3D旋转 animation 测试
        mList.add(new GuideBean("Google 3D Rotate测试", ActivityDefine.ROTATE_3D_TEST));
        //cube  魔方界面
        mList.add(new GuideBean("Cube 测试", ActivityDefine.MATRIX_TEST_CUBE));
        mList.add(new GuideBean("Cube ViewPager 测试", ActivityDefine.TEST_VIEWPAGER));
        /********************news app测试部分*********************/
        mList.add(new GuideBean("Lottie Test", ActivityDefine.TEST_LOTTIE));
        mList.add(new GuideBean("Net Test", ActivityDefine.TEST_NET));

        //color matrix
        mList.add(new GuideBean("Color Matrix ", ActivityDefine.TEST_COLOR_MATRIX));
        //横滑recycler
        mList.add(new GuideBean("Horizontal Recycler ", ActivityDefine.HORIZONTAL_RECYCLER_TEST));
        //垂直 recycler
        mList.add(new GuideBean("Vertical Recycler ", ActivityDefine.VERTICAL_RECYCLER_TEST));
        /************普通test****************/
        mList.add(new GuideBean("Plugin Test ", ActivityDefine.PLUGIN_APP_TEST));

    }

    /**
     * 点击后跳往对应页面
     *
     * @param holder
     * @param guideBean
     */
    @Override
    public void onClickGuideView(GuideAdapter.ViewHolder holder, final GuideBean guideBean) {

        holder.itemView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getStartIntent(guideBean.getClassName());
                if (intent != null) {
                    startActivity(intent);
                }
            }
        }, 150);


    }

    private Intent getStartIntent(String className) {
        Intent intent = null;
        try {
            Class clazz = Class.forName(className);
            intent = new Intent(this, clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return intent;
    }
}
