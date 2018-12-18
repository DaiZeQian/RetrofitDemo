package com.dzq.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dzq.base.BaseBarActivity;
import com.dzq.config.Config;
import com.dzq.net.BaseBaseObserver;
import com.dzq.net.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/12/18.
 */

public class RxGetView extends BaseBarActivity{
    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return 0;
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
        RetrofitUtils.getInstance(this).createBaseApi().get("joke/content/list.php", maps, new BaseBaseObserver() {
            @Override
            protected void onError(String errorMsg) {

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
