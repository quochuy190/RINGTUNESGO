package com.neo.media;

import android.app.Application;

import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Model.Ringtunes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by QQ on 8/17/2017.
 */

public class MyApplication extends Application {
    public static List<Item> listConllection;
    public static Ringtunes player_ring;
    public static PROFILE profile_bundle;
    public static GROUP objGroup;
    public static List<Ringtunes> lisRingtunesNew;
    public static List<CLI> listCLI;
    @Override
    public void onCreate() {
        objGroup = new GROUP();
        profile_bundle = new PROFILE();
        listConllection = new ArrayList<>();
        player_ring = new Ringtunes();
        lisRingtunesNew = new ArrayList<>();
        listCLI = new ArrayList<>();
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
