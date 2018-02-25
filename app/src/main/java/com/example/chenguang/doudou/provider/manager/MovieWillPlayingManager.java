package com.example.chenguang.doudou.provider.manager;

import com.example.chenguang.doudou.bean.MoviePlaying;
import com.example.chenguang.doudou.bean.WillPlayingSection;
import com.example.chenguang.doudou.provider.provider.MoviePlayingProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by chenguang on 2018/2/9.
 */

public class MovieWillPlayingManager {
    public Observable<List<WillPlayingSection>> getWillPlaying(final String address) {
        return Observable.create(new ObservableOnSubscribe<List<WillPlayingSection>>() {
            @Override
            public void subscribe(ObservableEmitter<List<WillPlayingSection>> e) throws Exception {
                List<WillPlayingSection> sections = new ArrayList<>();
                List<MoviePlaying> items = MoviePlayingProvider.getWillPlaying(address);
                //他是按添加顺序排序的
                Map<String, List<MoviePlaying>> hm = new LinkedHashMap<>();
                for (MoviePlaying item : items) {
                    if (hm.get(item.playingTime) == null) {
                        hm.put(item.playingTime, new ArrayList<MoviePlaying>());
                    }
                    hm.get(item.playingTime).add(item);
                }
                for (String key : hm.keySet()) {
                    WillPlayingSection section = new WillPlayingSection();
                    section.HeadTitle = key;
                    section.items = hm.get(key);
                    sections.add(section);
                    System.out.println(key);

                }
                e.onNext(sections);
                e.onComplete();
            }
        });
    }
}
