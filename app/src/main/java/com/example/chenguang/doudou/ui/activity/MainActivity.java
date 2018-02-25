package com.example.chenguang.doudou.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.ui.base.BaseActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;
import com.example.chenguang.doudou.ui.fragment.FindFragment;
import com.example.chenguang.doudou.ui.fragment.HotFragment;
import com.example.chenguang.doudou.ui.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.rb_group)
    RadioGroup rb_group;
    @BindView(R.id.rb_hot)
    RadioButton rb_hot;
    List<BaseFragment> mFragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initData() {
        this.mFragments = new ArrayList<>();
        mFragments.add(HotFragment.newInstance());
        mFragments.add(FindFragment.newInstance());
        mFragments.add(TestFragment.newInstance());
    }

    public void initView() {
        view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        rb_group.setOnCheckedChangeListener(this);
        rb_hot.setChecked(true);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_hot:
                view_pager.setCurrentItem(0, false);
                break;
            case R.id.rb_find:
                view_pager.setCurrentItem(1, false);
                break;
            case R.id.rb_mine:
                view_pager.setCurrentItem(2, false);
                break;
        }
    }
}
