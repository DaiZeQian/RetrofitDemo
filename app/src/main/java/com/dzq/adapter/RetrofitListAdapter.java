package com.dzq.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dzq.bean.JokerBean;
import com.dzq.retrofitdemo.R;

/**
 * Created by admin on 2018/12/10.
 */

public class RetrofitListAdapter extends BaseQuickAdapter<JokerBean.ResultBean.DataBean, BaseViewHolder> {

    public RetrofitListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokerBean.ResultBean.DataBean item) {
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_time, item.getUpdatetime());
    }
}
