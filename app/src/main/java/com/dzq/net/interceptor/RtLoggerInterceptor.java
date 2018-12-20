package com.dzq.net.interceptor;

import android.util.Log;

import com.dzq.util.JsonUtil;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by admin on 2018/12/20.
 */

public class RtLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    private StringBuffer mMessage = new StringBuffer();


    @Override
    public void log(String message) {
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        if (message.startsWith("--> GET")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        //  处理Json数据
        if ((message.startsWith("{") && message.endsWith("}")) || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(message);
        }
        mMessage.append(message.concat("\n"));
        if (message.startsWith("<-- END HTTP")) {
            Log.e("RxHttpUtils", mMessage.toString());
        }
    }
}
