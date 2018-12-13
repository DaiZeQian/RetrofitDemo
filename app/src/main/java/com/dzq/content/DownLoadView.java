package com.dzq.content;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dzq.base.BaseBarActivity;
import com.dzq.retrofitdemo.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2018/12/11.
 * 利用Retrofit 实现下载
 */

public class DownLoadView extends BaseBarActivity {
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
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
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

    }

    @Override
    public void doSomeThing() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
