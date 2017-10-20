package com.neo.media.CRBTModel.Group_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/24/2017.
 */

public class GROUP_MODEL {
    @SerializedName("id")
    private String id;
    @SerializedName("content_id")
    private String content_id;
    @SerializedName("num_of_clis")
    private String num_of_clis;
    //name song
    @SerializedName("name")
    private String name;
    // date expiration
    @SerializedName("type")
    private String type;
    //price
    @SerializedName("CLIS")
    private CLIS_GROUP clis;

    public GROUP_MODEL() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getNum_of_clis() {
        return num_of_clis;
    }

    public void setNum_of_clis(String num_of_clis) {
        this.num_of_clis = num_of_clis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CLIS_GROUP getClis() {
        return clis;
    }

    public void setClis(CLIS_GROUP clis) {
        this.clis = clis;
    }
}
