package com.dzq.retrofit;

import com.dzq.bean.JokerBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 2018/11/5.
 * <p>
 * 用于统一管理retorfit接口
 */

public interface RetrofitUrL {

    @GET("joke/content/list.php")
    Call<JokerBean> getJoker(@Query("sort") String sort, @Query("page") int page, @Query("pagesize") int pagesize,
                             @Query("time") String time, @Query("key") String key);

}
