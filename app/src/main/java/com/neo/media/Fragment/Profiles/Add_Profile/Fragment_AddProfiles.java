package com.neo.media.Fragment.Profiles.Add_Profile;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterCollectionGroup;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Config.Config;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.Collection.ConllectionInteface;
import com.neo.media.Fragment.Profiles.FragmentProfiles;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.Model.Ringtunes;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.ActivityContacts;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.MainNavigationActivity.listGitSongs;
import static com.neo.media.View.ActivityContacts.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by QQ on 7/26/2017.
 */

public class Fragment_AddProfiles extends BaseFragment implements Add_Profile_Inteface.View, ConllectionInteface.View {
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
    @BindView(R.id.spn_type_setup)
    Spinner spn_type_setup_profile;
    @BindView(R.id.spn_time_setup)
    Spinner spn_time_setup_profile;
    @BindView(R.id.recycle_colletion_addprofile)
    RecyclerView recycleAlbum;
    @BindView(R.id.id_linner_add_contact)
    RelativeLayout linner_add_contact;
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
    @BindView(R.id.img_add_contact_dialog)
    ImageView btn_getcontact;
    String caller_type;
    String from_time, to_time;
    String caller_id = "";
    String content_id;
    String setup_profle = "0";
    String setup_time = "0";
    @BindView(R.id.spn_addprofile_buy_group)
    Spinner spn_addprofile_buy_group;
    @BindView(R.id.linner_add_buy_cli)
    LinearLayout linner_add_buy_cli;
    List<GroupName> lisNameGroup;
    Realm realm;
    boolean isUpdate;
    @BindView(R.id.txt_title_add_profiles)
    TextView txt_title_add_profiles;
    @BindView(R.id.img_back_addprofile)
    ImageView img_back_addprofile;
    SharedPreferences pre;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_profiles, container, false);
        ButterKnife.bind(this, view);

        lisNameGroup = new ArrayList<>();
        realm = RealmController.with(this).getRealm();
        ed_cli_add_profile = (EditText) view.findViewById(R.id.ed_add_phone_dialog);
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        ed_cli_add_profile.setText("");
    }

    private void initSpinnerGroup() {
        List<String> lisName = new ArrayList<>();
        lisNameGroup.add(0, new GroupName("obj", "Chọn một nhóm", "abc"));
        if (lisNameGroup.size() > 0) {
            for (int i = 0; i < lisNameGroup.size(); i++) {
                if (lisNameGroup.get(i).getsNameLocal() != null && lisNameGroup.get(i).getsNameLocal().length() > 0)
                    lisName.add(lisNameGroup.get(i).getsNameLocal());
                else lisName.add(lisNameGroup.get(i).getsNameServer());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lisName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_addprofile_buy_group.setAdapter(adapter);
        spn_addprofile_buy_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    caller_id = lisNameGroup.get(position).getId_group();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initEvent() {
        img_back_addprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.popBackStack(getActivity());
            }
        });
        spn_type_setup_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setup_profle = "0";
                    caller_type = "ALL";
                    caller_id = "";
                    linner_add_contact.setVisibility(View.GONE);
                }
                if (position == 1) {
                    setup_profle = "1";
                    linner_add_contact.setVisibility(View.VISIBLE);
                    linner_add_buy_cli.setVisibility(View.VISIBLE);
                    caller_type = "CLI";

                }
                if (position == 2) {
                    initSpinnerGroup();
                    setup_profle = "2";
                    caller_type = "GROUP";
                    linner_add_contact.setVisibility(View.VISIBLE);
                    linner_add_buy_cli.setVisibility(View.GONE);
                    spn_addprofile_buy_group.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
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

        btn_getcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForContactPermission();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (caller_type.equals("CLI"))
                    caller_id = PhoneNumber.convertTo84PhoneNunber(ed_cli_add_profile.getText().toString());
                if (caller_id == null) {
                    Toast.makeText(getContext(), "Bạn hãy chọn kiểu cài đặt", Toast.LENGTH_SHORT).show();
                } else if (content_id == null) {
                    Toast.makeText(getContext(), "Bạn hãy chọn bài hát", Toast.LENGTH_SHORT).show();
                } else {
                    presenterConllection.add_profile(sesionID, msisdn, content_id, caller_type, caller_id,
                            from_time, to_time);
                }

               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }*/
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listGitSongs = new ArrayList<PhoneContactModel>();
        listConllection = new ArrayList<>();

        //isUpdate = getArguments().getBoolean("isUpdate", false);
        setup_profle = "0";
        setup_time = "0";
    }

    @Override
    public void onResume() {
        super.onResume();
        MainNavigationActivity.appbar.setVisibility(View.GONE);
        spn_type_setup_profile.setSelection(Integer.parseInt(setup_profle));

        spn_time_setup_profile.setSelection(Integer.parseInt(setup_time));

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

    private void initSpinner() {
        lisGroup = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, spn_stype_setup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type_setup_profile.setAdapter(adapter);

        ArrayAdapter<String> adapter_time = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, spn_time_setup);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_time_setup_profile.setAdapter(adapter_time);
    }

    private void init() {
        adapterCollection = new AdapterCollectionGroup(listConllection, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
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
                FragmentProfiles.get_profile();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            } else if (list.get(0).equals("117")) {
                Toast.makeText(getContext(), "Số điện thoại đã được cài đặt" + list.get(1), Toast.LENGTH_SHORT).show();
            } else if (list.get(0).equals("135")) {
                Toast.makeText(getContext(), "Luật phát đã bị trùng với các cài đặt trước", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
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
       /* if (listItems != null && listItems.size() > 0) {
            listConllection.clear();
            listConllection.addAll(listItems);
            adapterCollection.notifyDataSetChanged();
        }*/
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGroups(GROUPS groups) {
        if (groups != null) {
            lisGroup.addAll(groups.getGroup());
            List<GroupName> lis = realm.where(GroupName.class).findAll();
            for (int i = 0; i < lis.size(); i++) {
                for (int j = 0; j < lisGroup.size(); j++) {
                    if (lis.get(i).getsNameServer().equals(lisGroup.get(j).getName())) {
                        if (lis.get(i).getsNameLocal() != null)
                            lisNameGroup.add(new GroupName(lis.get(i).getsNameServer(), lis.get(i).getsNameLocal(), lisGroup.get(j).getId()));
                        else
                            lisNameGroup.add(new GroupName(lis.get(i).getsNameServer(), lisGroup.get(j).getName(), lisGroup.get(j).getId()));
                    }
                }
            }
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
                adapterCollection.notifyDataSetChanged();
                //presenterConllection.getSongsSame(listSongs.get(listSongs.size()-1).getSinger_id(), "" + page, "" + index);
            }
        }
    }

    public void get_contact() {
        MainNavigationActivity.datasvina = new ArrayList<PhoneContactModel>();
        MainNavigationActivity.datas = new ArrayList<PhoneContactModel>();
        listGitSongs = new ArrayList<PhoneContactModel>();
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("option", Config.ADD_PROFILES);
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
