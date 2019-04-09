package com.example.macroz.myapplication.newsapptest.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.mainactivity.BaseActivity
import com.example.macroz.myapplication.newsapptest.bean.LabelBean
import com.example.macroz.myapplication.newsapptest.view.LabelSelectLayout
import com.example.macroz.myapplication.retrofit.service.SpringTestService

import java.io.IOException
import java.util.ArrayList
import java.util.Random

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit

class NetTestActivity : BaseActivity() {


    private lateinit var mLabelSelectLayout: LabelSelectLayout
    private lateinit var mBeanList: MutableList<LabelBean>

    private var glideImgView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flex_layout_test)
        mLabelSelectLayout = findViewById(R.id.test_flex_layout)
        glideImgView = findViewById(R.id.glide_test_img)
        initData()

        mLabelSelectLayout.setOnSelectItemListener { toast(it.label) }

        mLabelSelectLayout.addChildren(mBeanList)

        testGlide()
        testOkHttp()
        testRetrofit()
    }

    fun initData() {
        mBeanList = ArrayList()
        val pattern = "标签1标签1标签1标签1标签3标签2标签1"
        val random = Random()

        for (i in 0..9) {
            val bean = LabelBean()
            bean.label = pattern.substring(0, random.nextInt(10) + 1)
            bean.isEnable = true
            mBeanList.add(bean)
        }
        mBeanList[0].isEnable = false

    }

    fun testGlide() {
//        val uri = "http://bjnewsrec-cv.ws.126.net/three467wuE40I9Jtct7fyPFgLQoTW3dCLS2gWkjf3xs0B2ybbksb1540134205248.jpg"
        val gifUri = "http://p1.pstatp.com/large/166200019850062839d3"

        val op = RequestOptions().error(R.drawable.icon_heart_selected).placeholder(R.drawable.icon_heart_unselected).diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(gifUri).apply(op).into(glideImgView!!)
    }

    fun testOkHttp() {

        Thread(Runnable {
            try {
                val uri = "http://10.234.122.136:8080/SpringTest_war_exploded/hi/say"
                //                    String uri = "http://bjnewsrec-cv.ws.126.net/three467wuE40I9Jtct7fyPFgLQoTW3dCLS2gWkjf3xs0B2ybbksb1540134205248.jpg";
                val client = OkHttpClient()
                val request = Request.Builder().url(uri).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    Log.d(tag, response.body()!!.string())
                }

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {

                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {

                    }
                })


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()


    }

    fun testRetrofit() {
        var refrofit = Retrofit.Builder().baseUrl("http://10.234.122.136:8080/").build()
        var service = refrofit.create(SpringTestService::class.java)

    }

}