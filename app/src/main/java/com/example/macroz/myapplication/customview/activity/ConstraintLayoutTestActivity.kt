package com.example.macroz.myapplication.customview.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.kotlin.TurnTo
import com.example.macroz.myapplication.activity.BaseActivity
import com.example.macroz.myapplication.customview.adapter.GuideAdapter
import kotlinx.android.synthetic.main.contraint_test_layout.*

class ConstraintLayoutTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contraint_test_layout)
        consRecycler?.run {
            layoutManager = LinearLayoutManager(this@ConstraintLayoutTestActivity)
            adapter = GuideAdapter(initData())
        }
    }

    private fun initData(): Array<TurnTo> {
        return arrayOf(
                TurnTo("PlayGround", R.layout.activity_cons_demo),
                TurnTo("Menu", R.layout.constraint_circle_menu),
                TurnTo("PlaceHolder Test", ConsPHTestActivity::class.java),
                TurnTo("State Test", ConsStateTestActivity::class.java),
                TurnTo("Motion Basic", R.layout.motion_basic),
                TurnTo("Motion Attr", R.layout.motion_attr),
                TurnTo("Motion Collapse", MotionCollapseTestActivity::class.java)
        )
    }


}
