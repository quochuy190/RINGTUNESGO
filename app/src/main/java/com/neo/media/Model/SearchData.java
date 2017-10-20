package com.neo.media.Model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by QQ on 9/6/2017.
 */

public class SearchData extends RealmObject implements Serializable{
    @PrimaryKey
    private int id;
    //content_search
    private String content_search;
    // date
    private Date time_search;

    public SearchData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent_search() {
        return content_search;
    }

    public void setContent_search(String content_search) {
        this.content_search = content_search;
    }

    public Date getTime_search() {
        return time_search;
    }

    public void setTime_search(Date time_search) {
        this.time_search = time_search;
    }
}
