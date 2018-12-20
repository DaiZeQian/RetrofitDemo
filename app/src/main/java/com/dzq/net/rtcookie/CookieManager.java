package com.dzq.net.rtcookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by admin on 2018/12/18.
 */

public class CookieManager implements CookieJar {

    private static Context context;
    private static PersistentCookieStore cookieStore;

    public CookieManager(Context context) {
        context = context;
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(context);
        }

    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
