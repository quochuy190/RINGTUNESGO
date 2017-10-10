package com.neo.ringtunesgo.Model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by QQ on 9/2/2017.
 */

public class GroupName extends RealmObject implements Serializable{
    public GroupName(String sNameServer, String sNameLocal, String id_group) {
        this.sNameServer = sNameServer;
        this.sNameLocal = sNameLocal;
        this.id_group = id_group;
    }

    public GroupName() {
    }

    @PrimaryKey
    private String sNameServer;
    //
    private String sNameLocal;
    //
    private String id_group;

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
