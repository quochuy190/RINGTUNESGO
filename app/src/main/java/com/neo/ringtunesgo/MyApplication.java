package com.neo.ringtunesgo;

import android.app.Application;

import com.neo.ringtunesgo.CRBTModel.GROUP;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.CRBTModel.PROFILE;
import com.neo.ringtunesgo.Model.Ringtunes;

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
    @Override
    public void onCreate() {
        objGroup = new GROUP();
        profile_bundle = new PROFILE();
        listConllection = new ArrayList<>();
        player_ring = new Ringtunes();
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
