package com.example.chenguang.doudou.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.example.chenguang.doudou.DouBanApplication;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.component.AppComponent;
import com.example.chenguang.doudou.utils.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenguang on 2018/1/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private View mProgressView;
    private View mContentView;
    protected Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        this.mContext = this;
        mContentView = findViewById(android.R.id.content);
        unbinder = ButterKnife.bind(this);
        setStatusBarColor();
        setupComponent(DouBanApplication.getInstance().getAppComponent());
        initData();
        initView();
    }

    protected void setStatusBarColor() {
        StatusBarUtils.setColorBar(this, this.getResources().getColor(R.color.colorPrimary), true);
    }


    public void setupComponent(AppComponent appComponent) {

    }

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private View getDialog() {
        mProgressView = View.inflate(this, R.layout.load_progress, null);
        return mProgressView;
    }

    public void showDialog() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ((ViewGroup) mContentView).addView(getDialog(), lp);
    }

    public void dismissDialog() {
        if (null != mProgressView) {
            ((ViewGroup) mContentView).removeView(mProgressView);
            mProgressView = null;
        }
    }

    public void hideSoftInput(SearchView searchView) {
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View v = this.getCurrentFocus();
            if (v == null) {
                return;
            }

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            searchView.clearFocus();
        }
    }
}
