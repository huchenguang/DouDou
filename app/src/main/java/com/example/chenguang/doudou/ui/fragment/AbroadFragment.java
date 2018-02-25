package com.example.chenguang.doudou.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.AddressAdapter;
import com.example.chenguang.doudou.adapter.layoutmanager.StickyHeaderLayoutManager;
import com.example.chenguang.doudou.bean.Province;
import com.example.chenguang.doudou.ui.activity.SelectionActivity;
import com.example.chenguang.doudou.ui.base.BaseFragment;
import com.example.chenguang.doudou.utils.RxUtil;
import com.example.chenguang.doudou.utils.SortUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by chenguang on 2018/1/8.
 */

public class AbroadFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private AddressAdapter mAdapter;
    private List<Province> mProvinces;

    public static AbroadFragment newInstance() {
        return new AbroadFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_domestic;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        recycler_view.setLayoutManager(new StickyHeaderLayoutManager());
        mAdapter = new AddressAdapter(new HashMap<String, List<String>>());
        recycler_view.setAdapter(mAdapter);
        mAdapter.setOnItemViewClickListener(new AddressAdapter.OnItemViewClickListener() {
            @Override
            public void onClick(int sectionIndex, int itemIndex) {
                String province = mAdapter.getData().get(mAdapter.getKeys().get(sectionIndex)).get
                        (itemIndex);
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
                Intent intent = new Intent();
                intent.putExtra("address", province);
                mActivity.setResult(1, intent);
                mActivity.finish();
            }
        });
        getAllAbroadProvinces();
    }

    public void getAllAbroadProvinces() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> ob) throws Exception {
                try {
                    InputStream in = getContext().getAssets().open("abroadCities.txt");
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                    }
                    String json = new String(out.toByteArray());
                    ob.onNext(json);
                    ob.onComplete();
                    in.close();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).map(new Function<String, HashMap<String, List<String>>>() {

            @Override
            public HashMap<String, List<String>> apply(String s) throws Exception {
                HashMap<String, List<String>> province_map;
                List<Province> provinces;
                Gson gson = new Gson();
                provinces = gson.fromJson(s, new TypeToken<List<Province>>() {
                }.getType());
                //获取gson填充后的实体
                mProvinces = provinces;

                List<String> allProvinces = new ArrayList<>();
                for (Province province : provinces) {
                    allProvinces.add(province.provinceName);
                }
                province_map = SortUtil.getHashMap
                        (allProvinces);
                return province_map;
            }
        }).compose(RxUtil.<HashMap<String, List<String>>>switchSchedulers())
                .subscribe(new Consumer<HashMap<String, List<String>>>() {
                    @Override
                    public void accept(HashMap<String, List<String>> mp) throws Exception {
                        showAllAbroadProvinces(mp);
                    }
                });
    }

    public void showAllAbroadProvinces(HashMap<String, List<String>> mp) {
        mAdapter.setData(mp);
        mAdapter.notifyAllSectionsDataSetChanged();
    }
}
