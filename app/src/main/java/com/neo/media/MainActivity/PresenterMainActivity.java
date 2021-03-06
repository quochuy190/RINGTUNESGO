package com.neo.media.MainActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.neo.media.Activity.ActivityMainHome;
import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.KeyWord;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.Player.IPlayback;
import com.neo.media.Player.PlaybackService;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by QQ on 7/20/2017.
 */

public class PresenterMainActivity implements MainActivityImpl.Presenter {
    private Context mContext;
    private ApiService apiService;

    private ActivityMainHome viewMain;
    private CompositeSubscription mSubscriptions;
    Subscription subscription;
    private PlaybackService mPlaybackService;
    private boolean mIsServiceBound;
    //private ActivitySearch viewSearch;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i("abc", "ServiceConnection---------------");
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mPlaybackService = ((PlaybackService.LocalBinder) service).getService();
            //viewMain.onPlaybackServiceBound(mPlaybackService);
           // viewMain.onSongUpdated(mPlaybackService.getPlayingSong());
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mPlaybackService = null;
         //   viewMain.onPlaybackServiceUnbound();
        }
    };

    public PresenterMainActivity(ActivityMainHome viewMain) {
        this.viewMain = viewMain;
        apiService = new ApiService();
    }
    @Override
    public void getSearch(String key, String page, String index) {
        String Service = "search_main_service";
        String Provider = "default";
        String ParamSize = "4";
        String P1 = key;
        String P2 = page;
        String P3 = index;

        apiService.getSearchRingtunes(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {

                //viewSearch.showListSearch(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3);
    }

    @Override
    public void getInfo_User(String sesionID, String msisdn) {
        String Service = "GET_SUBSCRIBER_DETAILS";
        String Provider = "default";
        String ParamSize = "2";
        String P1 = sesionID;
        String P2 = msisdn;

        apiService.get_SUBSCRIBER_DETAILS(new CallbackData<subscriber>() {
            @Override
            public void onGetDataSuccess(ArrayList<subscriber> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(subscriber Object) {
                if (Object != null && Object.getSUBID().length() > 0)
                    viewMain.showInfo_User(Object);

            }
        }, Service, Provider, ParamSize, P1, P2);
    }
    //update token

    public void update_token(String token, String user_id) {
        String Service = "update_token";
        String Provider = "default";
        String ParamSize = "2";

        apiService.update_token(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

                    viewMain.show_update_token(Object);


            }
        }, Service,Provider, ParamSize, token, user_id);
    }

    @Override
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

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                // SharedPreferences fr = getSharedPreferences("Token", MODE_PRIVATE);
                if (Object.length()>0&&Object!=null){
                    viewMain.show_init_service(Object);
                }

            }
        }, Service,Provider, ParamSize, token, version,isModel,model,version_os);
    }

    @Override
    public void onPlayToggle(IPlayback playback) {

    }

    @Override
    public void onPrevious(IPlayback playback) {

    }

    @Override
    public void onNext(IPlayback playback) {

    }

    @Override
    public void destroy() {

        subscription.unsubscribe();
    }


    @Override
    public void subscribe() {
        bindPlaybackService();
       // retrieveLastPlayMode();

        // TODO
        if (mPlaybackService != null && mPlaybackService.isPlaying()) {
           // mView.onSongUpdated(mPlaybackService.getPlayingSong());
        } else {
            // - load last play list/folder/song
        }
    }

    @Override
    public void unsubscribe() {
        unbindPlaybackService();
        // Release context reference
        mContext = null;
       // viewMain = null;
        mSubscriptions.clear();
    }

    @Override
    public void bindPlaybackService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        mContext.bindService(new Intent(mContext, PlaybackService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsServiceBound = true;
    }

    @Override
    public void unbindPlaybackService() {
        if (mIsServiceBound) {
            // Detach our existing connection.
            mContext.unbindService(mConnection);
            mIsServiceBound = false;
        }
    }

    public void search_keyword_top(String userid) {
        //fragmentSearchNew.showDialogLoading();
        String Service = "search_keyword_top";
        String Provider = "default";
        String ParamSize = "1";
        apiService.api_search_keyword_top(new CallbackData<KeyWord>() {
            @Override
            public void onGetDataSuccess(ArrayList<KeyWord> arrayList) {
                //fragmentSearchNew.hideDialogLoading();
                if (arrayList.size()>0){
                    MyApplication.listKeyword.clear();
                    MyApplication.listKeyword.addAll(arrayList);
                    //fragmentSearchNew.show_search_keyword(arrayList);
                }else {

                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                //fragmentSearchNew.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(KeyWord Object) {
               // fragmentSearchNew.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, userid);
    }

    public void api_checkver(String version, String user_name) {
        String Service = "CHECKVER";
        String Provider = "default";
        String ParamSize = "2";

        apiService.api_checkver(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                viewMain.show_checkver(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service,Provider, ParamSize, version, user_name);
    }
}
