package com.dzq.net;

import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/18.
 */

public abstract class BaseBaseObserver<T> extends BaseObserver<T>{

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    @Override
    public void onSubStart() {

    }

    @Override
    public void doOnSub(Disposable d) {

    }


    @Override
    public void doOnCompleted() {

    }

    @Override
    public void doOnError(String errorMsg) {
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

}
