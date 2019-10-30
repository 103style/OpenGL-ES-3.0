package com.lxk.luo.filter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.lxk.luo.R;
import com.lxk.luo.main.AbstractBaseActivity;

/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:34
 */
public class FilterActivity extends AbstractBaseActivity implements SeekBar.OnSeekBarChangeListener {

    private ViewGroup mRootLayer;

    private OpenGLView mGlView;

    private View colorFilterLayout;

    private SeekBar sbR, sbG, sbB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setupViews();
    }

    private void setupViews() {
        mRootLayer = findViewById(R.id.linear_root_layer);
        colorFilterLayout = findViewById(R.id.color_filter_layout);
        sbR = findViewById(R.id.sb_r);
        sbR.setOnSeekBarChangeListener(this);
        sbG = findViewById(R.id.sb_g);
        sbG.setOnSeekBarChangeListener(this);
        sbB = findViewById(R.id.sb_b);
        sbB.setOnSeekBarChangeListener(this);
        mGlView = new OpenGLView(this);
        mRootLayer.addView(mGlView, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        colorFilterLayout.setVisibility(View.GONE);
        if (itemId == R.id.filter_default) {
            mGlView.setFilter(new OriginFilter());
        } else if (itemId == R.id.filter_gray) {
            mGlView.setFilter(new ColorFilter());
            colorFilterLayout.setVisibility(View.VISIBLE);
        } else if (itemId == R.id.filter_mirror_1x2) {
            mGlView.setFilter(new MirrorFilter(MirrorFilter.TYPE_1x2));
        } else if (itemId == R.id.filter_mirror_2x1) {
            mGlView.setFilter(new MirrorFilter(MirrorFilter.TYPE_2x1));
        } else if (itemId == R.id.filter_mirror_2x2) {
            mGlView.setFilter(new MirrorFilter(MirrorFilter.TYPE_2x2));
        } else if (itemId == R.id.filter_mirror_3x2) {
            mGlView.setFilter(new MirrorFilter(MirrorFilter.TYPE_3x2));
        } else if (itemId == R.id.filter_mirror_3x3) {
            mGlView.setFilter(new MirrorFilter(MirrorFilter.TYPE_3x3));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_r:
                ColorFilter.filterValue[0] = progress * 1.0f / seekBar.getMax();
                break;
            case R.id.sb_g:
                ColorFilter.filterValue[0] = progress * 1.0f / seekBar.getMax();
                break;
            case R.id.sb_b:
                ColorFilter.filterValue[0] = progress * 1.0f / seekBar.getMax();
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
