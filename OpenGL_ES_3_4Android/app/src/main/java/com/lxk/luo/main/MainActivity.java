package com.lxk.luo.main;

import android.os.Bundle;
import android.view.View;

import com.lxk.luo.R;
import com.lxk.luo.basis.BasisActivity;
import com.lxk.luo.camera.CameraActivity;
import com.lxk.luo.color.ColorActivity;
import com.lxk.luo.filter.FilterActivity;
import com.lxk.luo.hellotriangle.HelloTriangleActivity;
import com.lxk.luo.jnative.NativeActivity;
import com.lxk.luo.texture.TextureActivity;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 14:52
 * <p>
 * demo
 */
public class MainActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * hello triangle
     */
    public void onHelloTriangleClick(View view) {
        startActivity(HelloTriangleActivity.class);
    }

    /**
     * 颜色显示
     */
    public void onColorClick(View view) {
        startActivity(ColorActivity.class);
    }

    /**
     * 基础图像
     */
    public void onBasisClick(View view) {
        startActivity(BasisActivity.class);
    }

    /**
     * 本地实现
     */
    public void onNativeClick(View view) {
        startActivity(NativeActivity.class);
    }

    /**
     * 图片纹理
     */
    public void onTextureClick(View view) {
        startActivity(TextureActivity.class);
    }

    /**
     * 黑白相机
     */
    public void onCameraClick(View view) {
        startActivity(CameraActivity.class);
    }

    /**
     * 滤镜实现
     */
    public void onFilterClick(View view) {
        startActivity(FilterActivity.class);
    }


}
