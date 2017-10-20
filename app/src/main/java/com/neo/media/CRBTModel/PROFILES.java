package com.neo.media.CRBTModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by QQ on 7/25/2017.
 */

public class PROFILES {
    @SerializedName("total")
    String total;
    @SerializedName("PROFILE")
    ArrayList<PROFILE> profile;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<PROFILE> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<PROFILE> profile) {
        this.profile = profile;
    }
}
