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
 * Created by QQ on 7/8/2017.
 */

public class Ringtunes  implements Serializable {
    public static Ringtunes ringtunes ;
    public static Ringtunes getInstance() {
        if (ringtunes == null) {
            synchronized (Ringtunes.class) {
                if (ringtunes == null) {
                    ringtunes = new Ringtunes();
                }
            }
        }
        return ringtunes;
    }

    @SerializedName("ID")
    private String Id;
    @SerializedName("PRODUCT_NAME")
    private String product_name;
    @SerializedName("DESCRIPTION")
    private String description;
    @SerializedName("CP_NAME")
    private String cp_name;
    @SerializedName("SINGER_ID")
    private String singer_id;
    @SerializedName("PATH")
    private String path;
    @SerializedName("CREATE_DATE")
    private String create_date;
    @SerializedName("MODIFY_DATE")
    private String modify_date;
    @SerializedName("PRODUCT_CODE")
    private String product_code;
    @SerializedName("STATUS_ID")
    private String status_id;
    @SerializedName("IS_NEW")
    private String is_new;
    @SerializedName("PRICE")
    private String price;
    @SerializedName("IMAGE_FILE")
    private String image_file;
    @SerializedName("PRODUCT_NAME_NO")
    private String product_name_no;
    @SerializedName("SINGER_NAME")
    private String singer_name;
    @SerializedName("PRODUCT_NAME_S")
    private String product_name_s;
    @SerializedName("HITS")
    private String hist;
    @SerializedName("PATHFULLTRACK")
    private String pathfulltrack;
    @SerializedName("COUNTER")
    private String COUNTER;
    private boolean isFull;
    private int duration;
    private String sExpiration;
    private boolean isFavorite;

    private boolean isPlaying;

    private static Ringtunes getObjectEntity (JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(),Ringtunes.class);
    }

    public  static ArrayList<Ringtunes> getListEntity(String jsonArray) throws JSONException {
        ArrayList<Ringtunes> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<Ringtunes>>(){}.getType();

        Gson gson= new Gson();
        arrayList = gson.fromJson(jsonArray,type);

        return arrayList;
    }

    public Ringtunes() {
    }

    public Ringtunes(String id, String image_file) {
        Id = id;
        this.image_file = image_file;
    }

    public static Ringtunes getRingtunes() {
        return ringtunes;
    }

    public String getsExpiration() {
        return sExpiration;
    }

    public void setsExpiration(String sExpiration) {
        this.sExpiration = sExpiration;
    }

    public static void setRingtunes(Ringtunes ringtunes) {
        Ringtunes.ringtunes = ringtunes;
    }

    public String getCOUNTER() {
        return COUNTER;
    }

    public void setCOUNTER(String COUNTER) {
        this.COUNTER = COUNTER;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getPathfulltrack() {
        return pathfulltrack;
    }

    public void setPathfulltrack(String pathfulltrack) {
        this.pathfulltrack = pathfulltrack;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCp_name() {
        return cp_name;
    }

    public void setCp_name(String cp_name) {
        this.cp_name = cp_name;
    }

    public String getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(String singer_id) {
        this.singer_id = singer_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_file() {
        return image_file;
    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }

    public String getProduct_name_no() {
        return product_name_no;
    }

    public void setProduct_name_no(String product_name_no) {
        this.product_name_no = product_name_no;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getProduct_name_s() {
        return product_name_s;
    }

    public void setProduct_name_s(String product_name_s) {
        this.product_name_s = product_name_s;
    }

    public String getHist() {
        return hist;
    }

    public void setHist(String hist) {
        this.hist = hist;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
