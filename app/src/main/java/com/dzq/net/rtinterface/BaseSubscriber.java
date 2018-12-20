package com.dzq.net.rtinterface;

import com.dzq.net.BaseRespose;

import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/18.
 * Rxjava2的Subscriber  1里边取消了 2就自定义
 */

public interface BaseSubscriber<T> {

    //Subscriber 回调 可以操作 d
    void doOnSubscriber(Disposable d);

    //错误
    void doOnError(String errorMsg, String error);

    /**
     * 成功回调并顺应 BaseResponse类型的数据
     *
     * @param t
     */
    void doOnNext(BaseRespose<T> t);

    //请求完成
    void doOnCompleted();
}
