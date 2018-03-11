package com.example.chenguang.doudou;

import com.example.chenguang.doudou.bean.Province;
import com.example.chenguang.doudou.utils.SortUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pinyinsearch.model.PinyinUnit;
import com.pinyinsearch.util.PinyinUtil;

import org.junit.Test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        String name = "胡";
        List<PinyinUnit> units = new ArrayList<>();
        PinyinUtil.chineseStringToPinyinUnit(name, units);
        /*System.out.println(units.toString());
//        units.*/
        String pinyin = PinyinUtil.getSortKey(units);
        if (pinyin.indexOf(" ") != -1) {
            pinyin = pinyin.substring(0, pinyin.indexOf(" "));
        }
        System.out.println(pinyin);
        /*String fl = PinyinUtil.getFirstLetter(units);
        System.out.println(fl);*/
    }

    @Test
    public void SortNames() {
        List<String> names = Arrays.asList("河南", "河北", "湖南", "湖北", "北京");
        List<String> fls = new ArrayList<>();
        for (String name : names) {
            List<PinyinUnit> units = new ArrayList<>();
            PinyinUtil.chineseStringToPinyinUnit(name, units);
            String fl = PinyinUtil.getFirstLetter(units);
            fls.add(fl);
        }
        for (int i = 0; i < fls.size(); i++) {
            List<String> na = new ArrayList<>();
            for (int j = i + 1; j < fls.size(); j++) {
                na.add(names.get(i));
                if (fls.get(i).equals(fls.get(j))) {
                    na.add(names.get(j));
                    na.remove(j);
                }
            }
        }
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("广东", "河南", "河北", "广西", "北京");
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Collator collator = Collator.getInstance(Locale.CHINA);
                System.out.println(o1.toString() + "," + o2.toString());
                return collator.compare(o1, o2);
            }
        });
        System.out.println(list.toString());
    }

    @Test
    public void test3() {
        List<String> list = Arrays.asList("广东", "河南", "广西", "河北", "北京");
//        List<Provinces> ps = new ArrayList<>();
        HashMap<String, List<String>> mp = new HashMap<>();
        for (String s : list) {
            ArrayList<String> al = new ArrayList<>();
            String fl = SortUtil.getChineseFistLetter(s);
            System.out.println(s);
            if (mp.get(fl) != null) {
                mp.get(fl).add(s);
            } else {
                al.add(s);
                mp.put(fl, al);
            }
        }
        for (String s : mp.keySet()) {
            String fl = SortUtil.getChineseFistLetter(s);
            System.out.println(s + ":" + mp.get(fl).toString());
        }

    }

    @Test
    public void test4() {
        String str = "[\n" +
                "    {\n" +
                "        \"citys\": [\n" +
                "            {\n" +
                "                \"citysName\": \"石家庄市\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"provinceName\": \"河北省\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"citys\": [\n" +
                "            {\n" +
                "                \"citysName\": \"石家庄市\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"provinceName\": \"河北省\"\n" +
                "    }\n" +
                "]";
        System.out.println(str);
        Gson gson = new Gson();
        List<Province> provinces = gson.fromJson(str, new TypeToken<List<Province>>() {
        }.getType());
        System.out.println(provinces.toString());
    }

}