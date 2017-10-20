package com.neo.media.Player;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.Ringtunes;
import com.neo.media.R;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.TimeUtils;
import com.neo.media.untils.Utilities;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.Config.Constant.IMAGE_URL;

/**
 * Created by QQ on 9/24/2017.
 */

public class FragmentPlayerFull extends BaseFragment {
    public static FragmentPlayerFull fragment_playerfull;

    public static FragmentPlayerFull getInstance() {
        if (fragment_playerfull == null) {
            synchronized (FragmentPlayerFull.class) {
                if (fragment_playerfull == null)
                    fragment_playerfull = new FragmentPlayerFull();
            }
        }
        return fragment_playerfull;
    }
    Ringtunes objRingtunes;
    public SharedPreferences pre;
    public String msisdn;
    @BindView(R.id.btnPlay)
    public ImageView btn_player;
    @BindView(R.id.player_progressbar)
    SeekBar seekBarProgress;
    @BindView(R.id.songCurrentDurationLabel)
    TextView textViewProgress;
    @BindView(R.id.songTotalDurationLabel)
    TextView texttotaltimeViewProgress;
    private Handler mHandler = new Handler();
    private static final long UPDATE_PROGRESS_INTERVAL = 100;
    private Utilities utils;
    private int total_time=1000*60*5;
    MediaPlayer mp= new MediaPlayer();
    public Player iPlayer;
    @BindView(R.id.img_backgroup_full)
    ImageView img_backgroup_full;
    @BindView(R.id.img_songs_full_screen)
    CircleImageView img_songs_full_screen;
    @BindView(R.id.txt_namesong_full)
    TextView txt_namesong_full;
    @BindView(R.id.txt_namesinger_full)
    TextView txt_namesinger_full;
    Handler handler = new Handler();
    RotateAnimation rotateAnimation;
    @BindView(R.id.btn_back_full)
    Button btn_back_full;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_full_screen, container, false);


        ButterKnife.bind(this, view);
        init();
        getData();
        iPlayer = Player.getInstance(getContext());
        if (objRingtunes!=null){
            updateData(objRingtunes);
        }
        initEvent();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initEvent() {
        btn_back_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });
    }

    public void init(){
        rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1000*30);
        rotateAnimation.setRepeatCount(Animation.INFINITE);



    }

    @Override
    public void onResume() {
        super.onResume();
        MainNavigationActivity.appbar.setVisibility(View.GONE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },1000);

    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            img_songs_full_screen.startAnimation(rotateAnimation);
        }
    };

    public void getData() {
       // isHome = getArguments().getBoolean("isHome", false);
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        objRingtunes = (Ringtunes) getArguments().getSerializable("objRing");
        msisdn = pre.getString("msisdn", "");
       // id_Singer = pre.getString("isSinger", "");
    }
    public void updateData(Ringtunes ringtunes) {
      //  presenterSongs.getSongsInformation(sessionID, msisdn, ringtunes.getId());
        StartPlayer(ringtunes);
        String urlImage = IMAGE_URL + ringtunes.getImage_file();
        Glide.with(getContext()).load(urlImage).into(img_backgroup_full);
        Glide.with(getContext()).load(urlImage).into(img_songs_full_screen);
        txt_namesinger_full.setText(ringtunes.getSinger_name());
        txt_namesong_full.setText(ringtunes.getProduct_name());

    }
    private void StartPlayer(Ringtunes ringtunes) {
        ringtunes.setFull(true);
        iPlayer.play(ringtunes);
        onSongUpdated(ringtunes);
        mHandler.postDelayed(mProgressCallback,100);
        //updateProgressBar();
    }
    private void onSongUpdated(Ringtunes song) {
        //  mHandler.post(mProgressCallback);
        texttotaltimeViewProgress.setText(TimeUtils.formatDuration(total_time));
    }

    private Runnable mProgressCallback = new Runnable() {
        @Override
        public void run() {
//            if (isDetached()) return;
            if (iPlayer.isPlaying()) {
                int seekbarmax= seekBarProgress.getMax();
                float getProress = iPlayer.getProgress();
                int getCuttentsong = iPlayer.getDuration();
                int progress = (int) (seekBarProgress.getMax()
                        * ((float) iPlayer.getProgress() / total_time ));
                updateProgressTextWithDuration(iPlayer.getProgress());
                if (progress >= 0 && progress <= seekBarProgress.getMax()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBarProgress.setProgress(progress, true);
                    } else {
                        seekBarProgress.setProgress(progress);
                    }
                }
            }
            mHandler.postDelayed(this, UPDATE_PROGRESS_INTERVAL);
        }
    };

    private void updateProgressTextWithDuration(int duration) {
        textViewProgress.setText(TimeUtils.formatDuration(duration));

    }

    @Override
    public void onPause() {
        super.onPause();
        iPlayer.releasePlayer();
        mHandler.removeCallbacks(mProgressCallback);
    }
}
