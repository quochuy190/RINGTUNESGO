package com.neo.ringtunesgo.Fragment.Stop_Pause;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.ringtunesgo.CRBTModel.Info_User;
import com.neo.ringtunesgo.CRBTModel.subscriber;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.Login;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.untils.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;


/**
 * Created by QQ on 7/27/2017.
 */

public class FragmentStopPause extends BaseFragment implements Stop_Pause_Interface.View {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriber, container, false);
        ButterKnife.bind(this, view);
        realm = RealmController.with(this).getRealm();
        presenter_stopPause = new Presenter_StopPause(this);

        initEvent();

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
        update_status();


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
                        txt_title_dangky.setText("Huỷ dịch vụ");
                        btn_next_dangky.setText("Huỷ dịch vụ");
                        txt_notifycation_addsubcriber.setText(R.string.stop_subcriber);
                    } else {
                        btn_tamdung.setVisibility(View.GONE);
                        // thuê bao đang tạm ngưng dịch vụ
                        txt_title_dangky.setText("Kích hoạt");
                        btn_next_dangky.setText("Kích hoạt");
                        txt_notifycation_addsubcriber.setText(R.string.resume_subcriber);
                    }

                } else {
                    btn_tamdung.setVisibility(View.GONE);
                    //thuê bao chưa active
                    txt_title_dangky.setText("Đăng Ký");
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
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });
        btn_tamdung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog_subscriber = new Dialog(getContext());
                dialog_subscriber.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_subscriber.setContentView(R.layout.dialog_yes_no);
                Button huydichvu = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_yes);
                Button tamngung = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_no);
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
                                final Dialog dialog_subscriber = new Dialog(getContext());
                                dialog_subscriber.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog_subscriber.setContentView(R.layout.dialog_yes_no);
                                Button huydichvu = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_yes);
                                Button tamngung = (Button) dialog_subscriber.findViewById(R.id.btn_dialog_no);
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
                            presenter_stopPause.add_subcriber(sesionID, msisdn, "30");
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
            } else Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void show_resue_service(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
            } else Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void show_add_service(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenter_stopPause.get_detail_subsriber(sesionID, msisdn);
            } else Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showInfo_User(subscriber subscriber) {
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
        update_status();
    }
}
