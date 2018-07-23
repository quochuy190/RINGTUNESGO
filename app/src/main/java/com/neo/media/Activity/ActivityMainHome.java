package com.neo.media.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.neo.media.BuildConfig;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.Favorite.FragmentPlayerFull;
import com.neo.media.MainActivity.PresenterMainActivity;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.FragmentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

/**
 * Created by QQ on 11/20/2017.
 */

public class ActivityMainHome extends AppCompatActivity {
    private static final String TAG = "ActivityMainHome";
    public static ActionBar ab;
    @BindView(R.id.fame_main)
    FrameLayout frameLayout;
    public static Realm myRealm;
    PresenterMainActivity presenterActivity;
    public static RelativeLayout relative_tab;
    public static String phone_addprofile = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        myRealm = RealmController.with(this).getRealm();
        relative_tab = (RelativeLayout) findViewById(R.id.relative_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        user_id = Prefs.with(this).getString("user_id", "");
        presenterActivity = new PresenterMainActivity(this);
        presenterActivity.search_keyword_top(user_id);
        if (notifi != null) {
       /*     startActivity(new Intent(ActivityMainHome.this, ActivityNotifycation.class));
            finish();*/
            if (!FragmentHomeActivity.getInstance().isAdded())
                FragmentUtil.pushFragment(getSupportFragmentManager(), R.id.fame_main, FragmentHomeActivity.getInstance(), null);
        } else {
            if (!FragmentHomeActivity.getInstance().isAdded())
                FragmentUtil.pushFragment(getSupportFragmentManager(), R.id.fame_main, FragmentHomeActivity.getInstance(), null);
            //FragmentUtil.addFragment(this, FragmentHomeActivity.getInstance(), true);
        }
        //FragmentUtil.pushFragment(getSupportFragmentManager(), R.id.fame_main, FragmentHomeActivity.getInstance(), null);
    }

    String notifi;
    String image;
    String subtype;

    private void initData() {
        subtype = getIntent().getStringExtra("subtype");
        image = getIntent().getStringExtra("image");
        notifi = getIntent().getStringExtra("type");
        String title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");
        String id_Singer = getIntent().getStringExtra("idsinger");
        Constant.USER_ID = Prefs.with(this).getString("user_id", "");
        Constant.sMSISDN = Prefs.with(this).getString("msisdn", "");
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserName();
        saveNameGroup();

    }

    public void notification() {

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fame_main);
        if (fragment instanceof FragmentHomeActivity) {
            FragmentHomeActivity.getInstance().fragmentBackTack();
        } else if (fragment instanceof FragmentPlayerFull) {
            FragmentPlayerFull.getInstance().fragmentBackTack();
        } else if (fragment instanceof FragmentDetailBuySongs) {
            FragmentDetailBuySongs.getInstance().fragmentBackTack();
        } else {
            super.onBackPressed();
        }
    }

    public void showInfo_User(subscriber subscriber) {
        Info_User info_user = new Info_User();
        if (subscriber.getERROR() != null && subscriber.getERROR().equals("0")) {
            //info_user.setsPhone(msisdn);
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
            //info_user.setsPhone(msisdn);
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

    //đời máy
    String _MODEL = android.os.Build.MODEL;
    String _PRODUCT = android.os.Build.PRODUCT;
    //hãng máy
    String BRAND = android.os.Build.BRAND;
    String token;
    String VERSION_OS = android.os.Build.VERSION.RELEASE;
    ;
    String ISMODEL = "2";

    String MODEL = BRAND + " " + _MODEL;
    String user_id;
    String VERSION = BuildConfig.VERSION_NAME;
    boolean is_token;

    public void getUserName() {
        Log.i(TAG, "getUserName: ");
        //boolean is_user_id = fr.getBoolean("is_user_id", false);
        boolean is_user_id = Prefs.with(this).getBoolean("is_user_id", false);
        boolean is_token = Prefs.with(this).getBoolean("token_sucsess", false);
        if (is_user_id) {
            Log.i(TAG, "getUserName: check version");
            boolean is_phien_dn = Prefs.with(this).getBoolean("is_checkver", false);
            if (is_phien_dn) {
                presenterActivity.api_checkver(VERSION, user_id);
                Prefs.with(this).save("is_checkver", false);
            }

        }
        //boolean is_token = fr.getBoolean("token_sucsess", false);
        if (!is_token) {
            FirebaseMessaging.getInstance().subscribeToTopic("testfcm");
            token = FirebaseInstanceId.getInstance().getToken();
            Log.i(TAG, "getUserName: "+token);
            // Log.i(TAG, token);
            if (token != null && token.length() > 0) {
                if (!is_user_id) {
                    Log.i(TAG, "getUserName: tạo mới init");
                    Prefs.with(this).save("token", token);
                    presenterActivity.init_service(token, VERSION, ISMODEL, MODEL, VERSION_OS);
                } else {
                    Log.i(TAG, "getUserName: cap nhat init");
                    // Log.i(TAG, "update token");
                    presenterActivity.update_token(token, user_id);
                }
            } else {

                if (!is_user_id)
                    presenterActivity.init_service("update", VERSION, ISMODEL, MODEL, VERSION_OS);
            }
        }else {
            user_id = Prefs.with(this).getString("user_id", "");
            Log.i(TAG, "getUserName: is token successful"+user_id);

        }
    }

    // lấy về user_id lưu vào share

    /**
     * @param sUserNameId
     */
    public void show_init_service(String sUserNameId) {
        Prefs.with(this).save("user_id", sUserNameId);
        Prefs.with(this).save("token_sucsess", true);
        Prefs.with(this).save("is_user_id", true);
        if (sUserNameId.length() > 0 && sUserNameId != null) {
            Login objLogin = new Login();
            objLogin.setsUserName(sUserNameId);
            Constant.USER_ID = sUserNameId;
            myRealm.beginTransaction();
            myRealm.copyToRealmOrUpdate(objLogin);
            myRealm.commitTransaction();
        }
    }

    public void show_update_token(String sUserNameId) {
        if (sUserNameId.equals("1:thanh cong")) {
            Prefs.with(this).save("token", token);
            Prefs.with(this).save("token_sucsess", true);
        }
    }

    public void show_checkver(List<String> listString) {
        if (listString.get(0).equals("2")) {
            final Dialog dialog_yes = new Dialog(this);
            dialog_yes.setCancelable(false);
            dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_yes.setContentView(R.layout.dialog_yes_no);
            TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.txt_content_dialog);
            TextView txt_title_dialog = dialog_yes.findViewById(R.id.txt_title_dialog);
            Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
            Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
            txt_buysongs.setText("Bạn cần cập nhật phiên bản mới để tiếp tục sử dụng Ringtunes");
            txt_title_dialog.setText("Thông báo");
            // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
            yes.setText("Đồng ý");
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    dialog_yes.dismiss();
                }
            });
            TextView btn_delete_dialog = dialog_yes.findViewById(R.id.btn_delete_dialog);
            btn_delete_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_yes.dismiss();
                }
            });
            no.setText("Không");
            dialog_yes.show();

        } else if (listString.get(0).equals("3")) {
            final Dialog dialog_yes = new Dialog(this);
            //  dialog_yes.setCancelable(false);
            dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_yes.setContentView(R.layout.dialog_yes_no);
            TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.txt_content_dialog);
            TextView txt_title_dialog = dialog_yes.findViewById(R.id.txt_title_dialog);
            Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
            Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
            txt_buysongs.setText("Ringtunes đã có phiên bản mới bạn có muốn cập nhật không ?");
            txt_title_dialog.setText("Thông báo");
            // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
            yes.setText("Đồng ý");
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    dialog_yes.dismiss();
                }
            });
            TextView btn_delete_dialog = dialog_yes.findViewById(R.id.btn_delete_dialog);
            btn_delete_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_yes.dismiss();
                }
            });
            no.setText("Không");
            dialog_yes.show();

        }

    }

    boolean is_save_namegroup = false;

    public void saveNameGroup() {
        is_save_namegroup = Prefs.with(this).getBoolean("is_save_namegroup", false);
        List<String> listName = new ArrayList<>();
        List<GroupName> lisGroupName = new ArrayList<>();
        // public GroupName(String sNameServer, String sNameLocal, String id_group, int background)
        lisGroupName.add(new GroupName("dongnghiep", null, null, 1));
        lisGroupName.add(new GroupName("giadinh", null, null, 2));
        lisGroupName.add(new GroupName("nhom2", null, null, 3));
        lisGroupName.add(new GroupName("banbe", null, null, 4));
        lisGroupName.add(new GroupName("nhom1", null, null, 5));
        lisGroupName.add(new GroupName("nhom3", null, null, 6));
        lisGroupName.add(new GroupName("nhom4", null, null, 1));
        lisGroupName.add(new GroupName("nhom5", null, null, 2));
        lisGroupName.add(new GroupName("nhom8", null, null, 3));
        lisGroupName.add(new GroupName("nhom6", null, null, 4));
        lisGroupName.add(new GroupName("nhom7", null, null, 5));
        lisGroupName.add(new GroupName("nhom9", null, null, 6));
        lisGroupName.add(new GroupName("nhom10", null, null, 1));
        lisGroupName.add(new GroupName("nhom11", null, null, 2));
        lisGroupName.add(new GroupName("nhom12", null, null, 3));
        lisGroupName.add(new GroupName("nhom13", null, null, 4));
        lisGroupName.add(new GroupName("nhom14", null, null, 5));

        if (!is_save_namegroup) {
            for (int i = 0; i < lisGroupName.size(); i++) {
                myRealm.beginTransaction();
                myRealm.copyToRealmOrUpdate(lisGroupName.get(i));
                myRealm.commitTransaction();
            }
            Prefs.with(this).save("is_save_namegroup", true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       //  Prefs.with(this).save("is_Phien_DN", false);
    }
}
