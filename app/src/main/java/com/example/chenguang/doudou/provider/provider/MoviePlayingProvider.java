package com.example.chenguang.doudou.provider.provider;

import com.example.chenguang.doudou.bean.MoviePlaying;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2018/1/29.
 */

public class MoviePlayingProvider {
    public static List<MoviePlaying> getWillPlaying(String address) {
        try {
            List<MoviePlaying> items = new ArrayList<>();
            Document document = Jsoup.connect("https://movie.douban.com/cinema/later/" + address
                    + "/").get();
            Elements itemEles = document.select("div.article").select("div.bd").select("div" +
                    "#showing-soon").select("div.item");
            for (Element itemEle : itemEles) {
                MoviePlaying movie = new MoviePlaying();
                String id = itemEle.select("a.thumb").attr("href");
                id = id.substring(0, id.lastIndexOf("/"));
                id = id.substring(id.lastIndexOf("/") + 1, id.length());
                movie.id = id;
                movie.coverImg = itemEle.select("a.thumb").select("img[src]").attr("src");
                movie.title = itemEle.select("div.intro").select("h3").text();
                Elements lis = itemEle.select("div.intro").select("ul").select("li");
                String date = lis.get(0).text();
                //格式化日期
                /*if (!date.contains("年")) {
                    date = new SimpleDateFormat("yyyy").format(new Date()) + "年" + date;
                }
                if (!date.contains("月")) {
                    date = date + "01月";
                }
                if (!date.contains("日")) {
                    date = date + "01日";
                }*/
                movie.playingTime = date;
                movie.type = lis.get(1).text();
                movie.region = lis.get(2).text();
                String t_num = lis.get(3).select("span").text();
                t_num = t_num.substring(0, t_num.lastIndexOf("人"));
                movie.wantToSeeNum = Integer.parseInt(t_num);
                items.add(movie);
            }
            Document doc2 = Jsoup.connect("https://movie.douban.com/coming").get();
            Elements itemEles2 = doc2.select("div.article").select("tbody").select("tr");
            for (int i = itemEles.size(); i < itemEles2.size(); i++) {
                MoviePlaying movie2 = new MoviePlaying();
                Elements tds = itemEles2.get(i).select("td");

                String date = tds.get(0).text();
                movie2.playingTime = date;
                movie2.title = tds.get(1).select("a").text();
                String id = tds.get(1).select("a").attr("href");
                id = id.substring(0, id.lastIndexOf("/"));
                id = id.substring(id.lastIndexOf("/") + 1, id.length());
                movie2.id = id;
                movie2.type = tds.get(2).text();
                movie2.region = tds.get(3).text();
                String num = tds.get(4).text();
                num = num.substring(0, num.lastIndexOf("人"));
                movie2.wantToSeeNum = Integer.parseInt(num);
                items.add(movie2);
            }
            return items;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MoviePlaying> getNowPlaying(String address) {
        List<MoviePlaying> itemList = null;
        try {
            Document document = Jsoup.connect("https://movie.douban.com/cinema/nowplaying/" +
                    address + "/").get();
            Elements playEles = document.select("div#nowplaying").select("ul.lists").select("li" +
                    ".list-item");
            if (playEles != null) {
                itemList = new ArrayList<>();
                for (Element playEle : playEles) {
                    MoviePlaying item = new MoviePlaying();
                    item.isPlaying = true;
                    item.id = playEle.attr("id");
                    item.title = playEle.attr("data-title");
                    item.coverImg = playEle.select("a").select("img").attr("src");
                    String rate = playEle.attr("data-score");
                    item.rate = Float.parseFloat(rate);
                    item.director = playEle.attr("data-director");
                    item.actors = playEle.attr("data-actors");
                    String num = playEle.attr("data-votecount");
                    item.seenNum = Integer.parseInt(num);
                    itemList.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemList;
        }
    }
}
