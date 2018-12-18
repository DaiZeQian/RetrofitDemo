package com.dzq.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dzq.adapter.RetrofitListAdapter;
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
 * 基础的Retrofit 应用
 */

public class GetRetorfitListView extends BaseBarActivity {


    private RecyclerView myRecycler;
    private RetrofitListAdapter listAdapter;


    @Override
    public void initData(@Nullable Bundle bundle) {
        setBack(false);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_retorfit_listview;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        myRecycler = findViewById(R.id.myRecycler);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new RetrofitListAdapter(R.layout.item_retrofit_list);
        myRecycler.setAdapter(listAdapter);
    }

    @Override
    public void doSomeThing() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.JH_BASE_URL).
                //设置数据解析器
                        addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitUrL retrofitUrL = retrofit.create(RetrofitUrL.class);
        Call<JokerBean> call = retrofitUrL.getJoker("desc", 1, 10, System.currentTimeMillis() / 1000 + "", Config.JH_JOKE_APPKEY);
        call.enqueue(new Callback<JokerBean>() {
            @Override
            public void onResponse(Call<JokerBean> call, Response<JokerBean> response) {
                Log.d("response_call_error", response.body().toString());
                if (response.body().getError_code() == 0) {
                    listAdapter.setNewData(response.body().getResult().getData());
                } else {
                    Toast.makeText(GetRetorfitListView.this, "error", Toast.LENGTH_SHORT);
                }
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
