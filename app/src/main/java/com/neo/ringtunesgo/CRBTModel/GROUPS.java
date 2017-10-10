package com.neo.ringtunesgo.CRBTModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by QQ on 7/24/2017.
 */

public class GROUPS {
    @SerializedName("total")
    private String total;
    @SerializedName("GROUP")
    private ArrayList<GROUP> group;

    public GROUPS() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<GROUP> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<GROUP> group) {
        this.group = group;
    }
}
