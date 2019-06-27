package com.lxk.luo.main;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.lxk.luo.R;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 15:26
 */
public abstract class AbstractGLSurfaceActivity extends AbstractBaseActivity {

    private GLSurfaceView mGLSurfaceView;

    private LinearLayout rootView;

    /**
     * 绑定对应的渲染器
     *
     * @return 渲染器
     */
    protected abstract GLSurfaceView.Renderer bindRenderer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_gl_surface);
        rootView = findViewById(R.id.linear_root);
        mGLSurfaceView = findViewById(R.id.abs_gl_surface);
        initSetup();
    }

    /**
     * 获取根布局
     */
    public LinearLayout getRootView() {
        return rootView;
    }

    /**
     * 获取SurfaceView
     */
    public GLSurfaceView getGLSurfaceView() {
        return mGLSurfaceView;
    }

    @Override
    protected void initSetup() {
        //设置版本为 3.0
        mGLSurfaceView.setEGLContextClientVersion(3);

        GLSurfaceView.Renderer renderer = bindRenderer();
        mGLSurfaceView.setRenderer(renderer);
    }

}
