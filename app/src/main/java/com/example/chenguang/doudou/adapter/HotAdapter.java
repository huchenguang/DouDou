package com.example.chenguang.doudou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.ItemContentListBean;

import java.util.List;

/**
 * Created by chenguang on 2018/1/18.
 */

public class HotAdapter extends BaseQuickAdapter<ItemContentListBean, BaseViewHolder> {
    public HotAdapter(@Nullable List<ItemContentListBean> data) {
        super(R.layout.item_hot, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemContentListBean item) {
        Glide.with(mContext)
                .load(item.cover)
                .into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.title);
        RatingBar rating_bar = helper.getView(R.id.rating_bar);
        rating_bar.setRating(item.rate / 2);
        helper.setText(R.id.tv_rate, item.rate + "");
    }
}
