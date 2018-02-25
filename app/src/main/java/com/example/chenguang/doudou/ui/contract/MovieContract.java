package com.example.chenguang.doudou.ui.contract;

import com.example.chenguang.doudou.base.BaseContract;
import com.example.chenguang.doudou.bean.MovieIndex;

import java.util.List;

/**
 * Created by chenguang on 2018/1/17.
 */

public interface MovieContract extends BaseContract {
    interface View extends BaseView {
        void showMovieIndex(List<MovieIndex.ItemInfoListBean> infoListBean);

        void showMoreInterestedMovie(List<MovieIndex.ItemInfoListBean> infoListBean);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getMovieIndex();

        void getMoreInterestedMovie(int page);
    }
}
