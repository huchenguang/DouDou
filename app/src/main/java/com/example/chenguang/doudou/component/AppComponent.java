package com.example.chenguang.doudou.component;

import android.content.Context;

import com.example.chenguang.doudou.api.DouBanApi;
import com.example.chenguang.doudou.module.AppModule;
import com.example.chenguang.doudou.module.DouBanApiModule;

import dagger.Component;

/**
 * Created by chenguang on 2018/1/16.
 */
@Component(modules = {AppModule.class, DouBanApiModule.class})
public interface AppComponent {
    Context getContext();

    DouBanApi getDouBanApi();
}
