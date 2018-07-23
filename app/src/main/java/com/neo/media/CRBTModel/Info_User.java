package com.neo.media.CRBTModel;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by QQ on 8/17/2017.
 */

public class Info_User extends RealmObject implements Serializable {
    @PrimaryKey
    private String sPhone;
    //Chuỗi định danh request
    private String req_id;
    //trạng thái thuê bao (đang active, hoặc đã hủy)
    //2 = đang active
    //4 = đã hủy
    private String status;
    //trạng thái thuê bao(đang tạm ngưng dịch vụ, hoặc đang kích hoạt)
    //0 = đang tạm ngưng
    //1 = đang kích hoạt
    private String service_status;
    //kiểu thuê bao (thuê bao doanh nghiệp, hoặc thuê bao thuờng)
     /*  0 = thuê bao thuờng
    1 = thuê bao doanh nghiệp*/
    private String is_corporate;
    //ngày đăng ký
    private String register_date;
    //Kết quả giao dịch
    private String error_code;
    //Giải thích mã lỗi
    private String error_desc;

    private String HOTEN;

    private String MOBILE;

    private String SUB_REGISTED;

    private String SEX;

    private String BIRTHDAY;

    public Info_User() {
    }

    public String getHOTEN() {
        return HOTEN;
    }

    public void setHOTEN(String HOTEN) {
        this.HOTEN = HOTEN;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getSUB_REGISTED() {
        return SUB_REGISTED;
    }

    public void setSUB_REGISTED(String SUB_REGISTED) {
        this.SUB_REGISTED = SUB_REGISTED;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService_status() {
        return service_status;
    }

    public void setService_status(String service_status) {
        this.service_status = service_status;
    }

    public String getIs_corporate() {
        return is_corporate;
    }

    public void setIs_corporate(String is_corporate) {
        this.is_corporate = is_corporate;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_desc() {
        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }
}
