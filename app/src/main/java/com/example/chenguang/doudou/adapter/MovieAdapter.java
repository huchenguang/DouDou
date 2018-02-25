package com.example.chenguang.doudou.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.MovieIndex;
import com.example.chenguang.doudou.ui.activity.MovieDetailActivity;
import com.example.chenguang.doudou.utils.ConstantValues;

import java.util.List;

/**
 * Created by chenguang on 2018/1/17.
 */

public class MovieAdapter extends BaseMultiItemQuickAdapter<MovieIndex.ItemInfoListBean,
        BaseViewHolder> {


    public MovieAdapter(List<MovieIndex.ItemInfoListBean> data) {
        super(data);
        this.addItemType(ConstantValues.DOUBAN_HOT, R.layout.item_movie_hot);
        this.addItemType(ConstantValues.DOUBAN_MOVIE_BANNER, R.layout.item_movie_banner);
        this.addItemType(ConstantValues.DOUBAN_MAY_INTERESTED, R.layout.item_movie_may_interested);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieIndex.ItemInfoListBean item) {
        switch (helper.getItemViewType()) {
            case ConstantValues.DOUBAN_HOT:
                bindHotData(helper, item);
                break;
            case ConstantValues.DOUBAN_MOVIE_BANNER:
                bindMovieBanner(helper, item);
                break;
            case ConstantValues.DOUBAN_MAY_INTERESTED:
                bindMayInterested(helper, item);
                break;
            default:
                break;
        }
    }

    /**
     * 重写此方法，解决recyclerView嵌套recyclerView条目显示不全
     *
     * @param layoutResId
     * @param parent
     * @return
     */
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, null);
    }

    private void bindHotData(BaseViewHolder helper, MovieIndex.ItemInfoListBean item) {
        RecyclerView recycler_view = helper.getView(R.id.recycler_view);
        recycler_view.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayoutManager
                .HORIZONTAL, false));
        HotAdapter hotAdapter;
        recycler_view.setAdapter(hotAdapter = new HotAdapter(item.itemContentList));
        hotAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MovieDetailActivity.startActivity(mContext, ((HotAdapter) adapter).getData().get
                        (position).id);
            }
        });

    }

    private void bindMovieBanner(final BaseViewHolder helper, final MovieIndex.ItemInfoListBean
            item) {

        ViewPager view_pager = helper.getView(R.id.view_pager);
        view_pager.setAdapter(new MoviePageAdapter(mContext, item));
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        helper.setText(R.id.tv_title, "豆瓣 Top250");
                        helper.setText(R.id.tv_all, "全部250部");
                        break;
                    case 1:
                        helper.setText(R.id.tv_title, "本周口碑榜");
                        helper.setText(R.id.tv_all, "全部10部");
                        break;
                    case 2:
                        helper.setText(R.id.tv_title, "票房榜");
                        helper.setText(R.id.tv_all, "全部10部");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void bindMayInterested(BaseViewHolder helper, MovieIndex.ItemInfoListBean itemList) {
        RecyclerView recycler_view = helper.getView(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        recycler_view.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration
                .VERTICAL));
        recycler_view.setAdapter(new InterestedAdapter(itemList.itemContentList));
    }

}
