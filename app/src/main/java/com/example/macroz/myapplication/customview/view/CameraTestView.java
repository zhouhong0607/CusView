package com.example.macroz.myapplication.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.Utils;
import com.example.macroz.myapplication.constant.RadioDefine;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/8/13 上午11:15
 * 修改人:   macroz
 * 修改时间: 2018/8/13 上午11:15
 * 修改备注:
 */
public class CameraTestView extends View {
    private static final String TAG = "CameraTestView";
    private Bitmap mBitmap;
    private Paint mPaint;
    private Matrix mMatrix;
    private Camera mCamera;

    public CameraTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBitmap = Utils.getBitmap(getResources(), R.drawable.page1);
        mPaint = new Paint();
        mMatrix = new Matrix();
        mCamera = new Camera();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCamera.applyToCanvas(canvas);
        canvas.drawBitmap(mBitmap, 0,0, mPaint);
    }


    /**
     * translate 方法测试
     *
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        mCamera.translate(x, y, z);
        printMatrix();
    }


    /**
     * X 轴旋转
     *
     * @param xDeg
     */
    public void rotateX(float xDeg) {
        mCamera.rotateX(xDeg);
        printMatrix();
    }

    /**
     * Y 轴旋转
     *
     * @param yDeg
     */
    public void rotateY(float yDeg) {
        mCamera.rotateY(yDeg);
        printMatrix();
    }

    /**
     * Z 轴旋转
     *
     * @param zDeg
     */
    public void rotateZ(float zDeg) {
        mCamera.rotateZ(zDeg);
        printMatrix();
    }

    /**
     * API 12
     * 摄像机的位置默认是 (0, 0, -576)。其中 -576＝ -8 x 72
     * Sets the location of the camera. The default location is set at
     * 0, 0, -8
     *
     * @param value      输入的值
     * @param coordinate 对应修改的坐标 x,y,z
     */
    public void setLocation(float value, int coordinate) {
        float toX = mCamera.getLocationX();
        float toY = mCamera.getLocationY();
        float toZ = mCamera.getLocationZ();


        switch (coordinate) {
            case RadioDefine.RADIO_GROUP_COORDINATE_X:
                toX = value;
                break;
            case RadioDefine.RADIO_GROUP_COORDINATE_Y:
                toY = value;
                break;
            case RadioDefine.RADIO_GROUP_COORDINATE_Z:
                toZ = value;
                break;
        }


        Log.e(TAG, "pre Location  x , y , z :" + "( " + mCamera.getLocationX() + " " + mCamera.getLocationY() + " " + mCamera.getLocationZ() + " )");

        mCamera.setLocation(toX, toY, toZ);

        float curX = mCamera.getLocationX();
        float curY = mCamera.getLocationY();
        float curZ = mCamera.getLocationZ();

        Log.e(TAG, "after Location x , y , z :" + "( " + curX + " " + curY + " " + curZ + " )");

        printMatrix();
    }


    /**
     * 保存camera状态
     */
    public void saveCamera() {
        mCamera.save();
    }

    /**
     * 回滚Camera状态
     */
    public void restoreCamera() {
        mCamera.restore();
        invalidate();
    }

    /**
     * 重置camera状态
     */
    public void resetCamera() {
        mCamera = new Camera();
        invalidate();
    }

    public void printMatrix() {
        mCamera.getMatrix(mMatrix);
        Log.e(TAG, "matrix: " + mMatrix.toShortString());
    }


}
