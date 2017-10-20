package com.neo.media.CRBTModel.MetaData;

import com.google.gson.annotations.SerializedName;
import com.neo.media.CRBTModel.FUNDIAL_PROFILE;

/**
 * Created by QQ on 7/24/2017.
 */

public class Meta_FUNDIAL_PROFILE {

    @SerializedName("FUNDIAL_PROFILE")
    FUNDIAL_PROFILE fundial_profile;

    public FUNDIAL_PROFILE getFundial_profile() {
        return fundial_profile;
    }

    public void setFundial_profile(FUNDIAL_PROFILE fundial_profile) {
        this.fundial_profile = fundial_profile;
    }
}
