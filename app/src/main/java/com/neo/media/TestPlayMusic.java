package com.neo.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.neo.media.untils.BaseFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 9/13/2017.
 */

public class TestPlayMusic extends BaseFragment implements MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener, View.OnClickListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener {
    public static TestPlayMusic fragment;
    public static TestPlayMusic getInstance() {
        if (fragment == null) {
            synchronized (TestPlayMusic.class) {
                if (fragment == null) {
                    fragment = new TestPlayMusic();

                }
            }
        }
        return fragment;
    }
    @BindView(R.id.btnPlay)
    ImageView btnPlay;
    MediaPlayer mp;
    String songDestination_URL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp = new MediaPlayer();
        mp.setOnCompletionListener(this);
        mp.setOnErrorListener(this);
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnSeekCompleteListener(this);
        mp.setOnInfoListener(this);
        mp.reset();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomplayer, container, false);
        ButterKnife.bind(this, view);
        songDestination_URL = getArguments().getString("url_music", "");
        playSong(songDestination_URL);
        playSong(songDestination_URL);
        initEvent();
        return view;

    }

    private void initEvent() {

    }

    public void playSong(String songPath) {
        // mHandler.removeCallbacks(mUpdateTimeTask);

        mp.reset();
        if (!mp.isPlaying()) {
            try {
                //mp.setDataSource(getActivity(), songPath);
                mp.setDataSource(songPath);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);//
                //gửi tin nhắn đến MainActivity để hiển thị đồng bộ
                // sendBufferingBroadcast();
                mp.prepareAsync();
                mp.start();
                //Thay đổi button tạm dừng
                btnPlay.setImageResource(R.drawable.icon_pause_bottom);
                //Thiết lập giá trị thanh Progress bar
                //songProgressBar.get().setProgress(0);
                //songProgressBar.get().setMax(100);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
