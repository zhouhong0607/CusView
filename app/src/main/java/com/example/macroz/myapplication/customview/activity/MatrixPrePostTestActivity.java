package com.example.macroz.myapplication.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.constant.MatrixMethodDefine;
import com.example.macroz.myapplication.customview.view.MatrixPrePostTestView;

public class MatrixPrePostTestActivity extends AppCompatActivity implements View.OnClickListener {

    private MatrixPrePostTestView mMatrixPrePostTestView;
    private Spinner selectSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_test);
        mMatrixPrePostTestView = findViewById(R.id.matrix_test_view);
        selectSpinner = findViewById(R.id.matrix_select_spinner);
        selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] methods = getResources().getStringArray(R.array.test_matrix_method);
                switch (methods[position]) {
                    case "pre":
                        mMatrixPrePostTestView.setSelectMethod(MatrixMethodDefine.MATRIX_TEST_METHOD_PRE);
                        break;
                    case "post":
                        mMatrixPrePostTestView.setSelectMethod(MatrixMethodDefine.MATRIX_TEST_METHOD_POST);
                        break;
                    case "concat":
                        mMatrixPrePostTestView.setSelectMethod(MatrixMethodDefine.MATRIX_TEST_METHOD_CANVAS_CONCAT);
                        break;
                    case "rotateP":
                        mMatrixPrePostTestView.setSelectMethod(MatrixMethodDefine.MATRIX_TEST_METHOD_ROTATE_BY_POINT);
                        break;
                    default:
                        mMatrixPrePostTestView.reset();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button applyButton = findViewById(R.id.matrix_apply_button);
        Button centerButton = findViewById(R.id.matrix_center_button);
        Button resetButton = findViewById(R.id.matrix_reset_button);
        applyButton.setOnClickListener(this);
        centerButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.matrix_apply_button:
                mMatrixPrePostTestView.apply();
                break;
            case R.id.matrix_center_button:
                mMatrixPrePostTestView.setCenter();
                break;
            case R.id.matrix_reset_button:
                //重置view到屏幕起始点
                mMatrixPrePostTestView.reset();
                //重置spinner选择default
                selectSpinner.setSelection(0);
                break;


        }
    }
}
