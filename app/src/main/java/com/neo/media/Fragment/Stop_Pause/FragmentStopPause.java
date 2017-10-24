package com.neo.media.Fragment.Stop_Pause;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.Login;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.DialogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;


/**
 * Created by QQ on 7/27/2017.
 */

public class FragmentStopPause extends BaseActivity implements Stop_Pause_Interface.View {
    public static FragmentStopPause fragmentStopPause;

    public static FragmentStopPause getInstance() {
        if (fragmentStopPause == null) {
            synchronized (FragmentStopPause.class) {
                if (fragmentStopPause == null)
                    fragmentStopPause = new FragmentStopPause();
            }
        }
        return fragmentStopPause;
    }

    Presenter_StopPause presenter_stopPause;
    @BindView(R.id.img_back_dangky)
    ImageView img_back_dangky;
    @BindView(R.id.txt_title_dangky)
    TextView txt_title_dangky;
    @BindView(R.id.btn_next_dangky)
    Button btn_next_dangky;
    @BindView(R.id.txt_notifycation_addsubcriber)
    TextView txt_notifycation_addsubcriber;
    @BindView(R.id.btn_tamdung)
    Button btn_tamdung;
    public SharedPreferences fr;
    public static boolean is_subscriber;
    public static boolean is_SVC_STATUS;
    Info_User objInfo;
    Login objLogin;
    public Realm realm;
    public String sesionID;
    public String msisdn;
    public boolean isActivated;
    boolean is_dk = false;

    @Override
    public int setContentViewId() {
        return R.layout.fragment_subscriber;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        realm = RealmController.with(this).getRealm();
        presenter_stopPause = new Presenter_StopPause(this);
        fr = getSharedPreferences("data", MODE_PRIVATE);
        initEvent();

    }
    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriber, container, false);
        ButterKnife.bind(this, view);
        realm = RealmController.with(this).getRealm();
        presenter_stopPause = new Presenter_StopPause(this);
        fr =getActivity().getSharedPreferences("data", MODE_PRIVATE);
        initEvent();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        update_status();
        //  Boolean isHome = getArguments().getBoolean("isHome", false);
        MainNavigationActivity.appbar.setVisibility(View.GONE);

        if (is_subscriber) {
            if (is_SVC_STATUS) {

            } else {

            }
        } else {


        }
    }

    private void update_status() {
        objInfo = realm.where(Info_User.class).findFirst();
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();
        if (objLogin != null) {
            if (objInfo != null) {
                String status = objInfo.getStatus();
                String status_service = objInfo.getService_status();
                sesionID = objLogin.getsSessinonID();
                msisdn = objLogin.getMsisdn();
                // thuê bao đã active
                if (objInfo.getStatus().equals("2")) {
                    if (objInfo.getService_status().equals("1")) {
                        btn_tamdung.setVisibility(View.VISIBLE);
                        // thuê bao đang sử dụng dịch vu
                        txt_title_dangky.setText("HUỶ DỊCH VỤ");
                        btn_next_dangky.setText("Huỷ dịch vụ");
                        txt_notifycation_addsubcriber.setText(R.string.stop_subcriber);
                    } else {
                        btn_tamdung.setVisibility(View.GONE);
                        // thuê bao đang tạm ngưng dịch vụ
                        txt_title_dangky.setText("KHÍCH HOẠT");
                        btn_next_dangky.setText("Kích hoạt");
                        txt_notifycation_addsubcriber.setText(R.string.resume_subcriber);
                    }

                } else {
                    btn_tamdung.setVisibility(View.GONE);
                    //thuê bao chưa active
                    txt_title_dangky.setText("ĐĂNG KÝ");
                    txt_notifycation_addsubcriber.setText(R.string.add_subcriber);
                    btn_next_dangky.setText("Đăng ký");

                }
            }
        }
    }

    private void initEvent() {
        img_back_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_tamdung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog_subscriber = new Dialog(FragmentStopPause.this);
                dialog_subscriber.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_subscriber.setContentView(R.layout.dialog_yes_no);
                Button huydichvu = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_no);
                Button tamngung = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_yes);
                TextView txt_message = (TextView) dialog_subscriber.findViewById(R.id.dialog_message);
                huydichvu.setText("Không");
                tamngung.setText("Đồng ý");
                txt_message.setText("Bạn có muốn tạm dừng dịch vụ hay không");

                huydichvu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_subscriber.dismiss();
                    }
                });

                tamngung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter_stopPause.pause_resume_subscriber(sesionID, msisdn, "0");
                        dialog_subscriber.dismiss();

                    }
                });
                dialog_subscriber.show();
            }
        });
        btn_next_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objLogin != null) {
                    if (objInfo != null) {
                        // thuê bao đã active
                        if (objInfo.getStatus().equals("2")) {
                            if (objInfo.getService_status().equals("1")) {
                                // thuê bao đang sử dụng dịch vu
                                final Dialog dialog_subscriber = new Dialog(FragmentStopPause.this);
                                dialog_subscriber.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog_subscriber.setContentView(R.layout.dialog_yes_no);
                                Button huydichvu = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_no);
                                Button tamngung = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_yes);
                                TextView txt_message = (TextView) dialog_subscriber.findViewById(R.id.dialog_message);
                                huydichvu.setText("Không");
                                tamngung.setText("Đồng ý");
                                txt_message.setText("Bạn có muốn huỷ dịch vụ hay không");
                                huydichvu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_subscriber.dismiss();
                                    }
                                });

                                tamngung.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        presenter_stopPause.stop_Service(sesionID, msisdn);
                                        dialog_subscriber.dismiss();

                                    }
                                });
                                dialog_subscriber.show();
                            } else {
                                // thuê bao đang tạm ngưng dịch vụ
                                presenter_stopPause.pause_resume_subscriber(sesionID, msisdn, "1");
                            }

                        } else {
                            //thuê bao chưa active
                            txt_title_dangky.setText("Đăng Ký");
                            txt_notifycation_addsubcriber.setText(R.string.add_subcriber);
                            btn_next_dangky.setText("Đăng ký");
                            presenter_stopPause.add_subcriber(sesionID, msisdn, "7");
                        }
                    }
                }

            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }


    /*@Override
    public void show_stop_service(String Message) {
        if (Message.equals("")) {
            presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
        } else Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show_resue_service(String Message) {
        if (Message.equals("")) {
            presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
        }
        Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show_add_service(String Message) {
        if (Message.equals("0")) {
            presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
        }
        Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void show_stop_service(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
            } else Toast.makeText(this, list.get(1), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void show_resue_service(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
            } else Toast.makeText(this, list.get(1), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void show_add_service(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Thành công");
                alertDialogBuilder.setMessage(getResources().getString(R.string.add_subcriber_success));

                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
                                dialog.dismiss();
                            }
                        });

                alertDialogBuilder.setCancelable(false);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                DialogUtil.showDialog(FragmentStopPause.this, "Lỗi", list.get(1));
            }
        }
    }

    @Override
    public void showInfo_User(subscriber subscriber) {
        hideDialogLoading();
        Info_User info_user = new Info_User();
        if (subscriber != null) {
            if (subscriber.getERROR() != null && subscriber.getERROR().equals("0")) {
                info_user.setsPhone(msisdn);
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
                SharedPreferences.Editor editor = fr.edit();
                if (info_user.getStatus().equals("2")) {
                    editor.putBoolean("isStatus", true);
                } else if (info_user.getStatus().equals("4"))
                    editor.putBoolean("isStatus", false);
                if (info_user.getService_status().equals("1")) {
                    editor.putBoolean("isService_status", true);
                } else if (info_user.getService_status().equals("0"))
                    editor.putBoolean("isService_status", false);
                editor.commit();
            } else if (subscriber.getERROR().equals("102")) {
                info_user.setsPhone(msisdn);
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
            }
        }
        finish();
    }


}
