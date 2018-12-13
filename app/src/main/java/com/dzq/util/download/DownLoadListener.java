package com.dzq.util.download;

/**
 * Created by admin on 2018/12/13.
 * 下载监听
 */

public interface DownLoadListener {

    void onStart();

    void onProgress(int currentLength);

    void onFinish(String localPath);

    void onFailure();
}
