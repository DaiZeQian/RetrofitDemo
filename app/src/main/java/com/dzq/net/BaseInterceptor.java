package com.dzq.net;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/12/18.
 * Okhttp 拦截器 分为Application和network层面的
 * <p>
 * <p>
 * Application interceptors
 * <p>
 * 无法操作中间的响应结果，比如当URL重定向发生以及请求重试等，只能操作客户端主动第一次请求以及最终的响应结果。
 * 在任何情况下只会调用一次，即使这个响应来自于缓存。
 * 可以监听观察这个请求的最原始未经改变的意图（请求头，请求体等），无法操作OkHttp为我们自动添加的额外的请求头，比如If-None-Match。
 * 允许short-circuit (短路)并且允许不去调用Chain.proceed()。（注：这句话的意思是Chain.proceed()不需要一定要调用去服务器请求，但是必须还是需要返回Respond实例。那么实例从哪里来？答案是缓存。如果本地有缓存，可以从本地缓存中获取响应实例返回给客户端。这就是short-circuit (短路)的意思。。囧）
 * 允许请求失败重试以及多次调用Chain.proceed()。
 * <p>
 * Network Interceptors
 * <p>
 * 允许操作中间响应，比如当请求操作发生重定向或者重试等。
 * 不允许调用缓存来short-circuit (短路)这个请求。（注：意思就是说不能从缓存池中获取缓存对象返回给客户端，必须通过请求服务的方式获取响应，也就是Chain.proceed()）
 * 可以监听数据的传输
 * 允许Connection对象装载这个请求对象。（注：Connection是通过Chain.proceed()获取的非空对象）
 * <p>
 * <p>
 * 解释引于简书作者miraclehen
 * 地址https://www.jianshu.com/p/fc4d4348dc58
 * 若有侵权请尽快联系删除
 */

public class BaseInterceptor implements Interceptor {

    /**
     * 重新定义请求头
     */
    private Map<String, String> headers;

    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> header = headers.keySet();
            for (String headerKey : header) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }
}
