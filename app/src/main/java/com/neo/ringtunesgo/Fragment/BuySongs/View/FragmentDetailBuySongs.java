package com.neo.ringtunesgo.Fragment.BuySongs.View;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neo.ringtunesgo.Adapter.AdapterComment;
import com.neo.ringtunesgo.Adapter.AdapterRingtunes;
import com.neo.ringtunesgo.CRBTModel.Info_User;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Fragment.BuySongs.Presenter.PresenterSongs;
import com.neo.ringtunesgo.Fragment.BuySongs.Presenter.PresenterSongsImpl;
import com.neo.ringtunesgo.Fragment.Collection.FragmentConllection;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.Comment;
import com.neo.ringtunesgo.Model.Login;
import com.neo.ringtunesgo.Model.PhoneContactModel;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.Model.Singer;
import com.neo.ringtunesgo.Player.IPlayback;
import com.neo.ringtunesgo.Player.Player;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.View.ActivityContacts;
import com.neo.ringtunesgo.View.HomeFragment;
import com.neo.ringtunesgo.View.Xacthuc_thuebao.ActivityXacthuc;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.PhoneNumber;
import com.neo.ringtunesgo.untils.TimeUtils;
import com.neo.ringtunesgo.untils.Utilities;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.neo.ringtunesgo.Config.Constant.IMAGE_URL;
import static com.neo.ringtunesgo.MyApplication.player_ring;
import static com.neo.ringtunesgo.View.ActivityContacts.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by QQ on 7/28/2017.
 */

public class FragmentDetailBuySongs extends BaseFragment implements PresenterSongsImpl.View, MediaPlayer.OnPreparedListener, IPlayback.Callback {
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
    /*    @BindView(R.id.linnear_phone_gift)
        LinearLayout liner_gif_songs;
        @BindView(R.id.img_getphone_buysongs)
        ImageView img_getphone_songs;*/
    /*  @BindView(R.id.btn_buy_gift_buysongs)
      Button btn_buy_gift;*/
    @BindView(R.id.img_buy_buysong)
    Button btn_buysong;
    @BindView(R.id.img_gift_buysong)
    Button btn_giftsongs;
    @BindView(R.id.recycle_comment)
    RecyclerView recyclerView;
    @BindView(R.id.img_buysong_send_comment)
    ImageView img_send_comment;
    @BindView(R.id.txt_title_detailsong)
    TextView txt_title_buysong;
    @BindView(R.id.txt_total_comment_songs)
    TextView txt_total_comment;
    @BindView(R.id.img_buysong_detail)
    ImageView img_buysong_detail;
    @BindView(R.id.img_back_detailsong)
    ImageView img_back_detailsong;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterComment adapterComment;
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
    public String userName;
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
    boolean notifi;
    boolean isFull = false;
    String id_Songs;

    private void initPlayer() {
        if (Build.VERSION.SDK_INT >= 21) {
            seekBarProgress.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
            seekBarProgress.setThumbTintList(ColorStateList.valueOf(Color.MAGENTA));
        } else {
            seekBarProgress.getProgressDrawable().setColorFilter(R.color.orange, PorterDuff.Mode.SRC_IN);
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
        //  seekBarProgress.setOnSeekBarChangeListener(listener);

        //  seekBarProgress.setOnSeekBarChangeListener(listener);
        // onSongUpdated( );

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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buysongs_detail, container, false);
        ButterKnife.bind(this, view);
        presenterSongs = new PresenterSongs(this);
        ed_add_comment_content = (EditText) view.findViewById(R.id.input_content_buysongs);
        img_buysong_detail_nen = (ImageView) view.findViewById(R.id.img_buysong_detail_nen);
        init();
        initEvent();
        initPlayer();
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
        intData();
        realm = RealmController.with(this).getRealm();
        iPlayer = Player.getInstance(getContext());
        iPlayer = new Player(this);
        //
        if (!notifi) {
            updateData(ringtunes);
            if (ringtunes != null && ringtunes.getProduct_name().length() > 0)
                txt_title_buysong.setText("" + ringtunes.getProduct_name());
            else
                txt_title_buysong.setText("Chi tiết bài hát");

            if (ringtunes != null) {
                String id_singer = ringtunes.getSinger_id();
                presenterSongs.getBySinger(id_singer, "1", "" + page, "" + index);
            }
        }
        MainNavigationActivity.appbar.setVisibility(View.GONE);

        objInfo = realm.where(Info_User.class).findFirst();
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();
        if (objLogin != null) {
            isLogin = objLogin.isLogin();
        }

    }

    private void StartPlayer(Ringtunes ringtunes) {
        ringtunes.setFull(false);
        //ringtunes.setPath(Constant.MUSIC_URL + ringtunes.getPath().replace(" ", "%20"));
        iPlayer.play(ringtunes);
        onSongUpdated(ringtunes);
        mHandler.postDelayed(mProgressCallback, 100);
        //updateProgressBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isHome) {
            MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
        }
        iPlayer.releasePlayer();
        mHandler.removeCallbacks(mProgressCallback);
        if (notifi) {
            SharedPreferences.Editor editor = pre.edit();
            editor.putBoolean("notifi", false);
            editor.putString("id_songs", "");
            editor.putString("id_singer", "");
            editor.commit();
            MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
            if (HomeFragment.getInstance() == null) {
                FragmentUtil.pushFragmentLayoutMain(getFragmentManager(),
                        R.id.fame_main, HomeFragment.getInstance(), null, HomeFragment.class.getSimpleName());
            } else {
                FragmentUtil.pushFragmentLayoutMain(getFragmentManager(),
                        R.id.fame_main, HomeFragment.getInstance(), null, HomeFragment.class.getSimpleName());
            }

        }
    }

    private void intData() {

        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        isHome = pre.getBoolean("isHome", false);
        notifi = pre.getBoolean("notifi", false);
        userName = pre.getString("user_id", "");
        ringtunes = player_ring;
        msisdn = pre.getString("msisdn", "");
        id_Singer = pre.getString("isSinger", "");
        isLogin = pre.getBoolean("isLogin", false);
        sessionID = pre.getString("sessionID", "");
        pass = pre.getString("password", "");
        if (notifi) {
            id_Songs = pre.getString("id_songs", "");
            id_Singer = pre.getString("id_singer", "");
            presenterSongs.get_info_songs_by_id(id_Songs, userName);
            presenterSongs.getBySinger(id_Singer, "1", "" + page, "" + index);
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

    private void initEvent() {
        btn_buysong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuySongs();
            }
        });

        btn_giftsongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    dialog_getphone = new Dialog(getContext());
                    dialog_getphone.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_getphone.setContentView(R.layout.dialog_get_contact);
                    ed_getphone = (EditText) dialog_getphone.findViewById(R.id.ed_add_phone_dialog);
                    ImageView contact = (ImageView) dialog_getphone.findViewById(R.id.img_add_contact_dialog);
                    Button ok = (Button) dialog_getphone.findViewById(R.id.btn_dialogcontact_ok);
                    Button cancel = (Button) dialog_getphone.findViewById(R.id.btn_dialogcontact_cancel);

                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            askForContactPermission();
                            dialog_getphone.dismiss();
                        }
                    });
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ed_getphone.getText().length() > 0) {
                                String phone_gift = ed_getphone.getText().toString();
                                phone_gift = phone_gift.replaceAll("\\,", "");
                                phone_gift = phone_gift.replaceAll(" ", "");
                                phone_gift = PhoneNumber.convertTo84PhoneNunber(phone_gift);
                                presenterSongs.addGifttoPlayList(sessionID, msisdn, phone_gift, sExpiration, ringtunes.getId());
                                dialog_getphone.dismiss();
                            } else
                                Toast.makeText(getContext(), "Bạn chưa nhập vào số điện thoại", Toast.LENGTH_SHORT).show();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_getphone.dismiss();
                        }
                    });
                    dialog_getphone.show();
                } else {
                    // yêu cầu login
                    final Dialog dialog_yes = new Dialog(getContext());
                    dialog_yes.setContentView(R.layout.dialog_yes_no);
                    TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
                    Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                    Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);

                    txt_buysongs.setText("Mời bạn đăng nhập để tiếp tục tặng bài");
                    yes.setText("Đăng nhập");

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(new Intent(getActivity(), ActivityXacthuc.class));
                            dialog_yes.dismiss();
                        }
                    });
                    no.setText("Trở lại");
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_yes.dismiss();
                        }
                    });
                    dialog_yes.show();
                }

            }
        });
        img_back_detailsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });

   /*     btn_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFull = true;
                Bundle bundle = new Bundle();
                bundle.putString("option", Config.GIFT);
                bundle.putSerializable("objRing", ringtunes);
                FragmentUtil.addFragmentData(getActivity(), FragmentPlayerFull.getInstance(), true, bundle);
            }
        });
*/
    }

    public void get_contact() {
        MainNavigationActivity.datasvina = new ArrayList<PhoneContactModel>();
        MainNavigationActivity.datas = new ArrayList<PhoneContactModel>();
        MainNavigationActivity.listGitSongs = new ArrayList<PhoneContactModel>();
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("option", Config.GIFT);
        editor.commit();
        FragmentUtil.addFragment(getActivity(), ActivityContacts.getInstance(), true);
    }

    private void sendComment() {
        InputMethodManager inputManager = (InputMethodManager) getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().
                getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        presenterSongs.add_comment_services("205197638", ringtunes.getId(), "401", "12-8-2017", "" + 1,
                ed_add_comment_content.getText().toString(), "1689932004",
                "APP", "1689932004", "0", "1", "1");
    }


    @Override
    public void showGiftSongsSuccess(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("XÁC NHẬN");
                builder.setMessage(list.get(1));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("XÁC NHẬN");
                builder.setMessage(list.get(1));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void ShowBuySongsSuccess(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("XÁC NHẬN");
                builder.setMessage("Bạn đã mua bài thành công, bạn có muốn vào bộ sưu tập");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentUtil.addFragment(getActivity(), FragmentConllection.getInstance(), true);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            fm.popBackStack();
                        }
                    }
                });
                builder.show();
            } else Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();

        }
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

    @Override
    public void show_lis_songs_bysinger(List<Ringtunes> lisRingtunes) {
        hideDialogLoading();
        if (lisRingtunes.size() > 0) {
            lisRing.addAll(lisRingtunes);
            adapterRingtunes.notifyDataSetChanged();

            adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
                @Override
                public void OnItemClickListener(int position) {
                    showDialogLoading();
                    ringtunes = lisRing.get(position);
                    updateData(ringtunes);
                    nestedScrollView.fullScroll(View.FOCUS_UP);
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
        /*if (ringtunes.getPathfulltrack() != null && ringtunes.getPathfulltrack().length() > 0) {
            btn_full.setVisibility(View.VISIBLE);
        } else btn_full.setVisibility(View.GONE);*/
        presenterSongs.getSongsInformation(sessionID, msisdn, ringtunes.getId());
        StartPlayer(ringtunes);
        String urlImage = IMAGE_URL + ringtunes.getImage_file();
        Glide.with(getContext()).load(urlImage).into(img_buysong_detail);
        // Glide.with(getContext()).load(urlImage).into(img_buysong_detail_nen);
        Glide.with(this).load(urlImage)
                .apply(bitmapTransform(new BlurTransformation(10)))
                .into(img_buysong_detail_nen);
        txtSinger_BuySongs.setText(ringtunes.getSinger_name());
        if (ringtunes.getHist() == null) {
            txtHits_BuySongs.setText("0");
        } else
            txtHits_BuySongs.setText(ringtunes.getHist());
        txtPrice_BuySongs.setText("(" + ringtunes.getPrice() + " Đ");
        txt_name_buysongs.setText(ringtunes.getProduct_name());

    }

    public void BuySongs() {
        if (isLogin) {
            final Dialog dialog_yes = new Dialog(getContext());
            dialog_yes.setContentView(R.layout.dialog_yes_no);
            TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
            Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
            Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);

            txt_buysongs.setText("Bạn muốn mua bài hát " + ringtunes.getProduct_name() + " với giá "
                    + ringtunes.getPrice() + " làm nhạc chờ");
            yes.setText("Đồng ý");
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenterSongs.addItemtoMyList(sessionID, msisdn, sExpiration, ringtunes.getId());
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

          /*  presenterSongs.log_Info_Charge_Server("app buy " + ringtunes.getId(),
                    "122121709123164557798245536981", "84912796369", "", "3");*/
        } else {
            // yêu cầu login
            final Dialog dialog_yes = new Dialog(getContext());
            dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_yes.setContentView(R.layout.dialog_yes_no);
            TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
            Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
            Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);

            txt_buysongs.setText("Mời bạn đăng nhập để tiếp tục mua bài");
            yes.setText("Đăng nhập");

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), ActivityXacthuc.class));
                    dialog_yes.dismiss();
                }
            });
            no.setText("Trở lại");
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_yes.dismiss();
                }
            });
            dialog_yes.show();
        }
    }


    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                get_contact();
                // datas = CustomUtils.getAllPhoneContacts(getContext());
            }
        } else {
            get_contact();
            //datas = CustomUtils.getAllPhoneContacts(getContext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    get_contact();
                    //datas = CustomUtils.getAllPhoneContacts(getContext());

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

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
}
