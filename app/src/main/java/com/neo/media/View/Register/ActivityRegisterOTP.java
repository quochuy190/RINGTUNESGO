package com.neo.media.View.Register;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Config.Constant;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.Login.InterfaceLogin;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.PhoneNumber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

/**
 * Created by QQ on 8/16/2017.
 */

public class ActivityRegisterOTP extends BaseActivity implements InterfaceRegisterOTP.View, InterfaceLogin.View {
    public static final String TAG = ActivityRegisterOTP.class.getSimpleName();
    @BindView(R.id.btn_next_register)
    Button btn_next_register;
    @BindView(R.id.editTextNumber_otp)
    EditText editTextNumber_otp;
    @BindView(R.id.txt_nhapOTp)
    TextView txt_nhapOTp;
    String pnone84;
    public PresenterRegisterOTP presenterRegisterOTP;
    Realm realm;
    boolean is_getOTP = false;
    String sUserName;
    Login objLogin;
    public SharedPreferences fr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_register);
        fr = getSharedPreferences("data", MODE_PRIVATE);
        // sUserName = fr.getString("user_id", "");
        sUserName = Prefs.with(ActivityRegisterOTP.this).getString("user_id", "");
        presenterRegisterOTP = new PresenterRegisterOTP(this);
        realm = RealmController.with(this).getRealm();
     //   ImageView back = (ImageView) findViewById(R.id.back);
       // TextView txt_appbar = (TextView) findViewById(R.id.txt_appbar);
        init_Appbar();
        /*txt_appbar.setText("Xác thực qua OTP");
        back.setVisibility(View.VISIBLE);
        txt_appbar.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        // objLogin = realm.where(Login.class).findFirst();
        objLogin = new Login();
        ButterKnife.bind(this);
        initEvent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {

    }

    private void initEvent() {
        btn_next_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_getOTP) {
                    if (editTextNumber_otp.getText().toString().length() > 0) {
                        String phone09 = PhoneNumber.convertToVnPhoneNumber(editTextNumber_otp.getText().toString());
                      //  boolean is_vinaphone = PhoneNumber.(phone09);

                        if (PhoneNumber.isPhoneNumber(editTextNumber_otp.getText().toString())&&
                                PhoneNumber.StandartTelco(editTextNumber_otp.getText().toString()).equals("VINA")) {
                            showDialogLoading();
                            pnone84 = PhoneNumber.convertTo84PhoneNunber(phone09);
                            presenterRegisterOTP.getOTPcode(pnone84, sUserName);
                            btn_next_register.setVisibility(View.INVISIBLE);
                        } else
                            Toast.makeText(ActivityRegisterOTP.this, "Mời nhập vào thuê bao Vinaphone",
                                    Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ActivityRegisterOTP.this, "Bạn phải nhập vào số điện thoại Vinaphone",
                                Toast.LENGTH_SHORT).show();
                } else {
                    btn_next_register.setVisibility(View.INVISIBLE);
                    showDialogLoading();
                    presenterRegisterOTP.xacthucOTP(pnone84, sUserName, editTextNumber_otp.getText().toString());
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });
    }

    @Override
    public void showGetOTP(List<String> listOTP) {
        hideDialogLoading();
        if (listOTP.size() > 0) {
            if (listOTP.get(0).equals("0")) {
                btn_next_register.setVisibility(View.VISIBLE);
                btn_next_register.setText("Đăng nhập");
                txt_nhapOTp.setText(Html.fromHtml("Bạn đang đăng nhập với số điện thoại <font color='#6f2b8e'>"
                        + editTextNumber_otp.getText().toString() + "</font>"));
                editTextNumber_otp.setText("");
                editTextNumber_otp.setHint("- Nhập vào mã OTP");
                is_getOTP = true;
            } else {
                //DialogUtil.showDialog(ActivityRegisterOTP.this, "Lỗi",listOTP.get(2));
                show_notification("Lỗi", listOTP.get(2));
                //Toast.makeText(this, listOTP.get(2), Toast.LENGTH_SHORT).show();
                editTextNumber_otp.setText("");
                btn_next_register.setVisibility(View.VISIBLE);
            }
        } else {
            show_notification("Lỗi", "Rất tiếc hệ thông đang bận");
            //  DialogUtil.showDialog(ActivityRegisterOTP.this, "Lỗi","Rất tiếc hệ thông đang bận");
            //Toast.makeText(this, listOTP.get(2), Toast.LENGTH_SHORT).show();
            editTextNumber_otp.setText("");
            btn_next_register.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showConfirmOTP(List<String> listConfirm) {
        if (listConfirm.size() > 0) {
            if (listConfirm.get(0).equals("0")) {
                btn_next_register.setVisibility(View.VISIBLE);
                Prefs.with(this).save("pass_sql_server", listConfirm.get(2));
              /*  realm.beginTransaction();
                objLogin.setsUserName(sUserName);
                objLogin.setsPassWord(listConfirm.get(2));
                objLogin.setMsisdn(pnone84);
                realm.copyToRealmOrUpdate(objLogin);
                realm.commitTransaction();*/
                presenterRegisterOTP.Login(sUserName, listConfirm.get(2));
            } else {
                hideDialogLoading();
                btn_next_register.setVisibility(View.VISIBLE);
                show_notification("Lỗi", listConfirm.get(2));
                //  DialogUtil.showDialog(ActivityRegisterOTP.this, "Lỗi", listConfirm.get(2));
                //  Toast.makeText(this, "" + listConfirm.get(2), Toast.LENGTH_SHORT).show();
            }
        } else{
            hideDialogLoading();
            btn_next_register.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void showInfo_User(subscriber subscriber) {
        Info_User info_user = new Info_User();
        if (subscriber != null) {
            if (subscriber.getERROR() != null && subscriber.getERROR().equals("0")) {
                info_user.setsPhone(pnone84);
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
                info_user.setsPhone(pnone84);
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

        } else finish();

    }

    @Override
    public void showDataLogin(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            Prefs.with(this).save("msisdn", pnone84);
            Prefs.with(this).save("sessionID", list.get(2));
            Constant.sMSISDN= list.get(2);
            Constant.sMSISDN= pnone84;
            Prefs.with(this).save("isLogin", true);
            Prefs.with(this).save("is_Show_Subscriber", true);
            Prefs.with(this).save("is_Phien_DN", true);
            finish();


        } else
            show_notification("Thông báo", "Hệ thống đang bận, mời thử lại");
        //DialogUtil.showDialog(ActivityRegisterOTP.this, "Lỗi", "Lỗi server mời thử lại");
    }

    @Override
    public void showDataLoginVinaphonePortal(List<String> list) {

    }

    @Override
    public void show_api_error() {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    ActionBar ab;
    public void init_Appbar(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle("Xác thực OTP");
    }
}
