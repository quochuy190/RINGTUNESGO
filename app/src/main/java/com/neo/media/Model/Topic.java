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
 * Created by QQ on 7/7/2017.
 */

public class Topic implements Serializable {
    @SerializedName("ID")
    private String Id;
    @SerializedName("PACKAGE_NAME")
    private String package_name;
    @SerializedName("PACKAGE_NAME_S")
    private String package_name_s;
    @SerializedName("DESCRIPTION")
    private String description;
    @SerializedName("COUNTER")
    private String counter;
    @SerializedName("HITS")
    private String hits;
    @SerializedName("PHOTO")
    private String photo;
    @SerializedName("BIGPHOTO")
    private String bigphoto;
    @SerializedName("TONG")
    private String sTONG;
    @SerializedName("THUMBNAIL_IMAGE")
    private String THUMBNAIL_IMAGE;
    @SerializedName("IMAGE")
    private String IMAGE;
    @SerializedName("NAME")
    private String NAME;

    private static Topic getObject (JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(),Topic.class);
    }


    public  static ArrayList<Topic> getList(String jsonArray) throws JSONException {
        ArrayList<Topic> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Topic>>(){}.getType();

        Gson gson= new Gson();
        arrayList = gson.fromJson(jsonArray,type);

        return arrayList;
    }

    public Topic() {

    }

    public String getTHUMBNAIL_IMAGE() {
        return THUMBNAIL_IMAGE;
    }

    public void setTHUMBNAIL_IMAGE(String THUMBNAIL_IMAGE) {
        this.THUMBNAIL_IMAGE = THUMBNAIL_IMAGE;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getsTONG() {
        return sTONG;
    }

    public void setsTONG(String sTONG) {
        this.sTONG = sTONG;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_name_s() {
        return package_name_s;
    }

    public void setPackage_name_s(String package_name_s) {
        this.package_name_s = package_name_s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBigphoto() {
        return bigphoto;
    }

    public void setBigphoto(String bigphoto) {
        this.bigphoto = bigphoto;
    }
}
