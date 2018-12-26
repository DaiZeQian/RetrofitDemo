package com.dzq.net.client;

import okhttp3.OkHttpClient;

/**
 * Created by admin on 2018/12/26
 * 获取HttpClient
 */

public class HttpClient {

    private static HttpClient instance;

    private OkHttpClient.Builder builder;

    public HttpClient() {
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

}
