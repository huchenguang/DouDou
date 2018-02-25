package com.example.chenguang.doudou.ui.contract;

import android.graphics.Bitmap;

import com.example.chenguang.doudou.base.BaseContract;
import com.example.chenguang.doudou.bean.MovieBasicData;

import java.util.List;

/**
 * Created by chenguang on 2018/1/24.
 */

public interface MovieDetailContract extends BaseContract {
    interface View extends BaseView {
        void showMovieBasicData(MovieBasicData movieBasicData);

        void showBackground(Bitmap bitmap);

        void showActorPhotos();

        void showStill(List<String> imgList);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void getMovieData(String id);

        void getActorPhotos();

        //电影剧照
        void getStill();

        void getAward();

        void getShowComment();

        void getTopic();

        void getSimilar();

        void getImageBitmapColor(String imgUrl);
    }
}
