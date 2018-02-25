package com.example.chenguang.doudou.ui.presenter;

import com.example.chenguang.doudou.base.RxPresenter;
import com.example.chenguang.doudou.bean.MoviePlaying;
import com.example.chenguang.doudou.provider.manager.MovieNowPlayingManager;
import com.example.chenguang.doudou.ui.contract.NowPlayingContract;
import com.example.chenguang.doudou.ui.fragment.NowPlayingFragment;
import com.example.chenguang.doudou.utils.RxUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by chenguang on 2018/1/29.
 */

public class NowPlayingFragmentPresenter extends RxPresenter<NowPlayingFragment> implements
        NowPlayingContract.Presenter<NowPlayingFragment> {
    private final MovieNowPlayingManager mManager;

    public NowPlayingFragmentPresenter() {
        mManager = new MovieNowPlayingManager();
    }

    @Override
    public void getNowPlayingMovie(String address) {
        DisposableObserver<List<MoviePlaying>> disposableObserver = mManager.getNowPlaying(address)
                .compose(RxUtil.<List<MoviePlaying>>switchSchedulers())
                .subscribeWith(new DisposableObserver<List<MoviePlaying>>() {
                    @Override
                    public void onNext(List<MoviePlaying> value) {
                        mView.showNowPlayingMovie(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.error(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        mView.complete();
                    }
                });
        addSubscribe(disposableObserver);
    }
}
