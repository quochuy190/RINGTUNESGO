package com.neo.media.View.Login;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.DialogUtil;
import com.neo.media.untils.MD5;
import com.neo.media.untils.PhoneNumber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

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
    boolean isCheckedRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        realm = RealmController.with(this).getRealm();
        presenterLogin = new PresenterLogin(this);
        initNetwork();

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
        pre = getSharedPreferences("data", MODE_PRIVATE);
        userID = pre.getString("user_id", "");
        userPortal = pre.getString("user_portal", "");
        passPortal = pre.getString("pass_portal", "");
        isCheckedRemember = pre.getBoolean("isCheckedRemember", false);
        objLogin = new Login();
    }

    private void initEvent() {
        btn_dangnhap_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNetwork();
                if (!is3g && !isWifi) {
                    Toast.makeText(getApplicationContext(), "Bạn hãy kết nỗi mạng để tiếp tục sử dụng dịch vụ", Toast.LENGTH_LONG).show();
                } else {
                    if (edt_taikhoan_Login.getText().length() > 0 && edt_matkhau_Login.getText().length() > 0) {

                        if (PhoneNumber.StandartTelco(edt_taikhoan_Login.getText().toString()).equals("VINA")) {
                            String s = edt_matkhau_Login.getText().toString();
                            String pass = md.getMD5(edt_matkhau_Login.getText().toString()).toUpperCase();
                            username = PhoneNumber.convertTo84PhoneNunber(edt_taikhoan_Login.getText().toString());
                            presenterLogin.LoginVinaphonePortal(username, pass, userID);
                        } else
                            DialogUtil.showDialog(ActivityLogin.this, "Sai tài khoản",
                                    "Bạn phải nhập vào số điện thoại của Vinaphone");

                    } else Toast.makeText(ActivityLogin.this,
                            "Mời bạn nhập vào Tài khoản và mật khẩu để đăng nhập", Toast.LENGTH_SHORT).show();
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
        } else finish();
    }

    @Override
    public void showDataLogin(List<String> list) {
        if (list.size() > 0) {
            objLogin.setsUserName(userID);
            objLogin.setsSessinonID(list.get(2));
            objLogin.setMsisdn(username);
            objLogin.setLogin(true);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(objLogin);
            realm.commitTransaction();
            if (remember_pass.isChecked()) {
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("msisdn", username);
                editor.putString("sessionID", list.get(2));
                editor.commit();
            } else {
                edt_matkhau_Login.setText("");
            }
            presenterLogin.get_detail_subsriber(list.get(2), username);
        } else {
            Toast.makeText(this, "" + list.get(1), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void showDataLoginVinaphonePortal(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0") && list.get(1).equals("SUCCESS")) {
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("user_portal", edt_taikhoan_Login.getText().toString());
                editor.putString("pass_portal", edt_matkhau_Login.getText().toString());
                if (remember_pass.isChecked()) {
                    editor.putBoolean("isCheckedRemember", true);
                } else
                    editor.putBoolean("isCheckedRemember", false);
                editor.commit();
                objLogin.setsUserName(userID);
                objLogin.setsPassWord(list.get(2));
                objLogin.setMsisdn(username);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(objLogin);
                realm.commitTransaction();
                presenterLogin.Login(userID, list.get(2));
            } else {
                Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*public void hideDialogLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected ProgressDialog dialog;
    private Handler StopDialogLoadingHandler = new Handler();

    public void showDialogLoading() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 10000);
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getString(R.string.alert_message_loading));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialogLoading1s() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 1000);
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getString(R.string.alert_message_loading));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }*/
}
