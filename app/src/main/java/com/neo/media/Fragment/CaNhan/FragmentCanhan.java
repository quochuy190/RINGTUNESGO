package com.neo.media.Fragment.CaNhan;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.Adapter.AdapterCanhan;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.CaNhan.Collection.FragmentConllection;
import com.neo.media.Fragment.CaNhan.Groups.FragmentGroups;
import com.neo.media.Fragment.Profiles.FragmentProfiles;
import com.neo.media.GetUserInfo.IpmGetUserInfo;
import com.neo.media.GetUserInfo.Presenter_GetUser_Info;
import com.neo.media.Model.Canhan;
import com.neo.media.Model.Login;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.Register.ActivityRegisterOTP;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.DialogUtil;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by QQ on 11/27/2017.
 */

public class FragmentCanhan extends BaseFragment implements IpmGetUserInfo.View {
    private static final String TAG = "FragmentCanhan";
    public static FragmentCanhan fragment;

    public static FragmentCanhan getInstance() {
        if (fragment == null) {
            synchronized (FragmentCanhan.class) {
                if (fragment == null)
                    fragment = new FragmentCanhan();
            }
        }
        return fragment;
    }

    @BindView(R.id.list_canhan)
    RecyclerView recyclerCanhan;
    @BindView(R.id.txt__title_canhan)
    TextView txt_title_canhan;
    @BindView(R.id.btn_dangnhap)
    TextView btn_dangnhap;
    AdapterCanhan adapterCanhan;
    List<Canhan> listCanhan;
    RecyclerView.LayoutManager mLayoutManager;
    public Realm realm;
    public String sesionID;
    public String msisdn;
    Info_User objInfo;
    Login objLogin;
    @BindView(R.id.relative_canhan)
    RelativeLayout relative_canhan;
    @BindView(R.id.img_background_canhan)
    ImageView img_background_canhan;
    public SharedPreferences fr;
    PresenterCanhan presenterCanhan;
    Presenter_GetUser_Info presenter_getUser_info;
    private String sKey_Switch = "reload";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        is_subscriber = false;
        is_SVC_STATUS = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canhan, container, false);
        ButterKnife.bind(this, view);
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        // relative_canhan.setBackground(getResources().getDrawable(R.drawable.backgrond_canhan));
        Glide.with(this).load(R.drawable.backgrond_canhan).into(img_background_canhan);
        realm = RealmController.with(this).getRealm();
        objInfo = realm.where(Info_User.class).findFirst();
        objLogin = realm.where(Login.class).findFirst();
        presenterCanhan = new PresenterCanhan(this);
        presenter_getUser_info = new Presenter_GetUser_Info(this);
        init();
        MyApplication.is_get_sub_detail = false;
        // initData();
        Log.i(TAG, "onCreateView");
        initEvent();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }

    private void initEvent() {
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = Prefs.with(getActivity()).getBoolean("isLogin", false);
                if (!isLogin) {
                    sKey_Switch = "reload";
                    check_login_3g();
                    //  startActivity(new Intent(getContext(), ActivityXacthuc.class));
                } else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Thông báo")
                            .setContentText("Bạn có muốn đăng xuất khỏi Ringtunes")
                            .setConfirmText("Đồng ý")
                            .setCancelText("Trở lại")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    MyApplication.objGroup = new GROUP();
                                    MyApplication.profile_bundle = new PROFILE();
                                    MyApplication.listConllection = new ArrayList<>();
                                    MyApplication.player_ring = new Ringtunes();
                                    isLogin = false;
                                    is_subscriber = false;
                                    is_SVC_STATUS = false;
                                    MyApplication.is_get_sub_detail = false;
                                    Prefs.with(getContext()).save("is_save_namegroup", false);
                                    Prefs.with(getContext()).save("isLogin", false);
                                    Prefs.with(getContext()).save("sessionID", "");
                                    Prefs.with(getContext()).save("msisdn", "");
                                    initData();
                                }
                            })
                            .show();
                }
            }
        });
    }

    private boolean is_Waiting = false;

    private void init() {
        listCanhan = new ArrayList<>();
        adapterCanhan = new AdapterCanhan(listCanhan, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerCanhan.setNestedScrollingEnabled(false);
        recyclerCanhan.setHasFixedSize(true);
        recyclerCanhan.setLayoutManager(mLayoutManager);
        recyclerCanhan.setItemAnimator(new DefaultItemAnimator());
        recyclerCanhan.setAdapter(adapterCanhan);
        adapterCanhan.notifyDataSetChanged();

        adapterCanhan.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                isLogin = Prefs.with(getActivity()).getBoolean("isLogin", false);
                switch (position) {
                    case 0:
                        // vào bộ sưu tập
                        if (!is_Waiting) {
                            sKey_Switch = "collection";
                            if (isLogin) {
                                //showDialogLoading();
                                is_Waiting = true;
                                sesionID = Prefs.with(getActivity()).getString("sessionID", "");
                                msisdn = Prefs.with(getActivity()).getString("msisdn", "");
                                presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
                            } else {
                                check_login();
                            }
                        }
                        break;
                    case 1:
                        if (!is_Waiting) {
                            // vào quản lý nhóm
                            sKey_Switch = "group";
                            if (isLogin) {

                                is_Waiting = true;
                                sesionID = Prefs.with(getActivity()).getString("sessionID", "");
                                msisdn = Prefs.with(getActivity()).getString("msisdn", "");
                                presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
                            } else {
                                check_login();
                            }
                        }
                        break;
                    case 2:
                        // vào cài đặt luật phát nhạc
                        if (!is_Waiting) {
                            sKey_Switch = "profile";
                            if (isLogin) {

                                is_Waiting = true;
                                sesionID = Prefs.with(getActivity()).getString("sessionID", "");
                                msisdn = Prefs.with(getActivity()).getString("msisdn", "");

                                presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
                            } else {
                                check_login();
                            }
                        }
                        break;
                    case 3:
                        // trạng thái đăng nhập, dăng ký, tạm dừng kích hoạt dịch vụ
                        if (!is_Waiting) {
                            //  showDialogLoading();
                            sKey_Switch = "subscriber";
                            if (isLogin) {

                                is_Waiting = true;
                                sesionID = Prefs.with(getActivity()).getString("sessionID", "");
                                msisdn = Prefs.with(getActivity()).getString("msisdn", "");

                                presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
                            } else {
                                check_login();
                            }
                        }
                        break;
                    case 4:
                        if (check_login()) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Thông báo")
                                    .setContentText("Để huỷ dịch vụ, quý khách vui lòng soạn tin nhắn \"HUY gửi 9194\"")
                                    .setConfirmText("Đóng")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();
                        }
                        break;
                    case 5:
                        if (!FragmentDieukhoan.getInstance().isAdded()) {
                            FragmentUtil.addFragment(getActivity(), FragmentDieukhoan.getInstance(), true);
                        }
                        break;
                }
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    public void show_stop_service(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                //showDialogLoading();
                sKey_Switch = "reload";
                //presenterCanhan.get_detail_subsriber(sesionID, msisdn);
                presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
            } else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText(list.get(1))
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
    }

    private void kichhoat() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText("Bạn có muốn chắc chắn kích hoạt lại dịch vụ không?")
                .setConfirmText("Đồng ý")
                .setCancelText("Trở lại")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        presenterCanhan.pause_resume_subscriber(sesionID, msisdn, "1");
                    }
                })
                .show();

    }

    private void tamdung() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText(getResources().getString(R.string.tamdung))
                .setConfirmText("Đồng ý")
                .setCancelText("Trở lại")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        presenterCanhan.pause_resume_subscriber(sesionID, msisdn, "0");
                    }
                })
                .show();
    }

    private void dangky() {
        // startActivity(new Intent(getContext(), FragmentStopPause.class));
        final Dialog dialog_yes = new Dialog(getContext());
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_yes_no);
        TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.txt_content_dialog);

        Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
        Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
        String formattedText = "This is &lt;font color='#0000FF'&gt;blue&lt;/font&gt;";
        txt_buysongs.setText(Html.fromHtml("Để hoàn tất dịch vụ Ringtunes, Quý khách vui lòng thực hiện thao tác <font color='#FF0000'>\"Y2 gửi 9194\"</font> từ số điện thoại <font color='#FF0000'> " + PhoneNumber.convertToVnPhoneNumber(msisdn) + "</font> giá cước 3000đ/ 7 ngày. Cảm ơn quý khách"));

        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        yes.setText("Đồng ý");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
                presenter_getUser_info.api_subsriber_sms(msisdn, "2");
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "9194");
                smsIntent.putExtra("sms_body", "Y2");
                try {
                    startActivity(smsIntent);
                } catch (ActivityNotFoundException e) {
                    // Display some sort of error message here.
                }

                //  startActivity(new Intent(getActivity(), FragmentStopPause.class));
            }
        });
        TextView btn_delete_dialog = dialog_yes.findViewById(R.id.btn_delete_dialog);
        btn_delete_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_phien_dn = Prefs.with(getActivity()).getBoolean("is_Phien_DN", false);
                if (is_phien_dn) {
                    presenter_getUser_info.api_subsriber_sms(msisdn, "1");
                    Prefs.with(getActivity()).save("is_Phien_DN", false);
                }
                dialog_yes.dismiss();
            }
        });
        no.setText("Không");
        dialog_yes.show();
        /*new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện " +
                        "thao tác soạn tin nhắn \"Y2 gửi 9194\".Giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!")
                .setConfirmText("Đồng ý")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        presenter_getUser_info.api_subsriber_sms(msisdn);
                        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", "9194");
                        smsIntent.putExtra("sms_body", "Y2");
                        startActivity(smsIntent);
                        sweetAlertDialog.dismiss();
                    }
                }).show();*/
    }

    public void show_resue_service(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                // presenterCanhan.get_detail_subsriber(sesionID, msisdn);
                sKey_Switch = "reload";
                presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
            } else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText(list.get(1))
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
    }

    public void initData() {
        listCanhan.clear();
        isLogin = Prefs.with(getActivity()).getBoolean("isLogin", false);
        sesionID = Prefs.with(getActivity()).getString("sessionID", "");
        msisdn = Prefs.with(getActivity()).getString("msisdn", "");
        if (isLogin) {
            if (initNetwork()) {
                sKey_Switch = "reload";
                if (!MyApplication.is_get_sub_detail) {
                    showDialogLoading();
                    presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
                } else update_status();
            } else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Lỗi kết nối, mời bạn kiểm tra lại Wifi hoặc 3G sau đó thử lại")
                        .setCancelText("Đóng")
                        .setConfirmText("Thử lại")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (initNetwork()) {
                                    sKey_Switch = "reload";
                                    showDialogLoading();
                                    presenter_getUser_info.api_get_detail_subsriber(sesionID, msisdn);
                                    sweetAlertDialog.dismiss();
                                }
                            }
                        }).show();
            }

        } else update_status();

    }

    boolean isLogin = false, is_subscriber, is_SVC_STATUS;

    private void update_status() {
        listCanhan.clear();
        if (!isLogin) {
            txt_title_canhan.setText("Bạn chưa đăng nhập");
            btn_dangnhap.setText("ĐĂNG NHẬP");
            listCanhan.add(new Canhan("Bộ sưu tập cá nhân", R.drawable.icon_bosuutap));
            listCanhan.add(new Canhan("Quản lý nhóm", R.drawable.icon_nhom));
            listCanhan.add(new Canhan("Cài đặt luật phát nhạc", R.drawable.icon_caidatluat));
            listCanhan.add(new Canhan("Đăng ký dịch vụ", R.drawable.icon_dangky));
            listCanhan.add(new Canhan("Huỷ dịch vụ", R.drawable.icon_huy));
            listCanhan.add(new Canhan("Điều khoản dịch vụ", R.drawable.icon_dieukhoan));
            adapterCanhan.notifyDataSetChanged();
        } else {
            if (is_subscriber) {
                if (is_SVC_STATUS) {
                    txt_title_canhan.setText("" + PhoneNumber.convertToVnPhoneNumber("" + msisdn));
                    listCanhan.add(new Canhan("Bộ sưu tập cá nhân", R.drawable.icon_bosuutap));
                    listCanhan.add(new Canhan("Quản lý nhóm", R.drawable.icon_nhom));
                    listCanhan.add(new Canhan("Cài đặt luật phát nhạc", R.drawable.icon_caidatluat));
                    listCanhan.add(new Canhan("Tạm dừng dịch vụ", R.drawable.icon_tamdung));
                    listCanhan.add(new Canhan("Huỷ dịch vụ", R.drawable.icon_huy));
                    listCanhan.add(new Canhan("Điều khoản dịch vụ", R.drawable.icon_dieukhoan));
                    adapterCanhan.notifyDataSetChanged();
                } else {
                    txt_title_canhan.setText("Bạn chưa kịch hoạt dịch vụ");
                    listCanhan.add(new Canhan("Bộ sưu tập cá nhân", R.drawable.icon_bosuutap));
                    listCanhan.add(new Canhan("Quản lý nhóm", R.drawable.icon_nhom));
                    listCanhan.add(new Canhan("Cài đặt luật phát nhạc", R.drawable.icon_caidatluat));
                    listCanhan.add(new Canhan("Kích hoạt dịch vụ", R.drawable.icon_kichhoat));
                    listCanhan.add(new Canhan("Huỷ dịch vụ", R.drawable.icon_huy));
                    listCanhan.add(new Canhan("Điều khoản dịch vụ", R.drawable.icon_dieukhoan));
                    adapterCanhan.notifyDataSetChanged();
                }
            } else {
                txt_title_canhan.setText("Bạn chưa đăng ký dịch vụ");
                listCanhan.add(new Canhan("Bộ sưu tập cá nhân", R.drawable.icon_bosuutap));
                listCanhan.add(new Canhan("Quản lý nhóm", R.drawable.icon_nhom));
                listCanhan.add(new Canhan("Cài đặt luật phát nhạc", R.drawable.icon_caidatluat));
                listCanhan.add(new Canhan("Đăng ký dịch vụ dịch vụ", R.drawable.icon_dangky));
                listCanhan.add(new Canhan("Huỷ dịch vụ", R.drawable.icon_huy));
                listCanhan.add(new Canhan("Điều khoản dịch vụ", R.drawable.icon_dieukhoan));
                adapterCanhan.notifyDataSetChanged();
            }
            btn_dangnhap.setText("ĐĂNG XUẤT");
        }
    }

    private boolean check_login() {
        isLogin = Prefs.with(getActivity()).getBoolean("isLogin", false);
        if (isLogin) {
            if (is_subscriber) {
                return true;
            } else {
                final Dialog dialog_yes = new Dialog(getContext());
                dialog_yes.setCancelable(false);
                dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_yes.setContentView(R.layout.dialog_yes_no);
                TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.txt_content_dialog);

                Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
                txt_buysongs.setText(Html.fromHtml("Để hoàn tất dịch vụ Ringtunes, " +
                        "Quý khách vui lòng thực hiện thao tác <font color='#FF0000'>\"Y2 gửi 9194\"</font> từ số điện thoại <font color='#FF0000'> " + PhoneNumber.convertToVnPhoneNumber(msisdn) + "</font> giá cước 3000đ/ 7 ngày. Cảm ơn quý khách"));

                // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
                yes.setText("Đồng ý");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes.dismiss();
                        presenter_getUser_info.api_subsriber_sms(msisdn, "2");
                        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", "9194");
                        smsIntent.putExtra("sms_body", "Y2");
                        try {
                            startActivity(smsIntent);
                        } catch (ActivityNotFoundException e) {
                            // Display some sort of error message here.
                        }

                        //  startActivity(new Intent(getActivity(), FragmentStopPause.class));
                    }
                });
                TextView btn_delete_dialog = dialog_yes.findViewById(R.id.btn_delete_dialog);
                btn_delete_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean is_phien_dn = Prefs.with(getActivity()).getBoolean("is_Phien_DN", false);
                        if (is_phien_dn) {
                            presenter_getUser_info.api_subsriber_sms(msisdn, "1");
                            Prefs.with(getActivity()).save("is_Phien_DN", false);
                        }
                        dialog_yes.dismiss();
                    }
                });
                no.setText("Không");
                dialog_yes.show();
                return false;
            }
        } else {
            DialogUtil.ShowAlertDialogAnimationUpBottom2Button(getContext(),
                    "Thông báo",
                    "Bạn chưa đăng nhập, Hãy đăng nhập để sử dụng dịch vụ",
                    "Đăng nhập",
                    "Trở lại",
                    new DialogUtil.ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            check_login_3g();
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });
            /*new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Thông báo")
                    .setContentText("Bạn chưa đăng nhập, Hãy đăng nhập để sử dụng dịch vụ")
                    .setConfirmText("Đăng nhập")
                    .setCancelText("Trở lại")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            check_login_3g();
                        }
                    }).show();*/
            return false;
        }
    }

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
            hideDialogLoading();
            initData();
        }

    }

    @Override
    public void show_detail_subsriber(subscriber objSub) {
        is_Waiting = false;
        if (objSub != null) {
            // MyApplication.is_get_sub_detail=false;
            if (objSub.getERROR() != null && objSub.getERROR().equals("0")) {
                if (objSub.getSTATUS() != null && objSub.getSTATUS().equals("2")) {
                    MyApplication.is_get_sub_detail = true;
                    Prefs.with(getContext()).save("is_subscriber", true);
                    is_subscriber = true;
                    if (objSub.getSVC_STATUS().equals("1")) {
                        Prefs.with(getContext()).save("is_SVC_STATUS", true);
                        is_SVC_STATUS = true;
                    } else if (objSub.getSVC_STATUS().equals("0")) {
                        Prefs.with(getContext()).save("is_SVC_STATUS", false);
                        is_SVC_STATUS = false;
                    }
                } else {
                    Prefs.with(getContext()).save("is_subscriber", false);
                    is_subscriber = false;
                }
            } else {
                Prefs.with(getContext()).save("is_subscriber", false);
                is_subscriber = false;
            }
        } else {
            Prefs.with(getContext()).save("is_subscriber", false);
            is_subscriber = false;
        }
        switch_click_item(sKey_Switch);
    }

    @Override
    public void show_error_api() {
        hideDialogLoading();

    }

    @Override
    public void show_login_3g(final List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            if (list.get(0).equals("0") && list.get(3).equals("SUCCESS")) {
                DialogUtil.ShowAlertDialogAnimationUpBottom2Button(getContext(), getString(R.string.title_notification),
                        "Bạn có muốn đăng nhập Ringtunes bằng số điện thoại: "
                                +PhoneNumber.convertToVnPhoneNumber(list.get(2)),
                        "Đồng ý",
                        "Số khác",
                        new DialogUtil.ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                Prefs.with(getContext()).save("pass_sql_server", list.get(1));
                                Constant.sMSISDN = list.get(2);
                                Prefs.with(getContext()).save("msisdn", list.get(2));
                                presenter_getUser_info.Login(sUserId, list.get(1));
                            }

                            @Override
                            public void onClickNoDialog() {
                                startActivity(new Intent(getContext(), ActivityRegisterOTP.class));
                            }
                        });


            } else {
                DialogUtil.ShowAlertDialogAnimationUpBottom2Button(getContext(),
                        "Thông báo", getString(R.string.message)
                        , "Đăng nhập"
                        , "Huỷ bỏ",
                        new DialogUtil.ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                startActivity(new Intent(getContext(), ActivityRegisterOTP.class));
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });
                //show_notification("Thông", "Hệ thống đang bận mời thử lại sau");
                // Toast.makeText(this, "Lỗi, thuê bao của quý khách không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showDataLogin(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            Prefs.with(getContext()).save("sessionID", list.get(2));
            Constant.sSessionID= list.get(2);
            Prefs.with(getContext()).save("isLogin", true);
            Prefs.with(getContext()).save("is_Show_Subscriber", true);
            Prefs.with(getContext()).save("is_Phien_DN", true);
            msisdn = Prefs.with(getActivity()).getString("msisdn", "");
            presenter_getUser_info.api_get_detail_subsriber(list.get(2), msisdn);
        } else {
            show_notification("Lỗi", "Hệ thống đang bận mời bạn thử lại");
        }
    }

    private boolean is_Show_Subscriber = false;

    private void switch_click_item(String sKey) {
        is_Show_Subscriber = Prefs.with(getContext()).getBoolean("is_Show_Subscriber", false);
        switch (sKey) {
            case "reload":
                MyApplication.is_get_sub_detail = true;
                hideDialogLoading();
                if (is_Show_Subscriber) {
                    check_login();
                    Prefs.with(getContext()).save("is_Show_Subscriber", false);
                }
                update_status();
                //
                break;
            case "collection":
                if (check_login()) {
                    if (!FragmentConllection.getInstance().isAdded()) {
                        FragmentUtil.addFragment(getActivity(), FragmentConllection.getInstance(), true);
                    }
                    update_status();
                }
                //
                break;
            case "group":
                if (check_login()) {
                    //startActivity(new Intent(getContext(), FragmentGroups.class));
                    update_status();
                    if (!FragmentGroups.getInstance().isAdded()) {
                        FragmentUtil.addFragment(getActivity(), FragmentGroups.getInstance(), true);
                    }
                }
                break;
            case "profile":
                if (check_login()) {
                        /*    if (!FragmentConllection.getInstance().isAdded()) {
                                FragmentUtil.addFragment(getActivity(), FragmentProfiles.getInstance(), true);
                            }*/
                    startActivity(new Intent(getContext(), FragmentProfiles.class));
                }
                break;
            case "subscriber":
                hideDialogLoading();
                if (!is_subscriber) {
                    //trạng thái chưa đăng ký dịch vụ
                    dangky();
                } else {
                    // đã đăng ký dịch vụ kiểm tra trạng thái kích hoạt
                    if (is_SVC_STATUS) {
                        //đang kích hoạt dịch vụ
                        tamdung();
                    } else {
                        // đang tạm dừng dịch vụ
                        kichhoat();
                    }
                }
                break;
        }
    }

    private String sUserId;

    private void check_login_3g() {
        sUserId = Prefs.with(getContext()).getString("user_id", "");
        if (init3G()) {
            showDialogLoading();
            presenter_getUser_info.login_3g(sUserId);
        } else {
            startActivity(new Intent(getContext(), ActivityRegisterOTP.class));
        }
    }

}
