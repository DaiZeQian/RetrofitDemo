package com.dzq.net;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/12/18.
 * 基于Application层的拦截器
 * 一次性 不可更改 一般建议添加项目常量
 */

public class BaseCacheInterCeptor implements Interceptor {

    private Context context;

    public BaseCacheInterCeptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtils.isNetworkAvailable(context)) {
            Response response = chain.proceed(request);
            //页面缓存时间的最大值是60秒
            int maxAge = 60;
            //采用异步通信模式：发送消息者可以在发送消息后进行其它的工作，不用等待接收者的回应，而接收者也不必在接到消息后立即对发送者的请求进行处理。

            //不允许浏览器端或缓存服务器缓存当前页面信息。
           /*response.setHeader( "Pragma", "no-cache" );
            response.setDateHeader("Expires", 0);
            response.addHeader( "Cache-Control", "no-cache" );//浏览器和缓存服务器都不应该缓存页面信息
            response.addHeader( "Cache-Control", "no-store" );//请求和响应的信息都不应该被存储在对方的磁盘系统中；
            response.addHeader( "Cache-Control", "must-revalidate" );*///于客户机的每次请求，代理服务器必须想服务器验证缓存是否过时；

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-control")
                    .header("Cache-control", "public , max-age=" + maxAge)
                    .build();
        } else {//无网络可以选择缓存
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            Response response = chain.proceed(request);
            int maxStale = 60 * 60 * 24 * 3;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)//only if cached
                    .build();
        }

    }
}
