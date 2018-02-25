package com.example.chenguang.doudou.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by chenguang on 2018/1/16.
 */

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    protected T mView;
    private CompositeDisposable mDisposables;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        unSubscribe();
        mView = null;
    }

    //取消订阅
    protected void unSubscribe() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    //添加订阅关系
    protected void addSubscribe(Disposable observer) {
        if (null == mDisposables) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(observer);
    }
}
