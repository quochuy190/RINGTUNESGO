package com.neo.media.Fragment.Profiles.Add_Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterCollectionGroup;
import com.neo.media.Adapter.AdapterTime_Group;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Config.Constant;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Time_Group;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

/**
 * Created by QQ on 10/1/2017.
 */

public class Fragment_EditProfile extends BaseActivity implements Add_Profile_Inteface.View {
    public static Fragment_EditProfile fragment_addProfiles;

    public static Fragment_EditProfile getInstance() {
        if (fragment_addProfiles == null) {
            synchronized (Fragment_EditProfile.class) {
                if (fragment_addProfiles == null)
                    fragment_addProfiles = new Fragment_EditProfile();
            }
        }
        return fragment_addProfiles;
    }

    PresenterAddProfiles presenterAddProfiles;
    Realm realm;
    String sesionID;
    String msisdn;
    List<Item> listConllection;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycle_colletion_edit_profile)
    RecyclerView recycleConllection;
    @BindView(R.id.txt_name_edit_profile)
    TextView txt_name_edit_profile;
    AdapterCollectionGroup adapterCollection;
    String caller_type;
    String from_time, to_time;
    String caller_id = "";
    String content_id;
    String setup_profle = "0";
    String setup_time = "0";
 /*   @BindView(R.id.spn_time_setup_edit)
    Spinner spn_time_setup_profile;*/
    String profile_id = "";
    String name_profile = "";
    PROFILE objProfile;
    @BindView(R.id.btn_update_profile)
    Button btn_update;
    final String[] spn_time_setup = {"Cả ngày", "Ban ngày(08:00 - 17:00)", "Ban ngày 1(09:00 - 18:00)", "Ban ngày 2(07:30 - 16:30)",
            "Buổi tối(17:00 - 8:00)", "Buổi tối 1(18:00 - 09:00)", "Buổi tối 2(16:30 - 07:30)"};
    @BindView(R.id.img_back_detailsong)
    ImageView img_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        objProfile = new PROFILE();
        initTimeGroup();
        initDatas();
        realm = RealmController.with(this).getRealm();
        presenterAddProfiles = new PresenterAddProfiles(this);
        SharedPreferences pre = getSharedPreferences("data", MODE_PRIVATE);
        //id_Singer= pre.getString("isSinger", "");
     /*   sesionID = pre.getString("sessionID", "");
        msisdn = pre.getString("msisdn", "");*/
        sesionID = Prefs.with(this).getString("sessionID", "");
        msisdn = Prefs.with(this).getString("msisdn", "");
        presenterAddProfiles.getConllection(sesionID, msisdn);

        init();
        initSpinner();
        initEvent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.fragment_edit_profiles;
    }

    @Override
    public void initData() {

    }

 /*   @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profiles, container, false);
        ButterKnife.bind(this, view);
        objProfile = new PROFILE();
        initTimeGroup();
        initData();
        realm = RealmController.with(this).getRealm();
        presenterAddProfiles = new PresenterAddProfiles(this);
        SharedPreferences pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        //id_Singer= pre.getString("isSinger", "");
        sesionID = pre.getString("sessionID", "");
        msisdn = pre.getString("msisdn", "");
        presenterAddProfiles.getConllection(sesionID, msisdn);

        init();
        initSpinner();
        initEvent();

        return view;
    }*/

    private void initDatas() {
        objProfile = MyApplication.profile_bundle;
        if (objProfile != null) {
            caller_type = objProfile.getCaller_type();
            if (caller_type.equals("DEFAULT")) {
                name_profile = "Tất cả số gọi đến";
            } else if (caller_type.equals("CLI")) {
                name_profile = "Một số gọi đến: " + objProfile.getCaller_id();
            } else if (caller_type.equals("GROUP")) {
                if (objProfile.getCaller_name() != null)
                    name_profile = "Nhóm gọi đến: " + objProfile.getCaller_name();
                else
                    name_profile = "Nhóm gọi dến: " + objProfile.getCaller_id();
            }
            if (objProfile.getTimeBase() != null)
                setup_time = CustomUtils.getTime_Profile(objProfile.getTimeBase().getTime_category_4().getFrom_time());
            else setup_time = "0";
            if (objProfile.getTimeBase()!= null){
                for (int i = 0;i<lisTime.size();i++){
                    if (lisTime.get(i).getFrom_time().equals(objProfile.getTimeBase().getTime_category_4().getFrom_time())){
                        lisTime.get(i).setCheck(true);
                    }
                }
                from_time = objProfile.getTimeBase().getTime_category_4().getFrom_time();
                to_time = objProfile.getTimeBase().getTime_category_4().getTo_time();
            }
            adapter_time.notifyDataSetChanged();
            content_id = objProfile.getContent_id();
            profile_id = objProfile.getProfile_id();
            caller_id = objProfile.getCaller_id();
        }

    }

    AdapterTime_Group adapter_time;
    @BindView(R.id.recycle_time_edit_profile)
    RecyclerView recycle_time_addgroup;
    List<Time_Group> lisTime;

    private void initTimeGroup() {
        lisTime = new ArrayList<>();
        lisTime.add(new Time_Group("Cả ngày", false, "00:00:00", "23:59:59"));
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
    public void onResume() {
        super.onResume();
        //MainNavigationActivity.appbar.setVisibility(View.GONE);
       /* if (setup_time != null && setup_time.length() > 0)
            spn_time_setup_profile.setSelection(Integer.parseInt(setup_time));*/
        txt_name_edit_profile.setText(name_profile);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void initSpinner() {

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spn_time_setup);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spn_time_setup_profile.setAdapter(adapter_time);
    }


    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        /*spn_time_setup_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        from_time = "00:00:00";
                        to_time = "23:59:59";
                        break;
                    case 1:
                        from_time = "08:00:00";
                        to_time = "17:00:00";
                        break;
                    case 2:
                        from_time = "09:00:00";
                        to_time = "18:00:00";
                        break;
                    case 3:
                        from_time = "07:30:00";
                        to_time = "16:30:00";
                        break;
                    case 4:
                        from_time = "17:01:00";
                        to_time = "07:59:00";
                        break;
                    case 5:
                        from_time = "18:01:00";
                        to_time = "08:59:00";
                        break;
                    case 6:
                        from_time = "16:31:00";
                        to_time = "07:29:00";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterAddProfiles.updateProfile(sesionID, msisdn, profile_id, content_id, caller_type,
                        caller_id, from_time, to_time);
            }
        });
    }

    private void init() {
        listConllection = new ArrayList<>();
        adapterCollection = new AdapterCollectionGroup(listConllection, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycleConllection.setHasFixedSize(true);
        recycleConllection.setNestedScrollingEnabled(false);
        recycleConllection.setLayoutManager(mLayoutManager);
        recycleConllection.setItemAnimator(new DefaultItemAnimator());
        recycleConllection.setAdapter(adapterCollection);
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
    public void showaddProfile(List<String> list) {

    }

    @Override
    public void showConllection(List<Item> listItems) {
        listConllection.clear();
        String id_songs = "";
        if (listItems != null && listItems.size() > 0) {
            listConllection.addAll(listItems);
            for (int i = 0; i < listConllection.size(); i++) {
                if (listConllection.get(i).getContent_id().equals(content_id)) {
                    listConllection.get(i).setIs_check(true);
                }
                if (i < listConllection.size() - 1)
                    id_songs = id_songs + listConllection.get(i).getContent_id() + ",";
                else id_songs = id_songs + listConllection.get(i).getContent_id();
            }
            showDialogLoading();
            presenterAddProfiles.get_info_songs_collection(id_songs, Constant.USER_ID);

        }
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showGroups(GROUPS groups) {

    }

    @Override
    public void show_update_profile(List<String> string) {
        if (string.size() > 0) {
            if (string.get(0).equals("0")) {
                // FragmentProfiles.
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Sửa luật phát nhạc thành công")
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                finish();
                            }
                        }).show();

            } else {
                Toast.makeText(this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
               finish();
            }

        }

    }

    @Override
    public void show_list_songs_collection(List<Ringtunes> listSongs) {
        hideDialogLoading();
        if (listSongs.size()>0){
            for (int i = 0;i<listSongs.size();i++){
                for (int j = 0;j<listConllection.size();j++){
                    if (listSongs.get(i).getId().equals(listConllection.get(j).getContent_id())){
                        listConllection.get(j).setImage_url(listSongs.get(i).getImage_file());
                        listConllection.get(j).setProduct_name(listSongs.get(i).getProduct_name());
                        listConllection.get(j).setSinger_id(listSongs.get(i).getSinger_id());
                        listConllection.get(j).setPath(listSongs.get(i).getPath());
                    }
                }
                adapterCollection.notifyDataSetChanged();
                //presenterConllection.getSongsSame(listSongs.get(listSongs.size()-1).getSinger_id(), "" + page, "" + index);
            }
        }
    }
}
