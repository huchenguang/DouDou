package com.example.chenguang.doudou.ui.presenter;

import com.example.chenguang.doudou.api.DouBanApi;
import com.example.chenguang.doudou.api.DouBanJsoup;
import com.example.chenguang.doudou.base.RxPresenter;
import com.example.chenguang.doudou.bean.HotMovie;
import com.example.chenguang.doudou.bean.ItemContentListBean;
import com.example.chenguang.doudou.bean.MovieIndex;
import com.example.chenguang.doudou.ui.contract.MovieContract;
import com.example.chenguang.doudou.ui.fragment.MovieFragment;
import com.example.chenguang.doudou.utils.ConstantValues;
import com.example.chenguang.doudou.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by chenguang on 2018/1/17.
 */

public class MoviePresenter extends RxPresenter<MovieFragment> implements MovieContract
        .Presenter<MovieFragment> {
    private DouBanApi mApi;

    @Inject
    public MoviePresenter() {
        mApi = DouBanApi.getInstance(ConstantValues.BASE_API_2, DouBanApi.getDefaultClient());
    }

    @Override
    public void getMovieIndex() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<MovieIndex>() {

            @Override
            public void subscribe(final ObservableEmitter<MovieIndex> e) throws Exception {
                final MovieIndex movieIndex = new MovieIndex();
                movieIndex.itemInfoList = new ArrayList<>();
                HotMovie hotMovie = mApi.getHotMovies(0, 20);
                MovieIndex.ItemInfoListBean hot_list = movieIndex.new
                        ItemInfoListBean();
                hot_list.itemType = "douban_hot";
                hot_list.itemContentList = hotMovie.subjects;
                movieIndex.itemInfoList.add(hot_list);  //1

                MovieIndex.ItemInfoListBean banner_list = movieIndex.new
                        ItemInfoListBean();
                banner_list.itemContentList = new ArrayList<>();
                banner_list.itemType = "douban_movie_banner";
                banner_list.itemContentList.addAll(DouBanJsoup.getTop250());
                banner_list.itemContentList.addAll(DouBanJsoup.getPraise());
                banner_list.itemContentList.addAll(DouBanJsoup.getTicketOffice());
                movieIndex.itemInfoList.add(banner_list);   //2

                List<ItemContentListBean>
                        itemContentListBeans = mApi.getInterestMovieWithReview(0, 3);
                MovieIndex.ItemInfoListBean interested_list =
                        movieIndex.new ItemInfoListBean();
                interested_list.itemType = "douban_may_interested";
                interested_list.itemContentList =
                        itemContentListBeans;
                movieIndex.itemInfoList.add(interested_list);
                e.onNext(movieIndex);
                e.onComplete();
            }
        })
        .compose(RxUtil.<MovieIndex>switchSchedulers())
                .subscribe(new Consumer<MovieIndex>() {
                    @Override
                    public void accept(MovieIndex movieIndex) throws Exception {
                        mView.dismissDialog();
                        mView.showMovieIndex(movieIndex.itemInfoList);
                    }
                });
        addSubscribe(subscribe);

    }

    @Override
    public void getMoreInterestedMovie(final int page) {
        final int start = 0;
        final int count = 3;
        if (page > 10) {
            mView.showMoreInterestedMovie(null);
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<MovieIndex>() {
            @Override
            public void subscribe(ObservableEmitter<MovieIndex> e) throws Exception {
                List<ItemContentListBean> itemContentListBeans = mApi.getInterestMovieWithReview
                        (start + (page - 1) * count, count);

                final MovieIndex movieIndex = new MovieIndex();
                movieIndex.itemInfoList = new ArrayList<>();
                MovieIndex.ItemInfoListBean interested_list =
                        movieIndex.new ItemInfoListBean();
                interested_list.itemType = "douban_may_interested";
                interested_list.itemContentList =
                        itemContentListBeans;
                movieIndex.itemInfoList.add(interested_list);
                e.onNext(movieIndex);
                e.onComplete();

            }
        }).compose(RxUtil.<MovieIndex>switchSchedulers())
                .subscribe(new Consumer<MovieIndex>() {
                    @Override
                    public void accept(MovieIndex movieIndex) throws Exception {
                        mView.showMoreInterestedMovie(movieIndex.itemInfoList);
                    }
                });
        addSubscribe(subscribe);
    }
}
