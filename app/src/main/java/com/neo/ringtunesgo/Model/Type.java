package com.neo.ringtunesgo.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QQ on 7/4/2017.
 */

public class Type implements Serializable {
    @SerializedName("ID")
    private String Id;
    @SerializedName("NAME")
    private String name;
    @SerializedName("THUMBNAIL_IMAGE")
    private String thumbnal_image;
    @SerializedName("COUNT")
    private String count;
    @SerializedName("NAME_S")
    private String name_s;
    @SerializedName("HITS")
    private String hits;
    @SerializedName("DESCRIPTION")
    private String description;


    private static Type getObject (JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(),Type.class);
    }

    public  static ArrayList<Type> getLisElement(String jsonArray) throws JSONException {
        ArrayList<Type> arrayList = new ArrayList<>();
        java.lang.reflect.Type type = new TypeToken<List<Type>>(){}.getType();

            Gson gson= new Gson();
           arrayList = gson.fromJson(jsonArray,type);

        return arrayList;
    }

    public Type() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnal_image() {
        return thumbnal_image;
    }

    public void setThumbnal_image(String thumbnal_image) {
        this.thumbnal_image = thumbnal_image;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName_s() {
        return name_s;
    }

    public void setName_s(String name_s) {
        this.name_s = name_s;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
