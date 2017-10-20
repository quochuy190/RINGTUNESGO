package com.neo.media.CRBTModel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QQ on 7/19/2017.
 */

public class FUNDIAL_OFAPI {
    @SerializedName("RPLY")
    private RPLY rply;
    @SerializedName("req_id")
    private String req_id;
    @SerializedName("version")
    private String version;

    public static FUNDIAL_OFAPI getFUNDIAL_OFAPI (JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(),FUNDIAL_OFAPI.class);
    }
    public  static ArrayList<FUNDIAL_OFAPI> getAllFUNDIAL_OFAPI(String jsonArray) throws JSONException {
        ArrayList<FUNDIAL_OFAPI> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<FUNDIAL_OFAPI>>(){}.getType();

        Gson gson= new Gson();
        arrayList = gson.fromJson(jsonArray,type);
        Log.i("abc", ""+arrayList.size());

        return arrayList;
    }

    public FUNDIAL_OFAPI() {

    }

    public RPLY getRply() {
        return rply;
    }

    public void setRply(RPLY rply) {
        this.rply = rply;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
