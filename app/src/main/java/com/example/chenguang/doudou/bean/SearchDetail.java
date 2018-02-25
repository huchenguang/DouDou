package com.example.chenguang.doudou.bean;


import java.util.List;

/**
 * Created by chenguang on 2018/1/14.
 */

public class SearchDetail {
    public int count;
    public int start;

    @Override
    public String toString() {
        return "SearchDetail{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", subjects=" + subjects +
                '}';
    }

    public int total;
    public List<SearchMovie> subjects;

    public class SearchMovie {
        public String title;
        public Rating rating;
        public List<Cast> casts;
        public List<Director> directors;
        public Image images;

        @Override
        public String toString() {
            return "SearchMovie{" +
                    "title='" + title + '\'' +
                    ", rating=" + rating +
                    ", casts=" + casts +
                    ", directors=" + directors +
                    ", images=" + images +
                    '}';
        }
    }

    public class Cast {
        public String name;
    }

    public class Director {
        public String name;
    }

    public class Image {
        public String large;
        public String medium;
        public String small;
    }
}
