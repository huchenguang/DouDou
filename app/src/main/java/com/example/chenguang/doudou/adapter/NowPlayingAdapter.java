package com.example.chenguang.doudou.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.MoviePlaying;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by chenguang on 2018/1/29.
 */

public class NowPlayingAdapter extends BaseQuickAdapter<MoviePlaying, BaseViewHolder> {

    private final DecimalFormat df;

    public NowPlayingAdapter(@Nullable List<MoviePlaying> data) {
        super(R.layout.item_now_playing, data);
        df = new DecimalFormat("0.0万人");
    }

    @Override
    protected void convert(BaseViewHolder helper, MoviePlaying item) {
        Glide.with(mContext)
                .load(item.coverImg)
                .into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_title, item.title);
        if (item.rate == 0) {
            helper.getView(R.id.rating_bar).setVisibility(View.GONE);
            helper.setText(R.id.tv_rate, "暂无评分");
        } else {
            helper.getView(R.id.rating_bar).setVisibility(View.VISIBLE);
            float rate = item.rate / 2;
            ((RatingBar) helper.getView(R.id.rating_bar)).setRating(rate);
            helper.setText(R.id.tv_rate, item.rate + "");
        }
        helper.setText(R.id.tv_director, String.format("导演：%s", item.director));

        helper.setText(R.id.tv_actors, String.format("主演：%s", item.actors));


        helper.setText(R.id.tv_buy, "购票");
        String str_num;
        if (item.seenNum >= 10000) {
            float num = item.seenNum / 10000.0f;
            str_num = df.format(num);
        } else {
            str_num = item.seenNum + "人";
        }
        helper.setText(R.id.tv_num, String.format("%s看过", str_num));

        helper.addOnClickListener(R.id.tv_buy);
    }
}
