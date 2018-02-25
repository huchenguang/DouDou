package com.example.chenguang.doudou.provider.manager;

import com.example.chenguang.doudou.bean.MoviePlaying;
import com.example.chenguang.doudou.provider.provider.MoviePlayingProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by chenguang on 2018/1/29.
 */

public class MovieNowPlayingManager {
    public Observable<List<MoviePlaying>> getNowPlaying(final String address) {
        return Observable.create(new ObservableOnSubscribe<List<MoviePlaying>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MoviePlaying>> e) throws Exception {
                List<MoviePlaying> nowPlaying = MoviePlayingProvider.getNowPlaying(address);
                e.onNext(nowPlaying);
                e.onComplete();
            }
        });
    }
}
