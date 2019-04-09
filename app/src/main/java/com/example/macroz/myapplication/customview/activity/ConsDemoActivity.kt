package com.example.macroz.myapplication.customview.activity

import android.os.Bundle
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.kotlin.LAYOUT_ID
import com.example.macroz.myapplication.mainactivity.BaseActivity

class ConsDemoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var layoutId=intent.getIntExtra(LAYOUT_ID,0)
        layoutId= if (layoutId > 0 ) layoutId else R.layout.activity_cons_demo
        setContentView(layoutId)
    }
}
