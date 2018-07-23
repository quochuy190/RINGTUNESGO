package com.neo.media.Fragment.Profiles.Add_Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterCollectionGroup;
import com.neo.media.Adapter.AdapterTime_Group;
import com.neo.media.Adapter.Adapter_ListGroup_Profile;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Config.Constant;
import com.neo.media.Contact.ViewActivity.ActivityListContact;
import com.neo.media.Fragment.CaNhan.Collection.ConllectionInteface;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Time_Group;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.KeyboardUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

import static com.neo.media.MyApplication.objPhone;

/**
 * Created by QQ on 7/26/2017.
 */

public class Fragment_AddProfiles extends BaseActivity implements Add_Profile_Inteface.View, ConllectionInteface.View {
    public static Fragment_AddProfiles fragment_addProfiles;

    public static Fragment_AddProfiles getInstance() {
        if (fragment_addProfiles == null) {
            synchronized (Fragment_AddProfiles.class) {
                if (fragment_addProfiles == null)
                    fragment_addProfiles = new Fragment_AddProfiles();
            }
        }
        return fragment_addProfiles;
    }

    final String[] spn_stype_setup = {"Ringtunes cho tất cả", "Ringtunes cho một số", "Ringtunes cho một nhóm"};
    final String[] spn_time_setup = {"Cả ngày", "Ban ngày(08:00 - 17:00)", "Ban ngày 1(09:00 - 18:00)", "Ban ngày 2(07:30 - 16:30)",
            "Buổi tối(17:00 - 8:00)", "Buổi tối 1(18:00 - 09:00)", "Buổi tối 2(16:30 - 07:30)"};
    /* @BindView(R.id.spn_type_setup)
     Spinner spn_type_setup_profile;
     @BindView(R.id.spn_time_setup)
     Spinner spn_time_setup_profile;*/
    @BindView(R.id.recycle_colletion_addprofile)
    RecyclerView recycleAlbum;
    /*    @BindView(R.id.id_linner_add_contact)
        RelativeLayout linner_add_contact;*/
    AdapterCollectionGroup adapterCollection;
    List<Item> listConllection;
    RecyclerView.LayoutManager mLayoutManager;
    PresenterAddProfiles presenterConllection;
    String page = "1";
    String index = "20";
    @BindView(R.id.btn_ok_add_profile)
    Button button_ok;
    List<GROUP> lisGroup;
    String sesionID;
    String msisdn;
    public static EditText ed_cli_add_profile;
    @BindView(R.id.img_add_contact_addprofile)
    ImageView btn_getcontact;
    String caller_type = "ALL";
    String from_time = "00:00:00", to_time = "23:59:59";
    String caller_id = "";
    String content_id;
    String setup_profle = "0";
    String setup_time = "0";
    /*    @BindView(R.id.spn_addprofile_buy_group)
        Spinner spn_addprofile_buy_group;
        @BindView(R.id.linner_add_buy_cli)
        LinearLayout linner_add_buy_cli;*/
    List<GroupName> lisNameGroup;
    Realm realm;
    boolean isUpdate;
    @BindView(R.id.txt_title_add_profiles)
    TextView txt_title_add_profiles;
    @BindView(R.id.img_back_addprofile)
    ImageView img_back_addprofile;
    SharedPreferences pre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objPhone = null;
        listConllection = new ArrayList<>();
        setup_profle = "0";
        setup_time = "0";
        ButterKnife.bind(this);
        lisNameGroup = new ArrayList<>();
        realm = RealmController.with(this).getRealm();
        ed_cli_add_profile = (EditText) findViewById(R.id.ed_add_phone_addprofile);
        ed_cli_add_profile.setText("");
       /* pre = getSharedPreferences("data", MODE_PRIVATE);
        //id_Singer= pre.getString("isSinger", "");
        sesionID = pre.getString("sessionID", "");
        msisdn = pre.getString("msisdn", "");*/
        sesionID = Prefs.with(this).getString("sessionID", "");
        msisdn = Prefs.with(this).getString("msisdn", "");
        init();
        presenterConllection = new PresenterAddProfiles(this);
        presenterConllection.getAllGroup(sesionID, msisdn, "All");
        presenterConllection.getConllection(sesionID, msisdn);
        initSpinner();
        initEvent();
        initTimeGroup();
    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_add_profiles;
    }

    @Override
    public void initData() {

    }
  /*  @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_profiles, container, false);
        ButterKnife.bind(this, view);

        lisNameGroup = new ArrayList<>();
        realm = RealmController.with(this).getRealm();
        ed_cli_add_profile = (EditText) view.findViewById(R.id.ed_add_phone_addprofile);
        ed_cli_add_profile.setText("");
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        //id_Singer= pre.getString("isSinger", "");
        sesionID = pre.getString("sessionID", "");
        msisdn = pre.getString("msisdn", "");
        init();
        presenterConllection = new PresenterAddProfiles(this);

        presenterConllection.getAllGroup(sesionID, msisdn, "All");
        presenterConllection.getConllection(sesionID, msisdn);
        initSpinner();
        initEvent();
        initTimeGroup();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }*/

    @Override
    public void onPause() {
        super.onPause();
        ed_cli_add_profile.setText("");
    }

    AdapterTime_Group adapter_time;
    @BindView(R.id.recycle_time_add_profile)
    RecyclerView recycle_time_addgroup;
    List<Time_Group> lisTime;

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

    private void initEvent() {
        img_back_addprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_getcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.sOption_getPhone = "GIFT";
                objPhone = null;
                KeyboardUtil.hideSoftKeyboard(Fragment_AddProfiles.this);
                startActivity(new Intent(Fragment_AddProfiles.this, ActivityListContact.class));
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (caller_type.equals("CLI"))
                    caller_id = PhoneNumber.convertTo84PhoneNunber(ed_cli_add_profile.getText().toString());
                if (caller_id == null) {
                    show_notification("Thông báo", "Hãy chọn kiểu cài đặt");
                    // Toast.makeText(getContext(), "Bạn hãy chọn kiểu cài đặt", Toast.LENGTH_SHORT).show();
                } else if (content_id == null) {
                    show_notification("Thông báo", "Hãy chọn bài hát");
                    //Toast.makeText(getContext(), "Bạn hãy chọn bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    presenterConllection.add_profile(sesionID, msisdn, content_id, caller_type, caller_id,
                            from_time, to_time);
                }


            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (objPhone != null) {
            ed_cli_add_profile.setText(objPhone.getPhoneNumber());
        } else {
            ed_cli_add_profile.setText("");
            ed_cli_add_profile.setHint("- Nhập vào số đt hoặc lấy từ danh bạ");
        }
        //   MainNavigationActivity.appbar.setVisibility(View.GONE);
      /*  spn_type_setup_profile.setSelection(Integer.parseInt(setup_profle));

        spn_time_setup_profile.setSelection(Integer.parseInt(setup_time));
*/
      /*  if (isUpdate){
            if(setup_profle.equals("1")){
                if (caller_id.length()>0){
                    String phone_addprofile = caller_id;
                    Fragment_AddProfiles.ed_cli_add_profile.setText(phone_addprofile);
                }
            }else if (setup_profle.equals("2")){
                up
            }

        }*/

    }

    Adapter_ListGroup_Profile adapter_group;
    @BindView(R.id.recycle_group_add_profile)
    RecyclerView recycle_profile;
    @BindView(R.id.linner_phone_add_profile)
    RelativeLayout linner_phone_add_profile;

    private void initSpinner() {
        lisGroup = new ArrayList<>();

        adapter_group = new Adapter_ListGroup_Profile(lisNameGroup, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_profile.setHasFixedSize(true);
        recycle_profile.setNestedScrollingEnabled(false);
        recycle_profile.setLayoutManager(mLayoutManager);
        recycle_profile.setItemAnimator(new DefaultItemAnimator());
        recycle_profile.setAdapter(adapter_group);
        adapter_group.notifyDataSetChanged();

        adapter_group.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {
                for (int i = 0; i < lisNameGroup.size(); i++) {
                    if (i == position) {
                        lisNameGroup.get(i).setCheck(true);
                      /*  from_time = lisTime.get(position).getFrom_time();
                        to_time = lisTime.get(position).getTo_time();*/
                    } else lisNameGroup.get(i).setCheck(false);
                }
                if (position == (lisNameGroup.size() - 1)) {
                    caller_type = "CLI";
                    caller_id = ed_cli_add_profile.getText().toString();
                    linner_phone_add_profile.setVisibility(View.VISIBLE);
                } else if (position == 0) {
                    // setup_profle = "0";
                    caller_type = "ALL";
                    caller_id = "";
                    linner_phone_add_profile.setVisibility(View.GONE);
                } else {
                    // initSpinnerGroup();
                    //setup_profle = "2";
                    caller_type = "GROUP";
                    caller_id = lisNameGroup.get(position).getId_group();
                    linner_phone_add_profile.setVisibility(View.GONE);
                    // linner_add_contact.setVisibility(View.VISIBLE);
                    // linner_add_buy_cli.setVisibility(View.GONE);
                    // spn_addprofile_buy_group.setVisibility(View.VISIBLE);
                }
                //content_id = lisItem.get(position).getContent_id();
                adapter_group.notifyDataSetChanged();
            }

            @Override
            public void OnLongItemClickListener(int position) {
            }
        });

    }

    private void init() {
        adapterCollection = new AdapterCollectionGroup(listConllection, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycleAlbum.setHasFixedSize(true);
        recycleAlbum.setNestedScrollingEnabled(false);
        recycleAlbum.setLayoutManager(mLayoutManager);
        recycleAlbum.setItemAnimator(new DefaultItemAnimator());
        recycleAlbum.setAdapter(adapterCollection);
        adapterCollection.notifyDataSetChanged();

        adapterCollection.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {

                for (int i = 0; i < listConllection.size(); i++) {
                    if (i == position) {
                        listConllection.get(i).setIs_check(true);
                    } else listConllection.get(i).setIs_check(false);
                }
                content_id = listConllection.get(position).getContent_id();
                adapterCollection.notifyDataSetChanged();

            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showLisSongsSame(List<Ringtunes> lisRing) {

    }

    @Override
    public void showaddProfile(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Thêm luật phát nhạc thành công")
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                finish();
                            }
                        }).show();
                // FragmentProfiles.get_profile();

            } else if (list.get(0).equals("117")) {
                show_notification("Thông báo", "Số điện thoại đã được cài đặt");
                //Toast.makeText(this, "Số điện thoại đã được cài đặt" + list.get(1), Toast.LENGTH_SHORT).show();
            } else if (list.get(0).equals("135")) {
                show_notification("Thông báo", "Luật phát đã bị trùng với các cài đặt trước");
                //  Toast.makeText(this, "Luật phát đã bị trùng với các cài đặt trước", Toast.LENGTH_SHORT).show();
            } else
                show_notification("Thông báo", "Hệ thống bận, mời thử lại sau");
            // Toast.makeText(this, "Hệ thống bận, mời thử lại sau", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showConllection(List<Item> listItems) {
        listConllection.clear();
        String id_songs = "";
        if (listItems != null && listItems.size() > 0) {
            listConllection.addAll(listItems);
            for (int i = 0; i < listConllection.size(); i++) {
                if (i < listConllection.size() - 1)
                    id_songs = id_songs + listConllection.get(i).getContent_id() + ",";
                else id_songs = id_songs + listConllection.get(i).getContent_id();
            }
            presenterConllection.get_info_songs_collection(id_songs, Constant.USER_ID);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGroups(GROUPS groups) {
        if (groups != null) {
            if (!groups.getTotal().equals("0")) {
                lisGroup.addAll(groups.getGroup());
                List<GroupName> lis = realm.where(GroupName.class).findAll();
                for (int i = 0; i < lis.size(); i++) {
                    for (int j = 0; j < lisGroup.size(); j++) {
                        if (lis.get(i).getsNameServer().equals(lisGroup.get(j).getName())) {
                            if (lis.get(i).getsNameLocal() != null)
                                lisNameGroup.add(new GroupName(lis.get(i).getsNameServer(), lis.get(i).getsNameLocal(), lisGroup.get(j).getId(), R.drawable.spr_green));
                            else
                                lisNameGroup.add(new GroupName(lis.get(i).getsNameServer(), lisGroup.get(j).getName(), lisGroup.get(j).getId(), R.drawable.spr_green));
                        }
                    }
                }
                lisNameGroup.add(0, new GroupName("obj", "Tất cả số gọi đến", null, R.drawable.spr_cam, true));
                lisNameGroup.add(new GroupName("obj", "Một số gọi đến", null, R.drawable.spr_cam));
                adapter_group.notifyDataSetChanged();
            } else {
                lisNameGroup.add(0, new GroupName("obj", "Tất cả số gọi đến", null, R.drawable.spr_cam, true));
                lisNameGroup.add(new GroupName("obj", "Một số gọi đến", null, R.drawable.spr_cam));
                adapter_group.notifyDataSetChanged();
            }
        } else {
            lisNameGroup.add(0, new GroupName("obj", "Tất cả số gọi đến", null, R.drawable.spr_cam, true));
            lisNameGroup.add(new GroupName("obj", "Một số gọi đến", null, R.drawable.spr_cam));
            //lisNameGroup.get(0).setCheck(true);
            adapter_group.notifyDataSetChanged();
        }
    }

    @Override
    public void show_update_profile(List<String> string) {

    }

    @Override
    public void showDeleteSuccessfull(List<String> list) {

    }

    @Override
    public void show_list_songs_collection(List<Ringtunes> listSongs) {
        if (listSongs.size() > 0) {
            for (int i = 0; i < listSongs.size(); i++) {
                for (int j = 0; j < listConllection.size(); j++) {
                    if (listSongs.get(i).getId().equals(listConllection.get(j).getContent_id())) {
                        listConllection.get(j).setImage_url(listSongs.get(i).getImage_file());
                        listConllection.get(j).setProduct_name(listSongs.get(i).getProduct_name());
                        listConllection.get(j).setSinger_id(listSongs.get(i).getSinger_id());
                        listConllection.get(j).setPath(listSongs.get(i).getPath());
                    }
                }
            }
            int sd = listConllection.size();
            adapterCollection.notifyDataSetChanged();
        }
    }

    public void get_contact() {

      /*  SharedPreferences.Editor editor = pre.edit();
        editor.putString("option", Config.ADD_PROFILES);
        editor.commit();*/

      /*  if (!ActivityContacts.getInstance().isAdded())
            FragmentUtil.addFragment(getActivity(), ActivityContacts.getInstance(), true);*/
    }
}
