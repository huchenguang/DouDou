package com.example.chenguang.doudou;

import com.example.chenguang.doudou.api.DouBanApi;
import com.example.chenguang.doudou.api.DouBanJsoup;
import com.example.chenguang.doudou.bean.HotMovie;
import com.example.chenguang.doudou.bean.ItemContentListBean;
import com.example.chenguang.doudou.bean.Province;
import com.example.chenguang.doudou.utils.ConstantValues;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2018/1/15.
 */

public class NetWorkTest {
    @Test
    public void test() {
        DouBanApi mApi = DouBanApi.getInstance(ConstantValues.BASE_API_2, DouBanApi
                .getDefaultClient());

        try {
            HotMovie hotMovies = mApi.getHotMovies(0, 20);
            System.out.println(hotMovies.subjects.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
//        DouBanJsoup.getTop250();
//        DouBanJsoup.getPraise();
//        DouBanJsoup.getTicketOffice();
//        DouBanJsoup.getBestReview(0);
//        MovieDetailProvider.getMovieBasicData("26425072");
//        MovieNowPlayingManager.getNowPlaying("jinhua");
//        MoviePlayingProvider.getWillPlaying("zhoukou");
        /*MovieWillPlayingManager manager = new MovieWillPlayingManager();
        manager.getWillPlaying("zhoukou");*/
        List<ItemContentListBean> praise = DouBanJsoup.getTicketOffice();
        System.out.println(praise.toString());
    }

    @Test
    public void test3() throws IOException {
        Document doc = Jsoup.connect("https://movie.douban.com/trailer/226011/#content").get();
        String video = doc.select("div.stage").select("div#player").select("source[src]").attr
                ("src");
        System.out.println(video);
    }

    @Test
    public void test4() {
        try {
            File file = new File("F:/my_projects/DouDou/app/src/main/assets/abroadCities.txt");
            if (file.exists()) {
                System.out.println(file.getAbsolutePath());
            } else {
                System.out.println("文件不存在...");
            }
            InputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            String json = new String(out.toByteArray());
            in.close();
            out.close();
            Gson gson = new Gson();
            List<MyLocation> locations = gson.fromJson(json, new TypeToken<List<MyLocation>>() {
            }.getType());
            System.out.println(locations.toString());
            List<Province> provinces = new ArrayList<>();
            for (MyLocation location : locations) {
                Province province = new Province();
                province.provinceName = location.name;
                province.citys = new ArrayList<>();
                for (MyLocation.Children child : location.children) {
                    province.citys.add(province.new City(child.name));
                }
                provinces.add(province);
            }
            String s = gson.toJson(provinces, new TypeToken<List<Province>>() {
            }.getType());
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
