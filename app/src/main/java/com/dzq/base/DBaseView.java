package com.dzq.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by admin on 2018/10/22.
 */

public interface DBaseView extends View.OnClickListener {

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的数据
     */
    void initData(@Nullable Bundle bundle);

    /**
     * 绑定布局
     *
     * @return int 布局id
     */
    int bindLayout();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     * @param contentView
     */
    void initView(final Bundle savedInstanceState, final View contentView);

    /**
     * 业务操作
     */
    void doSomeThing();

    /**
     * 视图点击事件
     */
    void onWidgetClick(View view);
}
