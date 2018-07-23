package com.neo.media.Fragment.CaNhan.Groups.GroupMember;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterCollectionGroup;
import com.neo.media.Adapter.AdapterIcon_Group;
import com.neo.media.Adapter.AdapterRingtunes;
import com.neo.media.Adapter.AdapterTime_Group;
import com.neo.media.Adapter.Adapter_Group_Member;
import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Contact.ViewActivity.ActivityListContact_Get_Add_Group;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.CaNhan.Collection.ConllectionInteface;
import com.neo.media.Fragment.CaNhan.Groups.FragmentGroups;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Icon_Group;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Time_Group;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.KeyboardUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.makeText;
import static com.neo.media.MyApplication.lisRingtunesNew;
import static com.neo.media.MyApplication.listConllection;

/**
 * Created by QQ on 7/24/2017.
 */

public class FragmentGroupMember extends BaseFragment implements GroupMemberInterface.View, ConllectionInteface.View {
    public static FragmentGroupMember fragmentGroupMember;
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;

    public static FragmentGroupMember getInstance() {
        if (fragmentGroupMember == null) {
            synchronized (FragmentGroupMember.class) {
                if (fragmentGroupMember == null) {
                    fragmentGroupMember = new FragmentGroupMember();
                }
            }
        }
        return fragmentGroupMember;
    }

    @BindView(R.id.recycle_group_member)
    RecyclerView recycleAlbum;
    public static Adapter_Group_Member adapter_group_member;
    public static PresenterGroupMember presenterGroupMember;
    List<CLI> lisCLI = new ArrayList<>();
    @BindView(R.id.txt_title_tabar_groupmember)
    TextView txt_title;
    @BindView(R.id.add_group_member)
    TextView txt_add;
    @BindView(R.id.img_back_group_member)
    ImageView img_back_group_member;
    @BindView(R.id.recycle_ringtunes_group_member)
    RecyclerView recycle_ringtunes_group_member;
    public static String group_id = "";
    public static String sesionID, msisdn, user_id;
    AdapterCollectionGroup adapterCollection;
    RecyclerView.LayoutManager mLayoutManager;
    Realm realm;
    List<Item> lisItem;
    List<Ringtunes> lisRingtunes_same;
    AdapterRingtunes adapterRingtunes;
    @BindView(R.id.recycle_thembaihat)
    RecyclerView recycle_thembaihat;
    public String profile_id = "";
    GroupName objGroupName;
    public static EditText ed_getphone_group;
    String all_phone;
    public static Dialog dialog;
    public static ImageView btn_get_contact;
    public static TextView ok;
    Button cancel;
    List<PhoneContactModel> lisPhone;
    String content_id;
    GROUP objGrop;
    String title;
    public static TextView txt_delete_phone;
    List<Ringtunes> listSongSame;
    public static TextView dialog_edit_menu;
    EditText ed_edit_name;
    @BindView(R.id.edt_doitennhom)
    EditText edt_doitennhom;
    @BindView(R.id.btn_doitennhom)
    TextView btn_doitennhom;
    @BindView(R.id.btn_luuthaydoi)
    TextView btn_luuthaydoi;
    @BindView(R.id.btn_xoanhom)
    TextView btn_xoanhom;
    @BindView(R.id.linner_time_groupmember)
    LinearLayout linner_time_groupmember;
    private boolean is_subscriber, is_SVC_STATUS;
    private boolean isLogin;
    @BindView(R.id.recycle_time)
    RecyclerView recycle_time;
    AdapterTime_Group adapter_time;
    List<Time_Group> lisTime;
    String from_time = "00:00:00", to_time = "23:59:59";
    SharedPreferences pre;
   /* @Override
    public int setContentViewId() {
        return R.layout.fragment_group_member;
    }

    @Override
    public void initData() {

    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_DataPref();
        lisItem = listConllection;
        objGrop = MyApplication.objGroup;
        group_id = objGrop.getId();
        if (objGrop.getClis() != null)
            lisCLI = objGrop.getClis().getCLI();
        title = objGrop.getName();
        datas = new ArrayList<>();
        objGroupName = realm.where(GroupName.class).equalTo("id_group", group_id).findFirst();
        MyApplication.list_ct_add_group.clear();
        // ButterKnife.bind(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_member, container, false);
        ButterKnife.bind(this, view);
        String s = objGrop.getName();
        ed_getphone_group = (EditText) view.findViewById(R.id.edt_themSDT);
        btn_get_contact = (ImageView) view.findViewById(R.id.img_laydanhba);
        ok = (TextView) view.findViewById(R.id.btn_them_sdt);
        txt_delete_phone = (TextView) view.findViewById(R.id.txt_delete_text);
        // initDialog();
        initTimeGroup();
        initDatas();
        initEvent();
        askForContactPermission();
        return view;
    }

    public void get_DataPref() {
        MyApplication.list_ct_add_group.clear();
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        realm = RealmController.with(this).getRealm();
        presenterGroupMember = new PresenterGroupMember(this);
        sesionID = Prefs.with(getActivity()).getString("sessionID", "");
        msisdn = Prefs.with(getActivity()).getString("msisdn", "");
        is_subscriber = Prefs.with(getActivity()).getBoolean("is_subscriber", false);
        is_SVC_STATUS = Prefs.with(getActivity()).getBoolean("is_SVC_STATUS", false);
        isLogin = Prefs.with(getActivity()).getBoolean("isLogin", false);
        user_id = Prefs.with(getActivity()).getString("user_id", "");

    }

    public void initDialog() {
        edt_doitennhom.setText(objGrop.getName());
        btn_doitennhom.setVisibility(View.GONE);
        txt_delete_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_getphone_group.setText("");
                txt_delete_phone.setVisibility(View.GONE);
                ok.setVisibility(View.GONE);
                btn_get_contact.setVisibility(View.VISIBLE);
            }
        });

        btn_get_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lisCLI.size() > 0)
                    MyApplication.listCLI.addAll(lisCLI);
                MyApplication.list_ct_add_group.clear();
                startActivity(new Intent(getActivity(), ActivityListContact_Get_Add_Group.class));
                // bundle.putSerializable("lis_CLI", (Serializable) lisCLI);
                // FragmentUtil.addFragment(FragmentGroupMember.this, ActivityContacts.getInstance(), true);

                KeyboardUtil.hideSoftKeyboard(getActivity());
            }
        });
        ed_getphone_group.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_delete_phone.setVisibility(View.VISIBLE);
                ok.setVisibility(View.VISIBLE);
                btn_get_contact.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_phone = ed_getphone_group.getText().toString();
                if (all_phone.length() > 9) {
                    MyApplication.list_ct_add_group.clear();
                    ed_getphone_group.setText("");
                    txt_delete_phone.setVisibility(View.GONE);
                    ok.setVisibility(View.GONE);
                    btn_get_contact.setVisibility(View.VISIBLE);
                    presenterGroupMember.add_cli_to_group(sesionID, msisdn, group_id, all_phone);
                } else {
                    show_notification("Thông báo", "Số điện thoại không đúng định dạng");
                }
                // makeText(FragmentGroupMember.this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void initDatas() {
        initGroupMember();
        initSongSame();
        //initTimeGroup();
        initIcon();
        initConllection();
    }


    @Override
    public void onResume() {
        super.onResume();
        icon_group = objGrop.getImg_backgroup();
        initDialog();
        if (MyApplication.list_ct_add_group.size() > 0) {
            ed_getphone_group.setText(PhoneNumber.add_multil_phone(MyApplication.list_ct_add_group));
            txt_delete_phone.setVisibility(View.VISIBLE);
            ok.setVisibility(View.VISIBLE);
            btn_get_contact.setVisibility(View.GONE);
        } else {
            ed_getphone_group.setText("");
            ed_getphone_group.setHint(R.string.txt_hint_getcontact);
            txt_delete_phone.setVisibility(View.GONE);
            ok.setVisibility(View.GONE);
            btn_get_contact.setVisibility(View.VISIBLE);
        }
        txt_title.setText("Chỉnh sửa nhóm");
        // MainNavigationActivity.appbar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
     /*   MyApplication.objGroup = new GROUP();
        edt_doitennhom.setText("");
        is_DeleteItem = false;*/
        FragmentGroups.load_all_group();
    }

    private boolean isCollection = false;
    private boolean isProfile = false;

    private void initEvent() {
        btn_luuthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCollection || content_id == null) {
                    btn_doitennhom.setVisibility(View.GONE);
                    if (objGroupName != null) {
                        if (objGroupName.getsNameServer().length() > 0) {
                            GroupName obj = new GroupName();
                            obj.setsNameLocal(edt_doitennhom.getText().toString());
                            obj.setId_group(group_id);
                            obj.setsNameServer(objGroupName.getsNameServer());
                            obj.setBackground(icon_group);
                            edt_doitennhom.setText(edt_doitennhom.getText().toString());
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(obj);
                            realm.commitTransaction();
                        }
                    } else {
                        GroupName obj = new GroupName();
                        obj.setsNameLocal(edt_doitennhom.getText().toString());
                        obj.setId_group(group_id);
                        obj.setsNameServer(title);
                        obj.setBackground(icon_group);
                        edt_doitennhom.setText(edt_doitennhom.getText().toString());
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(obj);
                        realm.commitTransaction();
                    }
                    FragmentUtil.popBackStack(getActivity());
                } else {
                    if (!isProfile) {
                        presenterGroupMember.add_profile(sesionID, msisdn, content_id, "GROUP",
                                group_id, from_time, to_time);
                    } else {
                        presenterGroupMember.updateProfile(sesionID, msisdn, profile_id, content_id, "GROUP",
                                group_id, from_time, to_time);
                    }
                }
            }
        });
        btn_xoanhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Bạn có thực sự muốn xoá nhóm")
                        .setConfirmText("Đồng ý")
                        .setCancelText("Trở lại")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                if (isProfile) {
                                    presenterGroupMember.deleteProfile(sesionID, msisdn, profile_id);
                                } else {
                                    presenterGroupMember.deleteGroup(sesionID, msisdn, group_id);

                                }
                                // presenter_stopPause.add_subcriber(sesionID, msisdn, ngay);
                            }
                        })
                        .show();

            }
        });
        edt_doitennhom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_doitennhom.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_doitennhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_doitennhom.getText().toString().length() > 0 && !edt_doitennhom.getText().toString().equals(objGrop.getName())) {
                    btn_doitennhom.setVisibility(View.GONE);
                    if (objGroupName != null) {
                        if (objGroupName.getsNameServer().length() > 0) {
                            GroupName obj = new GroupName();
                            obj.setsNameLocal(edt_doitennhom.getText().toString());
                            obj.setId_group(group_id);
                            obj.setsNameServer(objGroupName.getsNameServer());
                            obj.setBackground(objGrop.getImg_backgroup());
                            edt_doitennhom.setText(edt_doitennhom.getText().toString());
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(obj);
                            realm.commitTransaction();
                        }
                    } else {
                        GroupName obj = new GroupName();
                        obj.setsNameLocal(edt_doitennhom.getText().toString());
                        obj.setId_group(group_id);
                        obj.setsNameServer(title);
                        obj.setBackground(objGrop.getImg_backgroup());
                        edt_doitennhom.setText(edt_doitennhom.getText().toString());
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(obj);
                        realm.commitTransaction();
                    }
                } else {
                    show_notification("Thông báo", "Bạn chưa nhập vào tên nhóm");
                    // makeText(FragmentGroupMember.this, "Bạn chưa nhập vào tên nhóm", Toast.LENGTH_SHORT).show();
                }
                KeyboardUtil.hideSoftKeyboard(getActivity());
            }
        });


        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_DeleteItem) {
                    txt_add.setText("Bỏ chọn");
                    is_DeleteItem = !is_DeleteItem;
                    if (lisPhone.size() > 0) {
                        for (int i = 0; i < lisPhone.size(); i++) {
                            lisPhone.get(i).setShowDelete(is_DeleteItem);
                        }
                    }
                    adapter_group_member.notifyDataSetChanged();
                } else {
                    txt_add.setText("Chọn");
                    is_DeleteItem = !is_DeleteItem;
                    if (lisPhone.size() > 0) {
                        for (int i = 0; i < lisPhone.size(); i++) {
                            lisPhone.get(i).setShowDelete(is_DeleteItem);
                        }
                    }
                    adapter_group_member.notifyDataSetChanged();
                }

            }
        });
        img_back_group_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });

    }

    boolean is_DeleteItem = false;

    public void initGroupMember() {
        lisPhone = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        adapter_group_member = new Adapter_Group_Member(lisPhone, getActivity());
        recycleAlbum.setHasFixedSize(true);
        recycleAlbum.setLayoutManager(mLayoutManager);
        recycleAlbum.setNestedScrollingEnabled(false);
        recycleAlbum.setItemAnimator(new DefaultItemAnimator());
        recycleAlbum.setAdapter(adapter_group_member);
        adapter_group_member.notifyDataSetChanged();

        adapter_group_member.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {

            }

            @Override
            public void OnLongItemClickListener(final int position) {

            }
        });

    }

    private List<PhoneContactModel> datas;

    @Override
    public void showGroupMember(GROUPS groups) {
        hideDialogLoading();
        lisPhone.clear();
        lisCLI.clear();
        List<PhoneContactModel> lisContact = new ArrayList<>();
        if (groups != null) {
            if (groups.getGroup() != null) {
                if (groups.getGroup().get(0).getClis() != null) {
                    List<CLI> clis = groups.getGroup().get(0).getClis().getCLI();
                    lisCLI.addAll(clis);
                    for (int i = 0; i < clis.size(); i++) {
                        lisContact.add(new PhoneContactModel("Unknown",
                                PhoneNumber.convertToVnPhoneNumber(clis.get(i).getCaller_id()), ""));
                        for (int j = 0; j < datas.size(); j++) {
                            String objcli = clis.get(i).getCaller_id();
                            String phone = datas.get(j).getPhoneNumber();
                            phone.replaceAll("\\+", "");
                            phone.replace(" ", "");
                            phone = PhoneNumber.convertTo84PhoneNunber(phone);
                            if (objcli.equals(phone))
                                lisContact.set(i, datas.get(j));
                        }
                    }
                    lisPhone.addAll(lisContact);
                    if (lisPhone.size() > 0) {
                        for (int i = 0; i < lisPhone.size(); i++) {
                            lisPhone.get(i).setShowDelete(is_DeleteItem);
                        }
                    }
                    adapter_group_member.notifyDataSetChanged();
                }
            } else {
                adapter_group_member.notifyDataSetChanged();
            }
        } else adapter_group_member.notifyDataSetChanged();
    }

    @Override
    public void show_add_cli_group(JSONObject mJsonObject) {
        if (mJsonObject != null) {
            try {
                String error = mJsonObject.getString("ERROR");
                String ERROR_DESC = mJsonObject.getString("ERROR_DESC");
                if (error.equals("0")) {
                    presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
                    show_notification("Thông báo", ERROR_DESC);
                } else if (error.equals("117")) {
                    presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
                    show_notification("Thông báo", ERROR_DESC);
                    //Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                } else {
                    show_notification("Thông báo", ERROR_DESC);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            show_notification("Thông báo", "Lỗi kết nối, vui long thử lại sau");
        }
    }
    @Override
    public void showMessage(String message) {
        if (message.length() > 0) {

        } else {

        }

    }
    @Override
    public void showDelete(CLI clis) {
        hideDialogLoading();
        lisPhone.clear();
        lisCLI.clear();
        if (clis != null) {
            presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
            makeText(getActivity(), "Delete thành công", Toast.LENGTH_SHORT).show();
        } else presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
    }

    @Override
    public void showAllProfiles(PROFILE listProfiles) {
        if (listProfiles != null && listProfiles.getContent_id() != null) {
            //linner_time_groupmember.setVisibility(View.VISIBLE);
            isProfile = true;
            profile_id = listProfiles.getProfile_id();
            content_id = listProfiles.getContent_id();
            String from = listProfiles.getTimeBase().getTime_category_4().getFrom_time();
            from_time = listProfiles.getTimeBase().getTime_category_4().getFrom_time();
            to_time = listProfiles.getTimeBase().getTime_category_4().getTo_time();
            for (int i = 0; i < lisTime.size(); i++) {
                if (from.equals(lisTime.get(i).getFrom_time())) {
                    lisTime.get(i).setCheck(true);
                }
            }
            adapter_time.notifyDataSetChanged();
            presenterGroupMember.getConllection(sesionID, msisdn, listProfiles.getContent_id());
        } else {
            isProfile = false;
            presenterGroupMember.getConllection(sesionID, msisdn, "");
        }

    }

    @Override
    public void showErrorDeleteProfile(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenterGroupMember.deleteGroup(sesionID, msisdn, group_id);
            } else {
                show_notification("Lỗi", "Xoá nhóm không thành công");
                // makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void showdeleteGroups(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                if (objGroupName != null) {
                    if (objGroupName.getsNameServer().length() > 0) {
                        GroupName obj = new GroupName();
                        obj.setsNameLocal("");
                        obj.setId_group("");
                        obj.setsNameServer(objGroupName.getsNameServer());
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(obj);
                        realm.commitTransaction();
                    }
                }
                //  show_notification("Lỗi", "Thêm nhóm không thành công");
                makeText(getActivity(), "Xoá nhóm thành công", Toast.LENGTH_SHORT).show();
               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }*/
                FragmentUtil.popBackStack(getActivity());
            } else show_notification("Lỗi", "Xoá nhóm không thành công");
        }
    }

    @Override
    public void show_update_profile(List<String> string) {
        if (string.size() > 0) {
            if (string.get(0).equals("0")) {
                btn_doitennhom.setVisibility(View.GONE);
                if (objGroupName != null) {
                    if (objGroupName.getsNameServer().length() > 0) {
                        GroupName obj = new GroupName();
                        obj.setsNameLocal(edt_doitennhom.getText().toString());
                        obj.setId_group(group_id);
                        obj.setsNameServer(objGroupName.getsNameServer());
                        obj.setBackground(icon_group);
                        edt_doitennhom.setText(edt_doitennhom.getText().toString());
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(obj);
                        realm.commitTransaction();
                    }
                } else {
                    GroupName obj = new GroupName();
                    obj.setsNameLocal(edt_doitennhom.getText().toString());
                    obj.setId_group(group_id);
                    obj.setsNameServer(title);
                    obj.setBackground(icon_group);
                    edt_doitennhom.setText(edt_doitennhom.getText().toString());
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(obj);
                    realm.commitTransaction();
                }
                presenterGroupMember.getProfiles_By_GroupId(sesionID, msisdn, group_id, "GROUP");
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Lưu thay đổi thành công")
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
            }
        }
    }


    public static void delete_cli_to_group(Context context, String groupID) {
        groupID = PhoneNumber.convertTo84PhoneNunber(groupID);
        final String finalGroupID1 = groupID;
        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText(String.valueOf(Html.fromHtml("Bạn có chắc chắn muốn xoá số <font color='#6f2b8e'>"
                        + groupID + "</font> ra khỏi nhóm không ?")))
                .setConfirmText("Đồng ý")
                .setCancelText("Trở lại")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        presenterGroupMember.delete_cli_to_group(sesionID, msisdn, group_id, finalGroupID1);
                    }
                })
                .show();


        //presenterGroupMember.delete_cli_to_group(sesionID, msisdn, "DEFAULT ", "");
    }

    public void initSongSame() {
        lisRingtunes_same = new ArrayList<>();
        adapterRingtunes = new AdapterRingtunes(lisRingtunes_same, getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recycle_thembaihat.setHasFixedSize(true);
        recycle_thembaihat.setNestedScrollingEnabled(false);
        recycle_thembaihat.setLayoutManager(mLayoutManager);
        recycle_thembaihat.setItemAnimator(new DefaultItemAnimator());
        recycle_thembaihat.setAdapter(adapterRingtunes);
        adapterRingtunes.notifyDataSetChanged();

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                MyApplication.player_ring = Ringtunes.getInstance();
                SharedPreferences.Editor editor = pre.edit();
                MyApplication.player_ring = lisRingtunes_same.get(position);
                editor.putBoolean("isHome", false);
                editor.putString("idSinger", lisRingtunes_same.get(position).getSinger_id());
                editor.commit();

                if (!FragmentDetailBuySongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentDetailBuySongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showLisSongsSame(List<Ringtunes> lisRing) {
        hideDialogLoading();
        lisRingtunes_same.clear();
        if (lisRing.size() > 0) {
            lisRingtunes_same.addAll(lisRing);
            adapterRingtunes.notifyDataSetChanged();
        }

    }

    @Override
    public void showConllection(List<Item> listItems) {


    }

    @Override
    public void showDeleteSuccessfull(List<String> list) {

    }


    @Override
    public void show_list_songs_collection(List<Ringtunes> listSongs) {
        int position = -1;
        if (listSongs.size() > 0) {
            // listSongSame.clear();
            for (int i = 0; i < listSongs.size(); i++) {
                for (int j = 0; j < lisItem.size(); j++) {
                    if (listSongs.get(i).getId().equals(lisItem.get(j).getContent_id())) {
                        lisItem.get(j).setImage_url(listSongs.get(i).getImage_file());
                        lisItem.get(j).setProduct_name(listSongs.get(i).getProduct_name());
                        lisItem.get(j).setSinger_id(listSongs.get(i).getSinger_id());
                        lisItem.get(j).setPath(listSongs.get(i).getPath());
                        if (lisItem.get(j).is_check)
                            position = j;
                    }
                }
            }
            adapterCollection.notifyDataSetChanged();
            if (position >= 0)
                presenterGroupMember.api_suggestion_play(lisItem.get(position).getSinger_id(),
                        lisItem.get(position).getContent_id(), user_id);
            else if (position < 0 && lisItem.size() > 0) {
                presenterGroupMember.api_suggestion_play(lisItem.get(0).getSinger_id(),
                        lisItem.get(0).getContent_id(), user_id);
            }
        } else {
            adapterRingtunes.notifyDataSetChanged();
            hideDialogLoading();
        }


    }

    public void initConllection() {
        adapterCollection = new AdapterCollectionGroup(lisItem, getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recycle_ringtunes_group_member.setHasFixedSize(true);
        recycle_ringtunes_group_member.setNestedScrollingEnabled(false);
        recycle_ringtunes_group_member.setLayoutManager(mLayoutManager);
        recycle_ringtunes_group_member.setItemAnimator(new DefaultItemAnimator());
        recycle_ringtunes_group_member.setAdapter(adapterCollection);
        adapterCollection.notifyDataSetChanged();

        adapterCollection.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {
                for (int i = 0; i < lisItem.size(); i++) {
                    if (i == position) {
                        if (!lisItem.get(i).is_check) {
                            lisItem.get(i).setIs_check(true);
                            content_id = lisItem.get(position).getContent_id();
                        }
                          /*  presenterGroupMember.updateProfile(sesionID, msisdn, profile_id, lisItem.get(position).getContent_id(), "GROUP",
                                    group_id, "00:00:00", "23:59:59");*/
                    } else lisItem.get(i).setIs_check(false);
                }
                adapterCollection.notifyDataSetChanged();
                /*final Dialog dialog_yes = new Dialog(getContext());
                dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_yes.setContentView(R.layout.dialog_yes_no);
                TextView txt_buysongs = (TextView) dialog_yes.findViewById(R.id.dialog_message);
                Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
                txt_buysongs.setText(Html.fromHtml("Bạn có muốn cài bài hát <font color='#6f2b8e'>" + lisItem.get(position).getContent_title()
                        + "</font> làm nhạc chờ cho nhóm"));
                yes.setText("Có");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content_id == null) {
                            presenterGroupMember.add_profile(sesionID, msisdn, lisItem.get(position).getContent_id(), "GROUP",
                                    group_id, "00:00:00", "23:59:59");
                        }
                        for (int i = 0; i < lisItem.size(); i++) {
                            if (i == position) {
                                if (!lisItem.get(i).is_check)
                                    presenterGroupMember.updateProfile(sesionID, msisdn, profile_id, lisItem.get(position).getContent_id(), "GROUP",
                                            group_id, "00:00:00", "23:59:59");
                            } else lisItem.get(i).setIs_check(false);
                        }
                        adapterCollection.notifyDataSetChanged();
                        dialog_yes.dismiss();
                    }
                });
                no.setText("Không");
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes.dismiss();
                    }
                });
                dialog_yes.show();*/
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showConllection(final List<Item> list, final String content_id) {
        lisItem.clear();
        String id_songs = "";
        if (list.size() > 0) {
            linner_time_groupmember.setVisibility(View.VISIBLE);
            isCollection = true;
            lisItem.addAll(list);
            for (int i = 0; i < lisItem.size(); i++) {
                if (lisItem.get(i).getContent_id().equals(content_id)) {
                    lisItem.get(i).setIs_check(true);
                }
                if (i < lisItem.size() - 1)
                    id_songs = id_songs + lisItem.get(i).getContent_id() + ",";
                else id_songs = id_songs + listConllection.get(i).getContent_id();
            }
            presenterGroupMember.get_info_songs_collection(id_songs, user_id);
        } else {
            linner_time_groupmember.setVisibility(View.GONE);
            hideDialogLoading();
            isCollection = false;
            lisRingtunes_same.addAll(lisRingtunesNew);
            adapterRingtunes.notifyDataSetChanged();
        }
    }


    private void initTimeGroup() {
        lisTime = new ArrayList<>();
        lisTime.add(new Time_Group("Cả ngày", false, "00:00:00", "23:59:59"));
        lisTime.add(new Time_Group("Ban ngày (08:00 - 17:00)", false, "08:00:00", "17:00:00"));
        lisTime.add(new Time_Group("Ban ngày 1 (09:00 - 18:00)", false, "09:00:00", "18:00:00"));
        lisTime.add(new Time_Group("Ban ngày 2 (07:30 - 16:00)", false, "07:30:00", "16:30:00"));
        lisTime.add(new Time_Group("Buổi tối (17:01 - 07:59)", false, "17:01:00", "07:59:00"));
        lisTime.add(new Time_Group("Buổi tối 1 (18:01 - 08:59)", false, "18:01:00", "08:59:00"));
        lisTime.add(new Time_Group("Buổi tối 2 (16:31 - 07:29)", false, "16:31:00", "07:29:00"));

        adapter_time = new AdapterTime_Group(lisTime, getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        //recycle_time.setHasFixedSize(true);
        recycle_time.setNestedScrollingEnabled(false);
        recycle_time.setLayoutManager(mLayoutManager);
        recycle_time.setItemAnimator(new DefaultItemAnimator());
        recycle_time.setAdapter(adapter_time);
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

    @BindView(R.id.recycle_doihinhdaidien)
    RecyclerView recycle_icon;
    AdapterIcon_Group adapter_icon;
    public int icon_group = 0;
    List<Icon_Group> lisIcon;

    private void initIcon() {
        lisIcon = new ArrayList<>();
        lisIcon.add(new Icon_Group(R.drawable.group_0, false, 1));
        lisIcon.add(new Icon_Group(R.drawable.group_1, false, 2));
        lisIcon.add(new Icon_Group(R.drawable.group_2, false, 3));
        lisIcon.add(new Icon_Group(R.drawable.group_3, false, 6));
        lisIcon.add(new Icon_Group(R.drawable.group_4, false, 4));
        for (int i = 0; i < lisIcon.size(); i++) {
            if (objGrop.getImg_backgroup() == lisIcon.get(i).getIcon_background()) {
                lisIcon.get(i).setCheck(true);
            }
        }
        //icon_group = lisIcon.get(0).getIcon_background();
        adapter_icon = new AdapterIcon_Group(lisIcon, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
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

    private void get_contact() {
        datas = CustomUtils.getAllPhoneContacts(getActivity());
        if (isLogin) {
            if (is_subscriber) {
                showDialogLoading();
                presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
                presenterGroupMember.getProfiles_By_GroupId(sesionID, msisdn, group_id, "GROUP");
                // presenterGroupMember.getConllection(sesionID, msisdn);
            } else {
                show_notification("Thông báo", "Bạn chưa đăng ký Ringtunes");
            }
        } else show_notification("Thông báo", "Bạn chưa đăng nhập Ringtunes");
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
