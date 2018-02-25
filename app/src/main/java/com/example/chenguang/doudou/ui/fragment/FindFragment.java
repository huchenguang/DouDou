package com.example.chenguang.doudou.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.ui.activity.SearchActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenguang on 2018/1/17.
 */

public class FindFragment extends BaseFragment {
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.tl_find)
    TabLayout tl_find;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private List<String> tb_names;
    private ArrayList<BaseFragment> mFragments;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }


    @Override
    protected void initData() {
        tb_names = Arrays.asList(getResources().getStringArray(R.array.tb_find));
        mFragments = new ArrayList<>();
        mFragments.add(MovieFragment.newInstance());
        mFragments.add(TestFragment.newInstance());
    }

    @Override
    protected void initView() {
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(mActivity);
            }
        });
//        initViewPager();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 经测试，当加载FindFragment里嵌套的两个fragment时，
     * 第一个fragment(也就是MovieFragment)的setUserVisibleHint的
     * isVisibleToUser传的是true。这就影响了我们的懒加载。
     * 故让FindFragment懒加载来设置viewPager的adapter
     */
    @Override
    protected void lazyLoad() {
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
        tl_find.setupWithViewPager(view_pager);
    }
}
