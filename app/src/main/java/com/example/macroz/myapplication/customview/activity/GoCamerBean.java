package com.example.macroz.myapplication.customview.activity;

import com.netease.apt_api.IGoBean;

import java.io.Serializable;

/**
 * @author: 周宏
 * @Date: 2020-07-09
 * @Description:
 */
public class GoCamerBean  implements IGoBean , Serializable {

    private String goString;

    public GoCamerBean(String goString) {
        this.goString = goString;
    }
}
