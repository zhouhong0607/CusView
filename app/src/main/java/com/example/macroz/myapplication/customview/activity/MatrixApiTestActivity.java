package com.example.macroz.myapplication.customview.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.activity.BaseActivity;
import com.example.macroz.myapplication.utils.Utils;
import com.example.macroz.myapplication.constant.RadioDefine;
import com.example.macroz.myapplication.customview.view.MatrixApiTextView;

public class MatrixApiTestActivity extends BaseActivity {
    private MatrixApiTextView mMatrixView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_api_test);
        mMatrixView = findViewById(R.id.matrix_api_test_view);
        initMethodView();
        initInputView();
        initButtonView();
        int i = 10;
        while (i-- > 0) {
            Log.d(getTag(), String.valueOf(i));
        }
    }

    //选择方法 translate rotate scale skew
    private void initMethodView() {
        RadioGroup radioGroup = findViewById(R.id.radio_group_method);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_method_translate:
                        mMatrixView.setSelectMethod(RadioDefine.RADIO_GROUP_METHOD_TRANSLATE);
                        break;
                    case R.id.radio_method_rotate:
                        mMatrixView.setSelectMethod(RadioDefine.RADIO_GROUP_METHOD_ROTATE);
                        break;
                    case R.id.radio_method_scale:
                        mMatrixView.setSelectMethod(RadioDefine.RADIO_GROUP_METHOD_SCALE);
                        break;
                    case R.id.radio_method_skew:
                        mMatrixView.setSelectMethod(RadioDefine.RADIO_GROUP_METHOD_SKEW);
                        break;
                }
            }
        });
    }

    //输入框 x ，y ，degree
    private void initInputView() {
        EditText editTextX = findViewById(R.id.input_edit_x);
        EditText editTextY = findViewById(R.id.input_edit_y);
        EditText editTextD = findViewById(R.id.input_edit_degree);

        editTextX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float x = Utils.String2Num(s.toString());
                mMatrixView.setValueX(x);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float y = Utils.String2Num(s.toString());
                mMatrixView.setValueY(y);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float degree = Utils.String2Num(s.toString());
                mMatrixView.setValueDegree(degree);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //按钮   apply center reset
    private void initButtonView() {
        Button applyButton = findViewById(R.id.button_apply);
        Button centerButton = findViewById(R.id.button_center);
        Button resetButton = findViewById(R.id.button_reset);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMatrixView.apply();
            }
        });
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMatrixView.setCenter();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMatrixView.reset();
            }
        });

    }
}
