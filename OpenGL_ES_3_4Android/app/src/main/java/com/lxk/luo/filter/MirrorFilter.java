package com.lxk.luo.filter;

import com.lxk.luo.R;
import com.lxk.luo.utils.IoUtils;

/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:42
 */
public class MirrorFilter extends BaseFilter {

    public static final int TYPE_1x2 = 1;
    public static final int TYPE_2x1 = 2;
    public static final int TYPE_2x2 = 3;
    public static final int TYPE_3x2 = 4;
    public static final int TYPE_3x3 = 5;

    public MirrorFilter(int type) {
        String fragmentShader;
        switch (type) {
            case TYPE_1x2:
                fragmentShader = IoUtils.readShaderResource(R.raw.shader_fragment_mirror_filter_1x2);
                break;
            case TYPE_2x1:
                fragmentShader = IoUtils.readShaderResource(R.raw.shader_fragment_mirror_filter_2x1);
                break;
            case TYPE_3x2:
                fragmentShader = IoUtils.readShaderResource(R.raw.shader_fragment_mirror_filter_3x2);
                break;
            case TYPE_3x3:
                fragmentShader = IoUtils.readShaderResource(R.raw.shader_fragment_mirror_filter_3x3);
                break;
            case TYPE_2x2:
            default:
                fragmentShader = IoUtils.readShaderResource(R.raw.shader_fragment_mirror_filter_2x2);
                break;
        }
        setFilter(IoUtils.readShaderResource(R.raw.shader_vertex_quarter_mirror_filter), fragmentShader);
    }

}
