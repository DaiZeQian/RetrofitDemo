package com.dzq.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dzq.base.BaseBarActivity;
import com.dzq.config.Config;
import com.dzq.net.RetrofitUtils;
import com.dzq.net.rtinterface.DataObserver;
import com.dzq.retrofitdemo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/12/18.
 */

public class RxGetView extends BaseBarActivity {
    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_rxget;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doSomeThing() {
        Map<String, String> maps = new HashMap<>();
        maps.put("sort", "desc");
        maps.put("time", System.currentTimeMillis() / 1000 + "");
        maps.put("key", Config.JH_JOKE_APPKEY);
        RetrofitUtils.getInstance(this).createBaseApi().get("joke/content/list.php", maps, new DataObserver() {

            @Override
            protected void onError(String errorMsg, String error) {

            }

            @Override
            protected void onSuccess(Object o) {
            }
        });
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
