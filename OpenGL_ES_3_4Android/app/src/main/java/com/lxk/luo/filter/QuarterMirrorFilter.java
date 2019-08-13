package com.lxk.luo.filter;

import com.lxk.luo.R;
import com.lxk.luo.utils.IoUtils;

/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:42
 */
public class QuarterMirrorFilter extends BaseFilter {

    public QuarterMirrorFilter() {
        super(IoUtils.readShaderResource(R.raw.shader_vertex_quarter_mirror_filter), IoUtils.readShaderResource(R.raw.shader_fragment_quarter_mirror_filter));
    }

}
