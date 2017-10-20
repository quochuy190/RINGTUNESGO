package com.neo.media.CRBTModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by QQ on 7/25/2017.
 */

public class PROFILE implements Serializable{
    //price songs
    @SerializedName("content_billing_category")
    String content_billing_category;
    // description price songs
    @SerializedName("content_billing_desc")
    String content_billing_desc;
    //-Là số điện thoại nếu caller_type=CLI, Là group id nếu caller_type=GROUP, Là giá trị ‘ALL’ nếu caller_type=DEFAULT
    @SerializedName("caller_id")
    String caller_id;
    // singer name
    @SerializedName("content_artist")
    String content_artist;
    //
    @SerializedName("profile_type")
    String profile_type;
    //loại content: TRACK = bài hát cụ thể, MYLIST = tòan bộ các bài hát trong bộ sưu tập, NONE = nhạc chuông chuẩn
    @SerializedName("content_entity")
    String content_entity;
    @SerializedName("content_title")
    String content_title;
    @SerializedName("id")
    String profile_id;
    @SerializedName("time_category")
    String time_category;
    @SerializedName("caller_type")
    String caller_type;
    @SerializedName("TIME_BASE")
    TimeBase timeBase;
    @SerializedName("content_id")
    String content_id;
    String caller_name;


    public String getCaller_name() {
        return caller_name;
    }

    public void setCaller_name(String caller_name) {
        this.caller_name = caller_name;
    }

    public String getContent_billing_category() {
        return content_billing_category;
    }

    public void setContent_billing_category(String content_billing_category) {
        this.content_billing_category = content_billing_category;
    }

    public String getContent_billing_desc() {
        return content_billing_desc;
    }

    public void setContent_billing_desc(String content_billing_desc) {
        this.content_billing_desc = content_billing_desc;
    }

    public String getCaller_id() {
        return caller_id;
    }

    public void setCaller_id(String caller_id) {
        this.caller_id = caller_id;
    }

    public String getContent_artist() {
        return content_artist;
    }

    public void setContent_artist(String content_artist) {
        this.content_artist = content_artist;
    }

    public String getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(String profile_type) {
        this.profile_type = profile_type;
    }

    public String getContent_entity() {
        return content_entity;
    }

    public void setContent_entity(String content_entity) {
        this.content_entity = content_entity;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getTime_category() {
        return time_category;
    }

    public void setTime_category(String time_category) {
        this.time_category = time_category;
    }

    public String getCaller_type() {
        return caller_type;
    }

    public void setCaller_type(String caller_type) {
        this.caller_type = caller_type;
    }

    public TimeBase getTimeBase() {
        return timeBase;
    }

    public void setTimeBase(TimeBase timeBase) {
        this.timeBase = timeBase;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }
}
