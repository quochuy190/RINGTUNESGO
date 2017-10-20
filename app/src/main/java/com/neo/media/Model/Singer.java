package com.neo.media.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QQ on 7/4/2017.
 */

public class Singer implements Serializable {
    @SerializedName("ID")
    private String Id;
    @SerializedName("SINGER_NAME")
    private String singer_name;
    @SerializedName("PHOTO")
    private String photo;
    @SerializedName("COUNT")
    private String count;
    @SerializedName("SINGER_NAME_S")
    private String singer_name_s;
    @SerializedName("HITS")
    private String hits;
    @SerializedName("FULL_NAME")
    private String FULL_NAME;
    @SerializedName("DESCRIPTION")
    private String DESCRIPTION;

    private static Singer getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), Singer.class);
    }

    public static ArrayList<Singer> getLisElement(String jsonArray) throws JSONException {
        ArrayList<Singer> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Singer>>() {
        }.getType();

        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);

        return arrayList;
    }

    public Singer() {
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSinger_name_s() {
        return singer_name_s;
    }

    public void setSinger_name_s(String singer_name_s) {
        this.singer_name_s = singer_name_s;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
}
