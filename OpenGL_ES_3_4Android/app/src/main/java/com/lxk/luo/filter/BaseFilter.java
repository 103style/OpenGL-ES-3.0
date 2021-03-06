package com.lxk.luo.filter;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.lxk.luo.R;
import com.lxk.luo.main.MainApplication;
import com.lxk.luo.utils.IoUtils;
import com.lxk.luo.utils.OpenGLESShaderUtils;
import com.lxk.luo.utils.TextureUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:39
 */
public abstract class BaseFilter implements RendererFilter {

    private static final String TAG = "RendererFilter";
    /**
     * 纹理坐标
     * (s,t)
     */
    private static final float[] TEX_VERTEX = {
            0.5f, 0.5f, //纹理坐标V0
            1f, 0f,     //纹理坐标V1
            0f, 0f,     //纹理坐标V2
            0f, 1.0f,   //纹理坐标V3
            1f, 1.0f    //纹理坐标V4
    };
    /**
     * 索引
     */
    private static final short[] VERTEX_INDEX = {
            0, 1, 2,  //V0,V1,V2 三个顶点组成一个三角形
            0, 2, 3,  //V0,V2,V3 三个顶点组成一个三角形
            0, 3, 4,  //V0,V3,V4 三个顶点组成一个三角形
            0, 4, 1   //V0,V4,V1 三个顶点组成一个三角形
    };
    protected int mProgram;
    private FloatBuffer vertexBuffer, mTexVertexBuffer;
    private ShortBuffer mVertexIndexBuffer;
    private int textureId;
    /**
     * 顶点坐标
     * (x,y,z)
     */
    private float[] POSITION_VERTEX = new float[]{
            0f, 0f, 0f,     //顶点坐标V0
            1f, 1f, 0f,     //顶点坐标V1
            -1f, 1f, 0f,    //顶点坐标V2
            -1f, -1f, 0f,   //顶点坐标V3
            1f, -1f, 0f     //顶点坐标V4
    };
    private int uMatrixLocation;

    /**
     * 矩阵
     */
    private float[] mMatrix = new float[16];

    /**
     * 顶点着色器
     */
    private String mVertexShader;

    /**
     * 片段着色器
     */
    private String mFragmentShader;

    /**
     * 加载默认的着色器
     */
    public BaseFilter() {
        this(IoUtils.readShaderResource(R.raw.shader_vertex_no_filter), IoUtils.readShaderResource(R.raw.shader_fragment_no_filter));
    }

    public BaseFilter(final String vertexShader, final String fragmentShader) {
        setFilter(vertexShader, fragmentShader);
    }

    public void setFilter(final String vertexShader, final String fragmentShader) {
        mVertexShader = vertexShader;
        mFragmentShader = fragmentShader;
        //初始化内存空间
        setupBuffer();
    }


    private void setupBuffer() {
        //分配内存空间,每个浮点型占4字节空间
        vertexBuffer = ByteBuffer.allocateDirect(POSITION_VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        //传入指定的坐标数据
        vertexBuffer.put(POSITION_VERTEX);
        vertexBuffer.position(0);

        mTexVertexBuffer = ByteBuffer.allocateDirect(TEX_VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(TEX_VERTEX);
        mTexVertexBuffer.position(0);

        mVertexIndexBuffer = ByteBuffer.allocateDirect(VERTEX_INDEX.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(VERTEX_INDEX);
        mVertexIndexBuffer.position(0);
    }

    public void setupProgram() {
        //编译着色器
        final int vertexShaderId = OpenGLESShaderUtils.compileVertexShader(mVertexShader);
        final int fragmentShaderId = OpenGLESShaderUtils.compileFragmentShader(mFragmentShader);
        //链接程序片段
        mProgram = OpenGLESShaderUtils.linkProgram(vertexShaderId, fragmentShaderId);
        uMatrixLocation = GLES30.glGetUniformLocation(mProgram, "u_Matrix");
        //加载纹理
        textureId = TextureUtils.loadTexture(MainApplication.getApplication(), R.drawable.example);
    }

    @Override
    public final void onSurfaceCreated() {
        //设置背景颜色
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //初始化程序对象
        setupProgram();
    }

    @Override
    public final void onSurfaceChanged(int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;
        if (width > height) {
            //横屏
            Matrix.orthoM(mMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            //竖屏
            Matrix.orthoM(mMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        //使用程序片段
        GLES30.glUseProgram(mProgram);

        //更新属性等信息
        onUpdateDrawFrame();

        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer);

        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, mTexVertexBuffer);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);

        // 绘制
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, VERTEX_INDEX.length, GLES20.GL_UNSIGNED_SHORT, mVertexIndexBuffer);
    }

    public void onUpdateDrawFrame() {

    }

    @Override
    public void onDestroy() {
        GLES30.glDeleteProgram(mProgram);
    }

}
