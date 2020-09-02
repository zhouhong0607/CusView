package com.netease.apt_api;

/**
 * @author: 周宏
 * @Date: 2020-07-08
 * @Description:
 */
public class AutoBind {
    private static AutoBind instance = null;

    public AutoBind() {
    }

    public static AutoBind getInstance() {
        if (instance == null) {
            synchronized (AutoBind.class) {
                if (instance == null) {
                    instance = new AutoBind();
                }
            }
        }
        return instance;
    }

    public void inject(Object target) {
        String className = target.getClass().getCanonicalName();
        String helperName = className + "$$Autobind";
        try {
            IBindHelper helper = (IBindHelper) (Class.forName(helperName).getConstructor().newInstance());
            helper.inject(target);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String goHelperName = className + "$$AutobindGo";
        try {
            IBindGoHelper helper = (IBindGoHelper) (Class.forName(goHelperName).getConstructor().newInstance());
            helper.inject(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
