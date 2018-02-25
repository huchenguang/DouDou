package com.example.chenguang.doudou.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.WillPlayingAdapter;
import com.example.chenguang.doudou.adapter.layoutmanager.StickyHeaderLayoutManager;
import com.example.chenguang.doudou.bean.MoviePlaying;
import com.example.chenguang.doudou.bean.WillPlayingSection;
import com.example.chenguang.doudou.ui.activity.MovieDetailActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;
import com.example.chenguang.doudou.ui.contract.WillPlayingContract;
import com.example.chenguang.doudou.ui.presenter.WillPlayingPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2018/2/9.
 */

public class WillPlayingFragment extends BaseFragment implements WillPlayingContract.View,
        RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rg_time)
    RadioGroup rg_time;
    @BindView(R.id.rg_sort)
    RadioGroup rg_sort;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;
    @BindView(R.id.rv_will)
    RecyclerView rv_will;

    @BindView(R.id.rb_all)
    RadioButton rb_all;
    @BindView(R.id.rb_jan)
    RadioButton rb_jan;
    @BindView(R.id.rb_feb)
    RadioButton rb_feb;
    @BindView(R.id.rb_mar)
    RadioButton rb_mar;

    private WillPlayingAdapter mAdapter;
    private String address;
    private WillPlayingPresenter mPresenter;

    public static WillPlayingFragment newInstance(String address) {
        WillPlayingFragment fragment = new WillPlayingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("address", address);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_will_playing;
    }

    @Override
    protected void initData() {
        address = getArguments().getString("address");
        mPresenter = new WillPlayingPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        rg_sort.setOnCheckedChangeListener(this);
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setColorSchemeResources(R.color.light_green);
        rv_will.setLayoutManager(new StickyHeaderLayoutManager());
        rv_will.setAdapter(mAdapter = new WillPlayingAdapter(mActivity));
        //设置监听器
        mAdapter.setOnItemClickListener(new WillPlayingAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(MoviePlaying item) {
                MovieDetailActivity.startActivity(mActivity, item.id);
            }
        });
        mAdapter.setOnItemChildClickListener(new WillPlayingAdapter.OnItemChildClickListener() {
            @Override
            public void OnItemClick(MoviePlaying item) {
                //TODO 想看的逻辑处理
                ToastUtils.showShort("想看" + item.title);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        mPresenter.getWillPlayingMovie(address);
        refresh_layout.setRefreshing(true);
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
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void showWillPlayingMovie(List<WillPlayingSection> sections) {
        mAdapter.addNewData(sections);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_time:
                //TODO 按时间排序

                break;
            case R.id.rb_hot:
                //TODO 按热度排序
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getWillPlayingMovie(address);
    }
}
