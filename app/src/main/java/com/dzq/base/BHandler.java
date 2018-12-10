package com.dzq.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by admin on 2018/12/10.
 */

public class BHandler extends Handler {

    private DHandler dHandler;//回调接口 消息传递给注册者

    public BHandler(Looper looper) {
        super(looper);

    }

    public BHandler(Looper looper, DHandler dHandler) {
        super(looper);
        this.dHandler = dHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (dHandler != null) {
            dHandler.handleMessage(msg);
        }
    }
}

