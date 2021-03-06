package com.dzq.net.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by admin on 2018/12/18.
 * 保存cookie
 */

public class SaveCookieInterceptor implements Interceptor {

    private Context context;
    SharedPreferences sharedPreferences;

    public SaveCookieInterceptor(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.just(response.headers("set-cookie"))
                    .map(new Function<List<String>, String>() {
                        @Override
                        public String apply(List<String> strings) throws Exception {
                            String[] cookieArray = strings.get(0).split(";");
                            return cookieArray[0];
                        }
                    }).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    cookieBuffer.append(s).append(";");
                }
            });

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            editor.commit();
//            Flowable.just(response.headers("set-cookie"))
//                    .map(new Function<List<String>, Object>() {
//                        @Override
//                        public Object apply(List<String> strings) throws Exception {
//                            String[] cookieArray = strings.get(0).split(";");
//                            return cookieArray[0];
//                        }
//                    })
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object o) throws Exception {
//                            cookieBuffer.append(o.toString()).append(";");
//
//                        }
//                    });
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("cookie", cookieBuffer.toString());
//            editor.commit();
        }
        return response;
    }
}
