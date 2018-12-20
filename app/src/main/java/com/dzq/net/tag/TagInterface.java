package com.dzq.net.tag;

import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/20.
 * tag 接口 用于管理请求
 */

public interface TagInterface<T> {

    /**
     * 添加
     *
     * @param tag        tag
     * @param disposable disposable
     */
    void add(T tag, Disposable disposable);

    /**
     * 移除请求
     *
     * @param tag tag
     */
    void remove(T tag);

    /**
     * 取消某个tag的请求
     *
     * @param tag tag
     */
    void cancel(T tag);

    /**
     * 取消某些tag的请求 设置可以取消多个请求
     *
     * @param tags tags
     */
    void cancel(T... tags);

    /**
     * 取消所有请求
     */
    void cancelAll();
}
