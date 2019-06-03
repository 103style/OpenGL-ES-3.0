package com.lxk.luo.utils;

import android.opengl.GLES30;
import android.util.Log;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 15:40
 */
public class OpenGLESShaderUtils {

    private static final String TAG = "OpenGLESShaderUtils";

    /**
     * 编译顶点着色器源代码
     *
     * @param shaderSource 着色器源代码
     */
    public static int compileVertexShader(String shaderSource) {
        Log.i(TAG, shaderSource);
        return compileShader(GLES30.GL_VERTEX_SHADER, shaderSource);
    }

    /**
     * 编译片段着色器源代码
     *
     * @param shaderSource 着色器源代码
     */
    public static int compileFragmentShader(String shaderSource) {
        Log.i(TAG, shaderSource);
        return compileShader(GLES30.GL_FRAGMENT_SHADER, shaderSource);
    }

    /**
     * 编译着色器源代码
     *
     * @param type         顶点着色器:{@link GLES30#GL_VERTEX_SHADER}
     *                     片段着色器:{@link GLES30#GL_FRAGMENT_SHADER }
     * @param shaderSource 着色器源代码
     */
    private static int compileShader(int type, String shaderSource) {
        //创建type类型的着色器
        final int shader = GLES30.glCreateShader(type);
        if (shader == 0) {
            //创建失败 输出日志
            Log.e(TAG, "Error create shader:");
            final String createInfo = GLES30.glGetShaderInfoLog(shader);
            Log.e(TAG, "compileShader createInfo = " + createInfo);
            return 0;
        }
        //加载着色器程序
        GLES30.glShaderSource(shader, shaderSource);
        //编辑着色器程序
        GLES30.glCompileShader(shader);

        //检查编译状态
        final int[] compileRes = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compileRes, 0);
        if (compileRes[0] == 0) {
            //编译失败
            Log.e(TAG, "Error compile shader:");
            //检查日志长度
            final int[] compileInfoLength = new int[1];
            GLES30.glGetShaderiv(shader, GLES30.GL_INFO_LOG_LENGTH, compileInfoLength, 0);
            if (compileInfoLength[0] > 1) {
                //输出日志
                final String compileInfo = GLES30.glGetShaderInfoLog(shader);
                Log.e(TAG, "compileShader compileInfo = " + compileInfo);
            }
            GLES30.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }

    /**
     * 链接着色器程序
     *
     * @param vertexShader   顶点着色器
     * @param fragmentShader 片段着色器
     */
    public static int linkProgram(int vertexShader, int fragmentShader) {
        //创建程序
        final int programObject = GLES30.glCreateProgram();
        if (programObject == 0) {
            //创建失败 输出日志
            Log.e(TAG, "Error create program:");
            final String createInfo = GLES30.glGetProgramInfoLog(programObject);
            Log.e(TAG, "linkProgram createInfo = " + createInfo);
            return 0;
        }

        //加载顶点着色器载入程序
        GLES30.glAttachShader(programObject, vertexShader);
        //加载片段着色器载入程序
        GLES30.glAttachShader(programObject, fragmentShader);

        //链接着色器程序
        GLES30.glLinkProgram(programObject);

        //检查状态
        final int[] linkStatus = new int[1];
        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            //链接失败
            Log.e(TAG, "Error linking program:");
            //检查日志长度
            final int[] compileInfoLength = new int[1];
            GLES30.glGetShaderiv(programObject, GLES30.GL_INFO_LOG_LENGTH, compileInfoLength, 0);
            if (compileInfoLength[0] > 1) {
                //输出日志
                final String linkInfo = GLES30.glGetProgramInfoLog(programObject);
                Log.e(TAG, "linkProgram linkInfo = " + linkInfo);
            }
            GLES30.glDeleteProgram(programObject);
            return 0;
        }
        return programObject;
    }

    /**
     * 验证程序片段是否有效
     */
    public static boolean validProgram(int program) {
        GLES30.glValidateProgram(program);
        final int[] programStatus = new int[1];
        GLES30.glGetProgramiv(program, GLES30.GL_VALIDATE_STATUS, programStatus, 0);
        return programStatus[0] != 0;
    }

}
