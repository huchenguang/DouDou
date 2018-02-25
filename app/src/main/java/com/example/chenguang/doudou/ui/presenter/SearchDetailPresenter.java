package com.example.chenguang.doudou.ui.presenter;

import com.example.chenguang.doudou.api.DouBanApi;
import com.example.chenguang.doudou.base.RxPresenter;
import com.example.chenguang.doudou.bean.SearchDetail;
import com.example.chenguang.doudou.ui.activity.SearchDetailActivity;
import com.example.chenguang.doudou.ui.contract.SearchDetailContract;
import com.example.chenguang.doudou.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by chenguang on 2018/1/16.
 */

public class SearchDetailPresenter extends RxPresenter<SearchDetailActivity> implements
        SearchDetailContract.Presenter<SearchDetailActivity> {
    private DouBanApi mApi;

    @Inject
    public SearchDetailPresenter(DouBanApi douBanApi) {
        this.mApi = douBanApi;
    }

    @Override
    public void getSearchResult(String key, int page) {
        mApi.searchMovie(key, (page - 1) * 15, 15)
                .compose(RxUtil.<SearchDetail>switchSchedulers())
                .map(new Function<SearchDetail, List<SearchDetail.SearchMovie>>() {
                    @Override
                    public List<SearchDetail.SearchMovie> apply(SearchDetail searchDetail) throws
                            Exception {
                        return searchDetail.subjects;
                    }
                })
                .subscribe(new Observer<List<SearchDetail.SearchMovie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<SearchDetail.SearchMovie> searchMovies) {
                        mView.dismissDialog();
                        mView.showSearchResult(searchMovies);
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
    }
}
