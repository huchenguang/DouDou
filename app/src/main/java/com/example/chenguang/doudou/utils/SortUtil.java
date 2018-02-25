package com.example.chenguang.doudou.utils;

import com.pinyinsearch.model.PinyinUnit;
import com.pinyinsearch.util.PinyinUtil;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenguang on 2018/1/10.
 */

public class SortUtil {
    //将汉字按字母排序
    public static List<String> sortByChineseName(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Collator collator = Collator.getInstance(Locale.CHINA);
                return collator.compare(o1, o2);
            }
        });
        return list;
    }

    //获取首字母
    public static String getChineseFistLetter(String s) {

        List<PinyinUnit> units = new ArrayList<>();
        PinyinUtil.chineseStringToPinyinUnit(s, units);
        String fl = PinyinUtil.getFirstLetter(units);
        return fl;
    }

    /**
     * 将给定的省份按名称分组
     *
     * @param list
     * @return
     */
    public static HashMap<String, List<String>> getHashMap(List<String> list) {
        list = SortUtil.sortByChineseName(list);
        HashMap<String, List<String>> mp = new HashMap<>();
        for (String s : list) {
            ArrayList<String> al = new ArrayList<>();
            String fl = SortUtil.getChineseFistLetter(s);
            fl = fl.toUpperCase();//转为大写
            if (mp.get(fl) != null) {
                mp.get(fl).add(s);
            } else {
                al.add(s);
                mp.put(fl, al);
            }
        }
        return mp;
    }
}
