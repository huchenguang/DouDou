package com.example.chenguang.doudou.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.ui.base.BaseActivity;

public class StageDetailActivity extends BaseActivity {
    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, StageDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_stage_detail;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}
