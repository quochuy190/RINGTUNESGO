package com.neo.ringtunesgo.CRBTModel.Group_model;

import com.google.gson.annotations.SerializedName;
import com.neo.ringtunesgo.CRBTModel.Items;

/**
 * Created by QQ on 7/19/2017.
 */

public class RPLY_model {
    @SerializedName("ITEMS")
    private Items items;
    @SerializedName("ERROR")
    private String error;
    @SerializedName("name")
    private String name;
    @SerializedName("ERROR_DESC")
    private String ERROR_DESC;
    @SerializedName("SUBSCRIBER")
    private subscriber_model subscriber;



    public RPLY_model() {
    }

    public subscriber_model getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(subscriber_model subscriber) {
        this.subscriber = subscriber;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getERROR_DESC() {
        return ERROR_DESC;
    }

    public void setERROR_DESC(String ERROR_DESC) {
        this.ERROR_DESC = ERROR_DESC;
    }

   /* public com.neo.ringtunesgo.CRBTModel.SUBSCRIBER getSUBSCRIBER() {
        return SUBSCRIBER;
    }

    public void setSUBSCRIBER(com.neo.ringtunesgo.CRBTModel.SUBSCRIBER SUBSCRIBER) {
        this.SUBSCRIBER = SUBSCRIBER;
    }*/
}
