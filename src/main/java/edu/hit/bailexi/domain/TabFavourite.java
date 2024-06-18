package edu.hit.bailexi.domain;

import java.io.Serializable;

public class TabFavourite implements Serializable {
    private int rid;
    private String date;
    private int uid;

    public TabFavourite(){

    }

    public TabFavourite(int rid, String date, int uid){
        this.rid = rid;
        this.date = date;
        this.uid = uid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
