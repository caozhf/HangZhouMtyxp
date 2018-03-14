package com.mtyxp.hangzhoumtyxp.model.bean;

/**
 * Created by CaoZhF on 2017-11-15.
 */

public class Bashinai {
    private int ba_id;
    private int pass_num;
    private String ba_title;
    private int ba_num;

    public Bashinai() {
    }

    public Bashinai(int ba_id, int pass_num, String ba_title, int ba_num) {
        this.ba_id = ba_id;
        this.pass_num = pass_num;
        this.ba_title = ba_title;
        this.ba_num = ba_num;
    }

    public int getBa_id() {
        return ba_id;
    }

    public void setBa_id(int ba_id) {
        this.ba_id = ba_id;
    }

    public int getPass_num() {
        return pass_num;
    }

    public void setPass_num(int pass_num) {
        this.pass_num = pass_num;
    }

    public String getBa_title() {
        return ba_title;
    }

    public void setBa_title(String ba_title) {
        this.ba_title = ba_title;
    }

    public int getBa_num() {
        return ba_num;
    }

    public void setBa_num(int ba_num) {
        this.ba_num = ba_num;
    }

    @Override
    public String toString() {
        return "Bashinai{" +
                "ba_id=" + ba_id +
                ", pass_num=" + pass_num +
                ", ba_title='" + ba_title + '\'' +
                ", ba_num=" + ba_num +
                '}';
    }
}
