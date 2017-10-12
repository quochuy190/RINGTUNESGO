package com.neo.ringtunesgo.Fragment.Groups.GroupMember;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.ringtunesgo.Adapter.AdapterCollectionGroup;
import com.neo.ringtunesgo.Adapter.AdapterRingtunes;
import com.neo.ringtunesgo.Adapter.Adapter_Group_Member;
import com.neo.ringtunesgo.CRBTModel.CLI;
import com.neo.ringtunesgo.CRBTModel.GROUP;
import com.neo.ringtunesgo.CRBTModel.Info_User;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.CRBTModel.PROFILE;
import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Config.Constant;
import com.neo.ringtunesgo.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.ringtunesgo.Fragment.Collection.ConllectionInteface;
import com.neo.ringtunesgo.Fragment.Groups.FragmentGroups;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.GroupName;
import com.neo.ringtunesgo.Model.Login;
import com.neo.ringtunesgo.Model.PhoneContactModel;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.MyApplication;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.View.ActivityContacts;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.PhoneNumber;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.ringtunesgo.MyApplication.lisRingtunesNew;
import static com.neo.ringtunesgo.MyApplication.listConllection;

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
        sesionID = pre.getString("sesionID", "");
        msisdn = pre.getString("msisdn", "");
        objGrop = MyApplication.objGroup;
        group_id = objGrop.getId();
        lisCLI = objGrop.getClis().getCLI();
        title = objGrop.getName();
        objGroupName = realm.where(GroupName.class).equalTo("id_group", group_id).findFirst();
        if (objLogin != null) {
            if (objInfo != null) {
                if (objInfo.getStatus().equals("2")) {
                    presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
                    // presenterGroupMember.getConllection(sesionID, msisdn);
                    presenterGroupMember.getProfiles_By_GroupId(sesionID, msisdn, group_id, "GROUP");
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


    private void initDialog() {
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
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MainNavigationActivity.datasvina = new ArrayList<PhoneContactModel>();
                MainNavigationActivity.datas = new ArrayList<PhoneContactModel>();
                MainNavigationActivity.listGitSongs = new ArrayList<PhoneContactModel>();
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("option", Config.GROUP_MEMBER);
                editor.commit();
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
                if (all_phone.length() > 0) {

                    presenterGroupMember.add_cli_to_group(sesionID, msisdn, group_id, all_phone);
                    dialog.dismiss();
                } else
                    Toast.makeText(getContext(), "Bạn chưa nhập vào số điện thoại", Toast.LENGTH_SHORT).show();

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
                        String phone = lisPhone.get(position).getPhoneNumber();
                        phone = PhoneNumber.convertTo84PhoneNunber(phone);
                        presenterGroupMember.delete_cli_to_group(sesionID, msisdn, group_id, phone);
                        dialog_yes_member.dismiss();
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
    public void showGroupMember(final List<PhoneContactModel> phoneContactModels) {
        lisPhone.clear();
        if (phoneContactModels.size() > 0) {
            lisPhone.addAll(phoneContactModels);
            adapter_group_member.notifyDataSetChanged();
        }
    }

    @Override
    public void show_add_cli_group(List<String> list) {
        presenterGroupMember.getAllMember(sesionID, msisdn, group_id);
        Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Delete thành công", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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
                FragmentGroups.add_Group();
                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            } else Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
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
        } else
            adapterRingtunes.notifyDataSetChanged();


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

                txt_buysongs.setText("Bạn có muốn cài bài hát " + lisItem.get(position).getContent_title() + " làm nhạc chờ cho nhóm");
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
        }else {
            lisRingtunes_same.addAll(lisRingtunesNew);
            adapterRingtunes.notifyDataSetChanged();
        }
    }
}
