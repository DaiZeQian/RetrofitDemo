package com.dzq.content;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dzq.base.BaseBarActivity;
import com.dzq.retrofitdemo.R;
import com.dzq.util.download.DownLoadListener;
import com.dzq.util.download.DownLoadUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2018/12/11.
 * 利用Retrofit 实现下载
 */

public class DownLoadView extends BaseBarActivity {

    private DownLoadUtils mDownLoadUtils;
    private static final String PICTURE_URL = "http://balabala/" +
            "image/8BBC6C00DF78476C98AD9CA482DE555F635.jpg";//瞎编的地址 就是为了要个名字
    private static final String TAG = "DownLoad";

    private Button btnStart;
    private TextView tvProgress;
    private ImageView ivPic;

    private void assignViews() {
        btnStart = (Button) findViewById(R.id.btn_start);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        ivPic = (ImageView) findViewById(R.id.iv_pic);
        btnStart.setOnClickListener(this);

    }


    @Override
    public void initData(@Nullable Bundle bundle) {
        RxPermissions rxPermission = new RxPermissions(DownLoadView.this);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            finish();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            finish();
                        }
                    }
                });

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_down_load;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        assignViews();

    }

    @Override
    public void doSomeThing() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                mDownLoadUtils = new DownLoadUtils();
                mDownLoadUtils.downLoadFlie(PICTURE_URL, new DownLoadListener() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "开始了");
                    }

                    @Override
                    public void onProgress(final int currentLength) {
                        Log.d(TAG, "下载进度 = " + currentLength);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvProgress.setText(currentLength + "%");
                            }
                        });
                    }

                    @Override
                    public void onFinish(final String localPath) {
                        Log.d(TAG, "下载地址" + localPath);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(DownLoadView.this).load(localPath).into(ivPic);
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "结束了");
                    }
                });
                break;
        }
    }


}
