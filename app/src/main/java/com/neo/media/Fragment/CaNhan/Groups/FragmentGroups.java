package com.neo.media.Fragment.CaNhan.Groups;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.Adapter.AdapterGroups;
import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.Fragment.CaNhan.Groups.AddGroup.FragmentAddGroup;
import com.neo.media.Fragment.CaNhan.Groups.GroupMember.FragmentGroupMember;
import com.neo.media.Model.GroupName;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

import static com.neo.media.MyApplication.objGroup;
import static com.neo.media.View.ActivityContacts.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by QQ on 7/24/2017.
 */

public class FragmentGroups extends BaseFragment implements GroupInterface.View {
    public static PresenterGroups presenterGroups;
    public static FragmentGroups fragmentGroups;

    public static FragmentGroups getInstance() {
        if (fragmentGroups == null) {
            synchronized (FragmentGroups.class) {
                if (fragmentGroups == null)
                    fragmentGroups = new FragmentGroups();
            }
        }
        return fragmentGroups;
    }

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
    List<GroupName> lisName;
    public static String sesionID;
    public static String msisdn;
    @BindView(R.id.txt_notification_group)
    TextView txt_notification_group;
    @BindView(R.id.txt_notifi_profile)
            TextView txt_notifi_null;
    Realm realm;
    private boolean is_subscriber, is_SVC_STATUS;
    private boolean isLogin;
 /*   @BindView(R.id.txt_add_fragmentall)
    TextView txt_add;*/
/*
    @Override
    public int setContentViewId() {
        return R.layout.fragment_group;
    }*/


    public void initData() {
        realm = RealmController.with(this).getRealm();
        lisName = new ArrayList<>();
        presenterGroups = new PresenterGroups(this);
        sesionID = Prefs.with(getActivity()).getString("sessionID", "");
        msisdn = Prefs.with(getContext()).getString("msisdn", "");
        is_subscriber = Prefs.with(getContext()).getBoolean("is_subscriber", false);
        is_SVC_STATUS = Prefs.with(getContext()).getBoolean("is_SVC_STATUS", false);
        isLogin = Prefs.with(getContext()).getBoolean("isLogin", false);
    }

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
        initEvent();
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);
        initData();
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
        count_reload = 0;
        showDialogLoading();
        presenterGroups.getAllGroups(sesionID, msisdn, group_id);
       /* if (isLogin) {
            if (is_subscriber) {
                txt_notification_group.setVisibility(View.GONE);

            } else {
                txt_notification_group.setVisibility(View.VISIBLE);
            }

        }*/
        txt_title.setText("Quản lý nhóm");
    }

    private void initEvent() {
        back_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.popBackStack(getActivity());
            }
        });
    }
    public static void load_all_group(){
        count_reload = 0;
        presenterGroups.getAllGroups(sesionID, msisdn, "ALL");
    }
    private void init() {
        lisGroups = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        adapterGroups = new AdapterGroups(lisGroups, getContext());
        recycleAlbum.setNestedScrollingEnabled(false);
        recycleAlbum.setHasFixedSize(true);
        recycleAlbum.setLayoutManager(mLayoutManager);
        recycleAlbum.setItemAnimator(new DefaultItemAnimator());
        recycleAlbum.setAdapter(adapterGroups);

        adapterGroups.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (lisGroups.get(position).isIs_add_group()) {
                    startActivity(new Intent(getActivity(), FragmentAddGroup.class));
                } else
                    askForContactPermission(position);
            }

            // long click delete nhóm
            @Override
            public void OnLongItemClickListener(final int position) {

            }
        });
    }

    @Override
    public void showaddGroups(List<String> list) {
       /* if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                count_reload = 0;
                presenterGroups.getAllGroups(sesionID, msisdn, group_id);
                show_notifi("Thông báo", "Thêm nhóm thành công");
                //  Toast.makeText(this, "Thêm nhóm thành công", Toast.LENGTH_SHORT).show();
            } else {
                show_notifi("Thông báo", list.get(1));
                //  Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    @Override
    public void showGroups(List<GROUP> groups) {
        hideDialogLoading();
        lisName = realm.where(GroupName.class).findAll();
        if (groups != null && groups.size() > 0) {
            txt_notifi_null.setVisibility(View.GONE);
            lisGroups.clear();
            lisGroups.addAll(groups);
            for (int i = 0; i < lisName.size(); i++) {
                for (int j = 0; j < lisGroups.size(); j++) {
                    if (lisName.get(i).getsNameServer().equals(lisGroups.get(j).getName())) {
                        lisGroups.get(j).setImg_backgroup(lisName.get(i).getBackground());
                     //   lisGroups.get(j).setImg_backgroup(R.drawable.background_3);
                        if (lisName.get(i).getsNameLocal() == null) {
                            GroupName groupName = new GroupName();
                            groupName.setsNameServer(lisName.get(i).getsNameServer());
                            groupName.setsNameLocal(lisGroups.get(j).getName());
                            groupName.setId_group(lisGroups.get(j).getId());
                            groupName.setBackground(lisName.get(i).getBackground());
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(groupName);
                            realm.commitTransaction();
                        } else {
                            lisGroups.get(j).setName(lisName.get(i).getsNameLocal());
                            lisGroups.get(j).setImg_backgroup(lisName.get(i).getBackground());
                           // lisGroups.get(j).setImg_backgroup(R.drawable.background_3);
                        }
                    }
                }
            }
            Random randrom = new Random();
            if (lisGroups.size() < 3) {
                for (int i = lisGroups.size(); i < 3; i++) {
                    int rd = randrom.nextInt((lisName.size() - 0) + 1) + 0;
                    GROUP obj = new GROUP();
                    obj.setName("THÊM NHÓM");
                    obj.setImg_backgroup(lisName.get(rd).getBackground());
                    obj.setIs_add_group(true);
                    lisGroups.add(obj);
                }
            } else {
                int rd = randrom.nextInt((lisName.size() - 0) + 1) + 0;
                GROUP obj = new GROUP();
                obj.setName("THÊM NHÓM");
                obj.setImg_backgroup(lisName.get(rd).getBackground());
                obj.setIs_add_group(true);
                lisGroups.add(obj);
            }
            adapterGroups.notifyDataSetChanged();
            // set click cho nhóm
        } else {
            lisGroups.clear();
            for (int i = lisGroups.size(); i < 3; i++) {
                GROUP obj = new GROUP();
                obj.setName("THÊM NHÓM");
                obj.setImg_backgroup(lisName.get(i).getBackground());
                obj.setIs_add_group(true);
                lisGroups.add(obj);
            }
            adapterGroups.notifyDataSetChanged();
            txt_notifi_null.setVisibility(View.VISIBLE);
            txt_notifi_null.setText("Bạn chưa tạo nhóm nào");
          /*  new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Thông báo")
                    .setContentText("Bạn chưa tạo nhóm nào")
                    .setConfirmText("Đóng")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();*/
        }
        adapterGroups.notifyDataSetChanged();
    }

    public void show_notifi(String title, String content) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("Đóng")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }
    public static int count_reload = 0;
    @Override
    public void showMessage(String message) {
        if (count_reload<3){
            count_reload++;
            presenterGroups.getAllGroups(sesionID, msisdn, group_id);
        }else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText("Kết nối không thành công mời bạn thử lại sau")
                .setCancelText("Đóng")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
        }

      //  show_notifi("Thông báo", "");
       /* show_notifi("Thông báo", message);
        //Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
        showDialogLoading();
        presenterGroups.getAllGroups(sesionID, msisdn, "ALL");*/

    }

    @Override
    public void showdeleteGroups(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                show_notifi("Thông báo", "Bạn xoá nhóm thành công");
                // Toast.makeText(getContext(), "Bạn xoá nhóm thành công", Toast.LENGTH_SHORT).show();
            } else {
                show_notifi("Thông báo", list.get(1));
                //  Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        // MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }


   /* private void get_contact(int position) {
        // chuyển sang view Group member
        if (lisGroups.get(position).getClis() != null) {
            final List<CLI> finalLis = new ArrayList<>();
            finalLis.addAll(lisGroups.get(position).getClis().getCLI());
        }
        objGroup = lisGroups.get(position);
        if (!FragmentGroupMember.getInstance().isAdded())
            FragmentUtil.addFragment(getActivity(), FragmentGroupMember.getInstance(), true);
        //  startActivity(new Intent(this, FragmentGroupMember.class));
    }*/
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
        if (lisGroups.get(position).getClis()!=null){
            final List<CLI> finalLis = new ArrayList<>();
            finalLis.addAll(lisGroups.get(position).getClis().getCLI());
        }
       /* Bundle bundle = new Bundle();
        bundle.putString("group_id", lisGroups.get(position).getId());
        bundle.putString("title", lisGroups.get(position).getName());
        bundle.putSerializable("lis_member", (Serializable) finalLis);*/
        objGroup = lisGroups.get(position);
        if (!FragmentGroupMember.getInstance().isAdded())
            FragmentUtil.addFragment(getActivity(), FragmentGroupMember.getInstance(), true);
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
