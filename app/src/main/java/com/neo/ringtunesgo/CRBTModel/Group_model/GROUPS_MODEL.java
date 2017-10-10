package com.neo.ringtunesgo.CRBTModel.Group_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/24/2017.
 */

public class GROUPS_MODEL {
    @SerializedName("total")
    private String total;
    @SerializedName("GROUP")
    private GROUP_MODEL group;

    public GROUPS_MODEL() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public GROUP_MODEL getGroup() {
        return group;
    }

    public void setGroup(GROUP_MODEL group) {
        this.group = group;
    }
}
