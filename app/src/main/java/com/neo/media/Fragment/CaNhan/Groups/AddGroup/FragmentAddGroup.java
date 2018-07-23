package com.neo.media.Fragment.CaNhan.Groups.AddGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterCollectionGroup;
import com.neo.media.Adapter.AdapterIcon_Group;
import com.neo.media.Adapter.AdapterTime_Group;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Config.Constant;
import com.neo.media.Contact.ViewActivity.ActivityListContact_Get_Add_Group;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Icon_Group;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Time_Group;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.KeyboardUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

import static com.neo.media.MyApplication.listConllection;

/**
 * Created by QQ on 9/26/2017.
 */

public class FragmentAddGroup extends BaseActivity implements AddGroupInterface.View {
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
    @BindView(R.id.img_back_detailsong)
    ImageView img_back;
    @BindView(R.id.txt_delete_phone)
    TextView txt_delete_phone;
    List<Icon_Group> lisIcon;
    List<Time_Group> lisTime;
    private boolean is_subscriber, is_SVC_STATUS;
    private boolean isLogin;
    boolean isClick = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.listGitSongs = new ArrayList<>();
        presenterAddGroup = new PresenterAddGroup(this);
        ButterKnife.bind(this);
        get_Data_Pref();
        ed_add_phone_addgroup = (EditText) findViewById(R.id.ed_add_phone_addgroup);
        //   pre = getSharedPreferences("data", MODE_PRIVATE);
        realm = RealmController.with(this).getRealm();
        lisName = realm.where(GroupName.class).findAll();
        initDatas();
        initTimeGroup();
        initCollection();
        initEvent();
    }

    private void get_Data_Pref() {
        MyApplication.list_ct_add_group.clear();
        sesionID = Prefs.with(this).getString("sessionID", "");
        msisdn = Prefs.with(this).getString("msisdn", "");
        is_subscriber = Prefs.with(this).getBoolean("is_subscriber", false);
        is_SVC_STATUS = Prefs.with(this).getBoolean("is_SVC_STATUS", false);
        isLogin = Prefs.with(this).getBoolean("isLogin", false);
    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_add_group;
    }

    @Override
    public void initData() {

    }


    @BindView(R.id.recycle_icon)
    RecyclerView recycle_icon;
    AdapterIcon_Group adapter_icon;
    public int icon_group = 0;

    private void initDatas() {
        lisIcon = new ArrayList<>();
        lisIcon.add(new Icon_Group(R.drawable.group_0, true, 1));
        lisIcon.add(new Icon_Group(R.drawable.group_1, false, 2));
        lisIcon.add(new Icon_Group(R.drawable.group_2, false, 3));
        lisIcon.add(new Icon_Group(R.drawable.group_3, false, 6));
        lisIcon.add(new Icon_Group(R.drawable.group_4, false, 4));
        icon_group = lisIcon.get(0).getIcon_background();
        adapter_icon = new AdapterIcon_Group(lisIcon, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recycle_icon.setHasFixedSize(true);
        recycle_icon.setNestedScrollingEnabled(false);
        recycle_icon.setLayoutManager(layoutManager);
        recycle_icon.setItemAnimator(new DefaultItemAnimator());
        recycle_icon.setAdapter(adapter_icon);
        adapter_icon.notifyDataSetChanged();

        adapter_icon.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {
                for (int i = 0; i < lisIcon.size(); i++) {
                    if (i == position) {
                        lisIcon.get(i).setCheck(true);
                    } else lisIcon.get(i).setCheck(false);
                }
                icon_group = lisIcon.get(position).getIcon_background();
                adapter_icon.notifyDataSetChanged();

            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });

    }

    AdapterTime_Group adapter_time;
    @BindView(R.id.recycle_time_addgroup)
    RecyclerView recycle_time_addgroup;

    private void initTimeGroup() {
        lisTime = new ArrayList<>();
        lisTime.add(new Time_Group("Cả ngày", true, "00:00:00", "23:59:59"));
        lisTime.add(new Time_Group("Ban ngày (08:00 - 17:00)", false, "08:00:00", "17:00:00"));
        lisTime.add(new Time_Group("Ban ngày 1 (09:00 - 18:00)", false, "09:00:00", "18:00:00"));
        lisTime.add(new Time_Group("Ban ngày 2 (07:30 - 16:00)", false, "07:30:00", "16:30:00"));
        lisTime.add(new Time_Group("Buổi tối (17:01 - 07:59)", false, "17:01:00", "07:59:00"));
        lisTime.add(new Time_Group("Buổi tối 1 (18:01 - 08:59)", false, "18:01:00", "08:59:00"));
        lisTime.add(new Time_Group("Buổi tối 2 (16:31 - 07:29)", false, "16:31:00", "07:29:00"));

        adapter_time = new AdapterTime_Group(lisTime, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_time_addgroup.setHasFixedSize(true);
        recycle_time_addgroup.setNestedScrollingEnabled(false);
        recycle_time_addgroup.setLayoutManager(mLayoutManager);
        recycle_time_addgroup.setItemAnimator(new DefaultItemAnimator());
        recycle_time_addgroup.setAdapter(adapter_time);
        adapter_time.notifyDataSetChanged();

        adapter_time.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {
                for (int i = 0; i < lisTime.size(); i++) {
                    if (i == position) {
                        lisTime.get(i).setCheck(true);
                        from_time = lisTime.get(position).getFrom_time();
                        to_time = lisTime.get(position).getTo_time();
                    } else lisTime.get(i).setCheck(false);
                }
                //content_id = lisItem.get(position).getContent_id();
                adapter_time.notifyDataSetChanged();

            }

            @Override
            public void OnLongItemClickListener(int position) {
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
      /*  ed_add_phone_addgroup.setText("");
        ed_name_addgroup.setText("");*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.list_ct_add_group.size()>0){
            ed_add_phone_addgroup.setText(PhoneNumber.add_multil_phone(MyApplication.list_ct_add_group));
        }else {
            ed_add_phone_addgroup.setText("");
        }
       /* objInfo = realm.where(Info_User.class).findFirst();
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();*/
        if (isLogin) {
            presenterAddGroup.getConllection(sesionID, msisdn);
        }
        /*if (ed_add_phone_addgroup.getText().toString().length() > 0)
            txt_delete_phone.setVisibility(View.VISIBLE);
        else txt_delete_phone.setVisibility(View.GONE);*/
    }

    private void initCollection() {
        lisItem = listConllection;
        adapter = new AdapterCollectionGroup(lisItem, this);
        mLayoutManager = new GridLayoutManager(this, 1);
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
        txt_delete_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_add_phone_addgroup.setText("");
                txt_delete_phone.setVisibility(View.GONE);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_add_contact_addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideSoftKeyboard(FragmentAddGroup.this);
                get_contact();
            }
        });
        btn_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (!(ed_name_addgroup.getText().toString().length() > 0)) {
                    show_notification("Lỗi", "Bạn chưa nhập vào tên cho nhóm");
                } else if (!(ed_add_phone_addgroup.getText().toString().length() > 0)) {
                    show_notification("Lỗi", "Bạn chưa có số điện thoại nào trong nhóm");
                } else if (!(content_id.length() > 0)) {
                    show_notification("Lỗi", "Bạn chưa chọn bài hát cho nhóm");
                } else if (icon_group == 0) {
                    show_notification("Lỗi", "Bạn chưa chọn hình đại diện cho nhóm");
                } else {
                    group_name = ed_name_addgroup.getText().toString();
                    iNumber = CustomUtils.getNameGroup(lisName);
                    if (iNumber >= 0) {
                        group_name_server = lisName.get(iNumber).getsNameServer();
                    }
                    if (!(ed_add_phone_addgroup.getText().toString().length() < 10)){
                        if (!isClick){
                            isClick= true;
                            presenterAddGroup.addGroup(sesionID, msisdn, group_name_server, ed_add_phone_addgroup.getText().toString());
                        }
                    }

                    else
                        show_notification("Lỗi", "Số điện thoại không đúng định dạng");
                }
            }
        });
        ed_add_phone_addgroup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // txt_delete_phone.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void show_add_profile_buygroup(List<String> list) {
        hideDialogLoading();
        if (list.size() > 0) {
            // FragmentGroups.add_Group();
            finish();
        }
    }

    @Override
    public void showaddGroups(List<String> list) {
        isClick= false;
        hideDialogLoading();
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                if (list.get(2).length() > 0) {
                    Toast.makeText(this, "Thêm nhóm thành công", Toast.LENGTH_SHORT).show();
                    GroupName objGroupname = new GroupName();
                    objGroupname.setsNameServer(group_name_server);
                    objGroupname.setsNameLocal(group_name);
                    objGroupname.setId_group(list.get(2));
                    objGroupname.setBackground(icon_group);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(objGroupname);
                    realm.commitTransaction();
                    caller_id = list.get(2);
                    presenterAddGroup.add_profile(sesionID, msisdn, content_id, caller_type, caller_id, from_time, to_time);
                    /*FragmentManager fm = getActivity().getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    }*/
                } else{
                    hideDialogLoading();
                    show_notification("Lỗi", list.get(1));
                }
            } else {
                hideDialogLoading();
                if (list.get(0).equals("117")){
                    show_notification("Lỗi",
                            "Tạo nhóm không thành công do số điện thoại đã tồn tại trong nhóm khác");
                }else  if (list.get(0).equals("104")){
                    show_notification("Lỗi",
                            "Tạo nhóm không thành công do số điện thoại đã tồn tại trong nhóm khác");
                }else {
                    show_notification("Lỗi", list.get(1));
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
        hideDialogLoading();
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
            }
            adapter.notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();
    }

    public void get_contact() {
    /*    MyApplication.datasvina = new ArrayList<PhoneContactModel>();
        MyApplication.datas = new ArrayList<PhoneContactModel>();
        MyApplication.listGitSongs = new ArrayList<PhoneContactModel>();
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("option", Config.ADD_GROUP);
        editor.commit();
        if (!ActivityContacts.getInstance().isAdded())
            FragmentUtil.addFragment(getActivity(), ActivityContacts.getInstance(), true);*/
        MyApplication.list_ct_add_group.clear();
        startActivity(new Intent(FragmentAddGroup.this, ActivityListContact_Get_Add_Group.class));
    }


}
