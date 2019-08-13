package com.lxk.luo.filter;

import android.opengl.GLES30;

import com.lxk.luo.R;
import com.lxk.luo.utils.IoUtils;

/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:38
 */
public class GrayFilter extends BaseFilter {

    private int aFilterLocation;

    private float[] filterValue = new float[]{0.299f, 0.587f, 0.114f};

    public GrayFilter() {
        super(IoUtils.readShaderResource(R.raw.shader_vertex_gray_filter), IoUtils.readShaderResource(R.raw.shader_fragment_gray_filter));
    }

    @Override
    public void setupProgram() {
        super.setupProgram();
        aFilterLocation = GLES30.glGetUniformLocation(mProgram, "a_Filter");
    }

    @Override
    public void onUpdateDrawFrame() {
        //更新参数
        GLES30.glUniform3fv(aFilterLocation, 1, filterValue, 0);
    }

}
