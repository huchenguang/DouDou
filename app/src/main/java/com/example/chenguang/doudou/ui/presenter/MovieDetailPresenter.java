package com.example.chenguang.doudou.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.example.chenguang.doudou.base.RxPresenter;
import com.example.chenguang.doudou.bean.MovieBasicData;
import com.example.chenguang.doudou.provider.manager.MovieDetailManager;
import com.example.chenguang.doudou.ui.activity.MovieDetailActivity;
import com.example.chenguang.doudou.ui.contract.MovieDetailContract;
import com.example.chenguang.doudou.utils.BlurUtils;
import com.example.chenguang.doudou.utils.RxUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by chenguang on 2018/1/24.
 */

public class MovieDetailPresenter extends RxPresenter<MovieDetailActivity> implements
        MovieDetailContract.Presenter<MovieDetailActivity> {

    private final MovieDetailManager mManager;

    public MovieDetailPresenter() {
        mManager = new MovieDetailManager();
    }

    @Override
    public void getMovieData(String id) {
        Observable.mergeArray(mManager.getMovieBasicData(id))
                .compose(RxUtil.<MovieBasicData>switchSchedulers())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        if (value instanceof MovieBasicData) {
                            mView.showMovieBasicData((MovieBasicData) value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.dismissDialog();
                    }
                });
    }

    @Override
    public void getActorPhotos() {

    }

    @Override
    public void getStill() {

    }

    @Override
    public void getAward() {

    }

    @Override
    public void getShowComment() {

    }

    @Override
    public void getTopic() {

    }

    @Override
    public void getSimilar() {

    }

    @Override
    public void getImageBitmapColor(String imgUrl) {
        Observable
                .just(imgUrl)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) {
                        try {
                            URL url = new URL(s);
                            return BitmapFactory.decodeStream(url.openStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) {
                        return BlurUtils.with(new WeakReference<Context>(mView))
                                .bitmap(bitmap)
                                .radius(14)
                                .blur();
                    }
                })
                .compose(RxUtil.<Bitmap>switchSchedulers())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(@NonNull Bitmap bitmap) throws Exception {
                        mView.showBackground(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println("出错了");
                    }
                });
    }
}
