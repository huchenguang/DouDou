package com.example.chenguang.doudou.base;

/**
 * Created by chenguang on 2018/1/16.
 */

public interface BaseContract {
    interface BaseView {
        void complete();

        void error(String e);
    }

    interface BasePresenter<T> {
        void attachView(T view);

        void detachView();
    }
}
