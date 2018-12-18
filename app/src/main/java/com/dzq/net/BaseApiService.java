package com.dzq.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by admin on 2018/12/17.
 */

public interface BaseApiService {

    /**
     * get
     *
     * @param url  ....
     * @param maps 参数
     * @return
     */
    @GET("{url}")
    Observable<BaseRespose<Object>> get(@Path("url") String url, @QueryMap Map<String, String> maps);

    /**
     * @return
     */
    @POST("{url}")
    Observable<BaseRespose<Object>> postByMap(@Path("url") String url, @QueryMap Map<String, String> maps);

    /**
     * post 上传Json
     *
     * @param url
     * @param jsonStr
     * @return
     */
    @POST("{url]")
    Observable<BaseRespose<Object>> postByJson(@Path("url") String url, @Body RequestBody jsonStr);

    /**
     * 文件上传 //后期可区分  图片/视频/文件等
     *
     * @param url
     * @param body
     * @return
     */
    @Multipart
    @POST("{url}")
    Observable<BaseRespose> upload(@Path("url") String url, @Part RequestBody body);

    /**
     * 类似于表单 可以文件文字一起上传
     *
     * @param url
     * @param headers     key
     * @param description 描述
     * @param maps        key：文件
     * @return
     */
    @POST("{url}")
    Call<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);


    /**
     * 文件下载
     *
     * @param loadUrl Streaming  下载大文件最好加上这个 要不容易oom
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downLoad(@Url String loadUrl);

}
