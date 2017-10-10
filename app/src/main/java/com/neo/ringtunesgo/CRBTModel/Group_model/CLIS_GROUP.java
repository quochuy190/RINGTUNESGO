package com.neo.ringtunesgo.CRBTModel.Group_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/24/2017.
 */

public class CLIS_GROUP {
    @SerializedName("CLI")
    CLI_model cli;


    public CLIS_GROUP() {
    }

    public CLI_model getCli() {
        return cli;
    }

    public void setCli(CLI_model cli) {
        this.cli = cli;
    }
}
