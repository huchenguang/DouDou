package com.example.chenguang.doudou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.SearchDetail;

import java.util.List;

/**
 * Created by chenguang on 2018/1/16.
 */

public class SearchDetailAdapter extends BaseQuickAdapter<SearchDetail.SearchMovie,
        BaseViewHolder> {
    public SearchDetailAdapter(@Nullable List<SearchDetail.SearchMovie> data) {
        super(R.layout.item_search_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDetail.SearchMovie item) {
        helper.setText(R.id.tv_title, item.title);
        if (item.rating.average == 0.0) {
            helper.setText(R.id.tv_rate, "暂无评分");
        } else {
            RatingBar rating_bar = helper.getView(R.id.rating_bar);
            rating_bar.setRating(item.rating.average / 2);
        }
        if (null != item.casts && item.casts.size() > 0) {
            String actors = "";
            for (SearchDetail.Cast cast : item.casts) {
                actors += cast.name + " / ";
            }
            helper.setText(R.id.tv_actor, actors.substring(0, actors.lastIndexOf("/")).trim());
        }
        if (null != item.directors && item.directors.size() > 0) {
            String directorName = "";
            for (SearchDetail.Director director : item.directors) {
                directorName += director.name + " / ";
            }
            helper.setText(R.id.tv_director, directorName.substring(0, directorName.lastIndexOf
                    ("/")).trim());
        }
        Glide.with(mContext)
                .load(item.images.small)
                .into((ImageView) helper.getView(R.id.iv_poster));

    }
}
