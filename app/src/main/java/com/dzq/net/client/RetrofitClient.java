package com.dzq.net.client;

import com.dzq.net.config.OkHttpConfig;
import com.dzq.net.config.RetrofitConfig;
import com.dzq.net.https.SSLConfig;
import com.dzq.net.interceptor.RtLoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
        CallAdapter.Factory[] callAdapterFactorys = RetrofitConfig.getInstance().getCallAdapterFactory();
        if (callAdapterFactorys != null && callAdapterFactorys.length > 0) {
            for (CallAdapter.Factory factory : callAdapterFactorys) {
                mRBuilder.addCallAdapterFactory(factory);
            }
        } else {
            mRBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }

        Converter.Factory[] callAdapterConver = RetrofitConfig.getInstance().getConverterFactory();
        if (callAdapterConver != null && callAdapterConver.length > 0) {
            for (Converter.Factory conver : callAdapterConver) {
                mRBuilder.addConverterFactory(conver);
            }
        } else {
            mRBuilder.addConverterFactory(ScalarsConverterFactory.create()).
                      addConverterFactory(GsonConverterFactory.create());
        }
    }

    /**
     * 获取Retrofit Builder 对象
     *
     * @return
     */
    public Retrofit.Builder getRetrofitBuilder() {
        return mRBuilder;
    }

    public Retrofit getRetrofit() {
        if (OkHttpConfig.getOkHttpClient() == null) {
            return mRBuilder.client(okHttpClient).build();
        } else {
            return mRBuilder.client(OkHttpConfig.getOkHttpClient()).build();
        }
    }


    /**
     * 初始化OKhttp
     */
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
