package com.example.chenguang.doudou.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.chenguang.doudou.DouBanApplication;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.component.AppComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenguang on 2018/1/7.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected View mRootView;
    private Unbinder unbinder;
    private boolean isInit = false;
    private boolean isLoaded = false;
    private View mProgressView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        isInit = true;
        setupComponent(DouBanApplication.getInstance().getAppComponent());
        initData();
        initView();
        if (getUserVisibleHint()) {
            lazyLoad();
        }
        return mRootView;
    }

    /**
     * 视图是否对用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    private void isCanLoadData() {
        if (getUserVisibleHint() && isInit && !isLoaded) {
            lazyLoad();
            isLoaded = true;
        }
    }

    /**
     * 如果想在用户可见的时候加载数据，重写此方法
     */
    protected void lazyLoad() {

    }

    public void setupComponent(AppComponent appComponent) {

    }

    public abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private View getDialog() {
        mProgressView = View.inflate(mActivity, R.layout.load_progress, null);
        return mProgressView;
    }

    public void showDialog() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ((ViewGroup) mRootView).addView(getDialog(), lp);
    }

    public void dismissDialog() {
        if (null != mProgressView) {
            ((ViewGroup) mRootView).removeView(mProgressView);
            mProgressView = null;
        }
    }
}
