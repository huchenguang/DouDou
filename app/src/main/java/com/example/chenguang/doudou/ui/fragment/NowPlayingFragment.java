package com.example.chenguang.doudou.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.NowPlayingAdapter;
import com.example.chenguang.doudou.bean.MoviePlaying;
import com.example.chenguang.doudou.ui.activity.MovieDetailActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;
import com.example.chenguang.doudou.ui.contract.NowPlayingContract;
import com.example.chenguang.doudou.ui.presenter.NowPlayingFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2018/1/29.
 */

public class NowPlayingFragment extends BaseFragment implements NowPlayingContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private String address;
    private NowPlayingAdapter mAdapter;
    private NowPlayingFragmentPresenter mPresenter;

    public static NowPlayingFragment newInstance(String address) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address", address);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_now_playing;
    }

    @Override
    protected void initData() {
        address = getArguments().getString("address");
        mPresenter = new NowPlayingFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setColorSchemeResources(R.color.light_green);
        recycler_view.setLayoutManager(new LinearLayoutManager(mActivity));
        recycler_view.addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL));
        recycler_view.setAdapter(mAdapter = new NowPlayingAdapter(new ArrayList<MoviePlaying>()));
        refresh_layout.setRefreshing(true);//自动刷新
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String id = ((MoviePlaying) adapter.getData().get(position)).id;
                MovieDetailActivity.startActivity(mActivity, id);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO 调到购票详情
                ToastUtils.showShort("购买" + ((MoviePlaying) adapter.getData().get(position)).title);

            }
        });
        mPresenter.getNowPlayingMovie(address);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void complete() {
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void error(String e) {
        ToastUtils.showShort("网络好像出错了");
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void showNowPlayingMovie(List<MoviePlaying> movies) {
        if (refresh_layout.isRefreshing()) {
            mAdapter.getData().clear();
        }
        if (movies != null) {
            mAdapter.addData(movies);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getNowPlayingMovie(address);
    }
}
