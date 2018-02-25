package com.example.chenguang.doudou.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.ItemContentListBean;

import java.util.List;

/**
 * Created by chenguang on 2018/1/18.
 */

public class InterestedAdapter extends BaseQuickAdapter<ItemContentListBean,
        BaseViewHolder> {
    private static final int MOVIE = 0xf0f1;
    private static final int REVIEW = 0xf0f2;

    public InterestedAdapter(@Nullable List<ItemContentListBean> data) {
        super(data);
        this.setMultiTypeDelegate(new MultiTypeDelegate<ItemContentListBean>() {
            @Override
            protected int getItemType(ItemContentListBean item) {
                if (!TextUtils.isEmpty(item.review_id)) {
                    return REVIEW;
                }
                return MOVIE;
            }
        });
        getMultiTypeDelegate().registerItemType(REVIEW, R.layout.item_movie_review)
                .registerItemType(MOVIE, R.layout.item_movie_interested);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemContentListBean item) {
        switch (helper.getItemViewType()) {
            case MOVIE:
                bindMovieData(helper, item);
                break;
            case REVIEW:
                bindReviewData(helper, item);
                break;
        }

    }

    private void bindMovieData(BaseViewHolder helper, ItemContentListBean item) {
        helper.setText(R.id.tv_title, item.title);
        RatingBar rating_bar = helper.getView(R.id.rating_bar);
        rating_bar.setRating(item.rate / 2);
        helper.setText(R.id.tv_rate, item.rate + "");
        String about = "";
        about += item.year + " / ";
        about += item.countries + " / ";
        if (null != item.directors) {
            about += item.directors.get(0) + " / ";
        }
        about += item.casts.get(0) + " / ";
        about += item.casts.get(1) + " / ";
        helper.setText(R.id.tv_about, about);
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        if (iv_cover != null && !TextUtils.isEmpty(item.cover)) {
            Glide.with(mContext)
                    .load(item.cover)
                    .into(iv_cover);
        }
    }

    private void bindReviewData(BaseViewHolder helper, ItemContentListBean itemContent) {
        helper.setText(R.id.tv_title, itemContent.comment);
        helper.setText(R.id.tv_comment, itemContent.comment);
        Glide.with(mContext)
                .load(itemContent.cover)
                .into((ImageView) helper.getView(R.id.iv_cover));
        String who = "";
        who += itemContent.author + " 评论 ";
        who += itemContent.title;
        helper.setText(R.id.tv_who, who);
        Glide.with(mContext)
                .load(itemContent.author_icon)
                .into((ImageView) helper.getView(R.id.iv_author));
    }
}
