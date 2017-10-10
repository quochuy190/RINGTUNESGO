package com.neo.ringtunesgo.CRBTModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/25/2017.
 */

public class TIME_CATEGORY_4 {

    @SerializedName("from_time")
    String from_time;
    @SerializedName("to_time")
    String to_time;

    public TIME_CATEGORY_4() {

    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }
}
