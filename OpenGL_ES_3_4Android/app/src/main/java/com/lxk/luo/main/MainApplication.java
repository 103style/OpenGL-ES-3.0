package com.lxk.luo.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 14:54
 */
public class MainApplication extends Application {

    private static Application mApplication;

    public static Application getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
