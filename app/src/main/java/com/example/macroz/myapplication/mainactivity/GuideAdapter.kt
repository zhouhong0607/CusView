package com.example.macroz.myapplication.mainactivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.kotlin.TurnTo
import com.example.macroz.myapplication.utils.jumpTo

/**
 * 类描述:   主页面 导航 recyclerView 的adapter
 * 创建人:   macroz
 * 创建时间: 2018/8/14 下午3:05
 * 修改人:   macroz
 * 修改时间: 2018/8/14 下午3:05
 * 修改备注:
 */
class GuideAdapter(val mList: Array<TurnTo>) : RecyclerView.Adapter<GuideAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.guide_holder_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guideBean = mList[position]

        holder.mTitleView?.text = guideBean.title
        holder.itemView.setOnClickListener {
            (it.context as BaseActivity).jumpTo(guideBean)
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View, var mTitleView: TextView? = null) : RecyclerView.ViewHolder(itemView) {
        init {
            mTitleView = itemView.findViewById(R.id.guide_item_title)
        }
    }

}
