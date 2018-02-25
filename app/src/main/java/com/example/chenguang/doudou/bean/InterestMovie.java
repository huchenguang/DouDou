package com.example.chenguang.doudou.bean;

import java.util.List;

/**
 * Created by chenguang on 2018/1/19.
 */

public class InterestMovie {
    public List<String> actors;
    public String cover_url;
    public String id;
    public float score;
    public String title;
    public List<String> types;
    public String release_date;
    public List<String> regions;

    @Override
    public String toString() {
        return "InterestMovie{" +
                "actors=" + actors +
                ", cover_url='" + cover_url + '\'' +
                ", id='" + id + '\'' +
                ", score=" + score +
                ", title='" + title + '\'' +
                ", types=" + types +
                ", release_date='" + release_date + '\'' +
                ", regions=" + regions +
                '}';
    }
}
