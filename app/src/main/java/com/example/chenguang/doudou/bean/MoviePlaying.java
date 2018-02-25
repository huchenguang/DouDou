package com.example.chenguang.doudou.bean;

/**
 * Created by chenguang on 2018/1/29.
 */

public class MoviePlaying {
    public String id;
    public String coverImg;
    public String title;
    //评分
    public float rate;
    public long ratingNum;
    //导演
    public String director;
    //主演
    public String actors;

    //tr，false即将热映
    public boolean isPlaying;
    //看过人数
    public int seenNum;
    //想看人数
    public int wantToSeeNum;
    public String playingTime;
    public String type;
    public String region;

}
