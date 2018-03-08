package com.example.chenguang.doudou.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.MovieAdapter;
import com.example.chenguang.doudou.bean.MovieIndex;
import com.example.chenguang.doudou.component.AppComponent;
import com.example.chenguang.doudou.component.DaggerFindComponent;
import com.example.chenguang.doudou.ui.base.BaseFragment;
import com.example.chenguang.doudou.ui.contract.MovieContract;
import com.example.chenguang.doudou.ui.presenter.MoviePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by chenguang on 2018/1/17.
 */

public class MovieFragment extends BaseFragment implements MovieContract.View, BaseQuickAdapter
        .RequestLoadMoreListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Inject
    MoviePresenter mPresenter;

    private MovieAdapter mAdapter;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager
                .VERTICAL, false));
        recycler_view.addItemDecoration(new DividerItemDecoration(mActivity,
                DividerItemDecoration.VERTICAL));
        recycler_view.setAdapter(mAdapter = new MovieAdapter(new ArrayList<MovieIndex
                .ItemInfoListBean>()));
        mAdapter.setOnLoadMoreListener(this, recycler_view);
    }

    @Override
    protected void lazyLoad() {
        this.showDialog();
        mPresenter.getMovieIndex();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void complete() {

    }

    @Override
    public void error(String e) {

    }

    @Override
    public void showMovieIndex(List<MovieIndex.ItemInfoListBean> infoListBean) {
        mAdapter.addData(infoListBean);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreInterestedMovie(List<MovieIndex.ItemInfoListBean> infoListBean) {
        if (infoListBean != null) {
            page++;
            mAdapter.addData(infoListBean);
            mAdapter.notifyDataSetChanged();
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    int page = 2;

    @Override
    public void onLoadMoreRequested() {
        //从第二页数据
        mPresenter.getMoreInterestedMovie(page);
    }
}
