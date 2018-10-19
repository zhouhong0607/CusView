package com.example.macroz.myapplication.mainactivity;

/**
 * 类描述:   主页面导航  item 对应的 Bean
 * 创建人:   macroz
 * 创建时间: 2018/8/14 下午3:09
 * 修改人:   macroz
 * 修改时间: 2018/8/14 下午3:09
 * 修改备注:
 */
public class GuideBean {
    private String title;
    //对应 测试界面 activity 的类名
    private String className;


    public GuideBean(String title, String className) {
        this.title = title;
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
