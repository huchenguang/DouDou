package com.example.chenguang.doudou.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.ui.activity.LocationActivity;
import com.example.chenguang.doudou.ui.activity.SearchActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2018/1/7.
 */

public class HotFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    @BindView(R.id.tl_hot)
    TabLayout tl_hot;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private List<String> tb_names;
    private ArrayList<BaseFragment> mFragments;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initData() {
        tb_names = Arrays.asList(getResources().getStringArray(R.array.tb_hot));
        mFragments = new ArrayList<>();
        mFragments.add(NowPlayingFragment.newInstance("zhoukou"));
        mFragments.add(WillPlayingFragment.newInstance("zhoukou"));
    }

    @Override
    protected void initView() {
        tv_city.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        initViewPager();
    }

    private void initViewPager() {
        view_pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tb_names.get(position);
            }
        });
        tl_hot.setupWithViewPager(view_pager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city:
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_search:
                SearchActivity.startActivity(this.getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String address = data.getStringExtra("address");
//            PinyinUtil.getSortKey()
            tv_city.setText(address);
        }
    }
}
