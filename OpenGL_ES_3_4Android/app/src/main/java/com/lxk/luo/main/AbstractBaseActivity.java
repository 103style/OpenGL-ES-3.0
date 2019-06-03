package com.lxk.luo.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 14:55
 */
public abstract class AbstractBaseActivity extends AppCompatActivity {

    protected void initView() {

    }

    protected void initParams() {

    }

    protected void initSetup() {

    }

    protected void startActivity(Class className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }
}
