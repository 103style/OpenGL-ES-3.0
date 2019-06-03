package com.lxk.luo.hellotriangle;

import android.opengl.GLSurfaceView;

import com.lxk.luo.main.AbstractGLSurfaceActivity;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 17:08
 * <p>
 * hello triangle
 */
public class HelloTriangleActivity extends AbstractGLSurfaceActivity {

    @Override
    protected GLSurfaceView.Renderer bindRenderer() {
        return new HelloTriangleRenderer();
    }
}
