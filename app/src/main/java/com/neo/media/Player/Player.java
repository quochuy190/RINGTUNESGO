package com.neo.media.Player;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.neo.media.ApiService.ApiService;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.PlayList;
import com.neo.media.Model.Ringtunes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.util.Log.i;


/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/5/16
 * Time: 5:57 PM
 * Desc: Player
 */
public class Player implements IPlayback, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    // public static String TAG = Player.class.getSimpleName();
    FragmentDetailBuySongs fragmentDetailBuySongs;

    public Player(FragmentDetailBuySongs fragmentDetailBuySongs) {
        this.fragmentDetailBuySongs = fragmentDetailBuySongs;
        mPlayer = new MediaPlayer();
        mPlayList = new PlayList();
        mPlayer.setOnCompletionListener(this);
    }

    private static final String TAG = "Player";

    private static volatile Player sInstance;

    private MediaPlayer mPlayer;

    private PlayList mPlayList;
    // Default size 2: for service and UI
    private List<Callback> mCallbacks = new ArrayList<>(2);

    // Player status
    private boolean isPaused;

    private Player() {
        mPlayer = new MediaPlayer();
        mPlayList = new PlayList();
        mPlayer.setOnCompletionListener(this);
    }

    public static Player getInstance(Context context) {
        if (sInstance == null) {
            synchronized (Player.class) {
                if (sInstance == null) {
                    sInstance = new Player();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void setPlayList(PlayList list) {
        if (list == null) {
            list = new PlayList();
        }
        mPlayList = list;
    }
    public void log_Info_Charge_Server(String P1, String P2, String P3, String P4, String P5, String P6,String P7, String P8) {
        ApiService apiService = new ApiService();
        String Service = "log_info_charge_service";
        String Provider = "default";
        String ParamSize = "8";
        apiService.log_info_charge_service(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4, P5, P6,P7,P8);
    }

    @Override
    public boolean play() {
        i("isPaused------>", "" + isPaused);
        if (isPaused) {
            mPlayer.start();
            notifyPlayStatusChanged(true);
            isPaused = false;
            return true;
        }
        if (mPlayList.prepare()) {
            Ringtunes song = mPlayList.getCurrentSong();
            i(TAG, "playsong ------->>>>" + song.getProduct_name());
            Log.i(TAG, "playsong ------->>>>" + song.getPath());
            log_Info_Charge_Server("app listen "+song.getId(), Constant.sSessionID, Constant.sMSISDN,
                    "", "2", "0", "", Constant.USER_ID);
            try {
                mPlayer.reset();
                String url = "";
                //
                if (song.isFull()) {
                    url = song.getPathfulltrack();
                } else {
                    url = Constant.MUSIC_URL + song.getPath().replace(" ", "%20");
                    url = url.replaceAll("WMA", "mp3");
                }
                mPlayer.setDataSource(url);
                mPlayer.setOnPreparedListener(this);
                mPlayer.prepareAsync();
                notifyPlayStatusChanged(true);
                isPlaying(true);
            } catch (IOException e) {
                Log.e(TAG, "play: ", e);
                notifyPlayStatusChanged(false);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean play(PlayList list) {
        if (list == null) return false;
        isPaused = false;
        setPlayList(list);
        return play();
    }

    @Override
    public boolean play(PlayList list, int startIndex) {
        if (list == null || startIndex < 0 || startIndex >= list.getNumOfSongs()) return false;
        isPaused = false;
        list.setPlayingIndex(startIndex);
        setPlayList(list);
        return play();
    }


    @Override
    public boolean play(Ringtunes song) {
        if (song == null) return false;

        isPaused = false;
        mPlayList.getSongs().clear();
        mPlayList.getSongs().add(song);
        return play();
    }

    @Override
    public boolean playLast() {
        isPaused = false;
        boolean hasLast = mPlayList.hasLast();
        if (hasLast) {
            Ringtunes last = mPlayList.last();
            play();
            notifyPlayLast(last);
            return true;
        }
        return false;
    }

    @Override
    public boolean playNext() {
        isPaused = false;
        boolean hasNext = mPlayList.hasNext(false);
        if (hasNext) {
            Ringtunes next = mPlayList.nextNormal();
            if (next == null) {
                pause();
            } else
                play();
            notifyPlayNext(next);
            return true;
        }
        return false;
    }


    @Override
    public boolean pause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            isPaused = true;
            notifyPlayStatusChanged(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        if (mPlayer != null)
            return mPlayer.getDuration();
        else return 0;
    }

    @Nullable
    @Override
    public Ringtunes getPlayingSong() {
        return mPlayList.getCurrentSong();
    }

    @Override
    public boolean seekTo(int progress) {
        if (mPlayList.getSongs().isEmpty()) return false;
        Ringtunes currentSong = mPlayList.getCurrentSong();
        if (currentSong != null) {
            if (55000 <= progress) {
                onCompletion(mPlayer);
            } else {
                mPlayer.seekTo(progress);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setPlayMode(PlayMode playMode) {
        mPlayList.setPlayMode(playMode);
    }

    // Listeners


    @Override
    public void onCompletion(MediaPlayer mp) {
        Ringtunes next = null;
        boolean hasNext = mPlayList.hasNext(true);
        if (hasNext) {
            next = mPlayList.next();
            play();
        } else {
            pause();
            isComplete(false);
            return;
        }
        notifyComplete(next);
    }

    @Override
    public void releasePlayer() {
        mPlayList = null;
        if (mPlayer != null) {
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
        sInstance = null;
    }

    @Override
    public String getUrlNotification() {
        return getPlayingSong().getPath();
    }


    // Callbacks

    @Override
    public void registerCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

    @Override
    public void removeCallbacks() {
        mCallbacks.clear();
    }

    private void notifyPlayStatusChanged(boolean isPlaying) {
        i("mCallbacks--------->", "" + mCallbacks.size());
        for (Callback callback : mCallbacks) {
            callback.onPlayStatusChanged(isPlaying);
            callback.PlayerCompete(isPlaying);
        }
    }

    private void notifyPlayLast(Ringtunes song) {
        for (Callback callback : mCallbacks) {
            callback.onSwitchLast(song);
        }
    }

    private void notifyPlayNext(Ringtunes song) {
        for (Callback callback : mCallbacks) {
            callback.onSwitchNext(song);
        }
    }

    private void notifyComplete(Ringtunes song) {
        for (Callback callback : mCallbacks) {
            callback.onComplete(song);
        }
    }
    private void isComplete(boolean isComplete) {
       fragmentDetailBuySongs.PlayerCompete(isComplete);
    }
    private void isPlaying(boolean isPlay) {
        fragmentDetailBuySongs.PlayerCompete(isPlay);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mp != null) {
            mp.start();
            notifyPlayStatusChanged(true);
        }
    }
}
