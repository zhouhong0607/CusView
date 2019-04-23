package com.example.macroz.myapplication.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/11/23 上午11:21
 * 修改人:   macroz
 * 修改时间: 2018/11/23 上午11:21
 * 修改备注:
 */
 abstract class BaseActivity : AppCompatActivity() {
    protected val tag = this::class.java.simpleName

    fun Context.toast(msg: String = "") {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}
