package com.neo.media.Fragment.Favorite;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neo.media.Activity.ActivityMainHome;
import com.neo.media.Adapter.AdapterListSongPlayer;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Fragment.BuySongs.Presenter.PresenterSongs;
import com.neo.media.Fragment.BuySongs.Presenter.PresenterSongsImpl;
import com.neo.media.Fragment.BuySongs.View.FragmenGiftRingtunes;
import com.neo.media.Fragment.CaNhan.Collection.ConllectionInteface;
import com.neo.media.Fragment.CaNhan.Collection.PresenterConllection;
import com.neo.media.GetUserInfo.IpmGetUserInfo;
import com.neo.media.GetUserInfo.Presenter_GetUser_Info;
import com.neo.media.Model.Comment;
import com.neo.media.Model.PlayList;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Songs;
import com.neo.media.MyApplication;
import com.neo.media.Player.IPlayback;
import com.neo.media.Player.PlayMode;
import com.neo.media.Player.Player;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.Xacthuc_thuebao.ActivityXacthuc;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.TimeUtils;
import com.neo.media.untils.Utilities;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.alexrs.prefs.lib.Prefs;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.neo.media.Config.Constant.IMAGE_URL;
import static com.neo.media.MyApplication.iLoop;
import static com.neo.media.MyApplication.lisItem;
import static com.neo.media.MyApplication.lisPlayRing;
import static com.neo.media.MyApplication.objRingGift;

/**
 * Created by QQ on 9/24/2017.
 */

public class FragmentPlayerFull extends BaseFragment implements IPlayback.Callback, PresenterSongsImpl.View,
        IpmGetUserInfo.View, ConllectionInteface.View {
    public static FragmentPlayerFull fragment_playerfull;
    private String sExpiration;

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
    public String sessionID;
    @BindView(R.id.player_progressbar)
    SeekBar seekBarProgress;
    @BindView(R.id.songCurrentDurationLabel)
    TextView textViewProgress;
    @BindView(R.id.songTotalDurationLabel)
    TextView texttotaltimeViewProgress;
    private Handler mHandler = new Handler();
    private static final long UPDATE_PROGRESS_INTERVAL = 100;
    private Utilities utils;
    private int total_time = 50000;
    MediaPlayer mp = new MediaPlayer();
    public static Player iPlayer;
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
    private List<Ringtunes> lisRingPlayer;
    PlayList objPlaylist;
    @BindView(R.id.btnPlay_full)
    ImageView btnPlay_full;
    @BindView(R.id.btn_play_next)
    ImageView btn_play_next;
    @BindView(R.id.btn_previous)
    ImageView btn_previous;
    @BindView(R.id.btn_play_loop)
    ImageView btn_play_loop;
    @BindView(R.id.btn_view_list)
    ImageView btn_view_list;

    @BindView(R.id.img_buysong)
    ImageView img_buysong;
    @BindView(R.id.img_gift_song)
    ImageView img_gift_song;
    @BindView(R.id.img_favorite_song)
    ImageView img_favorite_song;
    PresenterSongs presenterSongs;
    //Botton sheet
    BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;
    Presenter_GetUser_Info presenterGetUserInfo;
    PresenterConllection presenterConllection;
    AdapterListSongPlayer adapterRingtunes;
    private boolean is_Buy = true;
    private boolean iWating = false;
    List<Songs> lisSongFavorite;
    public Realm realm;
    @BindView(R.id.txt_hits_buysongs)
    TextView txtHits_BuySongs;
    @BindView(R.id.txt_price_buysongs)
    TextView txtPrice_BuySongs;
    @BindView(R.id.txt_time_buysongs)
    TextView txtTime_BuySongs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_full_screen, container, false);
        ButterKnife.bind(this, view);
        realm = RealmController.with(this).getRealm();
        presenterSongs = new PresenterSongs(this);
        presenterGetUserInfo = new Presenter_GetUser_Info(this);
        presenterConllection = new PresenterConllection(this);
        iPlayer = Player.getInstance(getContext());
        iPlayer.registerCallback(this);
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        //  view.findViewById(R.id.txt_namesong_full).setSelected(true);
        //  txt_namesong_full.setSelected(true);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
        init();
        getData();
       /*


        if (objRingtunes!=null){
            updateData(objRingtunes);
        }*/
        initEvent();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public boolean isLogin;

    private void initEvent() {
        img_buysong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = Prefs.with(getContext()).getBoolean("isLogin", false);
                //BuySongs();
                //Check login
                if (isLogin) {
                    if (!iWating) {
                        sessionID = Prefs.with(getContext()).getString("sessionID", "");
                        msisdn = Prefs.with(getContext()).getString("msisdn", "");
                        showDialogLoading();
                        is_Buy = true;
                        iWating = true;
                        //Gọi hàm check thông tin thuê bao
                        presenterGetUserInfo.api_get_detail_subsriber(sessionID, msisdn);
                    }
                } else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Thông báo")
                            .setContentText("Bạn chưa đăng nhập, mời bạn đăng nhập để tiếp tục mua bài")
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
                }
                // presenterSongs.addItemtoMyList("", "", "", iPlayer.getPlayingSong().getId());
            }
        });
        img_gift_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = Prefs.with(getContext()).getBoolean("isLogin", false);
                //Check login
                if (isLogin) {
                    if (!iWating) {
                        sessionID = Prefs.with(getContext()).getString("sessionID", "");
                        msisdn = Prefs.with(getContext()).getString("msisdn", "");
                        is_Buy = false;
                        iWating = true;
                        showDialogLoading();
                        //  is_Buy = true;
                        //Gọi hàm check thông tin thuê bao
                        presenterGetUserInfo.api_get_detail_subsriber(sessionID, msisdn);
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
                }

            }
        });
        img_favorite_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    Glide.with(getContext()).load(R.drawable.icon_favorite_red).into(img_favorite_song);
                    Toast.makeText(getContext(), "Bài hát đã được thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    Songs objSong = new Songs();
                    Ringtunes ringtunes = iPlayer.getPlayingSong();
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
                    isFavorite =true;
                }
            }
        });

        btnPlay_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer != null) {
                    if (iPlayer.isPlaying()) {
                        iPlayer.pause();
                    } else iPlayer.play();
                }
            }
        });
        btn_play_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer != null) {
                    //  objPlaylist.setPlayMode(PlayMode.SHUFFLE);
                    iPlayer.playNext();
                }

            }
        });
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlayer != null) {
                    //objPlaylist.setPlayMode(PlayMode.SHUFFLE);
                    iPlayer.playLast();
                }
            }
        });
        btn_view_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        btn_play_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iLoop < 3) {
                    iLoop++;
                    update_loop(iLoop);
                } else {
                    iLoop = 0;
                    update_loop(iLoop);
                }
            }
        });
    }

    public void init() {
        lisRingPlayer = new ArrayList<>();
        objPlaylist = new PlayList();
        rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1000 * 30);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        Resources resources = getContext().getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            seekBarProgress.setProgressTintList(resources.getColorStateList(R.color.orange, getContext().getTheme()));
            seekBarProgress.setThumbTintList(resources.getColorStateList(R.color.orange, getContext().getTheme()));
            //  valorslide.setTextColor(resources.getColorStateList(R.color.text_green, context.getTheme()));
        } else {
            seekBarProgress.setProgressTintList(resources.getColorStateList(R.color.orange));
            // valorslide.setTextColor(resources.getColorStateList(R.color.text_green));
        }
   /*     if (Build.VERSION.SDK_INT >= 21) {
            seekBarProgress.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
            seekBarProgress.setThumbTintList(ColorStateList.valueOf(Color.MAGENTA));
        } else {
            seekBarProgress.getProgressDrawable().setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
        }
*/
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityMainHome.relative_tab.setVisibility(View.GONE);
        //  MainNavigationActivity.appbar.setVisibility(View.GONE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 1000);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            img_songs_full_screen.startAnimation(rotateAnimation);
        }
    };
    String sUserId;
    public void getData() {
        showDialogLoading();
        lisSongFavorite = realm.where(Songs.class).findAll();
        sessionID = Prefs.with(getContext()).getString("sessionID", "");
        msisdn = Prefs.with(getContext()).getString("msisdn", "");
        sUserId = Prefs.with(getContext()).getString("user_id", "");
        presenterConllection.getConllection(sessionID, msisdn);
        if (lisPlayRing.size() > 0) {
            objPlaylist.setSongs(MyApplication.lisPlayRing);
            objPlaylist.setNumOfSongs(lisPlayRing.size());
        }
        update_loop(iLoop);
        iPlayer.play(objPlaylist, 0);
        lisRingPlayer.addAll(lisPlayRing);
        // adapterRingtunes.notifyDataSetChanged();
        // iPlayer.setPlayList(lisRingPlayer);


    }

    public boolean isFavorite =false;
    public void updateData() {
        //  presenterSongs.getSongsInformation(sessionID, msisdn, ringtunes.getId());
        if (objPlaylist != null && objPlaylist.getCurrentSong() != null) {
            Ringtunes ringtunes = objPlaylist.getCurrentSong();
            String urlImage = IMAGE_URL + ringtunes.getImage_file();
            //  Glide.with(getContext()).load(urlImage).into(img_backgroup_full);
            Glide.with(this).load(urlImage)
                    .apply(bitmapTransform(new BlurTransformation(10)))
                    .into(img_backgroup_full);
            Glide.with(getContext()).load(urlImage).into(img_songs_full_screen);
            txt_namesinger_full.setText(ringtunes.getSinger_name());
            txt_namesong_full.setText(ringtunes.getProduct_name());
            img_buysong.setVisibility(View.VISIBLE);
            if (MyApplication.lisItem.size()>0){
                for (int i = 0;i<lisItem.size();i++){
                    if (lisItem.get(i).getContent_id().equals(objPlaylist.getCurrentSong().getId())){
                        img_buysong.setVisibility(View.GONE);
                    }
                }
            }
            isFavorite = false;
            Glide.with(getContext()).load(R.drawable.icon_favorite_white).into(img_favorite_song);
            if (lisSongFavorite != null && lisSongFavorite.size() > 0) {
                for (int i = 0; i < lisSongFavorite.size(); i++) {
                    if (ringtunes.getId().equals(lisSongFavorite.get(i).getId())) {
                        isFavorite = true;
                        Glide.with(getContext()).load(R.drawable.icon_favorite_red).into(img_favorite_song);
                    }
                }
            }
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
            txtTime_BuySongs.setText("30 ngày)");
           // presenterSongs.get_info_songs_by_id(ringtunes.getId(), sUserId);
            presenterSongs.getSongsInformation(sessionID, msisdn, ringtunes.getId());
        }
    }

    private Runnable mProgressCallback = new Runnable() {
        @Override
        public void run() {
//            if (isDetached()) return;
            if (iPlayer.isPlaying()) {
                int seekbarmax = seekBarProgress.getMax();
                float getProress = iPlayer.getProgress();
                int getCuttentsong = iPlayer.getDuration();
                int progress = (int) (seekBarProgress.getMax()
                        * ((float) iPlayer.getProgress() / total_time));
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
      /*  iPlayer.releasePlayer();
        mHandler.removeCallbacks(mProgressCallback);*/
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

        // onSongUpdated(ringtunes);
        mHandler.postDelayed(mProgressCallback, 100);
        if (isPlaying) {
            Glide.with(getActivity()).load(R.drawable.bt_playpage_button_pause_normal_new).into(btnPlay_full);
        } else
            Glide.with(getActivity()).load(R.drawable.bt_playpage_button_play_normal_new).into(btnPlay_full);
        if (lisPlayRing.size() > 0) {
            for (int i = 0; i < lisPlayRing.size(); i++) {
                if (lisPlayRing.get(i).getId().equals(iPlayer.getPlayingSong().getId())) {
                    lisPlayRing.get(i).setPlaying(true);
                } else {
                    lisPlayRing.get(i).setPlaying(false);
                }
            }
        }
    }

    @Override
    public void PlayerCompete(boolean isComplete) {
        if (isComplete) {
            hideDialogLoading();
            updateData();
        }

    }

    private void showBottomSheetDialog() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        adapterRingtunes = new AdapterListSongPlayer(MyApplication.lisPlayRing, getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        TextView txt_list_play_total = view.findViewById(R.id.txt_list_play_total);
        txt_list_play_total.setText("Tổng số bài hát (" + MyApplication.lisPlayRing.size() + ")");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterRingtunes);
        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (mBottomSheetDialog != null) {
                    iPlayer.play(objPlaylist, position);
                    mBottomSheetDialog.dismiss();
                }
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    private void update_loop(int iLoop) {
        switch (iLoop) {
            case 0:
                Glide.with(getContext()).load(R.drawable.bt_playpage_order_normal_new).into(btn_play_loop);
                if (objPlaylist != null)
                    objPlaylist.setPlayMode(PlayMode.SINGLE);
                break;
            case 1:
                Glide.with(getContext()).load(R.drawable.bt_playpage_loop_normal_new).into(btn_play_loop);
                if (objPlaylist != null)
                    objPlaylist.setPlayMode(PlayMode.LIST);
                break;
            case 2:
                Glide.with(getContext()).load(R.drawable.bt_playpage_random_normal_new).into(btn_play_loop);
                if (objPlaylist != null)
                    objPlaylist.setPlayMode(PlayMode.SHUFFLE);
                break;
            case 3:
                Glide.with(getContext()).load(R.drawable.bt_playpage_roundsingle_normal_new).into(btn_play_loop);
                if (objPlaylist != null)
                    objPlaylist.setPlayMode(PlayMode.LOOP);
                break;
        }
    }

    public void fragmentBackTack() {
        ActivityMainHome.relative_tab.setVisibility(View.GONE);
        iPlayer.releasePlayer();
        mHandler.removeCallbacks(mProgressCallback);
        iPlayer.removeCallbacks();
        FragmentUtil.popBackStack(getActivity());
    }

    @Override
    public void showGiftSongsSuccess(List<String> list) {

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
        sExpiration = items.getExpiration();
        txtTime_BuySongs.setText(items.getExpiration() + " ngày)");
    }

    @Override
    public void showComment(List<Comment> listComment) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void show_add_comment_success(String s) {

    }

    @Override
    public void show_lis_songs_bysinger(List<Ringtunes> lisRingtunes) {

    }

    @Override
    public void show_lis_songs_by_id(List<Ringtunes> lisSongs) {
        if (lisSongs!=null&&lisSongs.get(0)!=null){

        }
    }

    public void BuySongs(final Ringtunes ringtunes) {
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

    public static boolean is_subscriber = false;
    public static boolean is_SVC_STATUS = false;

    @Override
    public void show_detail_subsriber(subscriber objSub) {
        hideDialogLoading();
        iWating = false;
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
                        BuySongs(iPlayer.getPlayingSong());
                    else {
                        objRingGift = iPlayer.getPlayingSong();
                        objRingGift.setsExpiration("");
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
                    txt_buysongs.setText(Html.fromHtml("Để hoàn tất dịch vụ Ringtunes, Quý khách vui lòng thực hiện thao tác <font color='#FF0000'>\"Y2 gửi 9194\"</font> từ số điện thoại <font color='#FF0000'> " + PhoneNumber.convertToVnPhoneNumber(msisdn) + "</font> giá cước 3000đ/ 7 ngày. Cảm ơn quý khách"));

                    // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
                    yes.setText("Đồng ý");
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenterGetUserInfo.api_subsriber_sms(msisdn, "2");
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
                            presenterGetUserInfo.api_subsriber_sms(msisdn, "1");
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
                        presenterGetUserInfo.api_subsriber_sms(msisdn, "2");
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
                            presenterGetUserInfo.api_subsriber_sms(msisdn, "1");
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

    @Override
    public void show_error_api() {
        hideDialogLoading();
    }

    @Override
    public void show_login_3g(List<String> list) {

    }

    @Override
    public void showDataLogin(List<String> list) {

    }

    @Override
    public void showLisSongsSame(List<Ringtunes> lisRing) {

    }

    @Override
    public void showConllection(List<Item> listItems) {

    }

    @Override
    public void showDeleteSuccessfull(List<String> list) {

    }

    @Override
    public void show_list_songs_collection(List<Ringtunes> listSongs) {

    }
    public void add_songs_favorite(Songs objSong) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objSong);
        realm.commitTransaction();
    }
}
