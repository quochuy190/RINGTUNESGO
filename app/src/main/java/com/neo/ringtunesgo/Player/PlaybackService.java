package com.neo.ringtunesgo.Player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.neo.ringtunesgo.Model.PlayList;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.R;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/12/16
 * Time: 4:27 PM
 * Desc: PlayService
 */
public class PlaybackService extends Service implements IPlayback, IPlayback.Callback {
    private static final String ACTION_PLAY_TOGGLE = "com.bebeboo.funringvn.ACTION.PLAY_TOGGLE";
    private static final String ACTION_PLAY_LAST = "com.bebeboo.funringvn.ACTION.PLAY_LAST";
    private static final String ACTION_PLAY_NEXT = "com.bebeboo.funringvn.ACTION.PLAY_NEXT";
    private static final String ACTION_STOP_SERVICE = "com.bebeboo.funringvn.ACTION.STOP_SERVICE";
    public static String TAG = PlaybackService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 1;

    private RemoteViews mContentViewBig, mContentViewSmall;

    private Player mPlayer;

    private final Binder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public PlaybackService getService() {
            return PlaybackService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
        mPlayer = Player.getInstance(this);
        mPlayer.registerCallback(this);
    }

    boolean stopFg = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_PLAY_TOGGLE.equals(action)) {
                if (isPlaying()) {
                    pause();
                } else {
                    play();
                }
            } else if (ACTION_PLAY_NEXT.equals(action)) {
                playNext();
            } else if (ACTION_PLAY_LAST.equals(action)) {
                playLast();
            } else if (ACTION_STOP_SERVICE.equals(action)) {
                Log.i(TAG,"ACTION_STOP_SERVICE");
                if (isPlaying()) {
                    pause();
                }
                stopForeground(true);
//                unregisterCallback(this);
//                RxBus.getInstance().post(new EventStopService());
            }
        }
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG,"onTaskRemoved----------------");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind-------------------");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG,"onRebind------------------------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean stopService(Intent name) {
        stopForeground(true);
        unregisterCallback(this);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy Service ------------------>");
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public void setPlayList(PlayList list) {
        mPlayer.setPlayList(list);
    }

    @Override
    public boolean play() {
        return mPlayer.play();
    }

    @Override
    public boolean play(PlayList list) {
        return mPlayer.play(list);
    }

    @Override
    public boolean play(PlayList list, int startIndex) {
        return mPlayer.play(list, startIndex);
    }

    @Override
    public boolean play(Ringtunes song) {
        return mPlayer.play(song);
    }

    @Override
    public boolean playLast() {
        return mPlayer.playLast();
    }

    @Override
    public boolean playNext() {
        return mPlayer.playNext();
    }

    @Override
    public boolean pause() {
        return mPlayer.pause();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getProgress();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public Ringtunes getPlayingSong() {
        return mPlayer.getPlayingSong();
    }

    @Override
    public boolean seekTo(int progress) {
        return mPlayer.seekTo(progress);
    }

    @Override
    public void setPlayMode(PlayMode playMode) {
        mPlayer.setPlayMode(playMode);
    }

    @Override
    public void registerCallback(Callback callback) {
        mPlayer.registerCallback(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mPlayer.unregisterCallback(callback);
    }

    @Override
    public void removeCallbacks() {
        mPlayer.removeCallbacks();
    }

    @Override
    public void releasePlayer() {
        mPlayer.releasePlayer();
        super.onDestroy();
    }

    @Override
    public String getUrlNotification() {
        return mPlayer.getUrlNotification();
    }

    // Playback Callbacks

    @Override
    public void onSwitchLast(@Nullable Ringtunes last) {
       // showNotification();
    }

    @Override
    public void onSwitchNext(@Nullable Ringtunes next) {
        //showNotification();
    }

    @Override
    public void onComplete(@Nullable Ringtunes next) {
        //showNotification();
    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {
     //   showNotification();

    }

    @Override
    public void PlayerCompete(boolean isComplete) {

    }

    // Notification

    /**
     * Show a notification while this service is running.
     */
    Notification notification;

   /* private void showNotification() {
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainNavigationActivity.class), 0);

        Log.i(TAG,"URL---->" + getUrlNotification());
        // Set the info for the views that show in the notification panel.


        mContentViewSmall = new RemoteViews(getPackageName(), R.layout.remote_view_music_player_small);
        mContentViewBig = new RemoteViews(getPackageName(), R.layout.remote_view_music_player);
        setUpRemoteView(mContentViewSmall);
        setUpRemoteView(mContentViewBig);
        updateRemoteViews(mContentViewSmall);
        updateRemoteViews(mContentViewBig);

        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ringtunes_final)  // the status icon
//                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .setCustomContentView(mContentViewSmall)
                .setCustomBigContentView(mContentViewBig)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setOngoing(true)
                .build();

        NotificationTarget notificationTarget = new NotificationTarget(
                PlaybackService.this,
                mContentViewBig,
                R.id.image_view_album,
                notification,
                NOTIFICATION_ID);

        NotificationTarget notificationTarget1 = new NotificationTarget(
                PlaybackService.this,
                mContentViewSmall,
                R.id.image_view_album,
                notification,
                NOTIFICATION_ID);

        Glide.with(this) // safer!
                .load(getUrlNotification())
                .asBitmap()
                .error(R.mipmap.ringtunes_final)
                .into(notificationTarget);
        Glide.with(this) // safer!
                .load(getUrlNotification())
                .asBitmap()
                .error(R.mipmap.ringtunes_final)
                .into(notificationTarget1);


        notification.flags |= Notification.FLAG_ONGOING_EVENT;


        // Send the notification.
        startForeground(NOTIFICATION_ID, notification);
    }*/


    private RemoteViews getSmallContentView(String urlImg, Notification notification) {
        if (mContentViewSmall == null) {
            mContentViewSmall = new RemoteViews(getPackageName(), R.layout.remote_view_music_player_small);
            setUpRemoteView(mContentViewSmall);
        }
        updateRemoteViews(mContentViewSmall);
        return mContentViewSmall;
    }

    private RemoteViews getBigContentView(String urlImg, Notification notification) {
        if (mContentViewBig == null) {
            mContentViewBig = new RemoteViews(getPackageName(), R.layout.remote_view_music_player);
            setUpRemoteView(mContentViewBig);
        }
        updateRemoteViews(mContentViewBig);
        return mContentViewBig;
    }

    private void setUpRemoteView(RemoteViews remoteView) {
        remoteView.setImageViewResource(R.id.image_view_close, R.drawable.ic_remote_view_close);
       // remoteView.setImageViewResource(R.id.image_view_play_last, R.drawable.ic_remote_view_play_last);
       // remoteView.setImageViewResource(R.id.image_view_play_next, R.drawable.ic_remote_view_play_next);

        remoteView.setOnClickPendingIntent(R.id.button_close, getPendingIntent(ACTION_STOP_SERVICE));
        remoteView.setOnClickPendingIntent(R.id.button_play_last, getPendingIntent(ACTION_PLAY_LAST));
        remoteView.setOnClickPendingIntent(R.id.button_play_next, getPendingIntent(ACTION_PLAY_NEXT));
        remoteView.setOnClickPendingIntent(R.id.button_play_toggle, getPendingIntent(ACTION_PLAY_TOGGLE));
    }


    private void updateRemoteViews(RemoteViews remoteView) {
        Ringtunes currentSong = mPlayer.getPlayingSong();
        if (currentSong != null) {
            remoteView.setTextViewText(R.id.text_view_name, currentSong.getProduct_name());
            remoteView.setTextViewText(R.id.text_view_artist, currentSong.getSinger_name());
        }
        remoteView.setImageViewResource(R.id.image_view_play_toggle, isPlaying()
                ? R.drawable.ic_remote_view_pause : R.drawable.ic_remote_view_play);
//        Bitmap album = AlbumUtils.parseAlbum(getPlayingSong());
//        if (album == null) {
//            remoteView.setImageViewResource(R.id.image_view_album, R.mipmap.ic_launcher);
//        } else {
//            remoteView.setImageViewBitmap(R.id.image_view_album, album);
//        }
    }

    // PendingIntent

    private PendingIntent getPendingIntent(String action) {
        return PendingIntent.getService(this, 0, new Intent(action), 0);
    }
}
