package com.example.chenguang.doudou.ui.presenter;


import com.example.chenguang.doudou.base.RxPresenter;
import com.example.chenguang.doudou.bean.WillPlayingSection;
import com.example.chenguang.doudou.provider.manager.MovieWillPlayingManager;
import com.example.chenguang.doudou.ui.contract.WillPlayingContract;
import com.example.chenguang.doudou.ui.fragment.WillPlayingFragment;
import com.example.chenguang.doudou.utils.RxUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by chenguang on 2018/2/9.
 */

public class WillPlayingPresenter extends RxPresenter<WillPlayingFragment> implements
        WillPlayingContract.Presenter<WillPlayingFragment> {

    private final MovieWillPlayingManager mManager;

    public WillPlayingPresenter() {
        mManager = new MovieWillPlayingManager();
    }


    @Override
    public void getWillPlayingMovie(String address) {
        DisposableObserver<List<WillPlayingSection>> disposableObserver = mManager.getWillPlaying
                (address)
                .compose(RxUtil.<List<WillPlayingSection>>switchSchedulers())
                .subscribeWith(new DisposableObserver<List<WillPlayingSection>>() {
                    @Override
                    public void onNext(List<WillPlayingSection> value) {
                        mView.showWillPlayingMovie(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.error(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.complete();
                    }
                });
        addSubscribe(disposableObserver);
    }
}
