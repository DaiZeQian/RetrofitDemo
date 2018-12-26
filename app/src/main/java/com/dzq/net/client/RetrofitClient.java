package com.dzq.net.client;

import com.dzq.net.https.SSLConfig;
import com.dzq.net.interceptor.RtLoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by admin on 2018/12/26.
 * <p>
 * unFinish
 */

public class RetrofitClient {

    private static RetrofitClient instance;

    private Retrofit.Builder mRBuilder;

    private OkHttpClient okHttpClient;


    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public RetrofitClient() {
        initOkhttp();

        mRBuilder = new Retrofit.Builder();
    }

    private void initOkhttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //配置默认信息
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);

        //开放https全局变量
        SSLConfig.SSLParams sslParams = SSLConfig.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new RtLoggerInterceptor());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        okHttpClient = builder.build();
    }

}
