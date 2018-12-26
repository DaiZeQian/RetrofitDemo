package com.dzq.net.config;

import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Created by admin on 2018/12/26.
 */

public class RetrofitConfig {

    private static RetrofitConfig instance;

    private CallAdapter.Factory[] callAdapterFactory;

    private Converter.Factory[] converterFactory;

    private RetrofitConfig() {
    }

    public static RetrofitConfig getInstance() {

        if (instance == null) {
            synchronized (RetrofitConfig.class) {
                if (instance == null) {
                    instance = new RetrofitConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 添加CallAdapterFactory
     *
     * @param factories CallAdapter.Factory
     */
    public void addCallAdapterFactory(CallAdapter.Factory... factories) {
        this.callAdapterFactory = factories;

    }

    /**
     * 添加ConverterFactory
     *
     * @param factories Converter.Factory
     */
    public void addConverterFactory(Converter.Factory... factories) {
        this.converterFactory = factories;
    }

    /**
     * 获取CallAdapter.Factory
     *
     * @return
     */
    public CallAdapter.Factory[] getCallAdapterFactory() {
        return callAdapterFactory;
    }

    /**
     * 获取Converter.Factory
     *
     * @return
     */
    public Converter.Factory[] getConverterFactory() {
        return converterFactory;
    }


}
