package com.neo.media.View.Login;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.MD5;
import com.neo.media.untils.PhoneNumber;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

import static com.neo.media.R.layout.login;

/**
 * Created by QQ on 8/16/2017.
 */

public class ActivityLogin extends BaseActivity implements InterfaceLogin.View {
    Realm realm;
    @BindView(R.id.btn_dangnhap_Login)
    Button btn_dangnhap_Login;
    @BindView(R.id.edt_taikhoan_Login)
    EditText edt_taikhoan_Login;
    @BindView(R.id.edt_matkhau_Login)
    EditText edt_matkhau_Login;
    String sUserNameID;
    String sPassWord;
    String sSessionID;
    String userID;
    PresenterLogin presenterLogin;
    Login objLogin;
    MD5 md = new MD5();
    SharedPreferences pre;
    String username;
    @BindView(R.id.remember_password)
    CheckBox remember_pass;
    @BindView(R.id.txt_get_password)
    TextView txt_get_password;
    String userPortal;
    String passPortal;
    boolean is3g;
    boolean isWifi;
    boolean isShowpass = false;
    @BindView(R.id.img_showpass)
    ImageView img_showpass;
    boolean isCheckedRemember = true;
    private int is_count_api = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        is_count_api = 0;
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView txt_appbar = (TextView) findViewById(R.id.txt_appbar);
        back.setVisibility(View.VISIBLE);
        txt_appbar.setVisibility(View.VISIBLE);
        txt_appbar.setText("Đăng nhập");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        realm = RealmController.with(this).getRealm();
        presenterLogin = new PresenterLogin(this);
        //initNetwork();
        remember_pass.setChecked(true);
        initEvent();
    }

    public void initNetwork() {
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
        txt_get_password.setText(Html.fromHtml("Trong trường hợp quên mật khẩu. Quý khách vui lòng " +
                "gửi tin nhắn theo cú pháp: <font color='#6f2b8e'>MK</font> gửi đến <font color='#6f2b8e'>333</font> (Miễn phí) "));
        remember_pass.setChecked(isCheckedRemember);
        if (remember_pass.isChecked()) {
            edt_matkhau_Login.setText(passPortal);
            edt_taikhoan_Login.setText(userPortal);
        }

    }

    @Override
    public int setContentViewId() {
        return login;
    }

    @Override
    public void initData() {
        userID = Prefs.with(ActivityLogin.this).getString("user_id", "");
        userPortal = Prefs.with(ActivityLogin.this).getString("user_portal", "");
        passPortal = Prefs.with(ActivityLogin.this).getString("pass_portal", "");
        isCheckedRemember = Prefs.with(ActivityLogin.this).getBoolean("isCheckedRemember", false);
        objLogin = new Login();
    }

    private void initEvent() {
        btn_dangnhap_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNetwork();
                if (!is3g && !isWifi) {
                    show_notification("Thông báo", "Bạn hãy kết nỗi mạng để tiếp tục sử dụng dịch vụ");
                    //  Toast.makeText(getApplicationContext(), "Bạn hãy kết nỗi mạng để tiếp tục sử dụng dịch vụ", Toast.LENGTH_LONG).show();
                } else {
                    if (edt_taikhoan_Login.getText().length() > 0 && edt_matkhau_Login.getText().length() > 0) {
                        if (PhoneNumber.StandartTelco(edt_taikhoan_Login.getText().toString()).equals("VINA")) {
                            //String s = edt_matkhau_Login.getText().toString();
                            String pass = md.getMD5(edt_matkhau_Login.getText().toString()).toUpperCase();
                            username = PhoneNumber.convertTo84PhoneNunber(edt_taikhoan_Login.getText().toString());
                            showDialogLoading();
                            is_count_api = 0;
                            presenterLogin.LoginVinaphonePortal(username, pass, userID);
                        } else
                            show_notification("Thông báo", "Bạn phải nhập vào số điện thoại của mạng Vinaphone");
                           /* DialogUtil.showDialog(ActivityLogin.this, "Sai tài khoản",
                                    "Bạn phải nhập vào số điện thoại của Vinaphone");*/

                    } else
                        show_notification("Thông báo", "Mời bạn nhập vào tài khoản và mật khẩu để đăng nhập");

                }
            }
        });

        img_showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowpass) {
                    edt_matkhau_Login.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowpass = !isShowpass;
                } else {
                    edt_matkhau_Login.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowpass = !isShowpass;
                }
            }
        });

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
                hideDialogLoading();
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
                hideDialogLoading();
                finish();
                /*final Dialog dialog_yes = new Dialog(this);
                dialog_yes.setContentView(R.layout.dialog_yes_no);
                TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
                Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);

                txt_buysongs.setText("Bạn chưa đăng ký dịch vụ Ringtunes, Bạn có muốn đăng ký sử dụng dịch vụ");
                yes.setText("Có");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = pre.edit();
                        editor.putBoolean("is_Dangky", true);

                        editor.commit();
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
                dialog_yes.show();*/

            }
        } else finish();
    }

    @Override
    public void showDataLogin(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            Prefs.with(this).save("msisdn", username);
            Prefs.with(this).save("sessionID", list.get(2));
            Prefs.with(this).save("isLogin", true);
            Prefs.with(this).save("is_Show_Subscriber", true);
            Prefs.with(this).save("is_Phien_DN", true);
            if (remember_pass.isChecked()) {
            } else {
                edt_matkhau_Login.setText("");
            }
            finish();
            //presenterLogin.get_detail_subsriber(list.get(2), username);
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Thông báo")
                    .setContentText("Hệ thống đang bận mời thử lại sau")
                    .setConfirmText("Đóng")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void showDataLoginVinaphonePortal(List<String> list) {
        if (list.size() > 0) {
            Log.i("login", list.get(1));
            if (list.get(0).equals("0") && list.get(1).equals("SUCCESS")) {
                Prefs.with(this).save("user_portal", edt_taikhoan_Login.getText().toString());
                Prefs.with(this).save("pass_portal", edt_matkhau_Login.getText().toString());
                Prefs.with(this).save("pass_sql_server", list.get(2));
                if (remember_pass.isChecked()) {
                    Prefs.with(this).save("isCheckedRemember", true);
                    //editor.putBoolean("isCheckedRemember", true);
                } else
                    Prefs.with(this).save("isCheckedRemember", true);
                presenterLogin.Login(userID, list.get(2));
            } else {
                hideDialogLoading();
                if (list.get(0).equals("1"))
                show_notification("Lỗi", "Tài khoản hoặc password không hợp lệ");
            }
        } else {
            show_notification("Lỗi kết nối", "Kiểm tra lại kết nối mạng, hoặc mời bạn thử phương thức đăng nhập khác." +
                    " Xin cảm ơn");
        }
    }

    @Override
    public void show_api_error() {
        show_notification("Lỗi kết nối", "Kiểm tra lại kết nối mạng, hoặc mời bạn thử phương thức đăng nhập khác." +
                " Xin cảm ơn");
      /*  if (is_count_api < 2) {
            is_count_api++;
            String pass = md.getMD5(edt_matkhau_Login.getText().toString()).toUpperCase();
            username = PhoneNumber.convertTo84PhoneNunber(edt_taikhoan_Login.getText().toString());
            //showDialogLoading();
            presenterLogin.LoginVinaphonePortal(username, pass, userID);
        } else {
            is_count_api = 0;
            hideDialogLoading();

        }*/
    }
}
