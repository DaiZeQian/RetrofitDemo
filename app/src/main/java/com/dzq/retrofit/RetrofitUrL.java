package com.dzq.retrofit;

import com.dzq.bean.JokerBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 2018/11/5.
 * <p>
 * 用于统一管理retorfit接口
 */

public interface RetrofitUrL {


    //    Retrofit将Http请求抽象成Java接口，并在接口里面采用注解来配置网络请求参数。用动态代理将该接口的注解“翻译”成一个Http请求，最后再执行 Http请求
//    注意： 接口中的每个方法的参数都需要使用注解标注，否则会报错
    //@Headers({"key:value", "key2:value2"}) header的添加
    @GET("joke/content/list.php")
    Call<JokerBean> getJoker(@Query("sort") String sort, @Query("page") int page, @Query("pagesize") int pagesize,
                             @Query("time") String time, @Query("key") String key);

    //post请求 @query 需要换成 @field
    //post 请求可以传递body 把参数换成 @Body bean
    @POST("joke/content/list.php")
    Call<JokerBean> getPostJoker(@Field("sort") String sort, @Field("page") int page, @Field("pagesize") int pagesize,
                                 @Field("time") String time, @Field("key") String key);


}
