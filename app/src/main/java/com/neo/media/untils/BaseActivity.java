package com.neo.media.untils;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseActivity extends AppCompatActivity {

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


    protected SweetAlertDialog dialog;
    private Handler StopDialogLoadingHandler = new Handler();


    public void showDialogLoading() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 20000);
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hideDialogLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    public void show_notification(String title, String contents){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(contents)
                .setConfirmText("Đóng")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }



}
