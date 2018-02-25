package com.example.chenguang.doudou.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.AddressAdapter;
import com.example.chenguang.doudou.adapter.layoutmanager.StickyHeaderLayoutManager;
import com.example.chenguang.doudou.bean.Province;
import com.example.chenguang.doudou.ui.base.BaseActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;
import com.example.chenguang.doudou.ui.fragment.AbroadFragment;
import com.example.chenguang.doudou.ui.fragment.DomesticFragment;
import com.example.chenguang.doudou.utils.SortUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class LocationActivity extends BaseActivity implements View
        .OnClickListener {
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tl_location)
    TabLayout tl_location;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_empty)
    TextView tv_empty;

    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    @BindView(R.id.ll_root)
    LinearLayout ll_root;
    private AddressAdapter mAddressAdapter;
    private List<String> tb_names;
    private ArrayList<BaseFragment> mFragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_loaction;
    }


    @Override
    public void initData() {
        tb_names = Arrays.asList(getResources().getStringArray(R.array.tb_location));
        mFragments = new ArrayList<>();
        mFragments.add(DomesticFragment.newInstance());
        mFragments.add(AbroadFragment.newInstance());

    }

    @Override
    public void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewPager();
    }

    private void initViewPager() {
        view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        tl_location.setupWithViewPager(view_pager);

        et_search.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入变化前执行
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入文本发生变化执行
                search(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入文本后执行
            }
        });
    }

    private void search(CharSequence s) {
        recycler_view.setVisibility(View.VISIBLE);
        List<String> relCitys = new ArrayList<>();
        List<Province> provinces = ((DomesticFragment) mFragments.get(0)).getProvinces();
        if (null != provinces) {
            for (Province province : provinces) {
                List<Province.City> citys = province.citys;
                for (Province.City city : citys) {
                    if (city.citysName.contains(s)) {
                        relCitys.add(city.citysName);
                    }
                }
            }
        }
        if (relCitys.size() > 0) {
            tv_empty.setVisibility(View.INVISIBLE);
            view_pager.setVisibility(View.INVISIBLE);
            HashMap<String, List<String>> mp = SortUtil.getHashMap(relCitys);
            if (mAddressAdapter == null) {
                recycler_view.setLayoutManager(new StickyHeaderLayoutManager());
                recycler_view.setAdapter(mAddressAdapter = new AddressAdapter(mp));
            } else {
                mAddressAdapter.setData(mp);
                mAddressAdapter.notifyAllSectionsDataSetChanged();
            }
        } else {
            tv_empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        System.out.println("ll_root:" + ll_root.getTranslationY());
        if (ll_root.getTranslationY() != 0) {
            ll_root.animate().translationY(0);
            recycler_view.setVisibility(View.GONE);
            if (mAddressAdapter != null) {
                mAddressAdapter.clearAll();
            }
            tv_empty.setVisibility(View.INVISIBLE);
            view_pager.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
            case R.id.et_search:
                goToTop();
                break;
        }
    }

    private void goToTop() {
        ll_root.animate().translationY(-ll_top.getHeight());
        System.out.println("ll_root:" + ll_top.getTranslationY());
        recycler_view.setVisibility(View.VISIBLE);
    }
}
