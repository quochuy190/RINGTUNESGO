package com.neo.media.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 9/5/2017.
 */

public class Banner {
    @SerializedName("ID")
    private String Id;
    @SerializedName("IMAGE")
    private String image;
    @SerializedName("TYPE")
    private String TYPE;
    @SerializedName("SUBTYPE")
    private String SUBTYPE;
    @SerializedName("NAME")
    private String NAME;
    @SerializedName("IMAGE_FILE")
    private String IMAGE_FILE;

    public String getIMAGE_FILE() {
        return IMAGE_FILE;
    }

    public void setIMAGE_FILE(String IMAGE_FILE) {
        this.IMAGE_FILE = IMAGE_FILE;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getSUBTYPE() {
        return SUBTYPE;
    }

    public void setSUBTYPE(String SUBTYPE) {
        this.SUBTYPE = SUBTYPE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
