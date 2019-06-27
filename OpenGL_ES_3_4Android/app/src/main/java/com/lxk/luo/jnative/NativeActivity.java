package com.lxk.luo.jnative;

import android.opengl.GLSurfaceView;

import com.lxk.luo.main.AbstractGLSurfaceActivity;

/**
 * @author https://github.com/103style
 * @date 2019/6/27 16:39
 */
public class NativeActivity extends AbstractGLSurfaceActivity {

    @Override
    protected GLSurfaceView.Renderer bindRenderer() {
        return new NativeRenderer();
    }
}
