package com.dzq.net.tag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2018/12/20.
 */

public class TagManager implements TagInterface {

    private static TagManager mInstance = null;

    //个人建议 Object 用点string 好区分  但是 算了当我没说
    private HashMap<Object, CompositeDisposable> mMaps;

    public static TagManager get() {
        if (mInstance == null) {
            synchronized (TagManager.class) {
                if (mInstance == null) {
                    mInstance = new TagManager();
                }
            }
        }
        return mInstance;
    }

    private TagManager() {
        mMaps = new HashMap<>();
    }

    @Override
    public void add(Object tag, Disposable disposable) {
        if (tag == null) {
            return;
        }
        //若页面tag相同 那么就可以处理整个页面所有的请求
        CompositeDisposable compositeDisposable = mMaps.get(tag);
        if (compositeDisposable == null) {
            CompositeDisposable compositeDisposableNew = new CompositeDisposable();
            compositeDisposableNew.add(disposable);
            mMaps.put(tag, compositeDisposableNew);
        } else {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void remove(Object tag) {
        if (null == tag) {
            return;
        }
        if (!mMaps.isEmpty()) {
            mMaps.remove(tag);
        }
    }

    @Override
    public void cancel(Object tag) {
        if (null == tag) {
            return;
        }
        if (mMaps.isEmpty()) {
            return;
        }
        if (null == mMaps.get(tag)) {
            return;
        }
        if (!mMaps.get(tag).isDisposed()) {//rxjava 终止指令接收操作 但是依然会继续发送
            mMaps.get(tag).dispose();
            mMaps.remove(tag);
        }
    }

    @Override
    public void cancel(Object[] tags) {
        if (null == tags) {
            return;
        }
        for (Object tag : tags) {
            cancel(tag);
        }
    }

    @Override
    public void cancelAll() {
        if (mMaps.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<Object, CompositeDisposable>> it = mMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = it.next();
            CompositeDisposable disposable = entry.getValue();
            //如果直接使用map的remove方法会报这个错误java.util.ConcurrentModificationException
            //所以要使用迭代器的方法remove
            if (null != disposable) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                    it.remove();
                }
            }
        }

    }
}
