package com.example.chenguang.doudou.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2018/2/3.
 */


public class MySection {
    public int type;
    public String title;
    public List<Item> items = new ArrayList<>();

    public MySection(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public class Item {
        public int type;
        public String title;
        public List<String> titles;

        public Item(int type, String title) {
            this.type = type;
            this.title = title;
        }

    }

}