package com.neo.ringtunesgo.Model;

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

public class Album implements Serializable {
    @SerializedName("ID")
    private String Id;
    @SerializedName("PACKAGE_NAME")
    private String package_name;
    @SerializedName("PACKAGE_CODE")
    private String package_code;
    @SerializedName("PRICE")
    private String price;
    @SerializedName("COUNTER")
    private String counter;
    @SerializedName("HITS")
    private String hits;
    @SerializedName("PHOTO")
    private String photo;
    @SerializedName("BIGPHOTO")
    private String bigphoto;

    private static Album getAlbum (JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(),Album.class);
    }

    public  static ArrayList<Album> getAllAlbum(String jsonArray) throws JSONException {
        ArrayList<Album> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Album>>(){}.getType();

            Gson gson= new Gson();
           arrayList = gson.fromJson(jsonArray,type);

        return arrayList;
    }

    public Album() {
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

    public String getPackage_code() {
        return package_code;
    }

    public void setPackage_code(String package_code) {
        this.package_code = package_code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
