package com.neo.media.Fragment.BuySongs.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neo.media.Activity.ActivityMainHome;
import com.neo.media.Adapter.AdapterRingtunes;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.BuySongs.Presenter.PresenterSongs;
import com.neo.media.Fragment.BuySongs.Presenter.PresenterSongsImpl;
import com.neo.media.GetUserInfo.IpmGetUserInfo;
import com.neo.media.GetUserInfo.Presenter_GetUser_Info;
import com.neo.media.Model.Comment;
import com.neo.media.Model.Login;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Singer;
import com.neo.media.Model.Songs;
import com.neo.media.Player.IPlayback;
import com.neo.media.Player.Player;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.Register.ActivityRegisterOTP;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.DialogUtil;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.TimeUtils;
import com.neo.media.untils.Utilities;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.alexrs.prefs.lib.Prefs;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION_CODES.M;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.neo.media.Config.Constant.IMAGE_URL;
import static com.neo.media.MyApplication.objRingGift;
import static com.neo.media.MyApplication.player_ring;

/**
 * Created by QQ on 7/28/2017.
 */

public class FragmentDetailBuySongs extends BaseFragment
        implements PresenterSongsImpl.View, MediaPlayer.OnPreparedListener, IPlayback.Callback, IpmGetUserInfo.View {
    public static String TAG = FragmentDetailBuySongs.class.getSimpleName();
    public static FragmentDetailBuySongs fragmentSingerDetail;

    public static FragmentDetailBuySongs getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentDetailBuySongs.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentDetailBuySongs();
            }
        }
        return fragmentSingerDetail;
    }

    Singer singer;
    @BindView(R.id.txt_name_buysongs)
    TextView txt_name_buysongs;
    @BindView(R.id.txt_singer_buysongs)
    TextView txtSinger_BuySongs;
    @BindView(R.id.txt_hits_buysongs)
    TextView txtHits_BuySongs;
    @BindView(R.id.txt_price_buysongs)
    TextView txtPrice_BuySongs;
    @BindView(R.id.txt_time_buysongs)
    TextView txtTime_BuySongs;

    @BindView(R.id.img_buy_buysong)
    Button btn_buysong;
    @BindView(R.id.img_gift_buysong)
    Button btn_giftsongs;
    @BindView(R.id.recycle_comment)
    RecyclerView recyclerView;

    @BindView(R.id.txt_total_comment_songs)
    TextView txt_total_comment;
    @BindView(R.id.img_buysong_detail)
    ImageView img_buysong_detail;

    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.nesScoll_DetailSong)
    NestedScrollView nestedScrollView;
    String option;
    public static Ringtunes ringtunes;
    PresenterSongs presenterSongs;
    List<Comment> lisComment;
    public static EditText ed_getphone;
    public static EditText ed_add_comment_content;
    int page = 1;
    int index = 20;
    public static Dialog dialog_getphone;
    AdapterRingtunes adapterRingtunes;
    List<Ringtunes> lisRing;
    String id_Singer;
    public boolean isLogin;
    public String msisdn;
    public String sessionID;
    public String sUserId;
    public String pass;
    Info_User objInfo;
    Login objLogin;
    public Realm realm;
    SharedPreferences pre;
    public String sExpiration;
    public Player iPlayer;
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
    private int total_time = 52000;
    MediaPlayer mp = new MediaPlayer();
    public ImageView img_buysong_detail_nen;
    boolean isHome;
    boolean notifi = false;
    boolean isFull = false;
    String id_Songs;
    String sEntity;
    public static boolean is_subscriber = false;
    public static boolean is_SVC_STATUS = false;
    private Presenter_GetUser_Info presenter_getUser_info;
    private boolean is_Buy = true;
    private boolean iWating = false;
    @BindView(R.id.img_favorite)
    ImageView img_favorite;
    List<Songs> lisSongFavorite;

    @SuppressLint("ResourceAsColor")
    private void initPlayer() {
        if (Build.VERSION.SDK_INT >= M) {
            seekBarProgress.setProgressTintList(getResources().getColorStateList(R.color.orange, getContext().getTheme()));
            seekBarProgress.setThumbTintList(getResources().getColorStateList(R.color.orange, getContext().getTheme()));
        } else {
            seekBarProgress.getProgressDrawable().setColorFilter(R.color.orange, PorterDuff.Mode.SRC_IN);
            seekBarProgress.getThumb().setColorFilter(R.color.orange, PorterDuff.Mode.SRC_IN);
           // seekBarProgress.getProgressDrawable().setColorFilter(R.color.orange, PorterDuff.Mode.SRC_IN);
            //seekBarProgress.setThumbTintList(R.color.orange, PorterDuff.Mode.SRC_IN);
            //   seekBarProgress.setBackgroundTintList();
        }
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    updateProgressTextWithProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mProgressCallback);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(getDuration(seekBar.getProgress()));
                if (iPlayer.isPlaying()) {
                    mHandler.removeCallbacks(mProgressCallback);
                    mHandler.post(mProgressCallback);
                }
            }
        };
        iPlayer.registerCallback(this);

        btn_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer != null) {
                    if (iPlayer.isPlaying()) {
                        iPlayer.pause();
                        btn_player.setImageResource(R.drawable.play_adapter);

                    } else {
                        iPlayer.play();
                        btn_player.setImageResource(R.drawable.pause_adapter);
                    }
                }

            }
        });

    }

    private void seekTo(int duration) {
        if (iPlayer != null)
            iPlayer.seekTo(duration);
    }

    private Runnable mProgressCallback = new Runnable() {
        @Override
        public void run() {
//            if (isDetached()) return;
            if (iPlayer.isPlaying()) {
                int progress = (int) (seekBarProgress.getMax() * ((float) iPlayer.getProgress() / total_time));
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

    private void updateProgressTextWithProgress(int progress) {
        int targetDuration = getDuration(progress);
        textViewProgress.setText(TimeUtils.formatDuration(targetDuration));

    }

    private void updateProgressTextWithDuration(int duration) {
        textViewProgress.setText(TimeUtils.formatDuration(duration));

    }

    private int getDuration(int progress) {
        int duration = (int) (getCurrentSongDuration() * ((float) progress
                / seekBarProgress.getMax()));
        return duration;
    }

    private int getCurrentSongDuration() {
        Ringtunes currentSong = iPlayer.getPlayingSong();
        int duration = 0;
        if (currentSong != null) {
            duration = currentSong.getDuration();
        }
        return total_time;
    }

    private void onSongUpdated(Ringtunes song) {
        //  mHandler.post(mProgressCallback);
        texttotaltimeViewProgress.setText(TimeUtils.formatDuration(total_time));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iWating = false;
        realm = RealmController.with(this).getRealm();
        iPlayer = Player.getInstance(getContext());
        iPlayer = new Player(this);
        count_get_api = 0;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_songs, container, false);
        ButterKnife.bind(this, view);
        presenterSongs = new PresenterSongs(this);
        presenter_getUser_info = new Presenter_GetUser_Info(this);
        img_buysong_detail_nen = (ImageView) view.findViewById(R.id.img_buysong_detail_nen);
        isFavorite = false;
        init();
        initEvent();
        initPlayer();
        intData();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume");
        if (iPlayer != null) {
            if (iPlayer.isPlaying()) {
                iPlayer.pause();
                btn_player.setImageResource(R.drawable.play_adapter);

            } else {
                if (!iPlayer.pause()) {
                    iPlayer.play();
                    btn_player.setImageResource(R.drawable.pause_adapter);
                }
            }
        }
        ActivityMainHome.relative_tab.setVisibility(View.GONE);

        //  MainNavigationActivity.appbar.setVisibility(View.GONE);
        /*objLogin = realm.where(Login.class).findFirst();
        objInfo = realm.where(Info_User.class).findFirst();
        if (objLogin != null) {

            if (objLogin.isLogin()) {
                msisdn = objLogin.getMsisdn();
                sessionID = objLogin.getsSessinonID();
                isLogin = objLogin.isLogin();
            }

            *//*if (objInfo != null) {
                if (objInfo.getStatus() != null && objInfo.getStatus().equals("2")) {
                    is_subscriber = true;

                    if (objInfo.getService_status().equals("1")) {
                        is_SVC_STATUS = true;
                    } else if (objInfo.getService_status().equals("0")) {
                        is_SVC_STATUS = false;
                    }
                } else {
                    is_subscriber = false;
                }
            }*//*
        }*/
    }

    private void StartPlayer(Ringtunes ringtunes) {
        ringtunes.setFull(false);
        //ringtunes.setPath(Constant.MUSIC_URL + ringtunes.getPath().replace(" ", "%20"));
        btn_player.setImageResource(R.drawable.pause_adapter);
        if (iPlayer != null)
            iPlayer.play(ringtunes);
        onSongUpdated(ringtunes);
        mHandler.postDelayed(mProgressCallback, 100);
        //updateProgressBar();
    }

    public void fragmentBackTack() {
        if (iPlayer != null) {
            iPlayer.releasePlayer();
            iPlayer.removeCallbacks();
        }
        mHandler.removeCallbacks(mProgressCallback);
        FragmentUtil.popBackStack(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (iPlayer != null)
            iPlayer.pause();
        //Log.i(TAG, "OnPause");
        if (isHome) {
            //MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
            ActivityMainHome.relative_tab.setVisibility(View.VISIBLE);
        }
       /* iPlayer.releasePlayer();
        mHandler.removeCallbacks(mProgressCallback);*/
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("notifi", false);
        editor.putString("id_songs", "");
        editor.putString("id_singer", "");
        editor.commit();
        // MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    private void intData() {
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        isHome = pre.getBoolean("isHome", false);
        notifi = pre.getBoolean("notifi", false);
        sUserId = Prefs.with(getContext()).getString("user_id", "");
        ringtunes = player_ring;
        //  msisdn = pre.getString("msisdn", "");
        id_Singer = pre.getString("isSinger", "");
        //isLogin = pre.getBoolean("isLogin", false);

        isLogin = Prefs.with(getContext()).getBoolean("isLogin", false);
        is_subscriber = Prefs.with(getContext()).getBoolean("is_subscriber", false);
        is_SVC_STATUS = Prefs.with(getContext()).getBoolean("is_SVC_STATUS", false);
        sessionID = Prefs.with(getContext()).getString("sessionID", "");
        msisdn = Prefs.with(getContext()).getString("msisdn", "");
        // sessionID = Prefs.with(getContext()).getString("sessionID", "");
        //  sessionID = pre.getString("sessionID", "");
        pass = pre.getString("password", "");
        sEntity = "app listen" + ringtunes.getId();
        //presenterSongs.log_Info_Charge_Server(sEntity, sessionID, msisdn, );
        lisSongFavorite = realm.where(Songs.class).findAll();
        if (notifi) {
            id_Songs = pre.getString("id_songs", "");
            id_Singer = pre.getString("id_singer", "");
            presenterSongs.get_info_songs_by_id(id_Songs, sUserId);
            presenterSongs.api_suggestion_play(id_Singer, id_Songs, sUserId);
        } else {
            updateData(ringtunes);
            if (ringtunes != null) {
                String id_singer = ringtunes.getSinger_id();
                presenterSongs.api_suggestion_play(id_singer, ringtunes.getId(), sUserId);
            }
        }
    }

    private void init() {
        lisRing = new ArrayList<>();
        adapterRingtunes = new AdapterRingtunes(lisRing, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterRingtunes);
    }

    private boolean isFavorite = false;

    private void initEvent() {
        // Thêm bài hát vào favorite
        img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    Toast.makeText(getContext(), "Bài hát đã được thêm vào danh sách yêu thích", Toast.LENGTH_LONG).show();
                    Glide.with(getContext()).load(R.drawable.icon_favorite_red).into(img_favorite);
                    Songs objSong = new Songs();
                    objSong.setId(ringtunes.getId());
                    objSong.setCOUNTER(ringtunes.getCOUNTER());
                    objSong.setCp_name(ringtunes.getCp_name());
                    objSong.setPath(ringtunes.getPath());
                    objSong.setCreate_date(ringtunes.getCreate_date());
                    objSong.setDescription(ringtunes.getDescription());
                    objSong.setImage_file(ringtunes.getImage_file());
                    objSong.setHist(ringtunes.getHist());
                    objSong.setPrice(ringtunes.getPrice());
                    objSong.setSinger_name(ringtunes.getSinger_name());
                    objSong.setsExpiration(ringtunes.getsExpiration());
                    objSong.setProduct_name(ringtunes.getProduct_name());
                    objSong.setProduct_name_no(ringtunes.getProduct_name_no());
                    objSong.setIs_new(ringtunes.getIs_new());
                    objSong.setSinger_id(ringtunes.getSinger_id());
                    objSong.setModify_date(ringtunes.getModify_date());
                    add_songs_favorite(objSong);
                }

            }
        });
        // mua bài hát
        btn_buysong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_Buy = true;
                check_buysong();

            }
        });
        // Bấm tặng bài hát
        btn_giftsongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_Buy =false;
                check_buysong();
                //isLogin = Prefs.with(getContext()).getBoolean("isLogin", false);
                //Check login
               /* if (isLogin) {
                    if (!iWating) {
                        sessionID = Prefs.with(getContext()).getString("sessionID", "");
                        msisdn = Prefs.with(getContext()).getString("msisdn", "");
                        is_Buy = false;
                        iWating = true;
                        showDialogLoading();
                        //  is_Buy = true;
                        count_get_api = 0;
                        //Gọi hàm check thông tin thuê bao
                        presenter_getUser_info.api_get_detail_subsriber(sessionID, msisdn);
                    }

                } else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Thông báo")
                            .setContentText("Bạn chưa đăng nhập, mời bạn đăng nhập để tiếp tục tặng bài")
                            .setConfirmText("Đăng nhập")
                            .setCancelText("Trở lại")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    getActivity().startActivity(new Intent(getActivity(), ActivityXacthuc.class));
                                }
                            })
                            .show();
                }*/


            }
        });

    }

    private void check_buysong() {
        isLogin = Prefs.with(getContext()).getBoolean("isLogin", false);
        if (isLogin) {
            if (!iWating) {
                sessionID = Prefs.with(getContext()).getString("sessionID", "");
                msisdn = Prefs.with(getContext()).getString("msisdn", "");
               // showDialogLoading();
                iWating = true;
                //Gọi hàm check thông tin thuê bao
                presenter_getUser_info.api_get_detail_subsriber(sessionID, msisdn);
            }
        } else {
            if (init3G()){
                showDialogLoading();
                presenter_getUser_info.login_3g(sUserId);
            }else {
                startActivity(new Intent(getContext(), ActivityRegisterOTP.class));
            }
        }
    }


    @Override
    public void showGiftSongsSuccess(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                show_notification("Thông báo", list.get(1));
            }
            if (list.get(0).equals("104")) {
                //
                show_notification("Thông báo", list.get(1));
            } else {
                show_notification("Thông báo", getResources().getString(R.string.error_104));
            }
        }
    }

    @Override
    public void ShowBuySongsSuccess(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Bạn đã mua bài hát thành công")
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
            } else {
                show_notification("Thông báo", list.get(1));

            }
        } else
            show_notification("Thông báo", "Hệ thống đang bận, mời thử lại sau");
    }

    @Override
    public void ShowItems(Item items) {
        hideDialogLoading();
        sExpiration = items.getExpiration();
        txtTime_BuySongs.setText(items.getExpiration() + " ngày)");
    }

    @Override
    public void showComment(List<Comment> listComment) {
        if (listComment != null && listComment.size() > 0) {
        }

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show_add_comment_success(String s) {
        if (s != null && s.equals("1")) {
            ed_add_comment_content.setText("");
            presenterSongs.getComment_by_Songid(ringtunes.getId(), "" + page, "" + index);
        }
    }

    /**
     * Hiện thị danh sách bài hát gợi ý
     *
     * @param lisRingtunes: Danh sách bài hát tương tự
     */
    @Override
    public void show_lis_songs_bysinger(List<Ringtunes> lisRingtunes) {
        hideDialogLoading();
        if (lisRingtunes.size() > 0) {
            lisRing.addAll(lisRingtunes);
            adapterRingtunes.notifyDataSetChanged();
            adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
                @Override
                public void OnItemClickListener(int position) {
                    ringtunes = lisRing.get(position);
                    updateData(ringtunes);
                }

                @Override
                public void OnLongItemClickListener(int position) {

                }
            });
        }
    }

    @Override
    public void show_lis_songs_by_id(List<Ringtunes> lisSongs) {
        if (lisSongs.size() > 0) {
            updateData(lisSongs.get(0));
        }
    }

    public void updateData(Ringtunes ringtunes) {
        Glide.with(getContext()).load(R.drawable.icon_favorite_white).into(img_favorite);
        isFavorite = false;
        if (lisSongFavorite != null && lisSongFavorite.size() > 0) {
            for (int i = 0; i < lisSongFavorite.size(); i++) {
                if (ringtunes.getId().equals(lisSongFavorite.get(i).getId())) {
                    isFavorite = true;
                    Glide.with(getContext()).load(R.drawable.icon_favorite_red).into(img_favorite);
                }
            }
        }
        presenterSongs.getSongsInformation(sessionID, msisdn, ringtunes.getId());
        StartPlayer(ringtunes);
        String urlImage = IMAGE_URL + ringtunes.getImage_file();
        Glide.with(getContext()).load(urlImage).into(img_buysong_detail);
        // Glide.with(getContext()).load(urlImage).into(img_buysong_detail_nen);
        Glide.with(this).load(urlImage)
                .apply(bitmapTransform(new BlurTransformation(10)))
                .into(img_buysong_detail_nen);
        txtSinger_BuySongs.setText(ringtunes.getSinger_name());
        if (ringtunes.getHist() != null) {
            String hit = CustomUtils.conventNumber(ringtunes.getHist());
            txtHits_BuySongs.setText(hit);
        } else if (ringtunes.getCOUNTER() != null) {
            String hit = CustomUtils.conventNumber(ringtunes.getCOUNTER());
            txtHits_BuySongs.setText(hit);

        } else {
            txtHits_BuySongs.setText("0");
        }
        txtPrice_BuySongs.setText("(" + ringtunes.getPrice() + " Đ");
        txt_name_buysongs.setText(ringtunes.getProduct_name());

    }

    public void BuySongs() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText("Bạn muốn mua bài hát " + ringtunes.getProduct_name() + " với giá "
                        + ringtunes.getPrice() + " làm nhạc chờ")
                .setConfirmText("Đồng ý")
                .setCancelText("Trở lại")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        presenterSongs.addItemtoMyList(sessionID, msisdn, sExpiration, ringtunes.getId());
                    }
                })
                .show();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onSwitchLast(@Nullable Ringtunes last) {

    }

    @Override
    public void onSwitchNext(@Nullable Ringtunes next) {

    }

    @Override
    public void onComplete(@Nullable Ringtunes next) {

    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {

    }

    public void isPlaying(boolean isComplete) {
        if (isComplete)
            btn_player.setImageResource(R.drawable.pause_adapter);
    }

    @Override
    public void PlayerCompete(boolean isComplete) {
        if (!isComplete)
            btn_player.setImageResource(R.drawable.play_adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seekBarProgress.setProgress(0, true);
        } else {
            seekBarProgress.setProgress(0);
        }
    }

    @Override
    public void show_detail_subsriber(subscriber objSub) {
        hideDialogLoading();
        iWating = false;
        count_get_api = 0;
        if (objSub != null) {
            if (objSub.getERROR() != null && objSub.getERROR().equals("0")) {
                if (objSub.getSTATUS() != null && objSub.getSTATUS().equals("2")) {
                    Prefs.with(getContext()).save("is_subscriber", true);
                    is_subscriber = true;
                    if (objSub.getSVC_STATUS().equals("1")) {
                        Prefs.with(getContext()).save("is_SVC_STATUS", true);
                        is_SVC_STATUS = true;
                    } else if (objSub.getSVC_STATUS().equals("0")) {
                        Prefs.with(getContext()).save("is_SVC_STATUS", false);
                        is_SVC_STATUS = false;
                    }
                    if (is_Buy)
                        BuySongs();
                    else {
                        objRingGift = ringtunes;
                        objRingGift.setsExpiration(sExpiration);
                        if (!FragmenGiftRingtunes.getInstance().isAdded()) {
                            FragmentUtil.addFragment(getActivity(), FragmenGiftRingtunes.getInstance(), true);
                        }
                    }
                } else {
                    Prefs.with(getContext()).save("is_subscriber", false);
                    is_subscriber = false;
                    final Dialog dialog_yes = new Dialog(getContext());
                    dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_yes.setContentView(R.layout.dialog_yes_no);
                    TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.txt_content_dialog);

                    Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                    Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
                    String formattedText = "This is &lt;font color='#0000FF'&gt;blue&lt;/font&gt;";
                    txt_buysongs.setText(Html.fromHtml("Để hoàn tất dịch vụ Ringtunes, Quý khách vui lòng thực hiện thao tác <font color='#FF0000'>\"Y2 gửi 9194\"</font> " +
                            "từ số điện thoại <font color='#FF0000'> " + PhoneNumber.convertToVnPhoneNumber(msisdn) + "</font> giá cước 3000đ/ 7 ngày." +
                            " Cảm ơn quý khách"));

                    // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
                    yes.setText("Đồng ý");
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter_getUser_info.api_subsriber_sms(msisdn, "2");
                            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                            smsIntent.setType("vnd.android-dir/mms-sms");
                            smsIntent.putExtra("address", "9194");
                            smsIntent.putExtra("sms_body", "Y2");
                            try {
                                startActivity(smsIntent);
                            } catch (ActivityNotFoundException e) {
                                // Display some sort of error message here.
                            }
                            //    startActivity(smsIntent);
                            dialog_yes.dismiss();
                            //  startActivity(new Intent(getActivity(), FragmentStopPause.class));
                        }
                    });
                    TextView btn_delete_dialog = dialog_yes.findViewById(R.id.btn_delete_dialog);
                    btn_delete_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter_getUser_info.api_subsriber_sms(msisdn, "1");
                            dialog_yes.dismiss();
                        }
                    });
                    no.setText("Không");
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_yes.dismiss();
                        }
                    });
                    dialog_yes.show();
                }
            } else {
                Prefs.with(getContext()).save("is_subscriber", false);
                is_subscriber = false;
                final Dialog dialog_yes = new Dialog(getContext());
                dialog_yes.setCancelable(false);
                dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_yes.setContentView(R.layout.dialog_yes_no);
                TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.txt_content_dialog);

                Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
                String formattedText = "This is &lt;font color='#0000FF'&gt;blue&lt;/font&gt;";
                txt_buysongs.setText(Html.fromHtml("Để hoàn tất dịch vụ Ringtunes, Quý khách vui lòng thực hiện thao tác <font color='#FF0000'>\"Y2 gửi 9194\"</font> từ số điện thoại <font color='#FF0000'> " + PhoneNumber.convertToVnPhoneNumber(msisdn) + "</font> giá cước 3000đ/ 7 ngày. Cảm ơn quý khách"));

                // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
                yes.setText("Đồng ý");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter_getUser_info.api_subsriber_sms(msisdn, "2");
                        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", "9194");
                        smsIntent.putExtra("sms_body", "Y2");
                        try {
                            startActivity(smsIntent);
                        } catch (ActivityNotFoundException e) {
                            // Display some sort of error message here.
                        }
                        //     startActivity(smsIntent);
                        dialog_yes.dismiss();
                        //  startActivity(new Intent(getActivity(), FragmentStopPause.class));
                    }
                });
                TextView btn_delete_dialog = dialog_yes.findViewById(R.id.btn_delete_dialog);
                btn_delete_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean is_phien_dn = Prefs.with(getActivity()).getBoolean("is_Phien_DN", false);
                        if (is_phien_dn) {
                            presenter_getUser_info.api_subsriber_sms(msisdn, "1");
                            Prefs.with(getActivity()).save("is_Phien_DN", false);
                        }
                        dialog_yes.dismiss();
                    }
                });
                no.setText("Không");
                dialog_yes.show();
            }
        } else {
            show_notification("Thông báo", "Hệ thống đang bận, mời thử lại sau");
        }
    }

    private int count_get_api = 0;

    @Override
    public void show_error_api() {
        hideDialogLoading();
        show_notification("Lỗi kết nối", "Mời bạn kiểm tra lại mạng và thử lại.");

    }

    @Override
    public void show_login_3g(final List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            if (list.get(0).equals("0") && list.get(3).equals("SUCCESS")) {
                DialogUtil.ShowAlertDialogAnimationUpBottom2Button(getContext(), getString(R.string.title_notification),
                        "Bạn có muốn đăng nhập Ringtunes bằng số điện thoại: "
                                +PhoneNumber.convertToVnPhoneNumber(list.get(2)),
                        "Đồng ý",
                        "Số khác",
                        new DialogUtil.ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        Prefs.with(getContext()).save("pass_sql_server", list.get(1));
                        Constant.sMSISDN = list.get(2);
                        Prefs.with(getContext()).save("msisdn", list.get(2));
                        presenter_getUser_info.Login(sUserId, list.get(1));
                    }

                    @Override
                    public void onClickNoDialog() {
                        startActivity(new Intent(getContext(), ActivityRegisterOTP.class));
                    }
                });

            } else {
                DialogUtil.ShowAlertDialogAnimationUpBottom2Button(getContext(),
                        "Thông báo", getString(R.string.message)
                        ,"Đăng nhập"
                        , "Huỷ bỏ",
                        new DialogUtil.ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        startActivity(new Intent(getContext(), ActivityRegisterOTP.class));
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });
                //show_notification("Thông", "Hệ thống đang bận mời thử lại sau");
                // Toast.makeText(this, "Lỗi, thuê bao của quý khách không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showDataLogin(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            Constant.sSessionID = list.get(2);
            Prefs.with(getContext()).save("sessionID", list.get(2));
            Prefs.with(getContext()).save("isLogin", true);
            Prefs.with(getContext()).save("is_Show_Subscriber", true);
            Prefs.with(getContext()).save("is_Phien_DN", true);
            check_buysong();
        } else {
            show_notification("Lỗi", "Hệ thống đang bận mời bạn thử lại");
        }
    }

    public void add_songs_favorite(Songs objSong) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objSong);
        realm.commitTransaction();
    }
}
