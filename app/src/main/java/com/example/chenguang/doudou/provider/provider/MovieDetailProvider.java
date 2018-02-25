package com.example.chenguang.doudou.provider.provider;

import com.example.chenguang.doudou.bean.MovieBasicData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2018/1/24.
 */

public class MovieDetailProvider {
    public static MovieBasicData getMovieBasicData(String id) {
        try {
            MovieBasicData movieBasicData = new MovieBasicData();
            Document document = Jsoup.connect("https://movie.douban.com/subject/" + id +
                    "/").get();

            String title = document.select("div#content").select("span[property]").first()
                    .text().trim();
            if (title.contains(" ")) {
                title = title.substring(0, title.indexOf(" "));
            }
            movieBasicData.title = title;
            String age = document.select("div#content").select("span.year").text();
            age = age.substring(1, age.length() - 1);
            movieBasicData.age = Integer.parseInt(age);
            movieBasicData.coverImg = document.select("div#mainpic").select("img").attr("src");
            Elements select = document.select("div#info").select("span[property]");
            ArrayList<String> types = new ArrayList<>();
            for (int i = 0; i < select.size(); i++) {
                if (i <= 2) {
                    types.add(select.get(i).text());
                    break;
                }
            }
            movieBasicData.types = types;
            movieBasicData.pub_date = document.select("div#info").select("span[content]").first()
                    .text();
            String time = document.select("div#info").select("span[content]:containsOwn(分钟)")
                    .text();//1
            movieBasicData.duration = Integer.parseInt(time.substring(0, "分钟".length()));
            Elements rateEle = document.select("div.article").select("div#interest_sectl");
            String rate = rateEle.select("div.rating_self").select("strong[property]").text();
            movieBasicData.rate = Float.parseFloat(rate);
            String num = rateEle.select("a.rating_people").select("span").text();
            movieBasicData.ratingNum = Long.parseLong(num);
            movieBasicData.introduction = document.select("div.article").select("div" +
                    ".related-info").select("div.indent").select
                    ("span").text();
            Elements actorEle = document.select("div.article").select("div#celebrities");
            String actorNum = actorEle.select("h2").select("span.pl").select("a").text();
            actorNum = actorNum.substring("全部".length()).trim();
            movieBasicData.actorNums = Integer.parseInt(actorNum);
            Elements acEles = actorEle.select("ul.celebrities-list").select("li.celebrity");
            List<MovieBasicData.ActorBean> actors = new ArrayList<>();
            for (Element acEle : acEles) {
                MovieBasicData.ActorBean actorBean = movieBasicData.new ActorBean();

                String imgurl = acEle.select("div.avatar").attr("style");
                imgurl = imgurl.substring(imgurl.indexOf("http"), imgurl.lastIndexOf(")"));
                actorBean.imgUrl = imgurl;
                actorBean.name = acEle.select("div.info").select("span.name").select("a").attr
                        ("title");
                String aid = acEle.select("div.info").select("span.name").select("a").attr("href");
                aid = aid.substring(0, aid.lastIndexOf("/"));
                aid = aid.substring(aid.lastIndexOf("/") + 1);
                actorBean.id = aid;
                actorBean.role = acEle.select("div.info").select("span.role").attr("title");
                actors.add(actorBean);
            }
            movieBasicData.actors = actors;
            Elements picEles = document.select("div.article").select("div.related-pic");
            Elements trailEle = picEles.select("h2").select("span.pl").select("a:containsOwn(预告片)");
            if (trailEle != null) {
                String trailNum = trailEle.text().substring("预告片".length()).trim();
            }
            String picNum = picEles.select("h2").select("span.pl").select("a:containsOwn(图片)")
                    .first().text().substring("图片".length());
            movieBasicData.stageNum = Integer.parseInt(picNum);
            Elements picLis = picEles.select("ul.related-pic-bd").select("li");
            List<MovieBasicData.Stage> stages = new ArrayList<>();
            for (int i = 0; i < picLis.size(); i++) {
                MovieBasicData.Stage stage = movieBasicData.new Stage();
                stage.isPreview = false;
                if (trailEle != null && i == 0) {
                    stage.isPreview = true;
                }
                String href = picLis.get(i).select("a").attr("href");
                href = href.substring(0, href.lastIndexOf("/"));
                href = href.substring(href.lastIndexOf("/") + 1);
                stage.id = href;
                stage.imgUrl = picLis.get(i).select("img").attr("src");
                stages.add(stage);
            }
            movieBasicData.stagePhotos = stages;
            return movieBasicData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
