package com.example.chenguang.doudou.provider.manager;

import com.example.chenguang.doudou.bean.MovieBasicData;
import com.example.chenguang.doudou.provider.provider.MovieDetailProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;

/**
 * Created by chenguang on 2018/1/25.
 */

public class MovieDetailManager {
    public ObservableSource<MovieBasicData> getMovieBasicData(final String id) {
        return Observable.create(new ObservableOnSubscribe<MovieBasicData>() {
            @Override
            public void subscribe(ObservableEmitter<MovieBasicData> e) throws Exception {
                MovieBasicData movieBasicData = MovieDetailProvider.getMovieBasicData(id);
                e.onNext(movieBasicData);
                e.onComplete();
            }
        });
    }
}
