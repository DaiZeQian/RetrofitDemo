package com.dzq.net;

import android.content.Context;

import com.dzq.config.Config;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2018/12/18.
 */

public class RetrofitUtils {
    private static final int TIME_OUT = 30;
    private static BaseApiService baseApiService;
    private static OkHttpClient okHttpClient;
    private static String baseUrl = Config.JH_BASE_URL;
    private static Context context;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;

    //个人感觉静态内部类比较好一点

    private static class SingletonInstance {
        private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    }

    public static RetrofitUtils getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public static RetrofitUtils getInstance(Context contexts) {
        context = contexts;
        return SingletonInstance.INSTANCE;
    }

    private RetrofitUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new CookieManager(context))
                .addInterceptor(new BaseCacheInterCeptor(context))
                .addNetworkInterceptor(new BaseCacheInterCeptor(context))
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 设置同时连接的个数和时间，共8个每个保持时间为15s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

    }


    //
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl);

    //
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addNetworkInterceptor(
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS);

    /**
     * new Api
     *
     * @param newApi
     */
    public static void changeApiBaseUrl(String newApi) {
        baseUrl = newApi;
        builder = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl);
    }

    /**
     * 通过cookieJar的方式添加Cookie
     * addcookieJar
     */
    public static void addCookieJar() {
        okHttpClient.newBuilder().cookieJar(new CookieManager(context)).build();
        retrofit = builder.client(okHttpClient).build();
    }

    public static void changeHeader(Map<String, String> headers) {
        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(headers)).build();
        builder.client(httpClient.build()).build();
    }

    /**
     * create retrofit api
     */
    public RetrofitUtils createBaseApi() {
        baseApiService = create(BaseApiService.class);
        return this;
    }


    private <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


    public void get(String url, Map<String, String> maps, BaseObserver baseObserver) {
        baseApiService.get(url, maps).compose(Transformer.<BaseRespose<Object>>switchSchedulers()).subscribe(baseObserver);
    }


}
