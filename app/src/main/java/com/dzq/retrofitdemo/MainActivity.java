package com.dzq.retrofitdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.dzq.base.BaseDrawerActivity;
import com.dzq.content.DownLoadView;
import com.dzq.content.GetRetorfitListView;

public class MainActivity extends BaseDrawerActivity {

    private Button btn_retrofit_listview;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        View fakeStatusBar = findViewById(R.id.fake_status_bar);
        CollapsingToolbarLayout ctl = findViewById(R.id.ctl);
        ctl.setExpandedTitleColor(Color.parseColor("#FFFFFFFF"));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, rootLayout, toolbar,
                R.string.main_nav_open, R.string.main_nav_open);

        rootLayout.addDrawerListener(toggle);
        toggle.syncState();

        btn_retrofit_listview = findViewById(R.id.btn_retrofit_listview);
        btn_retrofit_listview.setOnClickListener(this);
    }

    @Override
    public void doSomeThing() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retrofit_listview:
                startActivity(new Intent(MainActivity.this, GetRetorfitListView.class));
                break;
        }
    }

    public void easyDown(View view) {
        startActivity(new Intent(MainActivity.this, DownLoadView.class));

    }
}
