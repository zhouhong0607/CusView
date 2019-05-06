package com.example.macroz.myapplication.customview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.newsapptest.activity.RotateProcessor
import com.example.macroz.myapplication.newsapptest.adapter.VerticalAdapter
import com.example.macroz.myapplication.newsapptest.bean.AdItemBean

class MotionCollapseTestActivity : AppCompatActivity() {

    private lateinit var dataList: ArrayList<Any>
    private lateinit var mRotateProcessor: RotateProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.motion_collapse_toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        initData()
        recyclerView.setAdapter(VerticalAdapter(this, dataList))
        mRotateProcessor = RotateProcessor(recyclerView)
        recyclerView.addOnLayoutChangeListener(View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> mRotateProcessor!!.updateHeight(bottom - top) })
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mRotateProcessor.dealAdRotate(dy, false)
            }
        })

    }

    private fun initData() {
        dataList = ArrayList()
        for (i in 0..19) {
            dataList.add(Any())
        }
        dataList.add(3, AdItemBean("3"))
        dataList.add(10, AdItemBean("10"))
    }

}
