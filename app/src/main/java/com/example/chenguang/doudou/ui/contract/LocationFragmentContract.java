package com.example.chenguang.doudou.ui.contract;

import com.example.chenguang.doudou.base.BaseContract;
import com.example.chenguang.doudou.bean.MySection;

import java.util.List;

/**
 * Created by chenguang on 2018/1/16.
 */

public interface LocationFragmentContract extends BaseContract {
    interface View extends BaseView {
        void showHotCities(List<String> hotCities);

        void showAllProvinces(List<MySection> mySections);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void getHotCities();

        void getAllProvinces();

    }
}
