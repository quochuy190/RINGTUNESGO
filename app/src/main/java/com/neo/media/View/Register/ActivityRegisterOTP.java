package com.neo.media.View.Register;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.Login.InterfaceLogin;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.DialogUtil;
import com.neo.media.untils.PhoneNumber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

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
        sUserName = fr.getString("user_id", "");
        presenterRegisterOTP = new PresenterRegisterOTP(this);
        realm = RealmController.with(this).getRealm();
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
                        boolean is_vinaphone = CustomUtils.checkPhoneVina(phone09);
                        if (is_vinaphone) {
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
                    presenterRegisterOTP.xacthucOTP(pnone84, sUserName,
                            editTextNumber_otp.getText().toString());
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });
    }

    @Override
    public void showGetOTP(List<String> listOTP) {
        if (listOTP.get(0).equals("0")) {
            btn_next_register.setVisibility(View.VISIBLE);
            btn_next_register.setText("Đăng nhập");
            txt_nhapOTp.setText(Html.fromHtml("Bạn đang đăng nhập với số điện thoại <font color='#6f2b8e'>"
                    + editTextNumber_otp.getText().toString()+"</font>" ));
            editTextNumber_otp.setText("");
            editTextNumber_otp.setHint("- Nhập vào mã OTP");
            is_getOTP = true;
        } else {
            DialogUtil.showDialog(ActivityRegisterOTP.this, "Lỗi",listOTP.get(2));
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
                realm.beginTransaction();
                objLogin.setsUserName(sUserName);
                objLogin.setsPassWord(listConfirm.get(2));
                objLogin.setMsisdn(pnone84);
                realm.copyToRealmOrUpdate(objLogin);
                realm.commitTransaction();
                presenterRegisterOTP.Login(objLogin.getsUserName(), listConfirm.get(2));
            } else {
                btn_next_register.setVisibility(View.VISIBLE);
                DialogUtil.showDialog(ActivityRegisterOTP.this, "Lỗi", listConfirm.get(2));
              //  Toast.makeText(this, "" + listConfirm.get(2), Toast.LENGTH_SHORT).show();
            }
        } else btn_next_register.setVisibility(View.VISIBLE);

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
                final Dialog dialog_yes = new Dialog(this);
                dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_yes.setContentView(R.layout.dialog_yes_no);
                TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
                Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);

                txt_buysongs.setText("Bạn chưa đăng ký dịch vụ Ringtunes, Bạn hãy đăng ký để trải nghiệm dịch vụ");
                yes.setText("Có");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            realm.beginTransaction();
            objLogin.setsSessinonID(list.get(2));
            objLogin.setLogin(true);
            realm.copyToRealmOrUpdate(objLogin);
            realm.commitTransaction();
            SharedPreferences.Editor editor = fr.edit();
            editor.putString("msisdn", pnone84);
            editor.putString("sessionID", list.get(2));
            editor.commit();
            presenterRegisterOTP.get_detail_subsriber(list.get(2), objLogin.getMsisdn());
        }else DialogUtil.showDialog(ActivityRegisterOTP.this, "Lỗi", "Lỗi server mời thử lại");
    }

    @Override
    public void showDataLoginVinaphonePortal(List<String> list) {

    }

}
