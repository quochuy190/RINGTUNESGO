package com.neo.ringtunesgo.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 9/5/2017.
 */

public class Banner {
    @SerializedName("ID")
    private String Id;
    @SerializedName("IMAGE_FILE")
    private String image_file;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage_file() {
        return image_file;
    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }
}
