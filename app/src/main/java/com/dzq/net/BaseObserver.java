package com.dzq.net;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/18.
 */

public abstract class BaseObserver<T> implements Observer<T>, BaseSubscriber<T> {


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSub(d);
    }


    @Override
    public void onNext(T t) {
        doOnNext(t);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

    @Override
    public void onError(Throwable e) {
        doOnError(e.toString());

    }


}
