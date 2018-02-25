package com.example.chenguang.doudou.module;

import com.example.chenguang.doudou.api.DouBanApi;
import com.example.chenguang.doudou.utils.ConstantValues;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by chenguang on 2018/1/16.
 */
@Module
public class DouBanApiModule {
    @Provides
    protected OkHttpClient getOkHttpClient() {
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

    @Provides
    protected DouBanApi getDouBanApi(OkHttpClient client) {
        return DouBanApi.getInstance(ConstantValues.BASE_API, client);
    }

}
