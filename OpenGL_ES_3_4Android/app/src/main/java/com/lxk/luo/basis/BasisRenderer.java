package com.lxk.luo.basis;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.lxk.luo.R;
import com.lxk.luo.utils.IoUtils;
import com.lxk.luo.utils.OpenGLESShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author https://github.com/103style
 * @date 2019/6/26 17:01
 */
public class BasisRenderer implements GLSurfaceView.Renderer {

    private static final int VERTEX_POSITION_SIZE = 3;
    private static final int VERTEX_COLOR_SIZE = 4;
    /**
     * 顶点和颜色
     */
    private final FloatBuffer vertexBuffer, colorBuffer;
    /**
     * 索引
     */
    private final ShortBuffer indicesBuffer;
    /**
     * 着色器程序
     */
    private int mProgram;
    /**
     * 点的坐标
     */
    private float[] vertexPoints = new float[]{
            //正面矩形
            0.25f, 0.25f, 0.0f,  //V0
            -0.75f, 0.25f, 0.0f, //V1
            -0.75f, -0.75f, 0.0f, //V2
            0.25f, -0.75f, 0.0f, //V3

            //背面矩形
            0.75f, -0.25f, 0.0f, //V4
            0.75f, 0.75f, 0.0f, //V5
            -0.25f, 0.75f, 0.0f, //V6
            -0.25f, -0.25f, 0.0f //V7
    };
    /**
     * 定义索引
     */
    private short[] indices = {
            //背面
            5, 6, 7, 5, 7, 4,
            //左侧
            6, 1, 2, 6, 2, 7,
            //底部
            4, 7, 2, 4, 2, 3,
            //顶面
            5, 6, 7, 5, 7, 4,
            //右侧
            5, 0, 3, 5, 3, 4,
            //正面
            0, 1, 2, 0, 2, 3
    };
    /**
     * 立方体的顶点颜色
     */
    private float[] colors = {
            0.3f, 0.4f, 0.5f, 1f,   //V0
            0.3f, 0.4f, 0.5f, 1f,   //V1
            0.3f, 0.4f, 0.5f, 1f,   //V2
            0.3f, 0.4f, 0.5f, 1f,   //V3
            0.6f, 0.5f, 0.4f, 1f,   //V4
            0.6f, 0.5f, 0.4f, 1f,   //V5
            0.6f, 0.5f, 0.4f, 1f,   //V6
            0.6f, 0.5f, 0.4f, 1f    //V7
    };

    public BasisRenderer() {
        //分配顶点坐标的内存控件 并传入指定数据
        vertexBuffer = ByteBuffer.allocateDirect(vertexPoints.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.put(vertexPoints);
        vertexBuffer.position(0);

        //分配颜色的内存控件 并传入指定数据
        colorBuffer = ByteBuffer.allocateDirect(colors.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        //分配索引内存控件 并传入指定数据
        indicesBuffer = ByteBuffer.allocateDirect(indices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();
        indicesBuffer.put(indices);
        indicesBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glClearColor(0.5F, 0.5F, 0.5F, 0.5F);
        final int vertexId = OpenGLESShaderUtils.compileVertexShader(IoUtils.readShaderResource(R.raw.shader_vertex_basis));
        final int fragmentId = OpenGLESShaderUtils.compileFragmentShader(IoUtils.readShaderResource(R.raw.shader_fragment_basis));
        mProgram = OpenGLESShaderUtils.linkProgram(vertexId, fragmentId);
        GLES30.glUseProgram(mProgram);

        GLES30.glVertexAttribPointer(0, VERTEX_POSITION_SIZE, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        //启用顶点位置属性
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glVertexAttribPointer(1, VERTEX_COLOR_SIZE, GLES30.GL_FLOAT, false, 0, colorBuffer);
        //启用顶点颜色属性
        GLES30.glEnableVertexAttribArray(1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        //清空颜色缓冲区
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indicesBuffer);
    }
}
