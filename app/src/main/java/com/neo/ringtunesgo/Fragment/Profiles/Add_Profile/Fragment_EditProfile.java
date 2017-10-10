package com.neo.ringtunesgo.Fragment.Profiles.Add_Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.ringtunesgo.Adapter.AdapterCollectionGroup;
import com.neo.ringtunesgo.CRBTModel.GROUPS;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.CRBTModel.PROFILE;
import com.neo.ringtunesgo.Config.Constant;
import com.neo.ringtunesgo.Fragment.Profiles.FragmentProfiles;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.MyApplication;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
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

/**
 * Created by QQ on 10/1/2017.
 */

public class Fragment_EditProfile extends BaseFragment implements Add_Profile_Inteface.View {
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
    @BindView(R.id.spn_time_setup_edit)
    Spinner spn_time_setup_profile;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profiles, container, false);
        ButterKnife.bind(this, view);
        objProfile = new PROFILE();
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
    }

    private void initData() {
        objProfile = MyApplication.profile_bundle;
        if (objProfile != null) {
            caller_type = objProfile.getCaller_type();
            if (caller_type.equals("DEFAULT")) {
                name_profile = "Tất cả số gọi đến";
            } else if (caller_type.equals("CLI")) {
                name_profile = "Số ĐT: " + objProfile.getCaller_id();
            } else if (caller_type.equals("GROUP")) {
                if (objProfile.getCaller_name() != null)
                    name_profile = "Nhóm: " + objProfile.getCaller_name();
                else
                    name_profile = "Nhóm: " + objProfile.getCaller_id();
            }
            if (objProfile.getTimeBase() != null)
                setup_time = CustomUtils.getTime_Profile(objProfile.getTimeBase().getTime_category_4().getFrom_time());
            else setup_time = "0";
            content_id = objProfile.getContent_id();
            profile_id = objProfile.getProfile_id();
            caller_id = objProfile.getCaller_id();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MainNavigationActivity.appbar.setVisibility(View.GONE);
        if (setup_time != null && setup_time.length() > 0)
            spn_time_setup_profile.setSelection(Integer.parseInt(setup_time));
        txt_name_edit_profile.setText(name_profile);
    }

    private void initSpinner() {

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, spn_time_setup);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_time_setup_profile.setAdapter(adapter_time);
    }


    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.popBackStack(getActivity());
            }
        });
        spn_time_setup_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });
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
        adapterCollection = new AdapterCollectionGroup(listConllection, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycleConllection.setHasFixedSize(true);
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
                FragmentProfiles.get_profile();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            } else {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
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
