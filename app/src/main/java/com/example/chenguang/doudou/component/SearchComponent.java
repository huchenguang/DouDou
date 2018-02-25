package com.example.chenguang.doudou.component;

import com.example.chenguang.doudou.ui.activity.SearchActivity;
import com.example.chenguang.doudou.ui.activity.SearchDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chenguang on 2018/1/16.
 */
@Singleton
@Component(dependencies = AppComponent.class)
public interface SearchComponent {
    SearchActivity inject(SearchActivity activity);

    SearchDetailActivity inject(SearchDetailActivity activity);

}
