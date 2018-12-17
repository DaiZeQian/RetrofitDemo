package com.dzq.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dzq.base.BaseBarActivity;
import com.dzq.retrofitdemo.R;

/**
 * Created by admin on 2018/12/11.
 */

public class PostRetrofitListView extends BaseBarActivity {



    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_post_retrofitlist;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doSomeThing() {
            //这里请求跟get处理是一样的 但是缺少post api 所以暂时未测试出来，等到布置大体完成 开始处理api问题
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
