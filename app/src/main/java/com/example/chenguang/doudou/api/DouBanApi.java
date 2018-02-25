package com.example.chenguang.doudou.api;

import com.example.chenguang.doudou.bean.HotMovie;
import com.example.chenguang.doudou.bean.InterestMovie;
import com.example.chenguang.doudou.bean.ItemContentListBean;
import com.example.chenguang.doudou.bean.SearchDetail;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by chenguang on 2018/1/14.
 */

public class DouBanApi {
    private static DouBanApi mInstance;
    private DouBanApiService apiService;

    private DouBanApi(String baseApi, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApi)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.apiService = retrofit.create(DouBanApiService.class);
    }

    public static OkHttpClient getDefaultClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        return okHttpClient;
    }

    public static DouBanApi getInstance(String baseApi, OkHttpClient client) {
        if (mInstance == null) {
            mInstance = new DouBanApi(baseApi, client);
        }
        return mInstance;
    }

    public Observable<SearchDetail> searchMovie(String key, int start, int count) {
        return apiService.searchMovie(key, start, count);
    }

    /**
     * 从服务器上获取热门搜索
     *
     * @return
     */
    public Observable<String> getHotSearch() {
        return null;
    }

    /**
     * base_api_2
     */
    public HotMovie getHotMovies(int start, int count) throws IOException {
        return apiService.getMovies("movie", "热门", count, start).execute().body();
    }

    private List<InterestMovie> getInterestMovie(int start, int count) throws IOException {
        return apiService.getInterestMovie(13, "100:90", count, start).execute().body();
    }

    public List<ItemContentListBean> getInterestMovieWithReview(final int start,
                                                                final int count) throws
            IOException {
        List<InterestMovie> movies = getInterestMovie(start, count);
        List<ItemContentListBean> itemContentListBeans = new ArrayList<>();
        for (InterestMovie movie : movies) {
            ItemContentListBean item = new
                    ItemContentListBean();
            item.id = movie.id;
            item.cover = movie.cover_url;
            item.title = movie.title;
            item.rate = movie.score;
            item.year = movie.release_date;
            item.countries = movie.regions.get(0);
            item.genres = movie.types;
            item.casts = movie.actors;
            itemContentListBeans.add(item);
        }
        itemContentListBeans.add(DouBanJsoup.getBestReview(start / count));
        return itemContentListBeans;
    }
}
