package com.neo.media.CRBTModel.Group_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by QQ on 7/24/2017.
 */

public class CLI_model implements Serializable {
    @SerializedName("caller_id")
    String caller_id;
    @SerializedName("caller_name")
    String caller_name;
    @SerializedName("ERROR")
    String ERROR;
    @SerializedName("ERROR_DESC")
    String ERROR_DESC;


    public CLI_model() {
    }

    public String getCaller_id() {
        return caller_id;
    }

    public void setCaller_id(String caller_id) {
        this.caller_id = caller_id;
    }

    public String getCaller_name() {
        return caller_name;
    }

    public void setCaller_name(String caller_name) {
        this.caller_name = caller_name;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getERROR_DESC() {
        return ERROR_DESC;
    }

    public void setERROR_DESC(String ERROR_DESC) {
        this.ERROR_DESC = ERROR_DESC;
    }
}
