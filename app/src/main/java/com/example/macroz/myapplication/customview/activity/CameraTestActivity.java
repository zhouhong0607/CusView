package com.example.macroz.myapplication.customview.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.activity.BaseActivity;
import com.example.macroz.myapplication.utils.Utils;
import com.example.macroz.myapplication.constant.RadioDefine;
import com.example.macroz.myapplication.customview.view.CameraTestView;

public class CameraTestActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "CameraTestActivity";

    private CameraTestView mCameraTestView;
    private int curSelectMethod = RadioDefine.RADIO_GROUP_METHOD_TRANSLATE;
    private int curSelectCoordinate = RadioDefine.RADIO_GROUP_COORDINATE_X;
    private float inputValue = 0.0f;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        initViews();
        getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText != null) {
                    //降下软键盘,设置editText不选中
                    editText.clearFocus();
                    editText.setSelected(false);
//                    InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
                }
                return false;
            }
        });
    }

    private void initViews() {
        mCameraTestView = findViewById(R.id.camera_test_view);
        initRadios();
        initOthers();
    }

    private void initRadios() {
        RadioGroup methodGroup = findViewById(R.id.radio_group_method);
        methodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_method_translate:
                        curSelectMethod = RadioDefine.RADIO_GROUP_METHOD_TRANSLATE;
                        break;
                    case R.id.radio_method_rotate:
                        curSelectMethod = RadioDefine.RADIO_GROUP_METHOD_ROTATE;
                        break;
                    case R.id.radio_method_location:
                        curSelectMethod = RadioDefine.RADIO_GROUP_METHOD_SET_LOCATION;
                        break;
                }
            }
        });

        RadioGroup coordinateGroup = findViewById(R.id.radio_group_coordinate);
        coordinateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_coordinate_x:
                        curSelectCoordinate = RadioDefine.RADIO_GROUP_COORDINATE_X;
                        break;
                    case R.id.radio_coordinate_y:
                        curSelectCoordinate = RadioDefine.RADIO_GROUP_COORDINATE_Y;
                        break;
                    case R.id.radio_coordinate_z:
                        curSelectCoordinate = RadioDefine.RADIO_GROUP_COORDINATE_Z;
                        break;
                }
            }
        });

    }

    private void initOthers() {
        //输入框
        editText = findViewById(R.id.edit_input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputValue = Utils.String2Num(s.toString());
                Log.e(TAG, "inputValue changed : " + Utils.String2Num(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button applyButton = findViewById(R.id.button_apply);
        applyButton.setOnClickListener(this);

        Button resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        Button centerButton = findViewById(R.id.button_center);
        centerButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_apply:
                applyToCameraView();
                break;
            case R.id.button_reset:
                resetCameraView();
                break;
            case R.id.button_center:
                mCameraTestView.setCenter();
                break;
        }
    }

    private void applyToCameraView() {
        if (curSelectMethod == RadioDefine.RADIO_GROUP_METHOD_TRANSLATE) {
            //位移
            switch (curSelectCoordinate) {
                case RadioDefine.RADIO_GROUP_COORDINATE_X:
                    mCameraTestView.translate(inputValue, 0, 0);
                    Log.e(TAG, "translate X : " + inputValue);
                    break;
                case RadioDefine.RADIO_GROUP_COORDINATE_Y:
                    mCameraTestView.translate(0, inputValue, 0);
                    Log.e(TAG, "translate Y : " + inputValue);
                    break;
                case RadioDefine.RADIO_GROUP_COORDINATE_Z:
                    mCameraTestView.translate(0, 0, inputValue);
                    Log.e(TAG, "translate Z : " + inputValue);
                    break;
            }
        } else if (curSelectMethod == RadioDefine.RADIO_GROUP_METHOD_ROTATE) {
            //旋转
            switch (curSelectCoordinate) {
                case RadioDefine.RADIO_GROUP_COORDINATE_X:
                    mCameraTestView.rotateX(inputValue);
                    Log.e(TAG, "rotate X : " + inputValue);
                    break;
                case RadioDefine.RADIO_GROUP_COORDINATE_Y:
                    mCameraTestView.rotateY(inputValue);
                    Log.e(TAG, "rotate Y : " + inputValue);
                    break;
                case RadioDefine.RADIO_GROUP_COORDINATE_Z:
                    mCameraTestView.rotateZ(inputValue);
                    Log.e(TAG, "rotate Z : " + inputValue);
                    break;
            }
        } else if (curSelectMethod == RadioDefine.RADIO_GROUP_METHOD_SET_LOCATION) {
            //设置坐标
            mCameraTestView.setLocation(inputValue, curSelectCoordinate);
            switch (curSelectCoordinate) {
                case RadioDefine.RADIO_GROUP_COORDINATE_X:
                    Log.e(TAG, "setLocation X : " + inputValue);
                    break;
                case RadioDefine.RADIO_GROUP_COORDINATE_Y:
                    Log.e(TAG, "setLocation Y : " + inputValue);
                    break;
                case RadioDefine.RADIO_GROUP_COORDINATE_Z:
                    Log.e(TAG, "setLocation Z : " + inputValue);
                    break;
            }
        }
        //刷新界面
        mCameraTestView.invalidate();

    }

    private void resetCameraView() {
        mCameraTestView.reset();
    }

}
