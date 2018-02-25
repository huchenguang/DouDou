package com.example.chenguang.doudou.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.ui.base.BaseActivity;

public class PreviewDetailActivity extends BaseActivity {

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, PreviewDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_detail;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}
