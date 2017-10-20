package com.neo.media.CRBTModel.Group_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/24/2017.
 */

public class Meta_FUNDIAL_PROFILE_model {

    @SerializedName("FUNDIAL_PROFILE")
    FUNDIAL_PROFILE_model fundial_profile;

    public FUNDIAL_PROFILE_model getFundial_profile() {
        return fundial_profile;
    }

    public void setFundial_profile(FUNDIAL_PROFILE_model fundial_profile) {
        this.fundial_profile = fundial_profile;
    }
}
