package com.dzq.util.download;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by admin on 2018/12/13.
 * 针对Retrofit简单的封装 用户方便下载管理
 */

public class DownLoadNetManager {
    private static final String TAG = "DownLoadManager";

    private static DownLoadNetManager intence;
    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;

    private DownLoadNetManager() {
        this(30, 30, 30);
    }

    public DownLoadNetManager(int connTimeout, int readTimeout, int writeTimeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS);
        mHttpClient = builder.build();
    }

    public static DownLoadNetManager getIntence() {
        if (intence == null) {
            intence = new DownLoadNetManager();
        }
        return intence;
    }


    public DownLoadNetManager buildRetrofit(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mHttpClient)
                .build();
        return this;
    }

    public <T> T createService(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

}
