package com.neo.media;

import android.app.Application;

import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Model.KeyWord;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Topic;

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
    public static List<Topic> lisHotTopic;
    public static ArrayList<PhoneContactModel> datas = new ArrayList<>();
    public static List<PhoneContactModel> listGitSongs = new ArrayList<>();
    public static ArrayList<PhoneContactModel> datasvina = new ArrayList<>();
    public static List<KeyWord> listKeyword;
    public static Ringtunes objRingGift;
    public static boolean isHome = false;
    public static PhoneContactModel objPhone;
    public static String sOption_getPhone;
    public static List<PhoneContactModel> list_ct_add_group;
    public static List<Ringtunes> lisPlayRing;
    public static int iLoop =1;
    public static String img_banner_favorite= "";
    public static boolean is_get_sub_detail = false;
    public static List<Item> lisItem;
    public static List<Topic> lisPromotion;
    @Override
    public void onCreate() {
        lisPromotion = new ArrayList<>();
        lisPlayRing= new ArrayList<>();
        list_ct_add_group = new ArrayList<>();
        objRingGift = new Ringtunes();
        listKeyword = new ArrayList<>();
        lisHotTopic = new ArrayList<>();
        objGroup = new GROUP();
        profile_bundle = new PROFILE();
        listConllection = new ArrayList<>();
        player_ring = new Ringtunes();
        lisRingtunesNew = new ArrayList<>();
        objPhone = new PhoneContactModel();
        listCLI = new ArrayList<>();
        lisItem = new ArrayList<>();
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
