package com.neo.media.CRBTModel.Group_model;

import com.google.gson.annotations.SerializedName;
import com.neo.media.CRBTModel.Items;
import com.neo.media.CRBTModel.PROFILES;

/**
 * Created by QQ on 7/24/2017.
 */

public class subscriber_model {
    @SerializedName("ERROR")
    private String ERROR;
    @SerializedName("ITEMS")
    private Items Items;
    @SerializedName("ERROR_DESC")
    private String ERROR_DESC;
    @SerializedName("SUBID")
    private String SUBID;
    @SerializedName("GROUPS")
    private GROUPS_MODEL groups;
    @SerializedName("PROFILES")
    private PROFILES profiles;

    public subscriber_model() {
    }

    public PROFILES getProfiles() {
        return profiles;
    }

    public void setProfiles(PROFILES profiles) {
        this.profiles = profiles;
    }

    public GROUPS_MODEL getGroups() {
        return groups;
    }

    public void setGroups(GROUPS_MODEL groups) {
        this.groups = groups;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public com.neo.media.CRBTModel.Items getItems() {
        return Items;
    }

    public void setItems(com.neo.media.CRBTModel.Items items) {
        Items = items;
    }

    public String getERROR_DESC() {
        return ERROR_DESC;
    }

    public void setERROR_DESC(String ERROR_DESC) {
        this.ERROR_DESC = ERROR_DESC;
    }

    public String getSUBID() {
        return SUBID;
    }

    public void setSUBID(String SUBID) {
        this.SUBID = SUBID;
    }
}
