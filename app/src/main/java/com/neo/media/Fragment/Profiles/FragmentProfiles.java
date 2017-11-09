package com.neo.media.Fragment.Profiles;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterProfiles;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Fragment.Profiles.Add_Profile.Fragment_AddProfiles;
import com.neo.media.Fragment.Profiles.Add_Profile.Fragment_EditProfile;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Login;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by QQ on 7/25/2017.
 */

public class FragmentProfiles extends BaseFragment implements ProfilesInterface.View {
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
    List<PROFILE> lisProfiles;
    @BindView(R.id.txt_title_group)
    TextView txt_title;
    @BindView(R.id.txt_add_group)
    Button txt_add_profile;
    RecyclerView.LayoutManager mLayoutManager;
    public static PresenterProfiles presenterProfiles;
    Info_User objInfo;
    Login objLogin;
    public Realm realm;
    public static String sesionID;
    public static String msisdn;
    @BindView(R.id.txt_notification_group)
    TextView txt_notification_group;
    @BindView(R.id.img_back_group)
    ImageView back_group;
    List<GroupName> listName;

    /* @BindView(R.id.txt_add_fragmentall)
     TextView txt_add;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
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
    }

    @Override
    public void onResume() {
        super.onResume();
        objInfo = realm.where(Info_User.class).findFirst();
        listName = realm.where(GroupName.class).findAll();
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();
        if (objLogin != null) {
            if (objInfo != null) {
                if (objInfo.getService_status() != null && objInfo.getStatus().equals("2")) {
                    txt_add_profile.setVisibility(View.VISIBLE);
                    txt_add_profile.setText("Thêm cài đặt mới +");
                    txt_notification_group.setVisibility(View.GONE);
                    sesionID = objLogin.getsSessinonID();
                    msisdn = objLogin.getMsisdn();
                    showDialogLoading();
                    presenterProfiles.getAllProfiles(sesionID, msisdn, "", "");
                } else {
                    txt_add_profile.setVisibility(View.GONE);
                    txt_notification_group.setVisibility(View.VISIBLE);
                }
            }
        }
        MainNavigationActivity.appbar.setVisibility(View.GONE);
        txt_title.setText("CÀI ĐẶT RINGTUNES");
    }

    private void initEvent() {
        txt_add_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Fragment_AddProfiles.getInstance().isAdded())
                FragmentUtil.addFragment(MainNavigationActivity.fragmentActivity,Fragment_AddProfiles.getInstance(), true);
            }
        });

        back_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });
    }

    private void init() {
        lisProfiles = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        adapterProfiles = new AdapterProfiles(lisProfiles, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterProfiles);
        adapterProfiles.notifyDataSetChanged();

        adapterProfiles.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {

                final Dialog dialog_yes_member = new Dialog(getContext());
                dialog_yes_member.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_yes_member.setContentView(R.layout.dialog_menu);
                TextView add_menu = (TextView) dialog_yes_member.findViewById(R.id.dialog_add_menu);
                TextView delete_menu = (TextView) dialog_yes_member.findViewById(R.id.txt_delete_menu);
                TextView dialog_edit_menu = (TextView) dialog_yes_member.findViewById(R.id.dialog_edit_menu);
                dialog_edit_menu.setVisibility(View.GONE);
                add_menu.setText("Sửa luật phát nhạc");
                delete_menu.setText("Xoá luật phát nhạc");

                add_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.profile_bundle = lisProfiles.get(position);
                        FragmentUtil.addFragment(getActivity(), Fragment_EditProfile.getInstance(), true);
                        dialog_yes_member.dismiss();
                    }
                });
                delete_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes_member.dismiss();
                        final Dialog confirm = new Dialog(getContext());
                        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        confirm.setContentView(R.layout.dialog_yes_no);
                        Button yes = (Button) confirm.findViewById(R.id.btn_dialog_yes);
                        Button no = (Button) confirm.findViewById(R.id.btn_dialog_no);
                        TextView txt_message = (TextView) confirm.findViewById(R.id.dialog_message);
                        txt_message.setText("Bạn có muốn xoá luật phát nhạc");
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenterProfiles.deleteProfile(sesionID, msisdn, lisProfiles.get(position).getProfile_id());
                                confirm.dismiss();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirm.dismiss();
                            }
                        });
                        confirm.show();
                    }
                });

                dialog_yes_member.show();
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showAllProfiles(List<PROFILE> list) {
        if (list.size() > 0) {
            lisProfiles.clear();
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < listName.size(); j++) {
                    if (list.get(i).getCaller_id().equals(listName.get(j).getId_group())) {
                        list.get(i).setCaller_name(listName.get(j).getsNameLocal());
                    }
                }
            }
            lisProfiles.addAll(list);
            adapterProfiles.notifyDataSetChanged();
        } else {
            lisProfiles.clear();
            adapterProfiles.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrorDeleteProfile(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenterProfiles.getAllProfiles(sesionID, msisdn, "", "");
            } else {
                Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            }
        }else Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    public static void delete_profile(String id_profile) {
        if (id_profile != null && id_profile.length() > 0) {

        }

    }

    public static void get_profile() {
        presenterProfiles.getAllProfiles(sesionID, msisdn, "", "");
    }
}
