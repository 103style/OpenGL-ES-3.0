package com.lxk.luo.filter;


/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:38
 */
public interface RendererFilter {

    /**
     * 创建回调
     */
    void onSurfaceCreated();

    /**
     * 宽高改变回调
     */
    void onSurfaceChanged(int width, int height);

    /**
     * 绘制回调
     */
    void onDrawFrame();

    /**
     * 销毁回调
     */
    void onDestroy();

}
