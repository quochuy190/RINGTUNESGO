package com.neo.ringtunesgo.CRBTModel.MetaData;

import com.google.gson.annotations.SerializedName;
import com.neo.ringtunesgo.CRBTModel.FUNDIAL_PROFILE;

/**
 * Created by QQ on 7/27/2017.
 */

public class MetaData_FUNDIAL_CC {
    @SerializedName("FUNDIAL_CC")
    FUNDIAL_PROFILE fundial_profile;

    public FUNDIAL_PROFILE getFundial_profile() {
        return fundial_profile;
    }

    public void setFundial_profile(FUNDIAL_PROFILE fundial_profile) {
        this.fundial_profile = fundial_profile;
    }
}
