package com.mtyxp.hangzhoumtyxp.model.bean;

import java.io.Serializable;

/**
 * Created by CaoZhF on 2018-05-19.
 */

public class GoodsTitleInfo implements Serializable{

    private int goods_id;
    private String goods_title;

    public GoodsTitleInfo(int goods_id, String goods_title) {
        this.goods_id = goods_id;
        this.goods_title = goods_title;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }


    @Override
    public String toString() {
        return "GoodsTitleInfo{" +
                "goods_id=" + goods_id +
                ", goods_title='" + goods_title + '\'' +
                '}';
    }
}
