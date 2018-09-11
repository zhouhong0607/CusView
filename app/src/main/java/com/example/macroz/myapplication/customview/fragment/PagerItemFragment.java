package com.example.macroz.myapplication.customview.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macroz.myapplication.R;

/**
 * 类描述:
 * 创建人:   macroz
 * 创建时间: 2018/9/11 下午8:18
 * 修改人:   macroz
 * 修改时间: 2018/9/11 下午8:18
 * 修改备注:
 */
public class PagerItemFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matrix_api_test, container, false);
        return view;

    }
}
