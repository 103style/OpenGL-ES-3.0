package com.lxk.luo.filter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.lxk.luo.R;
import com.lxk.luo.main.AbstractBaseActivity;

/**
 * @author https://github.com/103style
 * @date 2019/8/13 16:34
 */
public class FilterActivity extends AbstractBaseActivity {

    private ViewGroup mRootLayer;

    private OpenGLView mGlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setupViews();
    }

    private void setupViews() {
        mRootLayer = findViewById(R.id.linear_root_layer);

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
        if (itemId == R.id.filter_default) {
            mGlView.setFilter(new OriginFilter());
        } else if (itemId == R.id.filter_gray) {
            mGlView.setFilter(new GrayFilter());
        } else if (itemId == R.id.filter_quarter_mirror) {
            mGlView.setFilter(new QuarterMirrorFilter());
        }
        return super.onOptionsItemSelected(item);
    }
}
