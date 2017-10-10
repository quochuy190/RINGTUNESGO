package com.neo.ringtunesgo.Model;

/**
 * Created by QQ on 8/21/2017.
 */

public class DrawerItem {
    String ItemName;
    int imgResID;

    public DrawerItem() {
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
