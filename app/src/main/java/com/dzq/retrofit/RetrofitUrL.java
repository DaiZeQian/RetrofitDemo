package com.dzq.retrofit;

import com.dzq.bean.JokerBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by admin on 2018/11/5.
 * <p>
 * 用于统一管理retorfit接口
 */

public interface RetrofitUrL {


    //    Retrofit将Http请求抽象成Java接口，并在接口里面采用注解来配置网络请求参数。用动态代理将该接口的注解“翻译”成一个Http请求，最后再执行 Http请求
//    注意： 接口中的每个方法的参数都需要使用注解标注，否则会报错
    //@Headers({"key:value", "key2:value2"}) header的添加 还有header 和 headermap
    @GET("joke/content/list.php")
    Call<JokerBean> getJoker(@Query("sort") String sort, @Query("page") int page, @Query("pagesize") int pagesize,
                             @Query("time") String time, @Query("key") String key);

    //post请求 @query 需要换成 @field
    //post 请求可以传递body 把参数换成 @Body bean
    @POST("joke/content/list.php")
    Call<JokerBean> getPostJoker(@Field("sort") String sort, @Field("page") int page, @Field("pagesize") int pagesize,
                                 @Field("time") String time, @Field("key") String key);

    //下载文件  下载文件的时候 不能用自定义返回类型 以防 retrofit解析他
    //防止下载大文件 用 Streaming  否则容易 oom
    @Streaming
    @GET("u/13990136?v=4")
    Call<ResponseBody> downLoadFileWithFieldUrl();

    /**
     * 上传文件
     *
     * @param dec  描述
     * @param body 文件
     * @return
     */
    @Multipart
    @POST("")
    Call<ResponseBody> upload(@Part("dec") RequestBody dec, @Part MultipartBody.Part body);
}
