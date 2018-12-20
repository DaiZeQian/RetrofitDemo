package com.dzq.net.rtinterface;

import com.dzq.net.BaseRespose;
import com.dzq.net.exception.RtException;
import com.dzq.net.tag.TagManager;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/18.
 */

public abstract class BaseObserver<T> implements Observer<BaseRespose<T>>, BaseSubscriber<T> {

    protected String tag;

    /**
     * @param tag 标签
     */
    protected void setTag(String tag) {
        this.tag = tag;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        TagManager.get().add(tag, d);
        doOnSubscriber(d);
    }

    @Override
    public void onNext(BaseRespose<T> tBaseRespose) {
        doOnNext(tBaseRespose);
    }


    @Override
    public void onComplete() {
        doOnCompleted();
    }

    @Override
    public void onError(Throwable e) {
        String error = RtException.handleException(e).getMessage();
        doOnError(error, e.toString());

    }
}
