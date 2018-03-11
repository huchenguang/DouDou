package com.example.chenguang.doudou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.HotSearchAdapter;
import com.example.chenguang.doudou.adapter.SearchHistoryAdapter;
import com.example.chenguang.doudou.adapter.layoutmanager.CustomLinearLayoutManager;
import com.example.chenguang.doudou.component.AppComponent;
import com.example.chenguang.doudou.component.DaggerSearchComponent;
import com.example.chenguang.doudou.ui.base.BaseActivity;
import com.example.chenguang.doudou.ui.contract.SearchContract;
import com.example.chenguang.doudou.ui.presenter.SearchPresenter;
import com.example.chenguang.doudou.utils.StatusBarUtils;
import com.example.chenguang.doudou.widget.MySearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements SearchContract.View, View
        .OnClickListener {
    /*@BindView(R.id.et_search)
    EditText et_search;*/
    @BindView(R.id.sv_search)
    MySearchView sv_search;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_clean)
    TextView tv_clean;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    @BindView(R.id.gv_history)
    GridView gv_history;

    @BindView(R.id.rl_history)
    RelativeLayout rl_history;


    private HotSearchAdapter mHotSearchAdapter;
    private SearchHistoryAdapter mSearchHistoryAdapter;

    @Inject
    SearchPresenter mPresenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setStatusBarColor() {
        StatusBarUtils.setColorBar(this, this.getResources().getColor(R.color.light_green), false);
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
//        this.mPresenter = new SearchPresenter();
        mPresenter.attachView(this);

    }

    @Override
    public void initView() {
        initSearchView();

        initSearchHistory();

        initHotSearch();

        tv_cancel.setOnClickListener(this);
        tv_clean.setOnClickListener(this);

        //从后台获取搜索
        mPresenter.getHotSearch();
    }

    private void initHotSearch() {
        recycler_view.setLayoutManager(new CustomLinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false, false));
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL));
        recycler_view.setAdapter(mHotSearchAdapter = new HotSearchAdapter(new ArrayList<String>()));
        mHotSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO 点击事件，调到电影详情页

            }
        });
    }

    private void initSearchView() {
        //设置hintFontSize
        sv_search.setHintTextSize(14);
        //设置搜索光标
        sv_search.setCursorIcon(R.drawable.drawable_cursor_location);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    mPresenter.addSearchHistory(query);
                    SearchDetailActivity.startActivity(SearchActivity.this, query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initSearchHistory() {
        gv_history.setAdapter(mSearchHistoryAdapter = new SearchHistoryAdapter(new
                ArrayList<String>()));
        mSearchHistoryAdapter.setOnItemClickListener(new SearchHistoryAdapter.OnItemClickListener
                () {
            @Override
            public void onItemClick(String history) {
                SearchDetailActivity.startActivity(SearchActivity.this, history);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getSearchHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clean:
                mPresenter.clearHistories();
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    public void complete() {

    }

    @Override
    public void error(String e) {

    }

    @Override
    public void showSearchHistory(List<String> histories) {
        if (histories.size() == 0) {
            rl_history.setVisibility(View.GONE);
        } else {
            rl_history.setVisibility(View.VISIBLE);
        }
        mSearchHistoryAdapter.setData(histories);
        mSearchHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHotSearch(List<String> hot_search) {
        mHotSearchAdapter.setNewData(hot_search);
    }


}
