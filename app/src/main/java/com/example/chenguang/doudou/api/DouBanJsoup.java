package com.example.chenguang.doudou.api;

import com.example.chenguang.doudou.bean.ItemContentListBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2018/1/19.
 */

public class DouBanJsoup {
    public static List<ItemContentListBean> getTop250() {
        try {
            List<ItemContentListBean> contentList = new ArrayList<>();
            Document document = Jsoup.connect("https://movie.douban.com/top250").get();
            Elements items = document.select("div.article").select("ol.grid_view").select("li");
            for (int i = 0; i < items.size(); i++) {
                if (i > 3) {
                    break;
                }
                ItemContentListBean content = new ItemContentListBean();
                String id = items.get(i).select("div.pic").select("a[href]").attr("href");
                id = id.substring(0, id.lastIndexOf("/"));
                id = id.substring(id.lastIndexOf("/") + 1, id.length());
                content.id = id;
                content.title = items.get(i).select("div.pic").select("img[alt]").attr("alt");
                content.cover = items.get(i).select("div.pic").select("img[src]").attr("src");
                content.rate = Float.parseFloat(items.get(i).select("span.rating_num").text());
                String rate_nums = items.get(i).select("div.star").select("span").get(3).text();
                content.ratings_count = Long.parseLong(rate_nums.substring(0, rate_nums
                        .lastIndexOf("人")));
                contentList.add(content);
                return contentList;
            }
            System.out.println("contentList:" + contentList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ItemContentListBean> getPraise() {
        try {
            List<ItemContentListBean> contentList = new ArrayList<>();
            Document document = Jsoup.connect("https://movie.douban.com/chart").get();
            Elements tables = document.select("div.article").select("div.indent").select("table");
            for (int i = 0; i < tables.size(); i++) {
                if (i > 3) {
                    break;
                }
                ItemContentListBean content = new ItemContentListBean();
                Element table = tables.get(i);
                String id = table.select("a.nbg").attr("href");
                id = id.substring(0, id.lastIndexOf("/"));
                id = id.substring(id.lastIndexOf("/") + 1, id.length());
                content.id = id;
                content.title = table.select("img").attr("alt");
                content.cover = table.select("img").attr("src");
                content.rate = Float.parseFloat(table.select("span.rating_nums").text());
                String rate_nums = table.select("div.star").select("span.pl").text();
                content.ratings_count = Long.parseLong(rate_nums.substring(1, rate_nums
                        .lastIndexOf("人")));
                contentList.add(content);
            }
            return contentList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //票房榜
    public static List<ItemContentListBean> getTicketOffice() {
        try {
            List<ItemContentListBean> contentList = new ArrayList<>();
            Document document = Jsoup.connect("https://movie.douban.com/cinema/nowplaying/jinhua/")
                    .get();
            Elements lis = document.select("div.movie_ranking_wrap").select("ul.view_1").select
                    ("li");
            for (int i = 0; i < lis.size(); i++) {
                if (i > 3) {
                    break;
                }
                ItemContentListBean content = new ItemContentListBean();
                Element li = lis.get(i);
                String id = li.select("h2.movie_ranking_title").select("a").attr("href");
                id = id.substring(0, id.lastIndexOf("/"));
                id = id.substring(id.lastIndexOf("/") + 1, id.length());
                content.id = id;
                //获取封面
                Elements tempLis = document.select("div#nowplaying").select("ul.lists").select
                        ("li");
                try {
                    for (Element tempLi : tempLis) {
                        String tempId = tempLi.attr("id");
                        if (id.equals(tempId)) {
                            content.cover = tempLi.select("li.poster").select("img").attr("src");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                content.title = li.select("h2.movie_ranking_title").select("a").text();
                content.rate = Float.parseFloat(li.select("div.text").select("p.star").select
                        ("span.subject-rate").text());
                String rate_nums = li.select("p.star").select("span").get(2).text().trim();
                content.ratings_count = Long.parseLong(rate_nums.substring(1, rate_nums
                        .lastIndexOf("人")));
                contentList.add(content);
            }
            return contentList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取好的影评
     *
     * @param a
     * @return
     */
    public static ItemContentListBean getBestReview(int a) {
        try {
            Document document = Jsoup.connect("https://movie.douban.com/review/best/").get();
            ItemContentListBean content = new ItemContentListBean();
            Elements divs = document.select("div.article").select("div.review-list").select
                    ("div[data-cid]");
            if (a < divs.size()) {
                Element div = divs.get(a);
                String id = div.select("a.subject-img").attr("href");
                id = id.substring(0, id.lastIndexOf("/"));
                id = id.substring(id.lastIndexOf("/") + 1, id.length());
                content.id = id;
                content.title = div.select("a.subject-img").select("img").attr("alt");
                content.cover = div.select("a.subject-img").select("img").attr("src");
                content.comTitle = div.select("div.main-bd").select("h2").select("a").text();
                String rid = div.select("div.main-bd").select("h2").select("a").attr("href");
                rid = rid.substring(0, rid.lastIndexOf("/"));
                rid = rid.substring(rid.lastIndexOf("/") + 1, rid.length());
                content.review_id = rid;
                content.comment = div.select("div.main-bd").select("div.short-content").text();
                content.author_icon = div.select("header.main-hd").select("a.avator").select("img")
                        .attr("src");
                content.author = div.select("header.main-hd").select("a.name").text();
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static vo
}
