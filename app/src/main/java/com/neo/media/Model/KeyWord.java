package com.neo.media.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoan123 on 1/11/17.
 */

public class KeyWord {

    @SerializedName("KEY_SEARCH")
    public String KEYWORD;
    @SerializedName("TONG")
    public String TONG;

    public KeyWord(String KEYWORD, String TONG) {
        this.KEYWORD = KEYWORD;
        this.TONG = TONG;
    }

    public KeyWord() {
    }

    private static KeyWord getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), KeyWord.class);
    }

    public static ArrayList<KeyWord> getLisElement(String jsonArray) throws JSONException {
        ArrayList<KeyWord> arrayList = new ArrayList<>();
        java.lang.reflect.Type type = new TypeToken<List<KeyWord>>() {
        }.getType();

        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);

        return arrayList;
    }


    public String getKEYWORD() {
        return KEYWORD;
    }

    public void setKEYWORD(String KEYWORD) {
        this.KEYWORD = KEYWORD;
    }

    public String getTONG() {
        return TONG;
    }

    public void setTONG(String TONG) {
        this.TONG = TONG;
    }
}
