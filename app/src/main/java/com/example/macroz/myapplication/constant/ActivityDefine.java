package com.example.macroz.myapplication.constant;

import com.example.macroz.myapplication.customview.activity.CameraTestActivity;
import com.example.macroz.myapplication.customview.activity.ColorMatrixTestActivity;
import com.example.macroz.myapplication.customview.activity.ConstraintLayoutTestActivity;
import com.example.macroz.myapplication.customview.activity.CubeActivity;
import com.example.macroz.myapplication.customview.activity.FoldLayoutTestActivity;
import com.example.macroz.myapplication.customview.activity.MatrixApiTestActivity;
import com.example.macroz.myapplication.customview.activity.MatrixPrePostTestActivity;
import com.example.macroz.myapplication.customview.activity.PlaneRotateActivity;
import com.example.macroz.myapplication.customview.activity.PolyToPolyTestActivity;
import com.example.macroz.myapplication.customview.activity.Rotate3DTestActivity;
import com.example.macroz.myapplication.customview.activity.ViewPagerTestActivity;
import com.example.macroz.myapplication.newsapptest.activity.NetTestActivity;
import com.example.macroz.myapplication.newsapptest.activity.HorizontalRecyclerTest;
import com.example.macroz.myapplication.newsapptest.activity.LottieTestActivity;
import com.example.macroz.myapplication.newsapptest.activity.VerticalRecyclerTest;
import com.example.macroz.myapplication.plugin.PluginTestActivity;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/8/13 上午11:39
 * 修改人:   macroz
 * 修改时间: 2018/8/13 上午11:39
 * 修改备注:
 */
public class ActivityDefine {
    /**
     * camera test   界面
     */
    public static final  Class<?> CAMERA_TEST_ACTIVITY = CameraTestActivity.class;
    /**
     * Matrix Api test
     */
    public static final  Class<?> MATRIX_API_TEST_ACTIVITY = MatrixApiTestActivity.class;
    /**
     * Matrix pre post Test   界面
     */
    public static final  Class<?> MATRIX_PRE_POST_TEST_ACTIVITY = MatrixPrePostTestActivity.class;
    /**
     * 小飞机    界面
     */
    public static final  Class<?> MATRIX_TEST_ROTATE = PlaneRotateActivity.class;
    /**
     * 魔方 测试界面
     */
    public static final  Class<?> MATRIX_TEST_CUBE = CubeActivity.class;
    /**
     * polygon 测试界面
     */
    public static final  Class<?> MATRIX_TEST_POLYGON = PolyToPolyTestActivity.class;
    /**
     * 折叠布局测试
     */
    public static final  Class<?> MATRIX_FOLDLAYOUT_TEST = FoldLayoutTestActivity.class;
    /**
     * 谷歌官方3D旋转测试
     */
    public static final  Class<?> ROTATE_3D_TEST = Rotate3DTestActivity.class;


    /**
     * FlexLayout , Glide测试
     */
    public static final  Class<?> TEST_NET = NetTestActivity.class;
    /**
     * lottie 测试
     */
    public static final  Class<?> TEST_LOTTIE = LottieTestActivity.class;


    /**
     * ViewPager测试
     */
    public static final  Class<?> TEST_VIEWPAGER = ViewPagerTestActivity.class;

    /**
     * 测试ColorMatrix
     */
    public static final  Class<?> TEST_COLOR_MATRIX = ColorMatrixTestActivity.class;
    /**
     * 横滑recycler
     */
    public static final  Class<?> HORIZONTAL_RECYCLER_TEST = HorizontalRecyclerTest.class;
    /**
     * 垂直类型recycler（新闻列表）
     */
    public static final  Class<?> VERTICAL_RECYCLER_TEST = VerticalRecyclerTest.class;
    /**
     * 测试插件化
     */
    public static final  Class<?> PLUGIN_APP_TEST = PluginTestActivity.class;
    /**
     * ConstraintLayoutTest
     */
    public static final  Class<?> CONSTRAINT_LAYOUT_TEST = ConstraintLayoutTestActivity.class;

}
