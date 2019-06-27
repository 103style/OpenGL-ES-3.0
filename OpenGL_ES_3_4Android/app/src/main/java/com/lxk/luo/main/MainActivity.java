package com.lxk.luo.main;

import android.os.Bundle;
import android.view.View;

import com.lxk.luo.R;
import com.lxk.luo.basis.BasisActivity;
import com.lxk.luo.color.ColorActivity;
import com.lxk.luo.hellotriangle.HelloTriangleActivity;
import com.lxk.luo.jnative.NativeActivity;

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

    public void onNativeClick(View view) {
        startActivity(NativeActivity.class);
    }

    public void onTextureClick(View view) {

    }

    public void onCameraClick(View view) {

    }

    public void onFilterClick(View view) {

    }


}
