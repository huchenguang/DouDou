package com.example.chenguang.doudou.ui.contract;

import com.example.chenguang.doudou.base.BaseContract;

import java.util.List;

/**
 * Created by chenguang on 2018/1/16.
 */

public interface SearchContract  extends BaseContract{
    interface View extends BaseView {
        void showSearchHistory(List<String> histories);

        void showHotSearch(List<String> hot_search);
    }

    interface Presenter<T> extends BasePresenter<T> {
        //历史记录相关
        void addSearchHistory(String key);

        void getSearchHistory();

        void clearHistories();

        //热门搜索
        void getHotSearch();
    }
}
