package com.example.chenguang.doudou;

import java.util.List;

/**
 * Created by chenguang on 2018/2/18.
 */

public class MyLocation {
    @Override
    public String toString() {
        return "MyLocation{" +
                "children=" + children +
                ", name='" + name + '\'' +
                '}';
    }

    public List<Children> children;
    public String name;

    public class Children {
        @Override
        public String toString() {
            return "Children{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public String name;
    }
}