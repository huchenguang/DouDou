package com.example.chenguang.doudou.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenguang on 2018/1/16.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
