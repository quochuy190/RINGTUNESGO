package com.neo.ringtunesgo.View.Xacthuc_thuebao;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.ringtunesgo.CRBTModel.Info_User;
import com.neo.ringtunesgo.CRBTModel.subscriber;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.Login;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.View.Login.ActivityLogin;
import com.neo.ringtunesgo.View.Register.ActivityRegisterOTP;
import com.neo.ringtunesgo.untils.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.neo.ringtunesgo.R.id.btn_next_xacthuc;

/**
 * Created by QQ on 8/31/2017.
 */

public class ActivityXacthuc extends BaseActivity implements Xacthuc_Impl.View {


    @BindView(R.id.img_back_xacthuc)
    ImageView img_back_xacthuc;
    @BindView(R.id.txt_title_xacthuc)
    TextView txt_title_xacthuc;
    @BindView(btn_next_xacthuc)
    Button btn_next;
    @BindView(R.id.rbtn_vinaphone_portal)
    RadioButton rbtn_vinaphone_portal;
    @BindView(R.id.rbtn_sms_OTP)
    RadioButton rbtn_sms_OTP;
    @BindView(R.id.rbtn_3g_vinaphone)
    RadioButton rbtn_3g_vinaphone;
    String user_id;
    Presenter presenterXacthuc;
    SharedPreferences pre;
    String username = "";
    Login objLogin;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.layout_xacthuc_thuebao);
        ButterKnife.bind(this);
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
        user_id = pre.getString("user_id", "");
        objLogin = new Login();
    }

    @Nullable


    private void initEvent() {
        img_back_xacthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbtn_vinaphone_portal.isChecked()) {
                    startActivity(new Intent(ActivityXacthuc.this, ActivityLogin.class));
                    finish();
                } else if (rbtn_sms_OTP.isChecked()) {
                    startActivity(new Intent(ActivityXacthuc.this, ActivityRegisterOTP.class));
                    finish();
                } else if (rbtn_3g_vinaphone.isChecked()) {
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
                        presenterXacthuc.login_3g(user_id);
                    }
                } else {
                    Toast.makeText(ActivityXacthuc.this, "Mời bạn chọn một phương thức xác thực",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        txt_title_xacthuc.setText("Xác thực thuê bao");
        MainNavigationActivity.appbar.setVisibility(View.GONE);

    }

    @Override
    public void onPause() {
        super.onPause();
        MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void show_login_3g(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0") && list.get(3).equals("SUCCESS")) {
                username = list.get(2);
                objLogin.setsUserName(user_id);
                objLogin.setsPassWord(list.get(1));
                objLogin.setMsisdn(username);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(objLogin);
                realm.commitTransaction();
                presenterXacthuc.Login(user_id, list.get(1));
            } else {
                Toast.makeText(this, "Lỗi, thuê bao của quý khách không hợp lệ", Toast.LENGTH_SHORT).show();
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
                final Dialog dialog_yes = new Dialog(this);
                dialog_yes.setContentView(R.layout.dialog_yes_no);
                TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
                Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);

                txt_buysongs.setText("Bạn chưa đăng ký dịch vụ Ringtunes, Bạn có muốn đăng ký sử dụng dịch vụ");
                yes.setText("Có");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  startActivity(new Intent(getActivity(), ActivityXacthuc.class));
                        dialog_yes.dismiss();
                        finish();
                    }
                });
                no.setText("Không");
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes.dismiss();
                        finish();
                    }
                });
                dialog_yes.show();
            }
        }
    }

    @Override
    public void showDataLogin(List<String> list) {
        if (list.size() > 0) {
            objLogin.setsUserName(user_id);
            objLogin.setsSessinonID(list.get(2));
            objLogin.setMsisdn(username);
            objLogin.setLogin(true);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(objLogin);
            realm.commitTransaction();
            SharedPreferences.Editor editor = pre.edit();
            editor.putString("msisdn", username);
            editor.putString("sessionID", list.get(2));
            editor.commit();
            presenterXacthuc.get_detail_subsriber(list.get(2), username);
        } else {
            Toast.makeText(this, "" + list.get(1), Toast.LENGTH_SHORT).show();
        }
    }
}
