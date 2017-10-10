package com.neo.ringtunesgo.CRBTModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/19/2017.
 */

public class RPLY_PROFILE {

    @SerializedName("name")
    private String name;

    @SerializedName("SUBSCRIBER")
    private subscriber subscriber;


    public RPLY_PROFILE() {
    }

    public com.neo.ringtunesgo.CRBTModel.subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(com.neo.ringtunesgo.CRBTModel.subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
