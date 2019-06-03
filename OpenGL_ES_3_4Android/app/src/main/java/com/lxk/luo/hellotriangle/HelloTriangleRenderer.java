package com.lxk.luo.hellotriangle;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.lxk.luo.R;
import com.lxk.luo.utils.IoUtils;
import com.lxk.luo.utils.OpenGLESShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 17:13
 * <p>
 * hello triangle 的渲染器
 * <p>
 * https://github.com/danginsburg/opengles3-book/blob/master/Android_Java/Chapter_2/Hello_Triangle/src/com/openglesbook/hellotriangle/HelloTriangleRenderer.java
 */
public class HelloTriangleRenderer implements GLSurfaceView.Renderer {

    /**
     * 顶点缓冲区对象
     */
    private final FloatBuffer mVertices;

    /**
     * 着色器程序
     */
    private int mProgramObject;
    /**
     * 宽高
     */
    private int mWidth, mHeight;
    /**
     * 三角形顶点的 x y z 坐标
     */
    private float[] vPointers = {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    public HelloTriangleRenderer() {
        //分配内存空间,每个浮点型占4字节空间
        mVertices = ByteBuffer.allocateDirect(vPointers.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        //传入指定的坐标数据
        mVertices.put(vPointers)
                .position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //编译顶点着色器
        final int vertexShader = OpenGLESShaderUtils.compileVertexShader(IoUtils.readShaderResource(R.raw.shader_vertex_hello_triangle));
        //编译片段着色器
        final int fragmentShader = OpenGLESShaderUtils.compileFragmentShader(IoUtils.readShaderResource(R.raw.shader_fragment_hello_triangle));
        //链接着色器程序
        mProgramObject = OpenGLESShaderUtils.linkProgram(vertexShader, fragmentShader);

        //设置背景色
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //设置窗口
        GLES30.glViewport(0, 0, mWidth, mHeight);

        //清空颜色缓冲区
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        //设置为活动程序
        GLES30.glUseProgram(mProgramObject);

        //加载顶点坐标数据
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mVertices);
        //启用位置顶点属性
        GLES30.glEnableVertexAttribArray(0);

        //绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
