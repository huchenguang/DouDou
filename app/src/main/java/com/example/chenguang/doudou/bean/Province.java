package com.example.chenguang.doudou.bean;

import java.util.List;

/**
 * Created by chenguang on 2018/1/9.
 */

public class Province {
    public String provinceName;
    public List<City> citys;

    public class City {
        public City(String citysName) {
            this.citysName = citysName;
        }

        public String citysName;
    }
}
