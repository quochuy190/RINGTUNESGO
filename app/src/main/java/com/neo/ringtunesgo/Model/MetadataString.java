package com.neo.ringtunesgo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 8/14/2017.
 */

public class MetadataString {
    @SerializedName("return")
    String return1;

    public MetadataString() {
    }

    public String getReturn1() {
        return return1;
    }

    public void setReturn1(String return1) {
        this.return1 = return1;
    }
}
