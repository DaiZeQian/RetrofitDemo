package com.dzq.net.rtinterface;

import com.dzq.net.BaseRespose;

import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/18.
 */

public abstract class DataObserver<T> extends BaseObserver<T> {

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onError(String errorMsg, String error);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    @Override
    public void doOnSubscriber(Disposable d) {
        //考虑是否需要把 disposable传出去  方便处理事物关闭等
    }

    @Override
    public void doOnNext(BaseRespose<T> t) {
        onSuccess(t.getData());
//        switch (t.getCode()){
//            //处理事物
//        }
    }

    @Override
    public void doOnError(String errorMsg, String error) {
        onError(errorMsg, error);
    }

    @Override
    public void doOnCompleted() {

    }
}
