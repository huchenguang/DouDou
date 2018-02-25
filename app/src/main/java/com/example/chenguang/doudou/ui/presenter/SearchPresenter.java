package com.example.chenguang.doudou.ui.presenter;

import com.blankj.utilcode.util.SPUtils;
import com.example.chenguang.doudou.api.DouBanApi;
import com.example.chenguang.doudou.base.RxPresenter;
import com.example.chenguang.doudou.ui.activity.SearchActivity;
import com.example.chenguang.doudou.ui.contract.SearchContract;
import com.example.chenguang.doudou.utils.ConstantValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by chenguang on 2018/1/16.
 */

public class SearchPresenter extends RxPresenter<SearchActivity> implements SearchContract
        .Presenter<SearchActivity> {
    private DouBanApi mApi;

    @Inject
    public SearchPresenter(DouBanApi douBanApi) {
        this.mApi = douBanApi;
    }

    @Override
    public void addSearchHistory(String key) {

        String histories = SPUtils.getInstance(ConstantValues.SEARCH_HISTORIES).getString
                ("histories", "");
        String[] split = histories.split(",");
        List<String> historyList = new ArrayList<>(Arrays.asList(split));
        if (historyList.size() > 0) {
            //移除重复添加
            if (historyList.contains(key)) {
                historyList.remove(key);
            }
            historyList.add(0, key);
            //最多保存4条记录
            if (historyList.size() > 4) {
                historyList.remove(historyList.size() - 1);
            }
            //逗号拼接
            StringBuilder sb = new StringBuilder();
            for (String s : historyList) {
                sb.append(s + ",");
            }
            SPUtils.getInstance(ConstantValues.SEARCH_HISTORIES).put("histories", sb.toString());
        } else {//之前未添加过
            SPUtils.getInstance(ConstantValues.SEARCH_HISTORIES).put("histories", key + ",");
        }
    }

    @Override
    public void getSearchHistory() {
        String histories = SPUtils.getInstance(ConstantValues.SEARCH_HISTORIES).getString
                ("histories", "");
        String[] split = histories.split(",");
        List<String> historyList = new ArrayList<>(Arrays.asList(split));
        if (historyList.size() == 1 && historyList.get(0).equals("")) {
            historyList.clear();
        }
        mView.showSearchHistory(historyList);
    }

    @Override
    public void clearHistories() {
        //直接擦除...
        SPUtils.getInstance(ConstantValues.SEARCH_HISTORIES).put("histories", "");
        mView.showSearchHistory(new ArrayList<String>());
    }

    @Override
    public void getHotSearch() {
//        mApi.getHotSearch();
        //模拟数据
        List<String> hot_search = new ArrayList<>();
        hot_search.add("马斯顿教授与神奇女侠");
        hot_search.add("水形物语");
        hot_search.add("密室逃脱");
        hot_search.add("电锯惊魂8：竖剧");
        hot_search.add("愿上帝宽恕我们");
        hot_search.add("无闻东西");
        hot_search.add("普通人");
        hot_search.add("斯特拉顿");
        hot_search.add("巨额来电");
        hot_search.add("前任3：再见前任");
        mView.showHotSearch(hot_search);
    }
}
