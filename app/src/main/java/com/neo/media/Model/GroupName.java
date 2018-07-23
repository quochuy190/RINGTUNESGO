package com.neo.media.Model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by QQ on 9/2/2017.
 */

public class GroupName extends RealmObject implements Serializable{
    public GroupName(String sNameServer, String sNameLocal, String id_group, int background) {
        this.sNameServer = sNameServer;
        this.sNameLocal = sNameLocal;
        this.id_group = id_group;
        this.background = background;
    }

    public GroupName(String sNameServer, String sNameLocal, String id_group, int background, boolean isCheck) {
        this.sNameServer = sNameServer;
        this.sNameLocal = sNameLocal;
        this.id_group = id_group;
        this.background = background;
        this.isCheck = isCheck;
    }

    public GroupName() {
    }

    @PrimaryKey
    private String sNameServer;
    //
    private String sNameLocal;
    //
    private String id_group;

    private int background;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public String getsNameServer() {
        return sNameServer;
    }

    public void setsNameServer(String sNameServer) {
        this.sNameServer = sNameServer;
    }

    public String getsNameLocal() {
        return sNameLocal;
    }

    public void setsNameLocal(String sNameLocal) {
        this.sNameLocal = sNameLocal;
    }
}
