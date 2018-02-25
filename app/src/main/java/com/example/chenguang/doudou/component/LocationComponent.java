package com.example.chenguang.doudou.component;

import com.example.chenguang.doudou.ui.activity.LocationActivity;
import com.example.chenguang.doudou.ui.fragment.DomesticFragment;

import dagger.Component;

/**
 * Created by chenguang on 2018/1/16.
 */

@Component(dependencies = {AppComponent.class})
public interface LocationComponent {
    LocationActivity inject(LocationActivity activity);

    DomesticFragment inject(DomesticFragment fragment);
}
