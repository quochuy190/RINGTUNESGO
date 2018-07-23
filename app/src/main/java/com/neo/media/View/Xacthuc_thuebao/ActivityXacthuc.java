package com.neo.media.View.Xacthuc_thuebao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.Login.ActivityLogin;
import com.neo.media.View.Register.ActivityRegisterOTP;
import com.neo.media.untils.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

/**
 * Created by QQ on 8/31/2017.
 */

public class ActivityXacthuc extends BaseActivity implements Xacthuc_Impl.View {

    @BindView(R.id.txt_vina_portal)
    TextView txt_vina_portal;
    @BindView(R.id.txt_otp)
    TextView txt_otp;
    @BindView(R.id.txt_vina_3g)
    TextView txt_vina_3g;
    String user_id;
    Presenter presenterXacthuc;
    SharedPreferences pre;
    String username = "";
    Login objLogin;
    Realm realm;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.layout_xacthuc_thuebao);
        ButterKnife.bind(this);
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView txt_appbar = (TextView) findViewById(R.id.txt_appbar);
        back.setVisibility(View.VISIBLE);
        txt_appbar.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenterXacthuc = new Presenter(this);
        realm = RealmController.with(this).getRealm();
        initEvent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.layout_xacthuc_thuebao;
    }

    @Override
    public void initData() {
        pre = getSharedPreferences("data", MODE_PRIVATE);
        user_id = Prefs.with(ActivityXacthuc.this).getString("user_id", "");
        objLogin = new Login();
    }

    @Nullable


    private void initEvent() {
        txt_vina_portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityXacthuc.this, ActivityLogin.class));
                finish();
            }
        });
        txt_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityXacthuc.this, ActivityRegisterOTP.class));
                finish();
            }
        });
        txt_vina_3g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                //For 3G check
                boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                        .isConnectedOrConnecting();
                //For WiFi Check
                boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .isConnectedOrConnecting();
                if (!is3g && !isWifi) {
                    Toast.makeText(getApplicationContext(), "Bạn hãy kết nỗi mạng để tiếp tục sử dụng dịch vụ", Toast.LENGTH_LONG).show();
                } else if (isWifi) {
                    Toast.makeText(ActivityXacthuc.this, "Bạn đang dùng mạng wifi. Hãy bật 3G Vinaphone để xác thực tài khoản", Toast.LENGTH_SHORT).show();
                } else if (is3g) {
                    showDialogLoading();
                    presenterXacthuc.login_3g(user_id);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //txt_title_xacthuc.setText("Xác thực thuê bao");
        //  MainNavigationActivity.appbar.setVisibility(View.GONE);

    }

    @Override
    public void onPause() {
        super.onPause();
        // MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void show_login_3g(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0") && list.get(3).equals("SUCCESS")) {
                Prefs.with(this).save("pass_sql_server", list.get(1));
                username = list.get(2);
                Prefs.with(this).save("msisdn", username);
                presenterXacthuc.Login(user_id, list.get(1));
            } else {
                show_notification("Lỗi", "Hệ thống đang bận mời thử lại sau");
               // Toast.makeText(this, "Lỗi, thuê bao của quý khách không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showInfo_User(subscriber subscriber) {
        Info_User info_user = new Info_User();
        if (subscriber != null) {
            if (subscriber.getERROR() != null && subscriber.getERROR().equals("0")) {
                info_user.setsPhone(username);
                info_user.setError_desc(subscriber.getERROR_DESC());
                info_user.setReq_id(subscriber.getPREPAID());
                info_user.setStatus(subscriber.getSTATUS());
                info_user.setRegister_date(subscriber.getREG_DATE());
                info_user.setService_status(subscriber.getSVC_STATUS());
                info_user.setError_code(subscriber.getERROR());
                info_user.setIs_corporate(subscriber.getCORPORATE());
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(info_user);
                realm.commitTransaction();
                finish();
            } else if (subscriber.getERROR().equals("102")) {
                info_user.setsPhone(username);
                info_user.setError_desc(subscriber.getERROR_DESC());
                info_user.setReq_id("");
                info_user.setStatus("");
                info_user.setRegister_date("");
                info_user.setService_status("");
                info_user.setError_code(subscriber.getERROR());
                info_user.setIs_corporate("");
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(info_user);
                realm.commitTransaction();
                finish();
            }
        }
    }

    @Override
    public void showDataLogin(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            Prefs.with(this).save("sessionID", list.get(2));
            Prefs.with(this).save("isLogin", true);
            Prefs.with(this).save("is_Show_Subscriber", true);
            Prefs.with(this).save("is_Phien_DN", true);
            finish();
        } else {
           show_notification("Lỗi", "Hệ thống đang bận mời bạn thử lại");
        }
    }
}
