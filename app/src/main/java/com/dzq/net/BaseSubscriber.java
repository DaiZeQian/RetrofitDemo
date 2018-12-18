package com.dzq.net;

import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/18.
 * Rxjava2的Subscriber  1里边取消了 2就自定义
 */

public interface BaseSubscriber<T> {

    void onSubStart();

    void doOnSub(Disposable d);

    void doOnError(String errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}
