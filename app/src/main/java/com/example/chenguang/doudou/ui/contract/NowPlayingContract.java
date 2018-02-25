package com.example.chenguang.doudou.ui.contract;

import com.example.chenguang.doudou.base.BaseContract;
import com.example.chenguang.doudou.bean.MoviePlaying;

import java.util.List;

/**
 * Created by chenguang on 2018/1/29.
 */

public interface NowPlayingContract extends BaseContract {
    interface View extends BaseView {
        void showNowPlayingMovie(List<MoviePlaying> movies);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getNowPlayingMovie(String address);
    }
}
