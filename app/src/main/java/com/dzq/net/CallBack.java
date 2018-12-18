package com.dzq.net;

/**
 * Created by admin on 2018/12/18.
 */

public abstract class CallBack {

    public void onStart() {
    }

    public void onCompleted() {
    }

    abstract public void onError(Throwable e);

    public void onProgress(long progress) {
    }

    abstract public void onSucess(String path, String name, long fileSize);
}
