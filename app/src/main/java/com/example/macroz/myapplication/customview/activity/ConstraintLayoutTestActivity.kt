package com.example.macroz.myapplication.customview.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.kotlin.TurnTo
import com.example.macroz.myapplication.mainactivity.BaseActivity
import com.example.macroz.myapplication.mainactivity.GuideAdapter
import kotlinx.android.synthetic.main.contraint_test_layout.*

class ConstraintLayoutTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contraint_test_layout)
        consRecycler?.run {
            layoutManager=LinearLayoutManager(this@ConstraintLayoutTestActivity)
            adapter=GuideAdapter(initData())
        }
    }

    private fun initData():Array<TurnTo>{
        return arrayOf(
                TurnTo("PlayGround",R.layout.activity_cons_demo),
                TurnTo("Motion1 Basic",R.layout.motion_01),
                TurnTo("PlaceHolder Test",ConsPHTestActivity::class.java)
        )
    }


}
