package com.dzq.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dzq.retrofitdemo.R;

/**
 * Created by admin on 2018/10/22.
 */

public abstract class BaseDrawerActivity extends BaseActivity {

    protected DrawerLayout rootLayout;
    protected FrameLayout flActivityContainer;

    NavigationView.OnNavigationItemSelectedListener mListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }
    };

    @Override
    protected void setBaseView(@LayoutRes int layoutId) {
        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_drawer, null);
        setContentView(mContentView);
        rootLayout = findViewById(R.id.root_layout);
        flActivityContainer = findViewById(R.id.activity_container);
        flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutId, flActivityContainer, false));
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mListener);
    }
}
