package com.neo.media.RealmController;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.neo.media.CRBTModel.Info_User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by QQ on 8/17/2017.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }
    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
      //  realm.clear(Book.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Info_User> getBooks() {
        return realm.where(Info_User.class).findAll();
    }

    //query a single item with the given id
    public Info_User getBook(String id) {
        return realm.where(Info_User.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasBooks() {
        return !realm.allObjects(Info_User.class).isEmpty();
    }
}
