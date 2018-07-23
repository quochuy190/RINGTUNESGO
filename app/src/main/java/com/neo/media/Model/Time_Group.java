package com.neo.media.Model;

/**
 * Created by QQ on 11/28/2017.
 */

public class Time_Group {
    private String txt_time;
    private boolean isCheck;
    private String from_time;
    private String to_time;

    public Time_Group(String txt_time, boolean isCheck, String from_time, String to_time) {
        this.txt_time = txt_time;
        this.isCheck = isCheck;
        this.from_time = from_time;
        this.to_time = to_time;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public Time_Group() {
    }

    public String getTxt_time() {
        return txt_time;
    }

    public void setTxt_time(String txt_time) {
        this.txt_time = txt_time;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
