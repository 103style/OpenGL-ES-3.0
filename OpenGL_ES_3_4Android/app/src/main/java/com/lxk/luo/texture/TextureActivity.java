package com.lxk.luo.texture;

import android.opengl.GLSurfaceView;

import com.lxk.luo.main.AbstractGLSurfaceActivity;

/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:44
 */
public class TextureActivity extends AbstractGLSurfaceActivity {

    @Override
    protected GLSurfaceView.Renderer bindRenderer() {
        return new TextureRenderer();
    }

}
