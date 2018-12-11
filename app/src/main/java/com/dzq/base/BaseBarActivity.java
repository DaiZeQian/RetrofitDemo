package com.dzq.base;

import android.annotation.SuppressLint;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.dzq.retrofitdemo.R;
import com.r0adkll.slidr.Slidr;

/**
 * Created by admin on 2018/10/22.
 */

public abstract class BaseBarActivity extends BaseActivity {

    protected CoordinatorLayout rootLayout;//悬浮布局
    protected Toolbar toolbar;//状态栏
    protected AppBarLayout abl;
    protected FrameLayout flActivityContainer;
    private boolean isBakc = true;

    @SuppressLint("ResourceType")
    @Override
    protected void setBaseView(@LayoutRes int layoutId) {
        if (isBakc) {
            Slidr.attach(this);
        }
        //充气主布局
        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_back, null);
        setContentView(mContentView);
        rootLayout = findViewById(R.id.root_layout);
        abl = findViewById(R.id.abl);
        toolbar = findViewById(R.id.toolbar);
        flActivityContainer = findViewById(R.id.activity_container);
        if (layoutId > 0) {
            flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutId, flActivityContainer, false));
        }
    }

    /**
     * 设置是否可以滑动返回
     *
     * @param isBack
     */
    protected void setBack(boolean isBack) {
        this.isBakc = isBack;
    }

    /**
     * 获取tool bar
     *
     * @return
     */
    protected ActionBar getToolBar() {
        return getSupportActionBar();
    }


}
