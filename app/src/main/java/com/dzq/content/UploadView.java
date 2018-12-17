package com.dzq.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.dzq.base.BaseBarActivity;
import com.dzq.config.Config;
import com.dzq.retrofit.RetrofitUrL;
import com.dzq.retrofitdemo.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2018/12/13.
 */

public class UploadView extends BaseBarActivity {

    private String fileName;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_upload;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UploadView.this, "仅仅简单的实现了文件上传的功能,因缺少上传api暂未开放擅长传功能", Toast.LENGTH_SHORT).show();
//                upload();
            }
        });
    }

    @Override
    public void doSomeThing() {

    }


    @Override
    public void onWidgetClick(View view) {

    }


    private void upload() {
        //创建Retrofit  上传文件进度封装将会放在项目整理里边
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.JH_BASE_URL).
                //设置数据解析器
                        addConverterFactory(GsonConverterFactory.create()).build();
        //构建要上传的文件
        File file = new File(fileName);
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);
        String dec = "this is a key";
        //表单方式提交数据
        RequestBody decbody = RequestBody.create(MediaType.parse("multipart/form-data"), dec);
        RetrofitUrL retrofitUrL = retrofit.create(RetrofitUrL.class);
        Call<ResponseBody> call = retrofitUrL.upload(decbody, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("OK");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Failed");
            }

        });
    }

}
