package com.neo.ringtunesgo.Player;

import android.support.annotation.Nullable;

import com.neo.ringtunesgo.Model.PlayList;
import com.neo.ringtunesgo.Model.Ringtunes;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/5/16
 * Time: 6:02 PM
 * Desc: IPlayer
 */
public interface IPlayback {

    //cái này là model ah

    void setPlayList(PlayList list);

    boolean play();

    boolean play(PlayList list);

    boolean play(PlayList list, int startIndex);

    boolean play(Ringtunes song);

    boolean playLast();

    boolean playNext();


    boolean pause();

    boolean isPlaying();

    int getProgress();

    int getDuration();

    Ringtunes getPlayingSong();

    boolean seekTo(int progress);

    void setPlayMode(PlayMode playMode);

    void registerCallback(Callback callback);

    void unregisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();

    String getUrlNotification();

    interface Callback {

        void onSwitchLast(@Nullable Ringtunes last);

        void onSwitchNext(@Nullable Ringtunes next);

        void onComplete(@Nullable Ringtunes next);

        void onPlayStatusChanged(boolean isPlaying);

        void PlayerCompete(boolean isComplete);
    }
}
