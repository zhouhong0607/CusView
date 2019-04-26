package com.example.macroz.myapplication.customview.activity

import android.os.Bundle
import android.widget.RadioGroup

import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.customview.view.PolygonView
import com.example.macroz.myapplication.activity.BaseActivity

class PolyToPolyTestActivity : BaseActivity() {
    private var mPolygonView: PolygonView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poly_to_poly_test)
        mPolygonView = findViewById(R.id.polygon_view)
        initBottom()
    }

    private fun initBottom() {
        val radioGroup = findViewById<RadioGroup>(R.id.polygon_select_bottom_layout)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            var select = 1
            when (checkedId) {
                R.id.radio_select_one -> select = 1
                R.id.radio_select_two -> select = 2
                R.id.radio_select_three -> select = 3
                R.id.radio_select_four -> select = 4
            }
            mPolygonView!!.select(select)
        }
        radioGroup.check(R.id.radio_select_one)
    }
}
