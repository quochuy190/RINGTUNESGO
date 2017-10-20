package com.neo.media.Model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by QQ on 9/2/2017.
 */

public class Login extends RealmObject implements Serializable{
    @PrimaryKey
    private String sUserName;
    //
    private String sPassWord;
    //
    private String sToken;
    //
    private String sSessinonID;
    // Time get Token
    private String sTimeLogin;
    // Đăng nhập hay chưa
    private boolean isLogin;
    // Session còn tồn tại hay không
    private boolean isSessionID;
    // lấy user lần đầu khi cài app
    private boolean is_UserID;

    private String msisdn;

    public String getsUserName() {
        return sUserName;
    }

    public Login() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }

    public String getsPassWord() {
        return sPassWord;
    }

    public void setsPassWord(String sPassWord) {
        this.sPassWord = sPassWord;
    }

    public String getsToken() {
        return sToken;
    }

    public void setsToken(String sToken) {
        this.sToken = sToken;
    }

    public String getsSessinonID() {
        return sSessinonID;
    }

    public void setsSessinonID(String sSessinonID) {
        this.sSessinonID = sSessinonID;
    }

    public String getsTimeLogin() {
        return sTimeLogin;
    }

    public void setsTimeLogin(String sTimeLogin) {
        this.sTimeLogin = sTimeLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isSessionID() {
        return isSessionID;
    }

    public void setSessionID(boolean sessionID) {
        isSessionID = sessionID;
    }

    public boolean is_UserID() {
        return is_UserID;
    }

    public void setIs_UserID(boolean is_UserID) {
        this.is_UserID = is_UserID;
    }
}
