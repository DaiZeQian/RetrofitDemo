package com.dzq.util.download;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.dzq.config.Config;
import com.dzq.retrofit.RetrofitUrL;
import com.dzq.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2018/12/13.
 */

public class DownLoadUtils {

    private static final String Tag = "DownLoadUtils";
    private static final String PATH_LOAD_PIC = Environment.getExternalStorageDirectory() + "/DownloadFile";
    //
    protected RetrofitUrL mApi;
    private Call<ResponseBody> mCall;
    private File mFile;
    private Thread mThread;
    private String localPath;//下载路径


    public DownLoadUtils() {
        if (mApi == null) {
            //初始化网络请求接口
            mApi = DownLoadNetManager.getIntence().buildRetrofit(Config.GITHUB_IMG_URL)
                    .createService(RetrofitUrL.class);
        }
    }


    public void downLoadFlie(String url, final DownLoadListener downLoadListener) {
        String name = url;
        if (FileUtils.createOrExistsDir(PATH_LOAD_PIC)) {
            int i = name.lastIndexOf('/');
            if (i != -1) {
                name = name.substring(i);
                localPath = PATH_LOAD_PIC + name;
            }
        }

        if (TextUtils.isEmpty(localPath)) {
            Log.e(Tag, "文件存储路径为null");
            return;
        }
        //建立一个文件
        mFile = new File(localPath);
        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile)) {//如果文件已存在那就不重复下载了 根据文件名命名的文件
            if (mApi == null) {
                Log.e(Tag, "下载地址不存在");
            }
            mCall = mApi.downLoadFileWithFieldUrl();

            mCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                    //下载文件放在子线程
                    mThread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //保存到本地
                            writeFile2Disk(response, mFile, downLoadListener);
                        }
                    };
                    mThread.start();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    downLoadListener.onFailure(); //下载失败

                }
            });
        } else {
            downLoadListener.onFinish(localPath);
        }
    }


    //将下载的文件写入本地存储
    private void writeFile2Disk(Response<ResponseBody> response, File file, DownLoadListener downloadListener) {
        downloadListener.onStart();
        long currentLength = 0;
        OutputStream os = null;

        InputStream is = response.body().byteStream(); //获取下载输入流
        long totalLength = response.body().contentLength();

        try {
            os = new FileOutputStream(file); //输出流
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e(Tag, "当前进度: " + currentLength);
                //计算当前下载百分比，并经由回调传出
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
                //当百分比为100时下载结束，调用结束回调，并传出下载后的本地路径
                if ((int) (100 * currentLength / totalLength) == 100) {
                    downloadListener.onFinish(localPath); //下载完成
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close(); //关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close(); //关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
