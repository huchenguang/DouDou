package com.example.chenguang.doudou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenguang.doudou.ui.base.BaseFragment;

/**
 * Created by chenguang on 2018/1/5.
 */

public class TestFragment extends BaseFragment {
    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        TextView tv = new TextView(container.getContext());
        tv.setText("你好");
        return tv;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }
}
