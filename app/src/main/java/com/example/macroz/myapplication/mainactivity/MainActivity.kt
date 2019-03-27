package com.example.macroz.myapplication.mainactivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.constant.ActivityDefine

class MainActivity : BaseActivity(), GuideAdapter.ClickGuideListener {

    private lateinit var mList: ArrayList<GuideBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        findViewById<RecyclerView>(R.id.main_guide_recycler).apply {
            this?.layoutManager = LinearLayoutManager(this@MainActivity)
            this?.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.HORIZONTAL))
            this?.adapter = GuideAdapter(mList, this@MainActivity)
        }
        tryToRequestPermission()
    }

    /**
     * 请求读写的权限
     */
    private fun tryToRequestPermission() {
        val STORAGE_REQUEST_CODE = 1
        val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        for (permission in PERMISSIONS_STORAGE) {
            val granted = ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (granted != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        this@MainActivity,
                        PERMISSIONS_STORAGE,
                        STORAGE_REQUEST_CODE
                )
            }
        }

    }

    /**
     * 初始化各个界面跳转数据
     */
    private fun initData() {
        mList = ArrayList()
        //Matrix Api 界面
        mList.add(GuideBean("Matrix Api 测试", ActivityDefine.MATRIX_API_TEST_ACTIVITY))
        //Matrix pre post test 界面
        mList.add(GuideBean("Matrix Pre/Post 测试", ActivityDefine.MATRIX_PRE_POST_TEST_ACTIVITY))
        //Camera test 界面
        mList.add(GuideBean("Camera 测试", ActivityDefine.CAMERA_TEST_ACTIVITY))
        //小飞机  转圈圈
        mList.add(GuideBean("小飞机", ActivityDefine.MATRIX_TEST_ROTATE))
        //poly to poly
        mList.add(GuideBean("Polygon 测试", ActivityDefine.MATRIX_TEST_POLYGON))
        //折叠布局测试
        mList.add(GuideBean("FoldLayout 测试", ActivityDefine.MATRIX_FOLDLAYOUT_TEST))
        //谷歌 3D旋转 animation 测试
        mList.add(GuideBean("Google 3D Rotate测试", ActivityDefine.ROTATE_3D_TEST))
        //cube  魔方界面
        mList.add(GuideBean("Cube 测试", ActivityDefine.MATRIX_TEST_CUBE))
        mList.add(GuideBean("Cube ViewPager 测试", ActivityDefine.TEST_VIEWPAGER))
        /********************news app测试部分 */
        mList.add(GuideBean("Lottie Test", ActivityDefine.TEST_LOTTIE))
        mList.add(GuideBean("Net Test", ActivityDefine.TEST_NET))

        //color matrix
        mList.add(GuideBean("Color Matrix ", ActivityDefine.TEST_COLOR_MATRIX))
        //横滑recycler
        mList.add(GuideBean("Horizontal Recycler ", ActivityDefine.HORIZONTAL_RECYCLER_TEST))
        //垂直 recycler
        mList.add(GuideBean("Vertical Recycler ", ActivityDefine.VERTICAL_RECYCLER_TEST))
        /************普通test */
        mList.add(GuideBean("Plugin Test ", ActivityDefine.PLUGIN_APP_TEST))
        //ConstraintLayout
        mList.add(GuideBean("ConstraintLayout Test ", ActivityDefine.CONSTRAINT_LAYOUT_TEST))

    }

    /**
     * 点击后跳往对应页面
     *
     * @param holder
     * @param guideBean
     */
    override fun onClickGuideView(holder: GuideAdapter.ViewHolder, guideBean: GuideBean) {

        holder.itemView.postDelayed({
            val intent = getStartIntent(guideBean.className)
            if (intent != null) {
                startActivity(intent)
            }
        }, 150)


    }

    private fun getStartIntent(className: String): Intent? {
        var intent: Intent? = null
        try {
            val clazz = Class.forName(className)
            intent = Intent(this, clazz)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return intent
    }


}
