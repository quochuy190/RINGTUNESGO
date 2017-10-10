package com.neo.ringtunesgo.CRBTModel.MetaData;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 8/14/2017.
 */

public class SUBID {
    @SerializedName("content")
    String content;
    @SerializedName("price")
    String price;
    @SerializedName("is_reg")
    String is_reg;
    @SerializedName("expiration")
    String expiration;

    public SUBID() {
    }

    public SUBID(String content, String price, String is_reg, String expiration) {
        this.content = content;
        this.price = price;
        this.is_reg = is_reg;
        this.expiration = expiration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIs_reg() {
        return is_reg;
    }

    public void setIs_reg(String is_reg) {
        this.is_reg = is_reg;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
