package com.neo.ringtunesgo.View.Splash_Screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.neo.ringtunesgo.ApiService.ApiService;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.untils.BaseActivity;

import io.realm.Realm;

public class SplashScreen extends BaseActivity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "SplashScreen";
    Realm realm;
   // public static Storage storage; // this Preference comes for free from the library
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent mainIntent = new Intent();
    ApiService apiService;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
       // setContentView(R.layout.activity_splash_screen);
        realm = RealmController.with(this).getRealm();
        apiService = new ApiService();
        mainIntent.setClass(SplashScreen.this, MainNavigationActivity.class);
        final SharedPreferences fr = getSharedPreferences("data", MODE_PRIVATE);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public void initData() {

    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */


}
