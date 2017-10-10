package com.neo.ringtunesgo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 8/2/2017.
 */

public class Return {
    @SerializedName("return")
    String result;

    public Return() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
