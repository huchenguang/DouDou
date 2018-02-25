package com.example.chenguang.doudou.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;

import java.util.List;

/**
 * Created by chenguang on 2018/1/22.
 */

public class HotCityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public HotCityAdapter(@Nullable List<String> data) {
        super(R.layout.item_hot_city, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_hot_city, item);
    }
}
