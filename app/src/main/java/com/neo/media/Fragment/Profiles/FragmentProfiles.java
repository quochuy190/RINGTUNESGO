package com.neo.media.Fragment.Profiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.Adapter.AdapterProfiles;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Fragment.Profiles.Add_Profile.Fragment_AddProfiles;
import com.neo.media.Fragment.Profiles.Add_Profile.Fragment_EditProfile;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Login;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

/**
 * Created by QQ on 7/25/2017.
 */

public class FragmentProfiles extends BaseActivity implements ProfilesInterface.View {
    public static FragmentProfiles fragmentProfiles;

    public static FragmentProfiles getInstance() {
        if (fragmentProfiles == null) {
            synchronized (FragmentProfiles.class) {
                if (fragmentProfiles == null) {
                    fragmentProfiles = new FragmentProfiles();
                }
            }
        }
        return fragmentProfiles;
    }

    AdapterProfiles adapterProfiles;
    @BindView(R.id.recycle_group)
    RecyclerView recyclerView;
    private static List<PROFILE> lisProfiles;
    @BindView(R.id.txt_title_group)
    TextView txt_title;
    @BindView(R.id.txt_add_group1)
    TextView btn_chose;
    RecyclerView.LayoutManager mLayoutManager;
    public static PresenterProfiles presenterProfiles;
    Info_User objInfo;
    Login objLogin;
    public Realm realm;
    private boolean is_subscriber, is_SVC_STATUS;
    private boolean isLogin;
    public static String sesionID;
    public static String msisdn;
    @BindView(R.id.txt_notification_group)
    TextView txt_notification_group;
    @BindView(R.id.img_back_group)
    ImageView back_group;
    List<GroupName> listName;
    @BindView(R.id.btn_add_profile)
    TextView btn_add_profile;
    @BindView(R.id.txt_notifi_profile)
    TextView txt_notifi_profile;
    public boolean isDelete = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        //user_id = Prefs.with(getActivity()).getString("user_id", "");
        //id_Singer= pre.getString("isSinger", "");
        sesionID = Prefs.with(this).getString("sessionID", "");
        msisdn = Prefs.with(this).getString("msisdn", "");
        is_subscriber = Prefs.with(this).getBoolean("is_subscriber", false);
        is_SVC_STATUS = Prefs.with(this).getBoolean("is_SVC_STATUS", false);
        isLogin = Prefs.with(this).getBoolean("isLogin", false);
        realm = RealmController.with(this).getRealm();
        init();
        presenterProfiles = new PresenterProfiles(this);
        initEvent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_group;
    }

    @Override
    public void initData() {

    }


   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);
        realm = RealmController.with(this).getRealm();
        init();
        presenterProfiles = new PresenterProfiles(this);

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

        btn_add_profile.setVisibility(View.VISIBLE);
        txt_title.setText("Quản lý luật phát nhạc");
        listName = realm.where(GroupName.class).findAll();
        btn_chose.setVisibility(View.VISIBLE);
        showDialogLoading();
        presenterProfiles.getAllProfiles(sesionID, msisdn, "", "");

    }

    private void initEvent() {
        btn_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDelete) {
                    btn_chose.setText("Bỏ chọn");
                    isDelete = !isDelete;
                    if (lisProfiles.size() > 0) {
                        for (int i = 0; i < lisProfiles.size(); i++) {
                            lisProfiles.get(i).setDelete(isDelete);
                        }
                        adapterProfiles.notifyDataSetChanged();
                    }
                } else {
                    btn_chose.setText("Chọn");
                    isDelete = !isDelete;
                    if (lisProfiles.size() > 0) {
                        for (int i = 0; i < lisProfiles.size(); i++) {
                            lisProfiles.get(i).setDelete(isDelete);
                        }
                        adapterProfiles.notifyDataSetChanged();
                    }
                }


            }
        });
        btn_add_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FragmentProfiles.this, Fragment_AddProfiles.class));
               /* if (!Fragment_AddProfiles.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), Fragment_AddProfiles.getInstance(), true);*/
            }
        });
        back_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        lisProfiles = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, 1);
        adapterProfiles = new AdapterProfiles(lisProfiles, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterProfiles);
        adapterProfiles.notifyDataSetChanged();

        adapterProfiles.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {
                MyApplication.profile_bundle = lisProfiles.get(position);
                startActivity(new Intent(FragmentProfiles.this, Fragment_EditProfile.class));
               /* MyApplication.profile_bundle = lisProfiles.get(position);
                FragmentUtil.addFragment(this, Fragment_EditProfile.getInstance(), true);*/

            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showAllProfiles(List<PROFILE> list) {
        List<PROFILE> listTem = new ArrayList<>();

        if (list.size() > 1) {
            for (int i = 0;i<list.size();i++){
                if (!list.get(i).getTime_category().equals("-1")){
                    listTem.add(list.get(i));
                }
            }
            list.clear();
            list.addAll(listTem);
            btn_chose.setVisibility(View.VISIBLE);
            txt_notifi_profile.setVisibility(View.GONE);
            lisProfiles.clear();
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < listName.size(); j++) {
                    if (list.get(i).getCaller_id().equals(listName.get(j).getId_group())) {
                        list.get(i).setCaller_name(listName.get(j).getsNameLocal());
                    }
                }
            }
            lisProfiles.addAll(list);
            if (lisProfiles.size() > 0) {
                for (int i = 0; i < lisProfiles.size(); i++) {
                    lisProfiles.get(i).setDelete(isDelete);
                }
                adapterProfiles.notifyDataSetChanged();
            }
            adapterProfiles.notifyDataSetChanged();
        } else {
            lisProfiles.clear();
            if (lisProfiles.size() > 0) {
                for (int i = 0; i < lisProfiles.size(); i++) {
                    lisProfiles.get(i).setDelete(isDelete);
                }
                adapterProfiles.notifyDataSetChanged();
            }
            btn_chose.setVisibility(View.GONE);
            txt_notifi_profile.setVisibility(View.VISIBLE);
            adapterProfiles.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrorDeleteProfile(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenterProfiles.getAllProfiles(sesionID, msisdn, "", "");
                show_notification("Thông báo", "Xoá lật phát nhạc thành công");
            } else {
                show_notification("Thông báo", list.get(1));
                //  Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            }
        } else
            show_notification("Thông báo", "Lỗi hệ thống");
        //  Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //  MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    public static void delete_profile(Context context, final String id_profile) {
        if (id_profile != null && id_profile.length() > 0) {
            new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Thông báo")
                    .setContentText("Bạn có muốn xoá luật phát nhạc này không ?")
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
                            presenterProfiles.deleteProfile(sesionID, msisdn, id_profile);
                        }
                    })
                    .show();
        }

    }
}
