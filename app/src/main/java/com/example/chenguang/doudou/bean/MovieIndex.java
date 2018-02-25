package com.example.chenguang.doudou.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.chenguang.doudou.utils.ConstantValues;

import java.util.List;

/**
 * Created by chenguang on 2018/1/17.
 */

public class MovieIndex {
    @Override
    public String toString() {
        return "MovieIndex{" +
                "code=" + code +
                ", itemInfoList=" + itemInfoList +
                '}';
    }

    /**
     * douban_hot
     * douban_top250
     * douban_chart本周口碑榜
     * douban_ticket
     * douban_may_interested
     * douban_review
     */
    public int code;
    public List<ItemInfoListBean> itemInfoList;

    public class ItemInfoListBean implements MultiItemEntity {
        @Override
        public String toString() {
            return "ItemInfoListBean{" +
                    "itemType='" + itemType + '\'' +
                    ", itemContentList=" + itemContentList +
                    '}';
        }

        public String itemType;
        public List<ItemContentListBean> itemContentList;

        @Override
        public int getItemType() {
            switch (itemType) {
                case "douban_hot":
                    return ConstantValues.DOUBAN_HOT;
                case "douban_movie_banner":
                    return ConstantValues.DOUBAN_MOVIE_BANNER;
                case "douban_may_interested":
                    return ConstantValues.DOUBAN_MAY_INTERESTED;
                default:
                    return 0;
            }
        }
    }


}
