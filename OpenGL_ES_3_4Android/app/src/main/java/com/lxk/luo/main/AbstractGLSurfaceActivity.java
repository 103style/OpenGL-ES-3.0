package com.lxk.luo.main;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 15:26
 */
public abstract class AbstractGLSurfaceActivity extends AbstractBaseActivity {

    private GLSurfaceView mGLSurfaceView;

    /**
     * 绑定对应的渲染器
     *
     * @return 渲染器
     */
    protected abstract GLSurfaceView.Renderer bindRenderer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        setContentView(mGLSurfaceView);
        initSetup();
    }

    @Override
    protected void initSetup() {
        //设置版本为 3.0
        mGLSurfaceView.setEGLContextClientVersion(3);

        GLSurfaceView.Renderer renderer = bindRenderer();
        mGLSurfaceView.setRenderer(renderer);
    }

}
