package com.example.chenguang.doudou.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.chenguang.doudou.DouBanApplication;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.MultiAddressAdapter;
import com.example.chenguang.doudou.adapter.layoutmanager.StickyHeaderLayoutManager;
import com.example.chenguang.doudou.bean.MySection;
import com.example.chenguang.doudou.bean.Province;
import com.example.chenguang.doudou.component.AppComponent;
import com.example.chenguang.doudou.component.DaggerLocationComponent;
import com.example.chenguang.doudou.ui.activity.SelectionActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;
import com.example.chenguang.doudou.ui.contract.LocationFragmentContract;
import com.example.chenguang.doudou.ui.presenter.LocationFragmentPresenter;
import com.pinyinsearch.model.PinyinUnit;
import com.pinyinsearch.util.PinyinUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by chenguang on 2018/1/8.
 */

public class DomesticFragment extends BaseFragment implements LocationFragmentContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private List<Province> mProvinces;
    private MultiAddressAdapter mAdapter;
    @Inject
    LocationFragmentPresenter mPresenter;
    private LocationClient mLocationClient;

    public static DomesticFragment newInstance() {
        return new DomesticFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_domestic;
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerLocationComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {

        recycler_view.setLayoutManager(new StickyHeaderLayoutManager());
        mAdapter = new MultiAddressAdapter(mActivity, new ArrayList<MySection>());
        recycler_view.setAdapter(mAdapter);

        mPresenter.getAllProvinces();

        mAdapter.setOnItemViewClickListener(new MultiAddressAdapter.OnItemViewClickListener() {
            @Override
            public void onClick(String address) {
                String province = address;
                List<Province.City> citys = null;
                for (Province mProvince : mProvinces) {
                    if (mProvince.provinceName.equals(province)) {
                        citys = mProvince.citys;
                    }
                }
                if (citys != null && citys.size() > 1) {
                    ArrayList<String> myCitys = new ArrayList<>();
                    for (Province.City city : citys) {
                        myCitys.add(city.citysName);
                    }
                    SelectionActivity.startActivity(mActivity, myCitys);
                    return;
                }
                //返回数据给上一个activity
                Intent intent = new Intent();
                //处理汉字为拼音
                List<PinyinUnit> units = new ArrayList<>();
                PinyinUtil.chineseStringToPinyinUnit(address, units);
                String pinyin = PinyinUtil.getSortKey(units);
                if (pinyin.indexOf(" ") != -1) {
                    pinyin = pinyin.substring(0, pinyin.indexOf(" "));
                }
                intent.putExtra("address", address);
                intent.putExtra("pinyin", pinyin);
                mActivity.setResult(1, intent);
                mActivity.finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public List<Province> getProvinces() {
        return mProvinces;
    }

    @Override
    public void complete() {

    }

    @Override
    public void error(String e) {

    }

    @Override
    public void showHotCities(List<String> hotCities) {
        MySection section = new MySection(MultiAddressAdapter.DEFAULT_USER_HEADER_TYPE,
                "热门城市");
        MySection.Item item = section.new Item(MultiAddressAdapter.USER_ITEM_TYPE_HOT_CITY, "");
        item.titles = hotCities;
        section.items.add(item);
        mAdapter.getData().add(0, section);
        mAdapter.notifySectionInserted(0);
        recycler_view.scrollToPosition(0);
        getLocation();
    }

    @Override
    public void showAllProvinces(List<MySection> provinces) {
        mAdapter.setNewData(provinces);
        mPresenter.getHotCities();
    }

    MyLocationListener myLocationListener;

    private void getLocation() {
        mLocationClient = new LocationClient(DouBanApplication.getInstance());
        mLocationClient.registerLocationListener(myLocationListener = new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(1000); //可选，设置发起定位请求的间隔，int类型，单位ms
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//低功耗
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public void acceptAllProvinces(List<Province> provinces) {
        this.mProvinces = provinces;
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                String city = location.getCity();    //获取城市
                if (TextUtils.isEmpty(city)) {
                    city = "定位中...";
                } else {
                    if (city.contains("市")) {
                        city = city.replace("市", "");
                    }
                    mLocationClient.stop();
                }
                if (mAdapter.getData().get(0).items.get(0).type != MultiAddressAdapter
                        .USER_ITEM_TYPE_GPS) {
                    MySection section = new MySection(MultiAddressAdapter.DEFAULT_USER_HEADER_TYPE,
                            "GPS定位城市");
                    section.items.add(section.new Item(MultiAddressAdapter.USER_ITEM_TYPE_GPS,
                            city));
                    mAdapter.getData().add(0, section);
                    mAdapter.notifySectionInserted(0);
                    recycler_view.smoothScrollToPosition(0);
                } else {
                    mAdapter.getData().get(0).items.get(0).title = city;
                    mAdapter.notifyAllSectionsDataSetChanged();
                }

            }
        }
    }
}
