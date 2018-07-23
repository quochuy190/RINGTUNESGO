package com.neo.media.CRBTModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/24/2017.
 */

public class GROUP {
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
    private CLIS clis;
    private int img_backgroup;
    private boolean is_add_group;
    public GROUP() {
    }

    public GROUP(String name, boolean is_add_group) {
        this.name = name;
        this.is_add_group = is_add_group;
    }

    public boolean isIs_add_group() {
        return is_add_group;
    }

    public void setIs_add_group(boolean is_add_group) {
        this.is_add_group = is_add_group;
    }

    public int getImg_backgroup() {
        return img_backgroup;
    }

    public void setImg_backgroup(int img_backgroup) {
        this.img_backgroup = img_backgroup;
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

    public CLIS getClis() {
        return clis;
    }

    public void setClis(CLIS clis) {
        this.clis = clis;
    }
}
