package com.example.chenguang.doudou.bean;

import java.util.List;

/**
 * Created by chenguang on 2018/1/19.
 */

public class ItemContentListBean {
    @Override
    public String toString() {
        return "ItemContentListBean{" +
                "cover='" + cover + '\'' +
                ", id='" + id + '\'' +
                ", rate=" + rate +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", index=" + index +
                ", ratings_count=" + ratings_count +
                ", year='" + year + '\'' +
                ", countries='" + countries + '\'' +
                ", genres=" + genres +
                ", directors=" + directors +
                ", casts=" + casts +
                ", comTitle='" + comTitle + '\'' +
                ", comment='" + comment + '\'' +
                ", author='" + author + '\'' +
                ", review_id='" + review_id + '\'' +
                '}';
    }

    public String cover;
    public String id;
    public float rate;
    public String title;//电影title
    public String url;

    public int index;
    public long ratings_count;

    //你可能感兴趣
    public String year;//年代
    public String countries;
    public List<String> genres;
    public List<String> directors;
    public List<String> casts;

    //影评
    public String comTitle;//影评title
    public String comment;//影评简介
    public String author;
    public String review_id;
    public String author_icon;

}
