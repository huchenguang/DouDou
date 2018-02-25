package com.example.chenguang.doudou.ui.contract;

import com.example.chenguang.doudou.base.BaseContract;
import com.example.chenguang.doudou.bean.WillPlayingSection;

import java.util.List;

/**
 * Created by chenguang on 2018/2/9.
 */

public interface WillPlayingContract extends BaseContract {
    interface View extends BaseView {
        void showWillPlayingMovie(List<WillPlayingSection> sections);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getWillPlayingMovie(String address);
    }
}
