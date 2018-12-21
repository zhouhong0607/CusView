package com.example.macroz.myapplication.mainactivity;

import android.app.Application;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/12/19 上午10:42
 * 修改人:   macroz
 * 修改时间: 2018/12/19 上午10:42
 * 修改备注:
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
