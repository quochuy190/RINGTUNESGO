package com.neo.media.Contact.ViewActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterContact;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.untils.BaseActivity;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.KeyboardUtil;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityListContact extends BaseActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;
    public static String TAG = ActivityListContact.class.getSimpleName();
    public static ActivityListContact fragment;
    private List<PhoneContactModel> lisService;
    private AdapterContact adapterService;
    @BindView(R.id.listContact)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.editTextSearch)
    EditText edt_search_service;
    String name_package;
    private boolean isChecked;
    public static ActivityListContact getInstance() {
        if (fragment == null) {
            synchronized (ActivityListContact.class) {
                if (fragment == null)
                    fragment = new ActivityListContact();
            }
        }
        return fragment;
    }
    @BindView(R.id.btn_ok_contact)
    Button btn_ok_service;
    List<PhoneContactModel> temp;
    private ArrayList<PhoneContactModel> datas;
    private String sOption;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        datas = new ArrayList<>();
        temp = new ArrayList<>();
        init();
        askForContactPermission();
        initDatas();
        initEvent();

    }

    private void initDatas() {
        sOption = MyApplication.sOption_getPhone;
    }

    @Override
    public int setContentViewId() {
        return R.layout.list_contacts;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        btn_ok_service.setVisibility(View.GONE);
    }

    private void initEvent() {
        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                if (temp!=null){
                    filter(s.toString());
                }else  new SweetAlertDialog(ActivityListContact.this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Để huỷ dịch vụ, quý khách vui lòng soạn tin nhắn \"HUY gửi 9194\"")
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                askForContactPermission();
                                sweetAlertDialog.dismiss();
                            }
                        }).show();

                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });

        btn_ok_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0;i<temp.size();i++){
                    if (temp.get(i).isChecked()){
                        MyApplication.objPhone= temp.get(i);
                    }
                }
                if (MyApplication.objPhone!=null){
                    finish();
                }else Toast.makeText(ActivityListContact.this, "Bạn chưa chọn số điện thoại nào", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void filter(String text) {
         temp.clear();
        for (PhoneContactModel d : datas) {
            if (d.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                temp.add(d);
            }
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getName().contains(text)) {

            }
        }
        //update recyclerview
        if (temp!=null){
            adapterService.updateList(temp);
            adapterService.setOnIListener(new setOnItemClickListener() {
                @Override
                public void OnItemClickListener(int position) {
                    if (sOption.equals("GIFT")){
                        MyApplication.objPhone= temp.get(position);
                        finish();
                    }
                }
                @Override
                public void OnLongItemClickListener(int position) {
                }
            });
        }
    }

    private void init() {
        adapterService = new AdapterContact(temp, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);
        adapterService.updateList(temp);
        adapterService.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (sOption.equals("GIFT")){
                    MyApplication.objPhone= temp.get(position);
                    finish();
                }
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });

    }
    public void click_item_phone(int position){

    }

/*    private void initData() {
        //MyApplication.mObjService = new Service();

        lisService.addAll(MyApplication.lisServiceApp);
        temp.addAll(lisService);
        name_package = getIntent().getStringExtra("name_package");
    }*/
    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

                    ActivityCompat.requestPermissions(this,
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


    private void get_contact() {
        datas.addAll(CustomUtils.getAllPhoneContacts(this));
        Collections.sort(datas, new Comparator<PhoneContactModel>() {
            @Override
            public int compare(PhoneContactModel sv1, PhoneContactModel sv2) {
                return (sv1.getName().compareTo(sv2.getName()));

            }
        });
        temp.addAll(datas);
        adapterService.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("gift", "onPause_contact");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("gift", "onDestroy_contact");
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
