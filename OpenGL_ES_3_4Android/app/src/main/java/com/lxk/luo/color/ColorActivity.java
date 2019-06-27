package com.lxk.luo.color;

import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.lxk.luo.main.AbstractGLSurfaceActivity;

/**
 * @author https://github.com/103style
 * @date 2019/6/27 15:22
 */
public class ColorActivity extends AbstractGLSurfaceActivity {

    private ColorRenderer colorRenderer = new ColorRenderer(Color.CYAN);

    private AppCompatButton changeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeButton = new AppCompatButton(this);
        changeButton.setAllCaps(false);
        changeButton.setText("click to change a random color");

        getRootView().addView(changeButton, 0);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorRenderer.updateColor(
                        (float) Math.random(),
                        (float) Math.random(),
                        (float) Math.random(),
                        (float) Math.random());
            }
        });
    }

    @Override
    protected GLSurfaceView.Renderer bindRenderer() {
        return colorRenderer;
    }
}
