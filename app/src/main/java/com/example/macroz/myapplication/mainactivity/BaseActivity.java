package com.example.macroz.myapplication.mainactivity;

import android.support.v7.app.AppCompatActivity;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/23 上午11:21
 * 修改人:   macroz
 * 修改时间: 2018/11/23 上午11:21
 * 修改备注:
 */
public class BaseActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    protected String getTAG() {
        return TAG;
    }

}
