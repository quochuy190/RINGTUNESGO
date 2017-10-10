package com.neo.ringtunesgo.untils;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity extends ActionBarActivity {

    protected AlertDialog alertDialog;
    boolean layout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        addSubscription(subscribeEvents());

        onPostSetContentView(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            handleDeepLinkData(intent.getData());
        }
        setContentView(setContentViewId());
        ButterKnife.bind(this);
//        Fabric.with(getApplicationContext(), new Crashlytics());
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/PTSerif_Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
//        alertDialog = DialogUtil.dontDialog(this, "", getString(R.string.check_config_internet));
        initData();
    }

    public void setLayout(boolean layout) {
        this.layout = layout;
    }

    protected void onPostSetContentView(Bundle savedInstanceState) {

    }

    protected void handleDeepLinkData(Uri uri) {
        Log.i("Log","uri: " + uri.toString());
    }

    public abstract int setContentViewId();

    public abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    private CompositeSubscription mSubscriptions;


    protected Subscription subscribeEvents() {
        return null;
    }

    protected void addSubscription(Subscription subscription) {
        if (subscription == null) return;
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
        mSubscriptions.add(subscription);
    }


}
