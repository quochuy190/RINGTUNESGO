package com.neo.ringtunesgo.MainActivity;

import com.neo.ringtunesgo.CRBTModel.subscriber;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.Player.IPlayback;
import com.neo.ringtunesgo.Player.PlaybackService;

import java.util.List;

/**
 * Created by QQ on 7/20/2017.
 */

public interface MainActivityImpl {
    interface Presenter {
        void getSearch(String key, String page, String index);
        void getInfo_User(String sesionID, String msisdn);
        void init_service(String token, String version, String isModel, String model, String version_os);
        void onPlayToggle(IPlayback playback);

        void onPrevious(IPlayback playback);

        void onNext(IPlayback playback);
        void destroy();

        void subscribe();

        void unsubscribe();

        void bindPlaybackService();

        void unbindPlaybackService();
    }
    interface StartService{


    }

    interface View {
        void showListSearch(List<Ringtunes> ringtunes);
        void showInfo_User(subscriber subscriber);
        void show_init_service(String sUserNameId);

        void onPlaybackServiceBound(PlaybackService service);

        void onPlaybackServiceUnbound();
    }
}
