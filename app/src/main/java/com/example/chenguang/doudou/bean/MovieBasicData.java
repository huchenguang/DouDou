package com.example.chenguang.doudou.bean;

import java.util.List;

/**
 * Created by chenguang on 2018/1/24.
 */

public class MovieBasicData {
    public String id;
    public String coverImg;
    public String title;
    public int age;
    public List<String> types;
    public String pub_date;//2018-03-02(中国大陆)
    public String regions;
    public int duration;
    public String introduction;
    //评分
    public float rate;
    public long ratingNum;
    //相关演员
    public int actorNums;
    public List<ActorBean> actors;

    //相关预告片及照片
    public int stageNum;
    public List<Stage> stagePhotos;

    public int awardNum;
    public List<String> awards;

    public class ActorBean {
        public String id;
        public String imgUrl;
        public String name;
        public String role;
    }

    public class Stage {
        public String id;
        public String imgUrl;
        public String href;
        public boolean isPreview;
    }

}
