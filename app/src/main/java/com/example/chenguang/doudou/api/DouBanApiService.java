package com.example.chenguang.doudou.api;

import com.example.chenguang.doudou.bean.HotMovie;
import com.example.chenguang.doudou.bean.InterestMovie;
import com.example.chenguang.doudou.bean.SearchDetail;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenguang on 2018/1/14.
 */

public interface DouBanApiService {
    @GET("v2/movie/earch")
    Observable<SearchDetail> searchMovie(@Query("q") String key, @Query("start") int
            start, @Query("count") int count);

    /**
     * base_api_2
     */
    @GET("/j/search_subjects")
    Call<HotMovie> getMovies(@Query("type") String type, @Query("tag") String tag, @Query
            ("page_limit") int count, @Query("page_start") int start);

    //https://movie.douban.com/j/chart/top_list?type=13&interval_id=100:90&start=0&limit=20
    @GET("/j/chart/top_list")
    Call<List<InterestMovie>> getInterestMovie(@Query("type") int type, @Query
            ("interval_id") String
            interval_id, @Query("limit") int count, @Query("start") int start);
}
