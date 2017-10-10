package com.neo.ringtunesgo.Fragment.Groups.AddGroup;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.neo.ringtunesgo.Adapter.AdapterCollectionGroup;
import com.neo.ringtunesgo.CRBTModel.Info_User;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Config.Constant;
import com.neo.ringtunesgo.Fragment.Groups.FragmentGroups;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.GroupName;
import com.neo.ringtunesgo.Model.Login;
import com.neo.ringtunesgo.Model.PhoneContactModel;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.View.ActivityContacts;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.CustomUtils;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.ringtunesgo.MainNavigationActivity.listGitSongs;
import static com.neo.ringtunesgo.MyApplication.listConllection;
import static com.neo.ringtunesgo.View.ActivityContacts.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by QQ on 9/26/2017.
 */

public class FragmentAddGroup extends BaseFragment implements AddGroupInterface.View {
    public static FragmentAddGroup fragment;

    public static FragmentAddGroup getInstance() {
        if (fragment == null) {
            synchronized (FragmentAddGroup.class) {
                if (fragment == null) {
                    fragment = new FragmentAddGroup();
                }
            }
        }
        return fragment;
    }

    PresenterAddGroup presenterAddGroup;
    SharedPreferences pre;
    String sesionID, msisdn;
    Realm realm;
    @BindView(R.id.recycle_colletion_addgroup)
    RecyclerView recycle_colletion_addgroup;
    AdapterCollectionGroup adapter;
    List<Item> lisItem;
    RecyclerView.LayoutManager mLayoutManager;
    String content_id = "";

    public static EditText ed_add_phone_addgroup;
    @BindView(R.id.img_add_contact_addgroup)
    ImageView img_add_contact_addgroup;

    @BindView(R.id.btn_add_group)
    Button btn_add_group;
    @BindView(R.id.ed_name_addgroup)
    EditText ed_name_addgroup;
    List<GroupName> lisName = null;
    String group_name_server = "";
    int iNumber;
    String caller_type = "GROUP";
    String from_time = "00:00:00", to_time = "23:59:59";
    String caller_id;
    String group_name = "";
    Info_User objInfo;
    Login objLogin;
    @BindView(R.id.img_back_detailsong)
    ImageView img_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listGitSongs = new ArrayList<>();
        presenterAddGroup = new PresenterAddGroup(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_group, container, false);
        ButterKnife.bind(this, view);
        ed_add_phone_addgroup = (EditText) view.findViewById(R.id.ed_add_phone_addgroup);
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        realm = RealmController.with(this).getRealm();
        lisName = realm.where(GroupName.class).findAll();
        sesionID = pre.getString("sesionID", "");
        msisdn = pre.getString("msisdn", "");
        presenterAddGroup.getConllection(sesionID, msisdn);
        initCollection();
        initEvent();
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (objLogin != null) {
            if (objInfo != null) {
                if (objInfo.getStatus() != null && objInfo.getStatus().equals("2")) {
                    sesionID = objLogin.getsSessinonID();
                    msisdn = objLogin.getMsisdn();
                } else {

                }
            }
        }

    }

    private void initCollection() {
        lisItem = listConllection;
        adapter = new AdapterCollectionGroup(lisItem, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_colletion_addgroup.setHasFixedSize(true);
        recycle_colletion_addgroup.setNestedScrollingEnabled(false);
        recycle_colletion_addgroup.setLayoutManager(mLayoutManager);
        recycle_colletion_addgroup.setItemAnimator(new DefaultItemAnimator());
        recycle_colletion_addgroup.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {
                for (int i = 0; i < lisItem.size(); i++) {
                    if (i == position) {
                        lisItem.get(i).setIs_check(true);
                    } else lisItem.get(i).setIs_check(false);
                }
                content_id = lisItem.get(position).getContent_id();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });
        img_add_contact_addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForContactPermission();
            }
        });
        btn_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(ed_name_addgroup.getText().toString().length() > 0)) {
                    Toast.makeText(getContext(), "Bạn chưa nhập vào tên cho nhóm", Toast.LENGTH_SHORT).show();
                } else if (!(ed_add_phone_addgroup.getText().toString().length() > 0)) {
                    Toast.makeText(getContext(), "Bạn chưa có số điện thoại nào trong nhóm", Toast.LENGTH_SHORT).show();
                } else if (!(content_id.length() > 0)) {
                    Toast.makeText(getContext(), "Bạn chưa chọn bài hát cho nhóm", Toast.LENGTH_SHORT).show();
                } else {
                    group_name = ed_name_addgroup.getText().toString();
                    iNumber = CustomUtils.getNameGroup(lisName);
                    if (iNumber >= 0) {
                        group_name_server = lisName.get(iNumber).getsNameServer();
                    }
                    presenterAddGroup.addGroup(sesionID, msisdn, group_name_server, ed_add_phone_addgroup.getText().toString());
                }
            }
        });
    }

    @Override
    public void show_add_profile_buygroup(List<String> list) {
        if (list.size() > 0) {
            FragmentGroups.add_Group();
            FragmentManager fm_add_group = getActivity().getSupportFragmentManager();
            if (fm_add_group.getBackStackEntryCount() > 0) {
                fm_add_group.popBackStack();
            }
        }
    }

    @Override
    public void showaddGroups(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                if (list.get(2).length() > 0) {
                    Toast.makeText(getContext(), "Thêm nhóm thành công", Toast.LENGTH_SHORT).show();
                    GroupName objGroupname = new GroupName();
                    objGroupname.setsNameServer(group_name_server);
                    objGroupname.setsNameLocal(group_name);
                    objGroupname.setId_group(list.get(2));
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(objGroupname);
                    realm.commitTransaction();
                    caller_id = list.get(2);
                    presenterAddGroup.add_profile(sesionID, msisdn, content_id, caller_type, caller_id, from_time, to_time);

                    /*FragmentManager fm = getActivity().getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    }*/
                } else
                    Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Số điện thoại đã có trong nhóm khác", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        }

    }

    @Override
    public void showConllection(List<Item> list) {
        lisItem.clear();
        String id_songs = "";
        if (list != null && list.size() > 0) {
            lisItem.addAll(list);
            for (int i = 0; i < lisItem.size(); i++) {
                if (i < lisItem.size() - 1)
                    id_songs = id_songs + lisItem.get(i).getContent_id() + ",";
                else id_songs = id_songs + lisItem.get(i).getContent_id();
            }
            presenterAddGroup.get_info_songs_collection(id_songs, Constant.USER_ID);

        }

    }

    public void show_list_songs_collection(List<Ringtunes> listSongs) {
        if (listSongs.size() > 0) {
            for (int i = 0; i < listSongs.size(); i++) {
                for (int j = 0; j < lisItem.size(); j++) {
                    if (listSongs.get(i).getId().equals(lisItem.get(j).getContent_id())) {
                        lisItem.get(j).setImage_url(listSongs.get(i).getImage_file());
                        lisItem.get(j).setProduct_name(listSongs.get(i).getProduct_name());
                        lisItem.get(j).setSinger_id(listSongs.get(i).getSinger_id());
                        lisItem.get(j).setPath(listSongs.get(i).getPath());
                    }
                }
                adapter.notifyDataSetChanged();
                //presenterConllection.getSongsSame(listSongs.get(listSongs.size()-1).getSinger_id(), "" + page, "" + index);
            }
        }
    }

    public void get_contact() {
        MainNavigationActivity.datasvina = new ArrayList<PhoneContactModel>();
        MainNavigationActivity.datas = new ArrayList<PhoneContactModel>();
        listGitSongs = new ArrayList<PhoneContactModel>();
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("option", Config.ADD_GROUP);
        editor.commit();
        if (!ActivityContacts.getInstance().isAdded())
            FragmentUtil.addFragment(getActivity(), ActivityContacts.getInstance(), true);
    }

    public void askForContactPermission() {
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
                get_contact();
                // datas = CustomUtils.getAllPhoneContacts(getContext());
            }
        } else {
            get_contact();
            //datas = CustomUtils.getAllPhoneContacts(getContext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    get_contact();
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
