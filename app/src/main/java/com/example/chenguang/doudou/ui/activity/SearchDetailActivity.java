package com.example.chenguang.doudou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.SearchDetailAdapter;
import com.example.chenguang.doudou.bean.SearchDetail;
import com.example.chenguang.doudou.component.AppComponent;
import com.example.chenguang.doudou.component.DaggerSearchComponent;
import com.example.chenguang.doudou.ui.base.BaseActivity;
import com.example.chenguang.doudou.ui.contract.SearchDetailContract;
import com.example.chenguang.doudou.ui.presenter.SearchDetailPresenter;
import com.example.chenguang.doudou.widget.MySearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchDetailActivity extends BaseActivity implements SearchDetailContract.View, View
        .OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.sv_search)
    MySearchView sv_search;

    @Inject
    SearchDetailPresenter mPresenter;

    int page = 1;
    private String key;
    private SearchDetailAdapter mAdapter;

    public static void startActivity(Context context, String key) {
        Intent intent = new Intent(context, SearchDetailActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_detail;
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerSearchComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initData() {
        key = getIntent().getStringExtra("key");
        mPresenter.attachView(this);
        mAdapter = new SearchDetailAdapter(new ArrayList<SearchDetail.SearchMovie>());
    }

    @Override
    public void initView() {
        initSearchView();
        initRecyclerView();
    }


    private void initSearchView() {
        //设置hintFontSize
        sv_search.setHintTextSize(14);
        //设置搜索图标
        sv_search.setCursorIcon(R.drawable.drawable_cursor_location);
        sv_search.setOnQueryTextListener(new android.support.v7.widget.SearchView
                .OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 1;
                mPresenter.getSearchResult(query, page);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRecyclerView() {
        tv_cancel.setOnClickListener(this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL));
        recycler_view.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO 跳转到电影详情页
            }
        });
        mAdapter.setOnLoadMoreListener(this, recycler_view);
        //TODO 将进度条的显示放到presenter里
        this.showDialog();
        mPresenter.getSearchResult(key, page);
    }

    @Override
    public void complete() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void error(String e) {
        mAdapter.loadMoreFail();
    }

    @Override
    public void showSearchResult(List<SearchDetail.SearchMovie> searchDetails) {
        if (page == 1) {
            mAdapter.getData().clear();
        }
        if (null != searchDetails) {
            mAdapter.addData(searchDetails);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getSearchResult(key, page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
