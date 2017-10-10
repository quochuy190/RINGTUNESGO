package com.neo.ringtunesgo.Fragment.Groups;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.ringtunesgo.Adapter.AdapterGroups;
import com.neo.ringtunesgo.CRBTModel.CLI;
import com.neo.ringtunesgo.CRBTModel.GROUP;
import com.neo.ringtunesgo.CRBTModel.Info_User;
import com.neo.ringtunesgo.Fragment.Groups.AddGroup.FragmentAddGroup;
import com.neo.ringtunesgo.Fragment.Groups.GroupMember.FragmentGroupMember;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.GroupName;
import com.neo.ringtunesgo.Model.Login;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.ringtunesgo.MyApplication.objGroup;
import static com.neo.ringtunesgo.View.ActivityContacts.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by QQ on 7/24/2017.
 */

public class FragmentGroups extends BaseFragment implements GroupInterface.View {
    public static PresenterGroups presenterGroups;
    public static FragmentGroups fragmentGroups;

    String group_id = "ALL";
    AdapterGroups adapterGroups;
    List<GROUP> lisGroups;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycle_group)
    RecyclerView recycleAlbum;
    @BindView(R.id.txt_title_group)
    TextView txt_title;
    @BindView(R.id.img_back_group)
    ImageView back_group;
    @BindView(R.id.txt_add_group)
    TextView txt_add_group;
    Dialog dialog_add;
    public static String sesionID;
    public static String msisdn;
    @BindView(R.id.txt_notification_group)
    TextView txt_notification_group;
    Realm realm;
    Info_User objInfo;
    Login objLogin;
 /*   @BindView(R.id.txt_add_fragmentall)
    TextView txt_add;*/

    public static FragmentGroups getInstance() {
        if (fragmentGroups == null) {
            synchronized (FragmentGroups.class) {
                if (fragmentGroups == null) {
                    fragmentGroups = new FragmentGroups();
                }
            }
        }
        return fragmentGroups;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        realm = RealmController.with(this).getRealm();
        sesionID = pre.getString("sesionID", "");
        msisdn = pre.getString("msisdn", "");
        init();
        presenterGroups = new PresenterGroups(this);
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
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();

        if (objLogin != null) {
            if (objInfo != null) {
                if (objInfo.getStatus() != null && objInfo.getStatus().equals("2")) {
                    txt_notification_group.setVisibility(View.GONE);
                    txt_add_group.setVisibility(View.VISIBLE);
                    sesionID = objLogin.getsSessinonID();
                    msisdn = objLogin.getMsisdn();
                    showDialogLoading();
                    presenterGroups.getAllGroups(sesionID, msisdn, group_id);
                } else {
                    txt_notification_group.setVisibility(View.VISIBLE);
                    txt_add_group.setVisibility(View.GONE);
                }
            }
        }
        MainNavigationActivity.appbar.setVisibility(View.GONE);
        txt_title.setText("Quản lý nhóm");
    }

    private void initEvent() {
        txt_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.addFragment(getActivity(), FragmentAddGroup.getInstance(), true);

              /*  dialog_add = new Dialog(getActivity());
                dialog_add.setContentView(R.layout.dialog_add_group);
                final TextView update = (TextView) dialog_add.findViewById(R.id.ed_dialog_group_name);
                Button ok = (Button) dialog_add.findViewById(R.id.btn_dialog_ok);
                Button cancel = (Button) dialog_add.findViewById(R.id.btn_dialog_cancel);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainNavigationActivity.datasvina = new ArrayList<PhoneContactModel>();
                        MainNavigationActivity.datas = new ArrayList<PhoneContactModel>();
                        MainNavigationActivity.listGitSongs = new ArrayList<PhoneContactModel>();
                        Bundle bundle = new Bundle();
                        bundle.putString("option", Config.ADD_GROUP);
                        bundle.putString("group_name", (update.getText().toString()));
                        InputMethodManager inputManager = (InputMethodManager) dialog_add.getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().
                                getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        FragmentUtil.addFragmentData(MainNavigationActivity.fragmentActivity,
                                ActivityContacts.getInstance(), true, bundle);
                        dialog_add.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_add.dismiss();
                    }
                });
                dialog_add.show();*/
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
        lisGroups = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        adapterGroups = new AdapterGroups(lisGroups, getContext());
        recycleAlbum.setHasFixedSize(true);
        recycleAlbum.setLayoutManager(mLayoutManager);
        recycleAlbum.setItemAnimator(new DefaultItemAnimator());
        recycleAlbum.setAdapter(adapterGroups);
    }

    @Override
    public void showaddGroups(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenterGroups.getAllGroups(sesionID, msisdn, group_id);
                Toast.makeText(getContext(), "Thêm nhóm thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showGroups(List<GROUP> groups) {
        hideDialogLoading();
        if (groups != null && groups.size() > 0) {
            lisGroups.clear();
            lisGroups.addAll(groups);
            List<GroupName> lisName = new ArrayList<>();
            lisName = realm.where(GroupName.class).findAll();
            for (int i = 0; i < lisName.size(); i++) {
                for (int j = 0; j < lisGroups.size(); j++) {
                    if (lisName.get(i).getsNameServer().equals(lisGroups.get(j).getName())) {
                        if (lisName.get(i).getsNameLocal() == null) {
                            GroupName groupName = new GroupName();
                            groupName.setsNameServer(lisName.get(i).getsNameServer());
                            groupName.setsNameLocal(lisGroups.get(j).getName());
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(groupName);
                            realm.commitTransaction();
                        } else {
                            lisGroups.get(j).setName(lisName.get(i).getsNameLocal());
                        }
                    }
                }
            }
            adapterGroups.notifyDataSetChanged();
            // set click cho nhóm
            adapterGroups.setSetOnItemClickListener(new setOnItemClickListener() {
                @Override
                public void OnItemClickListener(int position) {
                    askForContactPermission(position);
                }

                // long click delete nhóm
                @Override
                public void OnLongItemClickListener(final int position) {
                  /*  final Dialog dialog_yes = new Dialog(getContext());
                    dialog_yes.setContentView(R.layout.dialog_yes_no);
                    Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                    Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialogLoading();
                            presenterGroups.deleteGroup(sesionID, msisdn, lisGroups.get(position).getId());
                            presenterGroups.getAllGroups(sesionID, msisdn, group_id);
                            dialog_yes.dismiss();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            *//*showDialogLoading();
                            presenterGroups.getAllGroups(sesionID, msisdn, group_id);*//*
                            dialog_yes.dismiss();
                        }
                    });
                    dialog_yes.show();
*/
                }
            });
        } else {
            Toast.makeText(getActivity(), "Bạn chưa tạo nhóm nào", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
        showDialogLoading();
        presenterGroups.getAllGroups(sesionID, msisdn, "ALL");

    }

    @Override
    public void showdeleteGroups(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                Toast.makeText(getContext(), "Bạn xoá nhóm thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void add_Group() {
        presenterGroups.getAllGroups(sesionID, msisdn, "ALL");
    }

    @Override
    public void onPause() {
        super.onPause();
        MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    public void askForContactPermission(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                get_contact(position);
                // datas = CustomUtils.getAllPhoneContacts(getContext());
            }
        } else {
            get_contact(position);
            //datas = CustomUtils.getAllPhoneContacts(getContext());
        }
    }

    private void get_contact(int position) {
        // chuyển sang view Group member
        final List<CLI> finalLis = new ArrayList<>();
        finalLis.addAll(lisGroups.get(position).getClis().getCLI());
       /* Bundle bundle = new Bundle();
        bundle.putString("group_id", lisGroups.get(position).getId());
        bundle.putString("title", lisGroups.get(position).getName());
        bundle.putSerializable("lis_member", (Serializable) finalLis);*/
        objGroup = lisGroups.get(position);
        if (!FragmentGroupMember.getInstance().isAdded())
            FragmentUtil.addFragment(MainNavigationActivity.fragmentActivity, FragmentGroupMember.getInstance(), true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //datas = CustomUtils.getAllPhoneContacts(getContext());

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }
}
