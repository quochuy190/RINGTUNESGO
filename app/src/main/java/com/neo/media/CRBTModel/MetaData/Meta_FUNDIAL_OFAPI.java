package com.neo.media.CRBTModel.MetaData;

import com.google.gson.annotations.SerializedName;
import com.neo.media.CRBTModel.FUNDIAL_OFAPI;

/**
 * Created by QQ on 7/19/2017.
 */

public class Meta_FUNDIAL_OFAPI {
    @SerializedName("FUNDIAL_OFAPI")
    FUNDIAL_OFAPI fundial_ofapi;

    public FUNDIAL_OFAPI getFundial_ofapi() {
        return fundial_ofapi;
    }

    public void setFundial_ofapi(FUNDIAL_OFAPI fundial_ofapi) {
        this.fundial_ofapi = fundial_ofapi;
    }
}
