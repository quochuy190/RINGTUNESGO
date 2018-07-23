package com.neo.media.Service;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.neo.media.ApiService.ApiService;
import com.neo.media.BuildConfig;
import com.neo.media.Listener.CallbackData;

import java.util.ArrayList;

import me.alexrs.prefs.lib.Prefs;

/**
 * Created by QQ on 8/29/2017.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIDService";
    String VERSION_OS = "";
    String ISMODEL = "";
    String VERSION = "";
    String MODEL = "";
    ApiService apiService;
    public SharedPreferences fr;
    String mToken;
   // public static String TAG= MyFirebaseIDService.class.getSimpleName();
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        apiService = new ApiService();
        getInfoDevice();

        fr = getSharedPreferences("data", MODE_PRIVATE);
        mToken= FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "onTokenRefresh: "+mToken);
        if (mToken!=null&&mToken.length()>0){
            init_service(mToken, VERSION, ISMODEL, MODEL, VERSION_OS );
        }
    }
    public void init_service(String token, String version, String isModel, String model, String version_os) {
        String Service = "init_service";
        String Provider = "default";
        String ParamSize = "5";
        apiService.init_service(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {
                Log.i(TAG, "onGetDataFault: ");
            }
            @Override
            public void onGetObjectDataSuccess(String sUserNameId) {
                Log.i(TAG, "onGetObjectDataSuccess: ");
                Prefs.with(getApplicationContext()).save("user_id", sUserNameId);
                Prefs.with(getApplicationContext()).save("token_sucsess", true);
                Prefs.with(getApplicationContext()).save("is_user_id", true);
            }
        }, Service,Provider, ParamSize, token, version,isModel,model,version_os);
    }

    public void getInfoDevice() {
        int versionCode = BuildConfig.VERSION_CODE;
        VERSION = BuildConfig.VERSION_NAME;
        String _OSVERSION = System.getProperty("os.version");
        //phiên ban andoird
        String _RELEASE = android.os.Build.VERSION.RELEASE;
        String _DEVICE = android.os.Build.DEVICE;
        //đời máy
        String _MODEL = android.os.Build.MODEL;
        String _PRODUCT = android.os.Build.PRODUCT;
        //hãng máy
        String BRAND = android.os.Build.BRAND;

        String _DISPLAY = android.os.Build.DISPLAY;
        String _CPU_ABI = android.os.Build.CPU_ABI;
        String _CPU_ABI2 = android.os.Build.CPU_ABI2;
        String _UNKNOWN = android.os.Build.UNKNOWN;
        String _HARDWARE = android.os.Build.HARDWARE;
        String _ID = android.os.Build.ID;
        String _MANUFACTURER = android.os.Build.MANUFACTURER;
        String _SERIAL = android.os.Build.SERIAL;
        String _USER = android.os.Build.USER;
        String _HOST = android.os.Build.HOST;
        MODEL = BRAND + " " + _MODEL;
        VERSION_OS = _RELEASE;
        ISMODEL = "2";
    }
}
