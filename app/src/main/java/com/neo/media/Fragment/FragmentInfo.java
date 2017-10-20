package com.neo.media.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.CRBTModel.Info_User;
import com.neo.media.Fragment.InfoUser.InfoUserInterface;
import com.neo.media.Fragment.InfoUser.PresenterInfoUser;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by QQ on 8/16/2017.
 */

public class FragmentInfo extends BaseFragment implements InfoUserInterface.View {

    public static FragmentInfo fragmentStopPause;

    public static FragmentInfo getInstance() {
        if (fragmentStopPause == null) {
            synchronized (FragmentInfo.class) {
                if (fragmentStopPause == null)
                    fragmentStopPause = new FragmentInfo();
            }
        }
        return fragmentStopPause;
    }

    final String[] spn_sex_setup = {"Nam", "Nữ", "LGBT"};
    @BindView(R.id.btn_update_info)
    Button btn_update_info;
    @BindView(R.id.spinnerSex)
    Spinner spinnerSex;
    @BindView(R.id.ecFullName)
    EditText ecFullName;
    @BindView(R.id.editTextBirthday)
    EditText editTextBirthday;
    @BindView(R.id.txt_phone_info)
    TextView txt_phone_info;
    @BindView(R.id.txt_date_info)
    TextView txt_date_info;
    @BindView(R.id.img_back_info)
    ImageView img_back_info;
    @BindView(R.id.txt_title_info)
    TextView txt_title_info;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    Info_User objInfo;
    Login objLogin;
    public Realm realm;
    public String sesionID;
    public String msisdn;
    public String user_id;
    @BindView(R.id.txt_notification_info)
    TextView txt_notification_info;
    PresenterInfoUser presenterInfoUser;
    SharedPreferences fr;
    String sSex;
    String sDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        user_id = fr.getString("user_id", "");
        realm = RealmController.with(this).getRealm();
        objInfo = realm.where(Info_User.class).findFirst();
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();
        if (objLogin != null) {
            if (objInfo != null) {
                msisdn = objLogin.getMsisdn();
                sesionID = objLogin.getsSessinonID();
            }
        }
        msisdn = objLogin.getMsisdn();
        sesionID = objLogin.getsSessinonID();
        presenterInfoUser = new PresenterInfoUser(this);
        if (msisdn != null && user_id != null)
            presenterInfoUser.getInfoUser(msisdn, user_id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        txt_title_info.setText("THÔNG TIN THUÊ BAO");
        initSpinner();
        initEvents();
        String s = "";
        s = Utilities.encodeUTF(s);

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
        if (objLogin != null) {
            if (objInfo != null) {
                if (objInfo.getStatus().equals("2")) {
                    txt_notification_info.setVisibility(View.GONE);
                    getData(objInfo);
                } else {
                    txt_notification_info.setVisibility(View.VISIBLE);
                }
            }
        }
        MainNavigationActivity.appbar.setVisibility(View.GONE);
    }

    private void initEvents() {
        ecFullName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            if (ecFullName.getText().toString().length() > 0 && editTextBirthday.getText().toString().length() > 0) {
                                presenterInfoUser.update_profile(ecFullName.getText().toString(), msisdn, sDate,
                                        "1", editTextBirthday.getText().toString(), user_id);
                            }

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sSex = "1";
                } else sSex = "2";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        img_back_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });
        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txt_date_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_phone_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (ecFullName.getText().toString().length() > 0 && editTextBirthday.getText().toString().length() > 0) {
                    presenterInfoUser.update_profile(ecFullName.getText().toString(), msisdn, sDate,
                            "1", editTextBirthday.getText().toString(), user_id);
                }
            }
        });
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, spn_sex_setup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapter);

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextBirthday.setText(sdf.format(myCalendar.getTime()));
    }

    public void getData(Info_User objInfo) {
        if (objInfo != null) {
            if (objInfo.getRegister_date() != null && objInfo.getRegister_date().length() > 0) {
                sDate = objInfo.getRegister_date();
                String[] arrdate = sDate.split("-");
                sDate = arrdate[2] + "/" + arrdate[1] + "/" + arrdate[0];
                txt_date_info.setText("Ngày đăng ký: " + sDate);
            }
            if (objInfo.getsPhone() != null && objInfo.getsPhone().length() > 0)
                txt_phone_info.setText("Số ĐT: " + objInfo.getsPhone());
            if (objInfo.getSEX() != null && objInfo.getSEX().length() > 0) {
                if (objInfo.getSEX().equals("1")) {
                    spinnerSex.setSelection(0);
                } else if (objInfo.getSEX().equals("2")) {
                    spinnerSex.setSelection(1);
                }
            }
            if (objInfo.getHOTEN() != null && objInfo.getHOTEN().length() > 0)
                ecFullName.setText(objInfo.getHOTEN());
            if (objInfo.getBIRTHDAY() != null && objInfo.getBIRTHDAY().length() > 0)
                editTextBirthday.setText(objInfo.getBIRTHDAY());
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showGetInfoUser(Info_User obj) {
        Info_User objInfoUser = new Info_User();
        String name = "";
        String brithday = "";
        String sSex = "";
        //    objInfoUser = objInfo;

        if (obj != null) {
            if (obj.getHOTEN() != null && obj.getHOTEN().length() > 0)
                name = obj.getHOTEN();
            if (obj.getBIRTHDAY() != null && obj.getBIRTHDAY().length() > 0)
                brithday = obj.getBIRTHDAY();
            if (obj.getSEX() != null && obj.getSEX().length() > 0)
                sSex = obj.getSEX();
            objInfoUser.setsPhone(objInfo.getsPhone());
            objInfoUser.setService_status(objInfo.getService_status());
            objInfoUser.setStatus(objInfo.getStatus());
            objInfoUser.setRegister_date(objInfo.getRegister_date());
            objInfoUser.setSEX(sSex);
            objInfoUser.setHOTEN(name);
            objInfoUser.setBIRTHDAY(brithday);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(objInfoUser);
            realm.commitTransaction();
            getData(objInfoUser);
        }

    }

    @Override
    public void showUpdateInfo(String listString) {
        if (listString != null && listString.equals("1:thanh cong")) {
            Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            presenterInfoUser.getInfoUser(msisdn, user_id);
            FragmentUtil.popBackStack(getActivity());
        } else
            Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
    }
}
