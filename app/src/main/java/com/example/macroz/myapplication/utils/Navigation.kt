package com.example.macroz.myapplication.utils

import android.content.Intent
import com.example.macroz.myapplication.customview.activity.ConsDemoActivity
import com.example.macroz.myapplication.kotlin.LAYOUT_ID
import com.example.macroz.myapplication.kotlin.TurnTo
import com.example.macroz.myapplication.activity.BaseActivity

/**
 * 有Activity  启动新的Activity
 * 没有 启动DemoActivity 传入布局
 */
fun BaseActivity.jumpTo(turnTo: TurnTo) {
    val clazz: Class<*> = turnTo.clazz ?: ConsDemoActivity::class.java
    var intent: Intent = Intent(this, clazz).apply {
        putExtra(LAYOUT_ID, turnTo.layoutId)
    }
    startActivity(intent)
}
