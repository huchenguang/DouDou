package com.example.chenguang.doudou.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.ItemContentListBean;
import com.example.chenguang.doudou.bean.MovieIndex;

import java.util.List;

/**
 * Created by chenguang on 2018/1/18.
 */

public class MoviePageAdapter extends PagerAdapter {

    private MovieIndex.ItemInfoListBean item;
    private Context mContext;

    public MoviePageAdapter(Context context, MovieIndex.ItemInfoListBean item) {
        this.item = item;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //前4個top25o,中间4个口碑，后面4个票房榜
        List<ItemContentListBean> contentList = null;
        if (position + 4 <= item.itemContentList.size() - 1) {
            contentList = item.itemContentList.subList
                    (position, position + 4);
        }

        View view = View.inflate(mContext, R.layout.pager_movie, null);
        RecyclerView recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(new ContentAdapter(contentList, position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    class ContentAdapter extends BaseQuickAdapter<ItemContentListBean,
            BaseViewHolder> {
        private int mPosition;

        public ContentAdapter(@Nullable List<ItemContentListBean>
                                      data, int position) {
            super(R.layout.item_pager_movie_content, data);
            this.mPosition = position;
        }

        @Override
        protected void convert(BaseViewHolder helper, ItemContentListBean
                item) {
            helper.setText(R.id.tv_num, helper.getLayoutPosition() + 1 + "");
            helper.setText(R.id.tv_title, item.title);
            RatingBar rating_bar = helper.getView(R.id.rating_bar);
            rating_bar.setRating(item.rate / 2);
            helper.setText(R.id.tv_rate, item.rate + "");
            helper.setText(R.id.tv_rate_num, item.ratings_count + "");
            if (mPosition == 1) {
                helper.getView(R.id.iv_up).setVisibility(View.VISIBLE);
            }

            Glide.with(mContext)
                    .load(item.cover)
                    .into((ImageView) helper.getView(R.id.iv_cover));
        }
    }


}
