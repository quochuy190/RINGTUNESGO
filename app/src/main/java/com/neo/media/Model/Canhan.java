package com.neo.media.Model;

/**
 * Created by QQ on 11/27/2017.
 */

public class Canhan {
    private String name;
    private int img_photo;

    public Canhan(String name, int img_photo) {
        this.name = name;
        this.img_photo = img_photo;
    }

    public Canhan() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg_photo() {
        return img_photo;
    }

    public void setImg_photo(int img_photo) {
        this.img_photo = img_photo;
    }
}
