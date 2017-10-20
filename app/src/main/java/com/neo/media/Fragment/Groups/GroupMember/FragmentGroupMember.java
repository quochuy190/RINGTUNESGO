package com.neo.media.Fragment.Groups.GroupMember;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterCollectionGroup;
import com.neo.media.Adapter.AdapterRingtunes;
import com.neo.media.Adapter.Adapter_Group_Member;
import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Config.Config;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.Collection.ConllectionInteface;
import com.neo.media.Fragment.Groups.FragmentGroups;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.GroupName;
import com.neo.media.Model.Login;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.View.ActivityContacts;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.DialogUtil;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.makeText;
import static com.neo.media.MyApplication.lisRingtunesNew;
import static com.neo.media.MyApplication.listConllection;

/**
 * Created by QQ on 7/24/2017.
 */

public class FragmentGroupMember extends BaseFragment implements GroupMemberInterface.View, ConllectionInteface.View {
    public static FragmentGroupMember fragmentGroupMember;

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

    public static List<PhoneContactModel> lisContact;
    @BindView(R.id.recycle_group_member)
    RecyclerView recycleAlbum;
    public static Adapter_Group_Member adapter_group_member;
    public static PresenterGroupMember presenterGroupMember;
    List<CLI> lisCLI = new ArrayList<>();
    @BindView(R.id.txt_title_tabar_groupmember)
    TextView txt_title;
    @BindView(R.id.add_group_member)
    ImageView txt_add;
    @BindView(R.id.img_back_group_member)
    ImageView img_back_group_member;
    @BindView(R.id.recycle_ringtunes_group_member)
    RecyclerView recycle_ringtunes_group_member;
    public static String group_id = "";
    static String sesionID;
    static String msisdn;
    AdapterCollectionGroup adapterCollection;
    RecyclerView.LayoutManager mLayoutManager;
    Realm realm;
    Info_User objInfo;
    Login objLogin;
    List<Item> lisItem;
    List<Ringtunes> lisRingtunes_same;
    AdapterRingtunes adapterRingtunes;
    @BindView(R.id.recycle_thembaihat)
    RecyclerView recycle_thembaihat;
    SharedPreferences pre;
    public String profile_id = "";
    GroupName objGroupName;
    public static EditText ed_getphone_group;
    String all_phone;
    public static Dialog dialog;
    ImageView contact;
    Button ok;
    Button cancel;
    List<PhoneContactModel> lisPhone;
    String content_id;
    GROUP objGrop;
    String title;
    public static TextView txt_delete_phone;
    List<Ringtunes> listSongSame;
    public static TextView dialog_edit_menu;
    EditText ed_edit_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm();
        lisPhone = new ArrayList<>();
        listSongSame = new ArrayList<>();
        lisItem = listConllection;
        lisRingtunes_same = new ArrayList<>();
        presenterGroupMember = new PresenterGroupMember(this);
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("contack_group_member", "");
        editor.commit();
        objInfo = realm.where(Info_User.class).findFirst();
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();
        sesionID = pre.getString("sessionID", "");
        msisdn = pre.getString("msisdn", "");
        objGrop = MyApplication.objGroup;
        group_id = objGrop.getId();
        if (objGrop.getClis() != null)
            lisCLI = objGrop.getClis().getCLI();
        title = objGrop.getName();
        objGroupName = realm.where(GroupName.class).equalTo("id_group", group_id).findFirst();
        if (objLogin != null) {
            if (objInfo != null) {
                if (objInfo.getStatus().equals("2")) {
                    presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
                    // presenterGroupMember.getConllection(sesionID, msisdn);

                } else {
                }
            }
        }
    }

    //String group_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_member, container, false);
        ButterKnife.bind(this, view);
        initData();
        initEvent();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;

    }


    public void initDialog() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_get_contact);

        ed_getphone_group = (EditText) dialog.findViewById(R.id.ed_add_phone_dialog);
        contact = (ImageView) dialog.findViewById(R.id.img_add_contact_dialog);
        ok = (Button) dialog.findViewById(R.id.btn_dialogcontact_ok);
        txt_delete_phone = (TextView) dialog.findViewById(R.id.txt_delete_phone);
        cancel = (Button) dialog.findViewById(R.id.btn_dialogcontact_cancel);

        txt_delete_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_getphone_group.setText("");
                txt_delete_phone.setVisibility(View.GONE);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNavigationActivity.datasvina = new ArrayList<PhoneContactModel>();
                MainNavigationActivity.datas = new ArrayList<PhoneContactModel>();
                MainNavigationActivity.listGitSongs = new ArrayList<PhoneContactModel>();
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("option", Config.GROUP_MEMBER);
                editor.commit();
                if (lisCLI.size() > 0)
                    MyApplication.listCLI.addAll(lisCLI);
                // bundle.putSerializable("lis_CLI", (Serializable) lisCLI);
                FragmentUtil.addFragment(getActivity(), ActivityContacts.getInstance(), true);
                dialog.dismiss();
            }
        });
        ed_getphone_group.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_delete_phone.setVisibility(View.VISIBLE);
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
                    ed_getphone_group.setText("");
                    presenterGroupMember.add_cli_to_group(sesionID, msisdn, group_id, all_phone);
                    dialog.dismiss();
                } else
                    makeText(getContext(), "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void initData() {
        initGroupMember();
        initSongSame();
        initConllection();
    }


    @Override
    public void onResume() {
        super.onResume();
        initDialog();
        /*all_phone = pre.getString("contack_group_member", "");
        if (all_phone.length() > 0) {
            ed_getphone_group.setText(all_phone);
            dialog.show();
        }*/
        if (ed_getphone_group.getText().toString().length() > 0)
            txt_delete_phone.setVisibility(View.VISIBLE);
        else txt_delete_phone.setVisibility(View.GONE);

        txt_title.setText(title);
        MainNavigationActivity.appbar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ed_getphone_group.setText("");
        FragmentGroups.add_Group();
    }

    private void initEvent() {

        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog_yes_member = new Dialog(getContext());
                dialog_yes_member.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_yes_member.setContentView(R.layout.dialog_menu);
                TextView add_menu = (TextView) dialog_yes_member.findViewById(R.id.dialog_add_menu);
                TextView delete_menu = (TextView) dialog_yes_member.findViewById(R.id.txt_delete_menu);
                dialog_edit_menu = (TextView) dialog_yes_member.findViewById(R.id.dialog_edit_menu);
                dialog_edit_menu.setText("Sửa tên nhóm");
                dialog_edit_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes_member.dismiss();
                        final Dialog dialog_edit_name = new Dialog(getContext());
                        dialog_edit_name.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog_edit_name.setContentView(R.layout.dialog_edit_name);
                        Button ok = (Button) dialog_edit_name.findViewById(R.id.btn_dialogcontact_ok);
                        Button back = (Button) dialog_edit_name.findViewById(R.id.btn_dialogcontact_cancel);
                        ed_edit_name = (EditText) dialog_edit_name.findViewById(R.id.ed_edit_name_dialog);
                        ed_edit_name.setText(title);
                        ed_edit_name.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                    switch (keyCode) {
                                        case KeyEvent.KEYCODE_DPAD_CENTER:
                                        case KeyEvent.KEYCODE_ENTER:
                                            if (ed_edit_name.getText().toString().length() > 0) {
                                                if (objGroupName != null) {
                                                    if (objGroupName.getsNameServer().length() > 0) {
                                                        GroupName obj = new GroupName();
                                                        obj.setsNameLocal(ed_edit_name.getText().toString());
                                                        obj.setId_group(group_id);
                                                        obj.setsNameServer(objGroupName.getsNameServer());
                                                        realm.beginTransaction();
                                                        realm.copyToRealmOrUpdate(obj);
                                                        realm.commitTransaction();
                                                        FragmentGroups.add_Group();
                                                        FragmentUtil.popBackStack(getActivity());
                                                        dialog_edit_name.dismiss();
                                                    }
                                                } else {
                                                    GroupName obj = new GroupName();
                                                    obj.setsNameLocal(ed_edit_name.getText().toString());
                                                    obj.setId_group(group_id);
                                                    obj.setsNameServer(title);
                                                    realm.beginTransaction();
                                                    realm.copyToRealmOrUpdate(obj);
                                                    realm.commitTransaction();
                                                    FragmentGroups.add_Group();
                                                    FragmentUtil.popBackStack(getActivity());
                                                    dialog_edit_name.dismiss();
                                                }

                                            } else {
                                                makeText(getContext(), "Bạn chưa nhập vào tên nhóm", Toast.LENGTH_SHORT).show();
                                            }
                                            return true;
                                        default:
                                            break;
                                    }
                                }
                                return false;
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ed_edit_name.getText().toString().length() > 0) {
                                    if (objGroupName.getsNameServer().length() > 0) {
                                        GroupName obj = new GroupName();
                                        obj.setsNameLocal(ed_edit_name.getText().toString());
                                        obj.setId_group(group_id);
                                        obj.setsNameServer(objGroupName.getsNameServer());
                                        realm.beginTransaction();
                                        realm.copyToRealmOrUpdate(obj);
                                        realm.commitTransaction();
                                        FragmentGroups.add_Group();
                                        FragmentUtil.popBackStack(getActivity());
                                        dialog_edit_name.dismiss();
                                    } else {
                                        GroupName obj = new GroupName();
                                        obj.setsNameLocal(ed_edit_name.getText().toString());
                                        obj.setId_group(group_id);
                                        obj.setsNameServer(title);
                                        realm.beginTransaction();
                                        realm.copyToRealmOrUpdate(obj);
                                        realm.commitTransaction();
                                        FragmentGroups.add_Group();
                                        FragmentUtil.popBackStack(getActivity());
                                        dialog_edit_name.dismiss();
                                    }
                                } else {
                                    makeText(getContext(), "Bạn chưa nhập vào tên nhóm", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_edit_name.dismiss();
                            }
                        });
                        dialog_edit_name.show();
                    }
                });
                add_menu.setText("Thêm số ĐT vào nhóm");
                delete_menu.setText("Xoá nhóm");
                dialog_yes_member.setTitle(null);
               /* txt_message.setText("Bạn có muốn xoá số thuê bao " + phoneContactModels.get(position)
                        .getPhoneNumber() + " ra khỏi nhóm");*/

                add_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes_member.dismiss();
                        dialog.show();
                    }
                });
                delete_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes_member.dismiss();
                        final Dialog confirm = new Dialog(getContext());
                        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        confirm.setContentView(R.layout.dialog_yes_no);
                        Button yes = (Button) confirm.findViewById(R.id.btn_dialog_yes);
                        Button no = (Button) confirm.findViewById(R.id.btn_dialog_no);
                        TextView txt_message = (TextView) confirm.findViewById(R.id.dialog_message);
                        txt_message.setText("Bạn có muốn xoá nhóm");

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (profile_id != null) {
                                    presenterGroupMember.deleteProfile(sesionID, msisdn, profile_id);
                                    confirm.dismiss();
                                } else {
                                    presenterGroupMember.deleteGroup(sesionID, msisdn, group_id);
                                    confirm.dismiss();
                                }

                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirm.dismiss();
                            }
                        });
                        confirm.show();
                    }
                });
                dialog_yes_member.show();

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


    public void initGroupMember() {

        mLayoutManager = new GridLayoutManager(getContext(), 1);
        adapter_group_member = new Adapter_Group_Member(lisPhone, getContext());
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
                final Dialog dialog_yes_member = new Dialog(getContext());
                dialog_yes_member.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_yes_member.setContentView(R.layout.dialog_yes_no);
                Button yes = (Button) dialog_yes_member.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes_member.findViewById(R.id.btn_dialog_no);
                TextView txt_message = (TextView) dialog_yes_member.findViewById(R.id.dialog_message);
                txt_message.setText("Bạn có muốn xoá số thuê bao " + lisPhone.get(position)
                        .getPhoneNumber() + " ra khỏi nhóm");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lisPhone.size() > 1) {
                            String phone = lisPhone.get(position).getPhoneNumber();
                            phone = PhoneNumber.convertTo84PhoneNunber(phone);
                            presenterGroupMember.delete_cli_to_group(sesionID, msisdn, group_id, phone);
                            dialog_yes_member.dismiss();
                        } else {
                            if (lisPhone.size() == 1) {
                                dialog_yes_member.dismiss();
                                final Dialog dialog_xoanhom = new Dialog(getContext());
                                dialog_xoanhom.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog_xoanhom.setContentView(R.layout.dialog_yes_no);
                                Button yes = (Button) dialog_xoanhom.findViewById(R.id.btn_dialog_yes);
                                Button no = (Button) dialog_xoanhom.findViewById(R.id.btn_dialog_no);
                                TextView txt_message = (TextView) dialog_xoanhom.findViewById(R.id.dialog_message);
                                txt_message.setText("Phải có ít nhất một thành viên trong nhóm, bạn có muốn xoá nhóm");

                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (profile_id != null) {
                                            presenterGroupMember.deleteProfile(sesionID, msisdn, profile_id);
                                            dialog_xoanhom.dismiss();
                                        } else {
                                            presenterGroupMember.deleteGroup(sesionID, msisdn, group_id);
                                            dialog_xoanhom.dismiss();
                                        }
                                    }
                                });
                                no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_xoanhom.dismiss();
                                    }
                                });
                                dialog_xoanhom.show();
                            }
                        }

                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes_member.dismiss();
                    }
                });
                dialog_yes_member.show();
            }
        });

    }


    @Override
    public void showGroupMember(GROUPS groups) {
        presenterGroupMember.getProfiles_By_GroupId(sesionID, msisdn, group_id, "GROUP");
        lisPhone.clear();
        lisCLI.clear();
        List<PhoneContactModel> lisContact = new ArrayList<>();
        if (groups != null) {
            if (groups.getGroup() != null) {
                if (groups.getGroup().get(0).getClis() != null) {
                    List<CLI> clis = groups.getGroup().get(0).getClis().getCLI();
                    lisCLI.addAll(clis);
                    List<PhoneContactModel> datas = CustomUtils.getAllPhoneContacts(fragmentGroupMember.getContext());
                    for (int i = 0; i < clis.size(); i++) {
                        lisContact.add(new PhoneContactModel("Name", PhoneNumber.convertToVnPhoneNumber(clis.get(i).getCaller_id()), ""));
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
                    adapter_group_member.notifyDataSetChanged();
                }
            }
        } else adapter_group_member.notifyDataSetChanged();
    }

    @Override
    public void show_add_cli_group(List<CLI> list) {
        if (list.size() > 0) {
            if (list.get(0).getERROR().equals("0")) {
                presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
                makeText(getContext(), list.get(0).getERROR_DESC(), Toast.LENGTH_SHORT).show();
            } else if (list.get(0).getERROR().equals("343")) {
                DialogUtil.showDialog(getContext(), "Lỗi", "Số điện thoại không đúng định dạng");
            } else if (list.get(0).getERROR().equals("300")) {
                DialogUtil.showDialog(getContext(), "Lỗi", "Số điện thoại không hợp lệ");
            } else if (list.get(0).getERROR().equals("44")) {
                DialogUtil.showDialog(getContext(), "Lỗi", getResources().getString(R.string.error_44));
            } else if (list.get(0).getERROR().equals("117")) {
                String error = list.get(0).getERROR_DESC();
                error = error.replace("list contact fail", "Các số thuê bao lỗi");
                error = error.replace("list contact success", "\nCác số thuê bao thêm thành công");
                presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
                DialogUtil.showDialog(getContext(), "Thông báo", error);
            } else Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
        } else makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();

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
        if (clis != null) {
            presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
            makeText(getContext(), "Delete thành công", Toast.LENGTH_SHORT).show();
        } else presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
    }

    @Override
    public void showAllProfiles(PROFILE listProfiles) {
        if (listProfiles != null) {
            profile_id = listProfiles.getProfile_id();
            content_id = listProfiles.getContent_id();
            presenterGroupMember.getConllection(sesionID, msisdn, listProfiles.getContent_id());
        } else presenterGroupMember.getConllection(sesionID, msisdn, "");

    }

    @Override
    public void showErrorDeleteProfile(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenterGroupMember.deleteGroup(sesionID, msisdn, group_id);
            } else {
                makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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
                makeText(getContext(), "Xoá nhóm thành công", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            } else makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void show_update_profile(List<String> string) {
        if (string.size() > 0) {
            if (string.get(0).equals("0")) {
                presenterGroupMember.getProfiles_By_GroupId(sesionID, msisdn, group_id, "GROUP");
            }
        }
    }

    public static void add_cli_to_group(String caller_msisdn) {
        presenterGroupMember.add_cli_to_group(sesionID, msisdn, group_id, caller_msisdn);
    }

    public static void delete_cli_to_group(String sesionID, String msisdn, String groupID) {
        presenterGroupMember.delete_cli_to_group(sesionID, msisdn, "DEFAULT ", "");
    }

    public void initSongSame() {

        adapterRingtunes = new AdapterRingtunes(lisRingtunes_same, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
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
            listSongSame.clear();
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
                        lisItem.get(position).getContent_id(), Constant.USER_ID);
            else if (position < 0 && lisItem.size() > 0) {
                presenterGroupMember.api_suggestion_play(lisItem.get(0).getSinger_id(),
                        lisItem.get(0).getContent_id(), Constant.USER_ID);
            }
        } else{
            adapterRingtunes.notifyDataSetChanged();
            hideDialogLoading();
        }



    }

    public void initConllection() {
        adapterCollection = new AdapterCollectionGroup(lisItem, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_ringtunes_group_member.setHasFixedSize(true);
        recycle_ringtunes_group_member.setNestedScrollingEnabled(false);
        recycle_ringtunes_group_member.setLayoutManager(mLayoutManager);
        recycle_ringtunes_group_member.setItemAnimator(new DefaultItemAnimator());
        recycle_ringtunes_group_member.setAdapter(adapterCollection);
        adapterCollection.notifyDataSetChanged();

        adapterCollection.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(final int position) {
                final Dialog dialog_yes = new Dialog(getContext());
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
                dialog_yes.show();

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
            lisItem.addAll(list);
            for (int i = 0; i < lisItem.size(); i++) {
                if (lisItem.get(i).getContent_id().equals(content_id)) {
                    lisItem.get(i).setIs_check(true);
                }
                if (i < lisItem.size() - 1)
                    id_songs = id_songs + lisItem.get(i).getContent_id() + ",";
                else id_songs = id_songs + listConllection.get(i).getContent_id();
            }
            presenterGroupMember.get_info_songs_collection(id_songs, Constant.USER_ID);
        } else {
            hideDialogLoading();
            lisRingtunes_same.addAll(lisRingtunesNew);
            adapterRingtunes.notifyDataSetChanged();
        }
    }
}
