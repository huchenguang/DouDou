package com.example.chenguang.doudou.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;

import java.util.List;

/**
 * Created by chenguang on 2018/1/15.
 */

public class HotSearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final int[] ranks;

    public HotSearchAdapter(@Nullable List<String> data) {
        super(R.layout.item_hot_search, data);
        ranks = new int[]{
                R.mipmap.ic_hot_1,
                R.mipmap.ic_hot_2,
                R.mipmap.ic_hot_3,
                R.mipmap.ic_hot_4,
                R.mipmap.ic_hot_5,
                R.mipmap.ic_hot_6,
                R.mipmap.ic_hot_7,
                R.mipmap.ic_hot_8,
                R.mipmap.ic_hot_9,
                R.mipmap.ic_hot_10,
                R.mipmap.ic_hot_11,
                R.mipmap.ic_hot_12,
                R.mipmap.ic_hot_13,
                R.mipmap.ic_hot_14,
                R.mipmap.ic_hot_15,
        };
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setImageResource(R.id.iv_num, ranks[helper.getLayoutPosition()]);
        helper.setText(R.id.tv_content, item);
    }
}
