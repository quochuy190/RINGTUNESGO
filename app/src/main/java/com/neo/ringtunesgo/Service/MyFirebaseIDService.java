package com.neo.ringtunesgo.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by QQ on 8/29/2017.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    public static String TAG= MyFirebaseIDService.class.getSimpleName();
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token= FirebaseInstanceId.getInstance().getToken();

    }
}
