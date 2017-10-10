package com.neo.ringtunesgo.CRBTModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QQ on 7/19/2017.
 */

public class Items {
    @SerializedName("total")
    private String total;
    @SerializedName("ITEM")
    private Item objItem;
    @SerializedName("mylist_type")
    private String mylist_type;

    public Items() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Item getObjItem() {
        return objItem;
    }

    public void setObjItem(Item objItem) {
        this.objItem = objItem;
    }

    public String getMylist_type() {
        return mylist_type;
    }

    public void setMylist_type(String mylist_type) {
        this.mylist_type = mylist_type;
    }
}
