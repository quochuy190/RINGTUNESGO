package com.neo.ringtunesgo.Model;

/**
 * Created by QQ on 7/17/2017.
 */

public class PhoneContactModel {
    String name, phoneNumber, avatarSrc;
    boolean isChecked;

    public PhoneContactModel(String name, String phoneNumber, String avatarSrc) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.avatarSrc = avatarSrc;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }
}
