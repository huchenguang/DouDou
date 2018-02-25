package com.example.chenguang.doudou.component;

import com.example.chenguang.doudou.ui.fragment.MovieFragment;

import dagger.Component;

/**
 * Created by chenguang on 2018/1/17.
 */

@Component(dependencies = AppComponent.class)
public interface FindComponent {
    MovieFragment inject(MovieFragment fragment);
}
