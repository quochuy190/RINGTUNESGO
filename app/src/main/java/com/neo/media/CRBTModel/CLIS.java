package com.neo.media.CRBTModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by QQ on 7/24/2017.
 */

public class CLIS {
    @SerializedName("CLI")
    List<CLI> CLI;


    public CLIS() {
    }


    public List<com.neo.media.CRBTModel.CLI> getCLI() {
        return CLI;
    }

    public void setCLI(List<com.neo.media.CRBTModel.CLI> CLI) {
        this.CLI = CLI;
    }
}
