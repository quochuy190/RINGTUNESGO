package com.neo.media;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.neo.media.Adapter.AdapterDrawer;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Config.Config;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.Collection.FragmentConllection;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.Fragment.FragmentInfo;
import com.neo.media.Fragment.Groups.FragmentGroups;
import com.neo.media.Fragment.Home.View.FragmentHome;
import com.neo.media.Fragment.Profiles.FragmentProfiles;
import com.neo.media.Fragment.Stop_Pause.FragmentStopPause;
import com.neo.media.MainActivity.FragmetSearch;
import com.neo.media.MainActivity.MainActivityImpl;
import com.neo.media.MainActivity.PresenterMainActivity;
import com.neo.media.Model.DrawerItem;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Login;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Singer;
import com.neo.media.Player.IPlayback;
import com.neo.media.Player.PlaybackService;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.HomeFragment;
import com.neo.media.View.Xacthuc_thuebao.ActivityXacthuc;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.Storage;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainNavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityImpl.View, View.OnClickListener {
    public static final String TAG = MainNavigationActivity.class.getSimpleName();
    public static LinearLayout linearPlayer;
    public static Intent playerService;
    public static TextView txtPlayerNameSong;
    public static List<PhoneContactModel> listGitSongs;
    public static List<PhoneContactModel> listGroupRingtunes;
    public static ImageView btnPlayerPlay;
    public static TextView btnHide;
    public static ImageView btn_Search;
    public static EditText ed_key_search;
    private List<PhoneContactModel> lisContacts;
    public static SeekBar progresbar_Player;
    public static TextView songTotalDurationLabel;
    public static TextView songCurrentDurationLabel;
    public static FragmentActivity fragmentActivity;
    public static FragmentManager fragmentManager;
    //  public static RelativeLayout relatic_tab;
    boolean is_playing;
    public static ArrayList<PhoneContactModel> datas = new ArrayList<>();
    public static ArrayList<PhoneContactModel> datasvina = new ArrayList<>();
    public static String phone_addprofile = "";
    public static ActionBar ab;
    public static Ringtunes objBuySong = new Ringtunes();
    public static String option_BuySong = "";
    public static String idSinger = "";
    public static Singer objSinger = new Singer();
    public static Storage storage; // this Preference comes for free from the library
    public boolean doubleBackToExitPressedOnce = false;
    PresenterMainActivity presenterMainActivity;
    public static Realm myRealm;
    public static AdapterDrawer adapterDrawer;
    public static ListView listView;
    public static List<DrawerItem> lisDrawer;
    public static boolean isLogin = false;
    public static boolean isHome = false;
    public static AppBarLayout appbar;
    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    public static MediaPlayer mp;
    public static String title_lisSongs = "";
    public static String id_lisSongs = "";
    public static SharedPreferences fr;
    public static boolean is_subscriber = false;
    public static boolean is_SVC_STATUS = false;
    boolean is_user_id;
    String token;
    boolean is_token;
    Login objLoginAll;
    public static RelativeLayout layoutContainer;
    public static ImageView imgPlayPauseBottom;
    boolean isPurchase = false;
    int categoryPlay = 0;
    public static IPlayback mPlayer;
    private ServiceConnection connection;
    private boolean mIsServiceBound;
    private PlaybackService mPlaybackService;
    public String sesionID;
    public String msisdn;
    boolean is_save_namegroup;
    public static Info_User objInfo;
    public static Login objLogin;
    String VERSION_OS = "";
    String ISMODEL = "";
    String VERSION = "";
    String MODEL = "";
    String user_id;
    String notifi;
    public static Context mContext;
    String image;
    String subtype;
    boolean is3g;
    boolean isWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.activity_main_navigation);
        // khởi tạo Realm

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.lst_menu_items);
        mContext = getApplicationContext();
        setSupportActionBar(toolbar);
        mp = new MediaPlayer();
        ab = getSupportActionBar();
        myRealm = RealmController.with(this).getRealm();
        fragmentManager = this.getSupportFragmentManager();
        getInfoDevice();
        getDataSharedPreferences();
        presenterMainActivity = new PresenterMainActivity(getApplicationContext(), this);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mPlaybackService = ((PlaybackService.LocalBinder) service).getService();
                mIsServiceBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIsServiceBound = false;
            }
        };
        Intent intent = new Intent(MainNavigationActivity.this, PlaybackService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        mPlayer = mPlaybackService;

        presenterMainActivity.getInfo_User(sesionID, msisdn);
        // storage = new Storage(getApplicationContext());
        fragmentActivity = this;

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isLogin) {
                    if (position == 1) {
                        HomeFragment.viewpagerHome.setCurrentItem(0);
                        drawer.closeDrawers();
                    } else if (position == 2) {
                        HomeFragment.viewpagerHome.setCurrentItem(2);
                        drawer.closeDrawers();
                    } else if (position == 3) {
                        HomeFragment.viewpagerHome.setCurrentItem(1);
                        drawer.closeDrawers();
                    } else if (position == 4) {
                        HomeFragment.viewpagerHome.setCurrentItem(3);
                        drawer.closeDrawers();
                    } else if (position == 5) {
                        startActivity(new Intent(MainNavigationActivity.this, ActivityXacthuc.class));
                        drawer.closeDrawers();
                    }
                } else {
                    if (position == 1) {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fame_main);
                        if (fragment instanceof HomeFragment) {
                            HomeFragment.viewpagerHome.setCurrentItem(0);
                        }
                        drawer.closeDrawers();
                    } else if (position == 2) {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fame_main);
                        if (fragment instanceof HomeFragment) {
                            HomeFragment.viewpagerHome.setCurrentItem(2);
                        }
                        drawer.closeDrawers();
                    } else if (position == 3) {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fame_main);
                        if (fragment instanceof HomeFragment) {
                            HomeFragment.viewpagerHome.setCurrentItem(1);
                        }
                        drawer.closeDrawers();
                    } else if (position == 4) {
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fame_main);
                        if (fragment instanceof HomeFragment) {
                            HomeFragment.viewpagerHome.setCurrentItem(3);
                        }
                        drawer.closeDrawers();
                    } else if (position == 6) {
                        if (!FragmentConllection.getInstance().isAdded()) {
                            SharedPreferences.Editor editor = fr.edit();
                            editor.putBoolean("isHome", true);
                            editor.commit();
                            FragmentUtil.addFragment(MainNavigationActivity.this, FragmentConllection.getInstance(), true);
                        }
                        drawer.closeDrawers();
                    } else if (position == 7) {
                        if (!FragmentGroups.getInstance().isAdded()) {
                            FragmentUtil.addFragment(MainNavigationActivity.this, FragmentGroups.getInstance(), true);
                        }
                        drawer.closeDrawers();
                    } else if (position == 8) {
                        if (!FragmentProfiles.getInstance().isAdded())
                            FragmentUtil.addFragment(MainNavigationActivity.this, FragmentProfiles.getInstance(), true);
                        drawer.closeDrawers();
                    } else if (position == 9) {
                        startActivity(new Intent(MainNavigationActivity.this, FragmentStopPause.class));
                        drawer.closeDrawers();
                    } else if (position == 10) {
                        if (!FragmentInfo.getInstance().isAdded())
                            FragmentUtil.addFragment(MainNavigationActivity.this, FragmentInfo.getInstance(), true);
                        drawer.closeDrawers();
                    } else if (position == 11) {
                        final Dialog dialog_yes = new Dialog(MainNavigationActivity.this);
                        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog_yes.setContentView(R.layout.dialog_yes_no);
                        TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
                        Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                        Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
                        txt_buysongs.setText("Bạn có muốn đăng xuất không");
                        yes.setText("Đồng ý");
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyApplication.objGroup = new GROUP();
                                MyApplication.profile_bundle = new PROFILE();
                                MyApplication.listConllection = new ArrayList<>();
                                MyApplication.player_ring = new Ringtunes();
                                isLogin = false;
                                myRealm.beginTransaction();
                                myRealm.clear(Login.class);
                                myRealm.clear(Info_User.class);
                                myRealm.copyToRealmOrUpdate(objLoginAll);
                                myRealm.commitTransaction();
                                addDrawerItem();
                                initDrawer();
                                dialog_yes.dismiss();
                                SharedPreferences.Editor editor = fr.edit();
                                editor.putBoolean("is_Dangky", false);
                                editor.putBoolean("is_save_namegroup", false);
                                editor.commit();
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

                }


            }


        });
        //khoi tạo fragment
        addHomeFragment();
        lisContacts = new ArrayList<>();
        // lisContacts = CustomUtils.getAllPhoneContacts(this);
        Log.i(TAG, "" + lisContacts.size());
        //   storage = new Storage(getApplicationContext());

        linearPlayer = (LinearLayout) findViewById(R.id.ln_player);

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main_navigation;
    }

    @Override
    public void initData() {
        initViews();
        initEvent();


    }

    public static void reloadLeftMenu() {
        is_SVC_STATUS = fr.getBoolean("isStatus", false);
        is_subscriber = fr.getBoolean("isService_status", false);
        if (isLogin) {
            reloadDrawer();
            adapterDrawer.notifyDataSetChanged();
        } else {
            addDrawerItem();
            adapterDrawer.notifyDataSetChanged();
        }
    }

    private void getDataSharedPreferences() {
        fr = getSharedPreferences("data", MODE_PRIVATE);
       /* objInfo = myRealm.where(Info_User.class).findFirst();
        // String s = objInfo.getError_code();
        objLogin = myRealm.where(Login.class).findFirst();

        if (objLogin != null) {
            if (objInfo != null) {
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
            }
        }*/
        sesionID = fr.getString("sessionID", "");
        msisdn = fr.getString("msisdn", "");
        user_id = fr.getString("user_id", "");
        Constant.USER_ID = user_id;
        Constant.sSessionID = sesionID;
        Constant.sMSISDN = msisdn;
        token = fr.getString("token", "");
        is_token = fr.getBoolean("token_sucsess", false);
        is_user_id = fr.getBoolean("is_user_id", false);
        is_save_namegroup = fr.getBoolean("is_save_namegroup", false);
    }

    private void initDrawer() {
        adapterDrawer = new AdapterDrawer(this, R.layout.item_drawer, lisDrawer);
        listView.setAdapter(adapterDrawer);
        adapterDrawer.notifyDataSetChanged();
    }

    private static void reloadDrawer() {
        lisDrawer = new ArrayList<>();
        DrawerItem drawerItem0 = new DrawerItem();
        drawerItem0.setImgResID(R.drawable.home_blue);
        drawerItem0.setItemName("Trang chủ");
        lisDrawer.add(drawerItem0);
        DrawerItem drawerItem = new DrawerItem();
        drawerItem.setImgResID(R.drawable.home_blue);
        drawerItem.setItemName("Trang chủ");
        lisDrawer.add(drawerItem);
        DrawerItem drawerIte2 = new DrawerItem();
        drawerIte2.setImgResID(R.drawable.topic_blue);
        drawerIte2.setItemName("Chủ đề");
        lisDrawer.add(drawerIte2);
        DrawerItem drawerItem3 = new DrawerItem();
        drawerItem3.setImgResID(R.drawable.stype_blue);
        drawerItem3.setItemName("Thể loại");
        lisDrawer.add(drawerItem3);
        DrawerItem drawerItem4 = new DrawerItem();
        drawerItem4.setImgResID(R.drawable.singer_blue);
        drawerItem4.setItemName("Ca sĩ");
        lisDrawer.add(drawerItem4);
        DrawerItem drawerItem6_title = new DrawerItem();
        drawerItem6_title.setImgResID(R.drawable.home_blue);
        drawerItem6_title.setItemName("Trang chủ");
        lisDrawer.add(drawerItem6_title);
        DrawerItem drawerItem5 = new DrawerItem();
        drawerItem5.setImgResID(R.drawable.personal_blue);
        drawerItem5.setItemName("Bộ sưu tập cá nhân");
        lisDrawer.add(drawerItem5);
        DrawerItem drawerItem6 = new DrawerItem();
        drawerItem6.setImgResID(R.drawable.group_blue);
        drawerItem6.setItemName("Quản lý nhóm");
        lisDrawer.add(drawerItem6);
        DrawerItem drawerItem7 = new DrawerItem();
        drawerItem7.setImgResID(R.drawable.setup_blue);
        drawerItem7.setItemName("Cài đặt ringtunes");
        lisDrawer.add(drawerItem7);
        DrawerItem drawerIte8 = new DrawerItem();
        drawerIte8.setImgResID(R.drawable.stop_blue);
        if (is_subscriber) {
            if (!is_SVC_STATUS) {
                drawerIte8.setItemName("Kích hoạt");
            } else
                drawerIte8.setItemName("Tạm dừng| Huỷ");
        } else {
            drawerIte8.setItemName("Đăng ký");
        }

        lisDrawer.add(drawerIte8);
        DrawerItem drawerItem9 = new DrawerItem();
        drawerItem9.setImgResID(R.drawable.infomation);
        drawerItem9.setItemName("Thông tin thuê bao");
        lisDrawer.add(drawerItem9);
        DrawerItem drawerItem10 = new DrawerItem();
        drawerItem10.setImgResID(R.drawable.logout_blue);
        drawerItem10.setItemName("Đăng xuất");
        lisDrawer.add(drawerItem10);
    }

    public static void addDrawerItem() {
        lisDrawer = new ArrayList<>();
        DrawerItem drawerItem0 = new DrawerItem();
        drawerItem0.setImgResID(R.drawable.home_blue);
        drawerItem0.setItemName("Trang chủ");
        lisDrawer.add(drawerItem0);
        DrawerItem drawerItem = new DrawerItem();
        drawerItem.setImgResID(R.drawable.home_blue);
        drawerItem.setItemName("Trang chủ");
        lisDrawer.add(drawerItem);
        DrawerItem drawerIte2 = new DrawerItem();
        drawerIte2.setImgResID(R.drawable.topic_blue);
        drawerIte2.setItemName("Chủ đề");
        lisDrawer.add(drawerIte2);
        DrawerItem drawerItem3 = new DrawerItem();
        drawerItem3.setImgResID(R.drawable.stype_blue);
        drawerItem3.setItemName("Thể loại");
        lisDrawer.add(drawerItem3);
        DrawerItem drawerItem4 = new DrawerItem();
        drawerItem4.setImgResID(R.drawable.singer_blue);
        drawerItem4.setItemName("Ca sĩ");
        lisDrawer.add(drawerItem4);
        DrawerItem drawerItem5 = new DrawerItem();
        drawerItem5.setImgResID(R.drawable.logout_blue);
        drawerItem5.setItemName("Đăng nhập");
        lisDrawer.add(drawerItem5);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fame_main);
        if (fragment instanceof HomeFragment) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            HomeFragment.getInstance().fragmentBackTack();
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initViews() {

        appbar = (AppBarLayout) findViewById(R.id.appbar);
        btn_Search = (ImageView) findViewById(R.id.btn_key_search);
    }

    private void initEvent() {

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FragmetSearch.getInstance().isAdded())
                    FragmentUtil.addFragment(MainNavigationActivity.fragmentActivity, FragmetSearch.getInstance(), true);
                //  startActivity(new Intent(MainActivity.this, ActivitySearch.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showListSearch(List<Ringtunes> ringtunes) {

    }

    @Override
    public void showInfo_User(subscriber subscriber) {
        Info_User info_user = new Info_User();
        if (subscriber.getERROR() != null && subscriber.getERROR().equals("0")) {
            info_user.setsPhone(msisdn);
            info_user.setError_desc(subscriber.getERROR_DESC());
            info_user.setReq_id(subscriber.getPREPAID());
            info_user.setStatus(subscriber.getSTATUS());
            info_user.setRegister_date(subscriber.getREG_DATE());
            info_user.setService_status(subscriber.getSVC_STATUS());
            info_user.setError_code(subscriber.getERROR());
            info_user.setIs_corporate(subscriber.getCORPORATE());
            myRealm.beginTransaction();
            myRealm.copyToRealmOrUpdate(info_user);
            myRealm.commitTransaction();
        } else if (subscriber.getERROR().equals("102")) {
            info_user.setsPhone(msisdn);
            info_user.setError_desc(subscriber.getERROR_DESC());
            info_user.setReq_id("");
            info_user.setStatus("");
            info_user.setRegister_date("");
            info_user.setService_status("");
            info_user.setError_code(subscriber.getERROR());
            info_user.setIs_corporate("");
            myRealm.beginTransaction();
            myRealm.copyToRealmOrUpdate(info_user);
            myRealm.commitTransaction();
        }
    }

    public void show_update_token(String sUserNameId) {
        if (sUserNameId.equals("1:thanh cong")) {
            SharedPreferences.Editor editor = fr.edit();
            // editor.putString("user_id", sUserNameId);
            editor.putBoolean("token_sucsess", true);
            editor.putString("token", token);
            editor.commit();
        }
    }

    @Override
    public void show_init_service(String sUserNameId) {
        Log.i(TAG, sUserNameId);
        SharedPreferences.Editor editor = fr.edit();
        editor.putString("user_id", sUserNameId);
        editor.putString("token", token);
        editor.putBoolean("is_user_id", true);
        editor.commit();
        if (sUserNameId.length() > 0 && sUserNameId != null) {
            Login objLogin = new Login();
            objLogin.setsUserName(sUserNameId);
            Constant.USER_ID = sUserNameId;
            myRealm.beginTransaction();
            myRealm.copyToRealmOrUpdate(objLogin);
            myRealm.commitTransaction();
        }
    }

    @Override
    public void onPlaybackServiceBound(PlaybackService service) {
        mPlayer = service;
    }

    @Override
    public void onPlaybackServiceUnbound() {
        mPlayer = null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void initNetwork(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //For 3G check
        is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

    }
    @Override

    protected void onResume() {
        super.onResume();
        boolean is_Dangky= fr.getBoolean("is_Dangky", false);

        is_save_namegroup = fr.getBoolean("is_save_namegroup", false);
        getUserName();
        saveNameGroup();
        objLoginAll = myRealm.where(Login.class).findFirst();
        objInfo = myRealm.where(Info_User.class).findFirst();
        if (objLoginAll != null) {
            if (objLoginAll.isLogin())
                isLogin = objLoginAll.isLogin();
            if (objInfo != null) {
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
                if (is_Dangky){

                    SharedPreferences.Editor editor = fr.edit();
                    editor.putBoolean("is_Dangky", false);
                    editor.commit();
                    startActivity(new Intent(MainNavigationActivity.this, FragmentStopPause.class));
                }
            }
        }
        if (isLogin) {
            reloadDrawer();
            initDrawer();
        } else {
            addDrawerItem();
            initDrawer();
        }
        subtype = getIntent().getStringExtra("subtype");
        image = getIntent().getStringExtra("image");
        notifi = getIntent().getStringExtra("type");
        String title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");
        String id_Singer = getIntent().getStringExtra("idsinger");

        if (notifi != null) {
            if (notifi.equals("1")) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putBoolean("notifi", true);
                editor.putString("id_songs", id);
                editor.putString("id_singer", id_Singer);
                editor.commit();
                FragmentHome.addFragmentBuySongs();
            } else if (notifi.equals("2")) {
                SharedPreferences.Editor editor = fr.edit();
                switch (subtype) {
                    case "1":
                        editor.putString("option", Config.RINGTUNES_NEW);
                        editor.putString("title", "Ringtunes Mới");
                        break;
                    case "2":
                        editor.putString("option", Config.EVENT);
                        editor.putString("type_event", "event_details");
                        editor.putString("title", title);
                        break;
                    case "3":
                        editor.putString("option", Config.EVENT);
                        editor.putString("type_event", "promotion_details");
                        editor.putString("title", title);
                        break;
                    case "4":
                        editor.putString("option", Config.RINGTUNES_HOT);
                        editor.putString("title", "Ringtunes Hot");
                        break;
                    case "5":
                        editor.putString("option", Config.RINGTUNES_NEW);
                        editor.putString("title", "Ringtunes Mới");
                        break;
                    case "6":
                        editor.putString("option", Config.TYPE);
                        editor.putString("title", "Thể Loại");
                        break;
                    case "7":
                        editor.putString("option", Config.SINGER);
                        editor.putString("title", "Ca sĩ");
                        break;
                    case "8":
                        editor.putString("option", Config.TOPIC);
                        editor.putString("title", "Chủ đề");
                        break;
                }
                editor.putString("id", id);
                editor.putString("url_image_title", image);
                editor.commit();
                FragmentUtil.addFragment(fragmentActivity, FragmentSongs.getInstance(), true);
            }
        }
    }

    @Override
    protected void onPause() {
/*        Log.i(TAG, "onDestroy");
        //Hủy đăng ký broadcast receiver
       // unbindService(connection);
        if (mBufferBroadcastIsRegistered) {
            unregisterReceiver(broadcastBufferReceiver);
            mBufferBroadcastIsRegistered = false;
        }*/
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.releasePlayer();
        }
        if (mIsServiceBound) {
            this.unbindService(connection);
            mIsServiceBound = false;
        }

    }

    public static void addHomeFragment() {
        FragmentUtil.pushFragmentLayoutMain(fragmentManager,
                R.id.fame_main, HomeFragment.getInstance(), null, HomeFragment.class.getSimpleName());
    }

    public void getUserName() {

        boolean is_user_id = fr.getBoolean("is_user_id", false);
        boolean is_token = fr.getBoolean("token_sucsess", false);
        if (!is_token) {
            FirebaseMessaging.getInstance().subscribeToTopic("testfcm");
            token = FirebaseInstanceId.getInstance().getToken();
            // Log.i(TAG, token);
            if (token != null && token.length() > 0) {
                if (!is_user_id) {
                    SharedPreferences.Editor editor = fr.edit();
                    editor.putString("token", token);
                    editor.putBoolean("token_sucsess", true);
                    editor.commit();
                    presenterMainActivity.init_service(token, VERSION, ISMODEL, MODEL, VERSION_OS);
                } else {
                    Log.i(TAG, "update token");
                    presenterMainActivity.update_token(token, user_id);
                }
            } else {
                if (!is_user_id)
                    presenterMainActivity.init_service("update", VERSION, ISMODEL, MODEL, VERSION_OS);
            }
        }
    }

    public void saveNameGroup() {
        List<String> listName = new ArrayList<>();
        listName.add("dongnghiep");
        listName.add("giadinh");
        listName.add("banbe");
        listName.add("nhom1");
        listName.add("nhom2");
        listName.add("nhom3");
        listName.add("nhom4");
        listName.add("nhom5");
        listName.add("nhom6");
        listName.add("nhom7");
        listName.add("nhom8");
        listName.add("nhom9");
        listName.add("nhom10");
        if (!is_save_namegroup) {
            for (int i = 0; i < listName.size(); i++) {
                GroupName groupName = new GroupName();
                groupName.setsNameServer(listName.get(i));
                myRealm.beginTransaction();
                myRealm.copyToRealmOrUpdate(groupName);
                myRealm.commitTransaction();
            }
            SharedPreferences.Editor editor = fr.edit();
            editor.putBoolean("is_save_namegroup", true);
            editor.commit();
        }
    }


    public void getInfoDevice() {
        int versionCode = BuildConfig.VERSION_CODE;
        VERSION = BuildConfig.VERSION_NAME;
        String _OSVERSION = System.getProperty("os.version");
        //phiên ban andoird
        String _RELEASE = android.os.Build.VERSION.RELEASE;
        String _DEVICE = android.os.Build.DEVICE;
        //đời máy
        String _MODEL = android.os.Build.MODEL;
        String _PRODUCT = android.os.Build.PRODUCT;
        //hãng máy
        String BRAND = android.os.Build.BRAND;

        String _DISPLAY = android.os.Build.DISPLAY;
        String _CPU_ABI = android.os.Build.CPU_ABI;
        String _CPU_ABI2 = android.os.Build.CPU_ABI2;
        String _UNKNOWN = android.os.Build.UNKNOWN;
        String _HARDWARE = android.os.Build.HARDWARE;
        String _ID = android.os.Build.ID;
        String _MANUFACTURER = android.os.Build.MANUFACTURER;
        String _SERIAL = android.os.Build.SERIAL;
        String _USER = android.os.Build.USER;
        String _HOST = android.os.Build.HOST;
        MODEL = BRAND + " " + _MODEL;
        VERSION_OS = _RELEASE;
        ISMODEL = "2";
    }
}
