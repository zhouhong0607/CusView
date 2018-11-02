package com.example.macroz.myapplication.constant;

import com.example.macroz.myapplication.customview.activity.CameraTestActivity;
import com.example.macroz.myapplication.customview.activity.CubeActivity;
import com.example.macroz.myapplication.customview.activity.FoldLayoutTestActivity;
import com.example.macroz.myapplication.customview.activity.MatrixApiTestActivity;
import com.example.macroz.myapplication.customview.activity.MatrixPrePostTestActivity;
import com.example.macroz.myapplication.customview.activity.PlaneRotateActivity;
import com.example.macroz.myapplication.customview.activity.PolyToPolyTestActivity;
import com.example.macroz.myapplication.customview.activity.Rotate3DTestActivity;
import com.example.macroz.myapplication.customview.activity.ViewPagerTestActivity;
import com.example.macroz.myapplication.newsapptest.activity.FlexLayoutTestActivity;
import com.example.macroz.myapplication.newsapptest.activity.HorizontalPullTest;
import com.example.macroz.myapplication.newsapptest.activity.LottieTestActivity;
import com.example.macroz.myapplication.newsapptest.activity.RotateAdTest;

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
    public static final String CAMERA_TEST_ACTIVITY = CameraTestActivity.class.getName();
    /**
     * Matrix Api test
     */
    public static final String MATRIX_API_TEST_ACTIVITY = MatrixApiTestActivity.class.getName();
    /**
     * Matrix pre post Test   界面
     */
    public static final String MATRIX_PRE_POST_TEST_ACTIVITY = MatrixPrePostTestActivity.class.getName();
    /**
     * 小飞机    界面
     */
    public static final String MATRIX_TEST_ROTATE = PlaneRotateActivity.class.getName();
    /**
     * 魔方 测试界面
     */
    public static final String MATRIX_TEST_CUBE = CubeActivity.class.getName();
    /**
     * polygon 测试界面
     */
    public static final String MATRIX_TEST_POLYGON = PolyToPolyTestActivity.class.getName();
    /**
     * 折叠布局测试
     */
    public static final String MATRIX_FOLDLAYOUT_TEST = FoldLayoutTestActivity.class.getName();
    /**
     * 谷歌官方3D旋转测试
     */
    public static final String ROTATE_3D_TEST = Rotate3DTestActivity.class.getName();


    /**
     * FlexLayout测试
     */
    public static final String TEST_FLEXLAYOUT = FlexLayoutTestActivity.class.getName();
    /**
     * lottie 测试
     */
    public static final String TEST_LOTTIE = LottieTestActivity.class.getName();


    /**
     * ViewPager测试
     */
    public static final String TEST_VIEWPAGER = ViewPagerTestActivity.class.getName();

    /**
     * 左拉右拉布局测试
     */
    public static final String TEST_NEWS_HORIZONTAL_PULL = HorizontalPullTest.class.getName();
    /**
     * 旋转广告test
     */
    public static final String TEST_NEWS_ROTATE_AD = RotateAdTest.class.getName();


}
