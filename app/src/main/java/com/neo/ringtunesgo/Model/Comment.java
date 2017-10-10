package com.neo.ringtunesgo.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

/**
 * Created by QQ on 7/28/2017.
 */

public class Comment implements Serializable {
    @SerializedName("ID")
    String ID;
    @SerializedName("OBJECT_ID")
    String OBJECT_ID;
    @SerializedName("OBJECT_TYPE_ID")
    String OBJECT_TYPE_ID;
    @SerializedName("CREATE_DATE")
    String CREATE_DATE;
    @SerializedName("IS_PUBLIC")
    String IS_PUBLIC;
    @SerializedName("CONTENT")
    String CONTENT;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("CHANNEL")
    String CHANNEL;
    @SerializedName("AUTHOR")
    String AUTHOR;
    @SerializedName("PARENT_OBJECT_ID")
    String PARENT_OBJECT_ID;
    @SerializedName("NAME_")
    String NAME_;
    @SerializedName("CREATED_DATE")
    String CREATED_DATE;
    @SerializedName("FULL_NAME")
    String FULL_NAME;
    @SerializedName("PHOTO")
    String PHOTO;

    public Comment() {
    }

    private static Comment getObjectEntity (JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(),Comment.class);
    }

    public  static ArrayList<Comment> getListEntity(String jsonArray) throws JSONException {
        ArrayList<Comment> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Comment>>(){}.getType();

        Gson gson= new Gson();
        arrayList = gson.fromJson(jsonArray,type);

        return arrayList;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOBJECT_ID() {
        return OBJECT_ID;
    }

    public void setOBJECT_ID(String OBJECT_ID) {
        this.OBJECT_ID = OBJECT_ID;
    }

    public String getOBJECT_TYPE_ID() {
        return OBJECT_TYPE_ID;
    }

    public void setOBJECT_TYPE_ID(String OBJECT_TYPE_ID) {
        this.OBJECT_TYPE_ID = OBJECT_TYPE_ID;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getIS_PUBLIC() {
        return IS_PUBLIC;
    }

    public void setIS_PUBLIC(String IS_PUBLIC) {
        this.IS_PUBLIC = IS_PUBLIC;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCHANNEL() {
        return CHANNEL;
    }

    public void setCHANNEL(String CHANNEL) {
        this.CHANNEL = CHANNEL;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getPARENT_OBJECT_ID() {
        return PARENT_OBJECT_ID;
    }

    public void setPARENT_OBJECT_ID(String PARENT_OBJECT_ID) {
        this.PARENT_OBJECT_ID = PARENT_OBJECT_ID;
    }

    public String getNAME_() {
        return NAME_;
    }

    public void setNAME_(String NAME_) {
        this.NAME_ = NAME_;
    }

    public String getCREATED_DATE() {
        return CREATED_DATE;
    }

    public void setCREATED_DATE(String CREATED_DATE) {
        this.CREATED_DATE = CREATED_DATE;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(String PHOTO) {
        this.PHOTO = PHOTO;
    }
}
