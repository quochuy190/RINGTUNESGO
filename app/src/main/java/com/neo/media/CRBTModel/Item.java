package com.neo.media.CRBTModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by QQ on 7/19/2017.
 */

public class Item implements Serializable {
    //name singer
    @SerializedName("content_artist")
    private String content_artist;
    @SerializedName("expiration")
    private String expiration;
/*    @SerializedName("billing_desc")
    private String billing_desc;*/
    //name song
    @SerializedName("content_title")
    private String content_title;
    // date expiration
    @SerializedName("expiration_date")
    private String expiration_date;
    //price
    @SerializedName("content_billing_desc")
    private String content_billing_desc;
    @SerializedName("content_id")
    private String content_id;
    public boolean is_check;

    private String product_name;
    private String singer_id;
    private String path;
    private String singer_name;
    private String image_url;
    public Item() {
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(String singer_id) {
        this.singer_id = singer_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public boolean is_check() {
        return is_check;
    }

    public void setIs_check(boolean is_check) {
        this.is_check = is_check;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getContent_artist() {
        return content_artist;
    }

    public void setContent_artist(String content_artist) {
        this.content_artist = content_artist;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }


    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getContent_billing_desc() {
        return content_billing_desc;
    }

    public void setContent_billing_desc(String content_billing_desc) {
        this.content_billing_desc = content_billing_desc;
    }
}
