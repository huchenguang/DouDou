package com.example.chenguang.doudou.ui.contract;

import com.example.chenguang.doudou.base.BaseContract;
import com.example.chenguang.doudou.bean.SearchDetail;

import java.util.List;

/**
 * Created by chenguang on 2018/1/16.
 */

public interface SearchDetailContract extends BaseContract {
    interface View extends BaseView {
        void showSearchResult(List<SearchDetail.SearchMovie> searchDetails);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getSearchResult(String key, int page);
    }
}
