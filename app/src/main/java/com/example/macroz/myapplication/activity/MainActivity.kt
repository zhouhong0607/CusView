package com.example.macroz.myapplication.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.constant.ActivityDefine
import com.example.macroz.myapplication.kotlin.TurnTo
import com.example.macroz.myapplication.customview.adapter.GuideAdapter
import com.example.macroz.myapplication.receiver.InstalledReceiver
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        main_guide_recycler?.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.HORIZONTAL))
            this.adapter = GuideAdapter(initData())
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
    private fun initData(): Array<TurnTo> {
        return arrayOf(
                TurnTo("Matrix Api 测试", ActivityDefine.MATRIX_API_TEST_ACTIVITY),
                TurnTo("Matrix Pre/Post 测试", ActivityDefine.MATRIX_PRE_POST_TEST_ACTIVITY),
                //Camera test 界面
                TurnTo("Camera 测试", ActivityDefine.CAMERA_TEST_ACTIVITY),
                //小飞机  转圈圈
                TurnTo("小飞机", ActivityDefine.MATRIX_TEST_ROTATE),
                //poly to poly
                TurnTo("Polygon 测试", ActivityDefine.MATRIX_TEST_POLYGON),
                //折叠布局测试
                TurnTo("FoldLayout 测试", ActivityDefine.MATRIX_FOLDLAYOUT_TEST),
                //谷歌 3D旋转 animation 测试
                TurnTo("Google 3D Rotate测试", ActivityDefine.ROTATE_3D_TEST),
                //cube  魔方界面
                TurnTo("Cube 测试", ActivityDefine.MATRIX_TEST_CUBE),
                TurnTo("Cube ViewPager 测试", ActivityDefine.TEST_VIEWPAGER),
                /********************news app测试部分 */
                TurnTo("Lottie Test", ActivityDefine.TEST_LOTTIE),
                TurnTo("Net Test", ActivityDefine.TEST_NET),
                //color matrix
                TurnTo("Color Matrix ", ActivityDefine.TEST_COLOR_MATRIX),
                //横滑recycler
                TurnTo("Horizontal Recycler ", ActivityDefine.HORIZONTAL_RECYCLER_TEST),
                //垂直 recycler
                TurnTo("Vertical Recycler ", ActivityDefine.VERTICAL_RECYCLER_TEST),
                /************普通test */
                TurnTo("Plugin Test ", ActivityDefine.PLUGIN_APP_TEST),
                //ConstraintLayout
                TurnTo("ConstraintLayout Test ", ActivityDefine.CONSTRAINT_LAYOUT_TEST)
                )
    }

}
