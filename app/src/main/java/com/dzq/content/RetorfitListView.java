package com.dzq.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.dzq.base.BaseBarActivity;
import com.dzq.bean.JokerBean;
import com.dzq.config.Config;
import com.dzq.retrofit.RetrofitUrL;
import com.dzq.retrofitdemo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2018/11/5.
 */

public class RetorfitListView extends BaseBarActivity {

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_retorfit_listview;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doSomeThing() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.JH_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitUrL retrofitUrL = retrofit.create(RetrofitUrL.class);
        Call<JokerBean> call = retrofitUrL.getJoker("desc", 1, 10, System.currentTimeMillis() / 1000 + "", Config.JH_JOKE_APPKEY);
        call.enqueue(new Callback<JokerBean>() {
            @Override
            public void onResponse(Call<JokerBean> call, Response<JokerBean> response) {
                Log.d("response_call_error", response.body().toString());
            }

            @Override
            public void onFailure(Call<JokerBean> call, Throwable t) {
                Log.d("response_call_error", t.getMessage());
            }
        });
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
