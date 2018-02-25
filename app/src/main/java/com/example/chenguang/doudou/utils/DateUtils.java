package com.example.chenguang.doudou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenguang on 2018/2/9.
 */

public class DateUtils {
    public static long DateCompare(String s1, String s2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(s2);
        return ((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000));
    }
}
