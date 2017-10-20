package com.neo.media.View;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
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
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.neo.media.Adapter.ContactListPickerAdapter;
import com.neo.media.CRBTModel.CLI;
import com.neo.media.Config.Config;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.Fragment.Groups.AddGroup.FragmentAddGroup;
import com.neo.media.Fragment.Groups.GroupMember.FragmentGroupMember;
import com.neo.media.Fragment.Groups.GroupMember.PresenterGroupMember;
import com.neo.media.Fragment.Profiles.Add_Profile.Fragment_AddProfiles;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.PhoneNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.MainNavigationActivity.datas;
import static com.neo.media.MainNavigationActivity.datasvina;
import static com.neo.media.MainNavigationActivity.listGitSongs;


/**
 * Created by QQ on 7/17/2017.
 */

public class ActivityContacts extends BaseFragment {
    PresenterGroupMember presenterGroupMember;
    @BindView(R.id.btn_ok_contact)
    Button btn_ok_contact;
    public static ActivityContacts fragment;
    StickyListHeadersListView listContact;
    EditText editTextSearch;
    ContactListPickerAdapter adapter;
    Ringtunes ringtunes;
    public static String option = "";
    public static List<CLI> lisCLI;
    String sesionID;
    String msisdn;
    boolean is_check_contact;
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;
    SharedPreferences pre;
    Realm realm;
    InputMethodManager inputManager;
    public static int count = 0;

    private void changeListAndNotifyData() {
        adapter = (ContactListPickerAdapter) new ContactListPickerAdapter(datasvina,
                getActivity()).registerFilter(PhoneContactModel.class, "name");
        //set ignore case when searched
        adapter.setIgnoreCase(true);
        listContact.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public static ActivityContacts getInstance() {
        if (fragment == null) {
            synchronized (FragmentSongs.class) {
                if (fragment == null) {
                    fragment = new ActivityContacts();
                }
            }
        }
        return fragment;
    }

    private void init() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    datas = CustomUtils.getAllPhoneContacts(getContext());

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datasvina = new ArrayList<>();
        datas = new ArrayList<>();
        listGitSongs = new ArrayList<>();
        lisCLI = new ArrayList<>();
        count = 0;
        askForContactPermission();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainNavigationActivity.appbar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MyApplication.listCLI.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contacts, container, false);
        ButterKnife.bind(this, view);
        inputManager = (InputMethodManager) getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        getShare();
        realm = RealmController.with(this).getRealm();
        editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);
        listContact = (StickyListHeadersListView) view.findViewById(R.id.listContact);
        getData();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(editTextSearch.getText().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("abc", "" + position);
                PhoneContactModel bean = datasvina.get(position);
                bean.setChecked(!bean.isChecked());
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_contact);
                checkBox.setChecked(bean.isChecked());
                listGitSongs.add(bean);
                //mark
            }
        });
        initEvnet();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;

    }

    private void getShare() {
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        //id_Singer= pre.getString("isSinger", "");
        sesionID = pre.getString("sesionID", "");
        msisdn = pre.getString("msisdn", "");
        is_check_contact = pre.getBoolean("is_check_contact", false);
        lisCLI.addAll(MyApplication.listCLI);
        option = pre.getString("option", "");
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
                datas = CustomUtils.getAllPhoneContacts(getContext());
            }
        } else {
            datas = CustomUtils.getAllPhoneContacts(getContext());
        }
    }

    private void getData() {
        switch (option) {
            // thêm mới cài đặt ringtunes
            case Config.ADD_PROFILES:
                datasvina.addAll(datas);
                Collections.sort(datasvina, new Comparator<PhoneContactModel>() {
                    @Override
                    public int compare(PhoneContactModel sv1, PhoneContactModel sv2) {
                        return (sv1.getName().compareTo(sv2.getName()));

                    }
                });

                changeListAndNotifyData();
                break;
            // thêm số điện thoại vào nhóm
            case Config.GROUP_MEMBER:
                datasvina.addAll(datas);
                if (lisCLI.size()>0){
                    for (int i = 0; i < datasvina.size(); i++) {
                        for (int j = 0; j < lisCLI.size(); j++) {
                            if (datasvina.get(i).getPhoneNumber().equals(PhoneNumber.convertToVnPhoneNumber
                                    (lisCLI.get(j).getCaller_id()))) {
                                datasvina.get(i).setChecked(true);
                                datasvina.get(i).setNotChange(true);
                            }
                        }
                    }
                    count = lisCLI.size();
                }
                // sắp xếp theo thứ tự abc
                Collections.sort(datasvina, new Comparator<PhoneContactModel>() {
                    @Override
                    public int compare(PhoneContactModel sv1, PhoneContactModel sv2) {
                        return (sv1.getName().compareTo(sv2.getName()));

                    }
                });
                changeListAndNotifyData();
                break;
            // chọn số điện thoại để tặng ringtunes
            case Config.GIFT:
                for (int i = 0; i < datas.size(); i++) {
                    if (CustomUtils.checkPhoneVina(datas.get(i).getPhoneNumber())) {
                        datasvina.add(datas.get(i));
                    }
                }
                Collections.sort(datasvina, new Comparator<PhoneContactModel>() {
                    @Override
                    public int compare(PhoneContactModel sv1, PhoneContactModel sv2) {
                        return (sv1.getName().compareTo(sv2.getName()));
                        // Muốn đảo danh sách các bạn đối thành
                        //return (sv2.hoTen.compareTo(sv1.hoTen));

                    }
                });
                changeListAndNotifyData();
                break;
            case Config.ADD_GROUP:
                datasvina.addAll(datas);
                // sắp xếp theo thứ tự abc
                Collections.sort(datasvina, new Comparator<PhoneContactModel>() {
                    @Override
                    public int compare(PhoneContactModel sv1, PhoneContactModel sv2) {
                        return (sv1.getName().compareTo(sv2.getName()));

                    }
                });
                changeListAndNotifyData();
                break;

        }
    }

    private void initEvnet() {
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            switch (option) {
                                case Config.ADD_PROFILES:
                                    for (int j = 0; j < datasvina.size(); j++) {
                                        if (datasvina.get(j).isChecked()) {
                                            listGitSongs.add(datasvina.get(j));
                                        }
                                    }
                                    if (listGitSongs != null && listGitSongs.size() > 0) {
                                        String phone_addprofile = listGitSongs.get(0).getPhoneNumber();
                                        Fragment_AddProfiles.ed_cli_add_profile.setText(phone_addprofile);
                                    }

                                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().
                                            getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    if (fm.getBackStackEntryCount() > 0) {
                                        fm.popBackStack();
                                    }
                                    break;
                                case Config.GIFT:
                                    String phone = "";
                                    for (int j = 0; j < datasvina.size(); j++) {
                                        if (datasvina.get(j).isChecked()) {
                                            listGitSongs.add(datasvina.get(j));
                                        }
                                    }
                                    phone = phone + listGitSongs.get(0).getPhoneNumber();
                                    FragmentDetailBuySongs.ed_getphone.setText(phone);
                                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    FragmentManager fm_gift = getActivity().getSupportFragmentManager();
                                    if (fm_gift.getBackStackEntryCount() > 0) {
                                        fm_gift.popBackStack();
                                    }
                                    FragmentDetailBuySongs.dialog_getphone.show();
                                    break;
                                case Config.GROUP_MEMBER:
                                    List<PhoneContactModel> listPhone = new ArrayList<PhoneContactModel>();
                                    for (int j = 0; j < datasvina.size(); j++) {
                                        if (datasvina.get(j).isChecked()&&!datasvina.get(j).isNotChange()) {
                                            listPhone.add(datasvina.get(j));
                                        }
                                    }
                                    listGitSongs.addAll(listPhone);
                                    if (listGitSongs.size() > 0) {
                                        String phone_all = "";
                                        for (int i = 0; i < listGitSongs.size(); i++) {

                                            String temp = listGitSongs.get(i).getPhoneNumber();
                                            temp = PhoneNumber.convertTo84PhoneNunber(temp);
                                            if (!(i == listGitSongs.size() - 1)) {
                                                phone_all = phone_all + temp + ",";
                                            } else phone_all = phone_all + temp;

                                            //  FragmentGroupMember.add_cli_to_group(phone_all);
                                        }
                                        SharedPreferences.Editor editor = pre.edit();
                                        editor.putString("contack_group_member", phone_all);
                                        editor.commit();
                                        FragmentGroupMember.txt_delete_phone.setVisibility(View.VISIBLE);
                                        FragmentGroupMember.ed_getphone_group.setText(phone_all);
                                        FragmentGroupMember.dialog.show();
                                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                        FragmentManager fm_group_member = getActivity().getSupportFragmentManager();
                                        if (fm_group_member.getBackStackEntryCount() > 0) {
                                            fm_group_member.popBackStack();
                                        }
                                    } else
                                        Toast.makeText(getContext(), "Bạn chưa chọn số điện thoại nào", Toast.LENGTH_SHORT).show();
                                    break;
                                case Config.ADD_GROUP:
                                    List<PhoneContactModel> lisAddGroup = new ArrayList<PhoneContactModel>();
                                    for (int j = 0; j < datasvina.size(); j++) {
                                        if (datasvina.get(j).isChecked()) {
                                            lisAddGroup.add(datasvina.get(j));
                                        }
                                    }
                                    if (lisAddGroup.size() > 0) {
                                        listGitSongs.addAll(lisAddGroup);
                                        String sAddGroup = "";
                                        if (listGitSongs.size() > 0) {
                                            String phone_addprofile = listGitSongs.get(0).getPhoneNumber();
                                            for (int i = 0; i < listGitSongs.size(); i++) {
                                                sAddGroup = sAddGroup + listGitSongs.get(i).getPhoneNumber() + ",";
                                            }
                                        }
                                        FragmentAddGroup.ed_add_phone_addgroup.setText(sAddGroup);
                                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                        FragmentManager fm_group_member = getActivity().getSupportFragmentManager();
                                        if (fm_group_member.getBackStackEntryCount() > 0) {
                                            fm_group_member.popBackStack();
                                        }
                                    } else
                                        Toast.makeText(getContext(), "Bạn chưa chọn số điện thoại nào", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        btn_ok_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (option) {
                    case Config.ADD_PROFILES:
                        for (int j = 0; j < datasvina.size(); j++) {
                            if (datasvina.get(j).isChecked()) {
                                listGitSongs.add(datasvina.get(j));
                            }
                        }
                        if (listGitSongs != null && listGitSongs.size() > 0) {
                            String phone_addprofile = listGitSongs.get(0).getPhoneNumber();
                            Fragment_AddProfiles.ed_cli_add_profile.setText(phone_addprofile);
                        }

                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().
                                getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            fm.popBackStack();
                        }
                        break;
                    case Config.GIFT:
                        String phone = "";
                        for (int j = 0; j < datasvina.size(); j++) {
                            if (datasvina.get(j).isChecked()) {
                                listGitSongs.add(datasvina.get(j));
                            }
                        }
                        phone = phone + listGitSongs.get(0).getPhoneNumber();
                        FragmentDetailBuySongs.ed_getphone.setText(phone);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        FragmentManager fm_gift = getActivity().getSupportFragmentManager();
                        if (fm_gift.getBackStackEntryCount() > 0) {
                            fm_gift.popBackStack();
                        }
                        FragmentDetailBuySongs.dialog_getphone.show();
                        break;
                    case Config.GROUP_MEMBER:
                        List<PhoneContactModel> listPhone = new ArrayList<PhoneContactModel>();
                        for (int j = 0; j < datasvina.size(); j++) {
                            if (datasvina.get(j).isChecked()&&!datasvina.get(j).isNotChange()) {
                                listPhone.add(datasvina.get(j));
                            }
                        }
                        listGitSongs.addAll(listPhone);
                        if (listGitSongs.size() > 0) {
                            String phone_all = "";
                            for (int i = 0; i < listGitSongs.size(); i++) {

                                String temp = listGitSongs.get(i).getPhoneNumber();
                                temp = PhoneNumber.convertTo84PhoneNunber(temp);
                                if (!(i == listGitSongs.size() - 1)) {
                                    phone_all = phone_all + temp + ",";
                                } else phone_all = phone_all + temp;

                                //  FragmentGroupMember.add_cli_to_group(phone_all);
                            }
                            SharedPreferences.Editor editor = pre.edit();
                            editor.putString("contack_group_member", phone_all);
                            editor.commit();
                            FragmentGroupMember.txt_delete_phone.setVisibility(View.VISIBLE);
                            FragmentGroupMember.ed_getphone_group.setText(phone_all);
                            FragmentGroupMember.dialog.show();
                            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            FragmentManager fm_group_member = getActivity().getSupportFragmentManager();
                            if (fm_group_member.getBackStackEntryCount() > 0) {
                                fm_group_member.popBackStack();
                            }
                        } else
                            Toast.makeText(getContext(), "Bạn chưa chọn số điện thoại nào", Toast.LENGTH_SHORT).show();
                        break;
                    case Config.ADD_GROUP:
                        List<PhoneContactModel> lisAddGroup = new ArrayList<PhoneContactModel>();
                        for (int j = 0; j < datasvina.size(); j++) {
                            if (datasvina.get(j).isChecked()) {
                                lisAddGroup.add(datasvina.get(j));

                            }
                        }
                        if (lisAddGroup.size() > 0) {
                            listGitSongs.addAll(lisAddGroup);
                            String sAddGroup = "";
                            if (listGitSongs.size() > 0) {
                                String phone_addprofile = listGitSongs.get(0).getPhoneNumber();
                                for (int i = 0; i < listGitSongs.size(); i++) {
                                    sAddGroup = sAddGroup + listGitSongs.get(i).getPhoneNumber() + ",";
                                }
                            }
                            FragmentAddGroup.ed_add_phone_addgroup.setText(sAddGroup);
                            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            FragmentManager fm_group_member = getActivity().getSupportFragmentManager();
                            if (fm_group_member.getBackStackEntryCount() > 0) {
                                fm_group_member.popBackStack();
                            }
                        } else
                            Toast.makeText(getContext(), "Bạn chưa chọn số điện thoại nào", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });
        //search contact
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void requestPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
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
        }
    }


}
