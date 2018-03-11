package com.example.chenguang.doudou.ui.presenter;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.MultiAddressAdapter;
import com.example.chenguang.doudou.base.RxPresenter;
import com.example.chenguang.doudou.bean.MySection;
import com.example.chenguang.doudou.bean.Province;
import com.example.chenguang.doudou.ui.contract.LocationFragmentContract;
import com.example.chenguang.doudou.ui.fragment.DomesticFragment;
import com.example.chenguang.doudou.utils.RxUtil;
import com.example.chenguang.doudou.utils.SortUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by chenguang on 2018/1/16.
 */

public class LocationFragmentPresenter extends RxPresenter<DomesticFragment> implements
        LocationFragmentContract
                .Presenter<DomesticFragment> {


    @Inject
    public LocationFragmentPresenter() {

    }


    @Override
    public void getHotCities() {
        List<String> hot_cities;
        hot_cities = Arrays.asList(mView.getContext().getResources().getStringArray(R.array
                .hot_cities));
        mView.showHotCities(hot_cities);
    }


    @Override
    public void getAllProvinces() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> ob) throws Exception {
                try {
                    InputStream in = mView.getContext().getAssets().open("city.txt");
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int len;
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
        }).map(new Function<String, List<MySection>>() {

            @Override
            public List<MySection> apply(String s) throws Exception {
                HashMap<String, List<String>> province_map;
                List<Province> provinces;
                System.out.println(s);
                Gson gson = new Gson();
                provinces = gson.fromJson(s, new TypeToken<List<Province>>() {
                }.getType());
                //获取gson填充后的实体
                mView.acceptAllProvinces(provinces);

                List<String> allProvinces = new ArrayList<>();
                for (Province province : provinces) {
                    allProvinces.add(province.provinceName);
                }
                province_map = SortUtil.getHashMap
                        (allProvinces);
                List<MySection> sections = new ArrayList<>();
                List<String> keys = new ArrayList<>();
                for (String s1 : province_map.keySet()) {
                    keys.add(s1);
                }
                //按拼音首字母排序
                keys = SortUtil.sortByChineseName(keys);
                for (String key : keys) {
                    MySection section = new MySection(MultiAddressAdapter
                            .DEFAULT_USER_HEADER_TYPE, key);
                    List<String> strs = province_map.get(key);
                    for (String str : strs) {
                        section.items.add(section.new Item(MultiAddressAdapter
                                .USER_ITEM_TYPE_ADDRESS, str));
                    }
                    sections.add(section);
                }
                return sections;
            }
        }).compose(RxUtil.<List<MySection>>switchSchedulers())
                .subscribe(new Consumer<List<MySection>>() {
                    @Override
                    public void accept(List<MySection> mySections) throws Exception {
                        mView.showAllProvinces(mySections);
                    }
                });

    }


}
