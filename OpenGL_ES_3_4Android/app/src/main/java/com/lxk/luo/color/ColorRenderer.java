package com.lxk.luo.color;

import android.graphics.Color;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author https://github.com/103style
 * @date 2019/6/27 15:22
 */
public class ColorRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "ColorRenderer";

    private int color;

    private float red;
    private float green;
    private float blue;
    private float alpha;

    public ColorRenderer(int color) {
        this.color = color;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        updateColor(Color.red(color) / 255.f,
                Color.green(color) / 255.f,
                Color.blue(color) / 255.f,
                Color.alpha(color) / 255.f);
    }

    /**
     * 更新颜色
     */
    public void updateColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        Log.e(TAG, " rgba= " + red + ", " + green
                + ", " + blue + ", " + alpha);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        //清空颜色缓冲区
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glClearColor(red, green, blue, alpha);
    }
}
