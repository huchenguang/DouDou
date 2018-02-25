package com.example.chenguang.doudou;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.chenguang.doudou.component.AppComponent;
import com.example.chenguang.doudou.component.DaggerAppComponent;
import com.example.chenguang.doudou.module.AppModule;
import com.example.chenguang.doudou.module.DouBanApiModule;

/**
 * Created by chenguang on 2018/1/15.
 */

public class DouBanApplication extends Application {
    private static DouBanApplication mContext;


    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        mContext = this;
        initComponent();
    }

    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .douBanApiModule(new DouBanApiModule())
                .appModule(new AppModule(this))
                .build();
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static DouBanApplication getInstance() {
        return mContext;
    }
}
