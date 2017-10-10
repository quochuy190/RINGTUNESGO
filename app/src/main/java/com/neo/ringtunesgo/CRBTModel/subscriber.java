package com.neo.ringtunesgo.CRBTModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/24/2017.
 */

public class subscriber {
    @SerializedName("ERROR")
    private String ERROR;
    @SerializedName("ITEMS")
    private Items Items;
    @SerializedName("ERROR_DESC")
    private String ERROR_DESC;
    @SerializedName("SUBID")
    private String SUBID;
    @SerializedName("GROUPS")
    private GROUPS groups;
    @SerializedName("PROFILES")
    private PROFILES profiles;
    // infomation
    @SerializedName("OPERATOR_PROMPT")
    private String OPERATOR_PROMPT;
    @SerializedName("REG_DATE")
    private String REG_DATE;
    @SerializedName("PREPAID")
    private String PREPAID;
    @SerializedName("SVC_STATUS")
    private String SVC_STATUS;
    @SerializedName("STATUS")
    private String STATUS;
    @SerializedName("LANG")
    private String LANG;
    @SerializedName("CORPORATE")
    private String CORPORATE;


    public subscriber() {
    }

    public String getOPERATOR_PROMPT() {
        return OPERATOR_PROMPT;
    }

    public void setOPERATOR_PROMPT(String OPERATOR_PROMPT) {
        this.OPERATOR_PROMPT = OPERATOR_PROMPT;
    }

    public String getREG_DATE() {
        return REG_DATE;
    }

    public void setREG_DATE(String REG_DATE) {
        this.REG_DATE = REG_DATE;
    }

    public String getPREPAID() {
        return PREPAID;
    }

    public void setPREPAID(String PREPAID) {
        this.PREPAID = PREPAID;
    }

    public String getSVC_STATUS() {
        return SVC_STATUS;
    }

    public void setSVC_STATUS(String SVC_STATUS) {
        this.SVC_STATUS = SVC_STATUS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getLANG() {
        return LANG;
    }

    public void setLANG(String LANG) {
        this.LANG = LANG;
    }

    public String getCORPORATE() {
        return CORPORATE;
    }

    public void setCORPORATE(String CORPORATE) {
        this.CORPORATE = CORPORATE;
    }

    public PROFILES getProfiles() {
        return profiles;
    }

    public void setProfiles(PROFILES profiles) {
        this.profiles = profiles;
    }

    public GROUPS getGroups() {
        return groups;
    }

    public void setGroups(GROUPS groups) {
        this.groups = groups;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public com.neo.ringtunesgo.CRBTModel.Items getItems() {
        return Items;
    }

    public void setItems(com.neo.ringtunesgo.CRBTModel.Items items) {
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
