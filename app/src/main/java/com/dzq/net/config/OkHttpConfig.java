package com.dzq.net.config;

import android.content.Context;
import android.text.TextUtils;

import com.dzq.net.https.SSLConfig;
import com.dzq.net.interceptor.CacheInterCeptor;
import com.dzq.net.interceptor.HeaderInterceptor;
import com.dzq.net.interceptor.RtLoggerInterceptor;
import com.dzq.net.rtcookie.CookieManager;

import java.io.File;
import java.io.InputStream;
import java.net.CookieStore;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by admin on 2018/12/26.
 */

public class OkHttpConfig {

    private static String defaultCachePath;
    private static final long defaultCacheSize = 1024 * 1024 * 100;
    private static final long defalutTimeOut = 15;//s


    private static OkHttpConfig instance;

    private static OkHttpClient.Builder okBuilder;

    private static OkHttpClient okHttpClient;

    public OkHttpConfig() {
        okBuilder = new OkHttpClient.Builder();
    }

    public static OkHttpConfig getInstance() {
        if (instance == null) {
            synchronized (OkHttpConfig.class) {
                if (instance == null) {
                    instance = new OkHttpConfig();
                }
            }
        }
        return instance;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 配置Builder
     */
    public static class Bulider {
        public Context context;
        private Map<String, String> headerMaps;
        private boolean isDebug;
        private boolean isCache;
        private String cachePath;
        private long cacheMaxSize;
        private CookieStore cookieStore;
        private long readTimeout;
        private long writeTimeout;
        private long connectTimeout;
        private InputStream bksFile;
        private String password;
        private InputStream[] certificates;
        private Interceptor[] interceptors;

        public Bulider(Context context) {
            this.context = context;
        }

        public Bulider setHeaderMaps(Map<String, String> headerMaps) {
            this.headerMaps = headerMaps;
            return this;
        }

        public Bulider setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public Bulider setCache(boolean cache) {
            isCache = cache;
            return this;
        }

        public Bulider setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        public Bulider setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        public Bulider setCookieStore(CookieStore cookieStore) {
            this.cookieStore = cookieStore;
            return this;
        }

        public Bulider setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Bulider setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Bulider setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }


        public Bulider setSslSocketFactory(InputStream... certificates) {
            this.certificates = certificates;
            return this;
        }

        public Bulider setSslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
            this.bksFile = bksFile;
            this.password = password;
            this.certificates = certificates;
            return this;
        }

        public Bulider setAddInterceptors(Interceptor[] interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public OkHttpClient build() {
            OkHttpConfig.getInstance();
            addInterceptor();
            setLogConfig();
            setHeaders();
            setCacheConfig();
            setCookieJar();
            setTimeout();
            setSslConfig();
            okHttpClient = okBuilder.build();
            return okHttpClient;
        }

        /**
         * 添加注解器
         */
        private void addInterceptor() {
            if (interceptors != null) {
                for (Interceptor interceptor : interceptors) {
                    okBuilder.addInterceptor(interceptor);
                }
            }
        }

        /**
         * 配置日志
         */
        private void setLogConfig() {
            if (isDebug) {
                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new RtLoggerInterceptor());
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okBuilder.addInterceptor(logInterceptor);
            }
        }

        /**
         * 添加请求头
         */
        private void setHeaders() {
            okBuilder.addInterceptor(new HeaderInterceptor(headerMaps));
        }


        /**
         * 配置CookieJar
         */
        private void setCookieJar() {
            if (cookieStore != null) {
                okBuilder.cookieJar(new CookieManager(context));
            }
        }


        /**
         * 配置缓存
         */
        private void setCacheConfig() {
            File externalCacheDir = context.getExternalCacheDir();
            if (null == externalCacheDir) {
                return;
            }
            defaultCachePath = externalCacheDir.getPath() + "/RxHttpCacheData";
            if (isCache) {
                Cache cache;
                if (!TextUtils.isEmpty(cachePath) && cacheMaxSize > 0) {
                    cache = new Cache(new File(cachePath), cacheMaxSize);
                } else {
                    cache = new Cache(new File(defaultCachePath), defaultCacheSize);
                }
                okBuilder
                        .cache(cache)
                        .addInterceptor(new CacheInterCeptor(context))
                        .addNetworkInterceptor(new CacheInterCeptor(context));
            }
        }

        /**
         * 配置超时信息
         */
        private void setTimeout() {
            okBuilder.readTimeout(readTimeout == 0 ? defalutTimeOut : readTimeout, TimeUnit.SECONDS);
            okBuilder.writeTimeout(writeTimeout == 0 ? defalutTimeOut : writeTimeout, TimeUnit.SECONDS);
            okBuilder.connectTimeout(connectTimeout == 0 ? defalutTimeOut : connectTimeout, TimeUnit.SECONDS);
            okBuilder.retryOnConnectionFailure(true);
        }

        /**
         * 配置证书
         */
        private void setSslConfig() {
            SSLConfig.SSLParams sslParams = null;

            if (null == certificates) {
                //信任所有证书,不安全有风险
                sslParams = SSLConfig.getSslSocketFactory();
            } else {
                if (null != bksFile && !TextUtils.isEmpty(password)) {
                    //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLConfig.getSslSocketFactory(bksFile, password, certificates);
                } else {
                    //使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLConfig.getSslSocketFactory(certificates);
                }
            }

            okBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        }

    }
}
