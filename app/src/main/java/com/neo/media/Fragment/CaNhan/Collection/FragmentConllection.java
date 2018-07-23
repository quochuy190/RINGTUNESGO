package com.neo.media.Fragment.CaNhan.Collection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Activity.FragmentHomeActivity;
import com.neo.media.Adapter.AdapterCollection;
import com.neo.media.Adapter.AdapterRingtunes;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.Model.Login;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.Utilities;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.alexrs.prefs.lib.Prefs;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.MyApplication.listConllection;


/**
 * Created by QQ on 7/24/2017.
 */

public class FragmentConllection extends BaseFragment implements ConllectionInteface.View {
    //cais nayf
    public static FragmentConllection fragmentConllection;
    public static PresenterConllection presenterConllection;
    String page = "1";
    String index = "10";
    @BindView(R.id.list_songs_conllection)
    RecyclerView recycler_lis_conllection;
    @BindView(R.id.txt_title_tabar_conllection)
    TextView txt_title;
    @BindView(R.id.list_songs_conllection_same)
    RecyclerView recycle_lis_song_same;
    @BindView(R.id.img_back_conllection)
    ImageView back_conllection;
    AdapterCollection adapterCollection;
    AdapterRingtunes adapterRingtunes;
    RecyclerView.LayoutManager mLayoutManager;
    List<Ringtunes> lisRingtunes_same;
    Info_User objInfo;
    Login objLogin;
    public Realm realm;

    @BindView(R.id.relative_conllection)
    LinearLayout relative_conllection;
    @BindView(R.id.txt_notification_conllection)
    TextView txt_notification_conllection;
    public static String user_id;
    SharedPreferences pre;
    boolean isHome;
    public List<Ringtunes> mlisSongs;
    @BindView(R.id.txt_chon_bosuutap)
    TextView txt_chon_bosuutap;
    boolean isChon = false;
    @BindView(R.id.txt_title_conllection)
    TextView txt_title_conllection;
    @BindView(R.id.img_search_collection)
    ImageView img_search_collection;
    private boolean is_subscriber, is_SVC_STATUS;
    private boolean isLogin;
    public static String sesionID;
    public static String msisdn;
    public static FragmentConllection getInstance() {
        if (fragmentConllection == null) {
            synchronized (FragmentSongs.class) {
                if (fragmentConllection == null) {
                    fragmentConllection = new FragmentConllection();
                }
            }
        }
        return fragmentConllection;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        //isHome = pre.getBoolean("isHome", false);

        user_id = Prefs.with(getActivity()).getString("user_id", "");
        //id_Singer= pre.getString("isSinger", "");
        sesionID = Prefs.with(getActivity()).getString("sessionID", "");
        msisdn = Prefs.with(getActivity()).getString("msisdn", "");
        presenterConllection = new PresenterConllection(this);
        is_subscriber = Prefs.with(getActivity()).getBoolean("is_subscriber", false);
        is_SVC_STATUS = Prefs.with(getActivity()).getBoolean("is_SVC_STATUS", false);
        isLogin = Prefs.with(getActivity()).getBoolean("isLogin", false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conllection, container, false);
        ButterKnife.bind(this, view);
        realm = RealmController.with(this).getRealm();
        mlisSongs = new ArrayList<>();

        init();
        initEvent();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLogin) {
            if (is_subscriber) {
                relative_conllection.setVisibility(View.VISIBLE);
                txt_notification_conllection.setVisibility(View.GONE);
                showDialogLoading();
                presenterConllection.getConllection(sesionID, msisdn);
            } else {
                relative_conllection.setVisibility(View.GONE);
                txt_notification_conllection.setVisibility(View.VISIBLE);
            }
        }
        //  MainNavigationActivity.appbar.setVisibility(View.GONE);
        txt_title.setText("Bộ sưu tập cá nhân");
    }

    @Override
    public void onPause() {
        super.onPause();
        isChon =false;
        txt_chon_bosuutap.setText("Chọn");
    }


    private void init() {
        adapterCollection = new AdapterCollection(listConllection, getContext(), presenterConllection);
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycler_lis_conllection.setHasFixedSize(true);
        recycler_lis_conllection.setNestedScrollingEnabled(false);
        recycler_lis_conllection.setLayoutManager(mLayoutManager);
        recycler_lis_conllection.setItemAnimator(new DefaultItemAnimator());
        recycler_lis_conllection.setAdapter(adapterCollection);

        adapterCollection.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (mlisSongs.size() > 0) {
                    for (int i = 0; i < mlisSongs.size(); i++) {
                        if (mlisSongs.get(i).getId().equals(listConllection.get(position).getContent_id())) {
                            MyApplication.player_ring = Ringtunes.getInstance();
                            SharedPreferences.Editor editor = pre.edit();
                            MyApplication.player_ring = mlisSongs.get(i);
                            editor.putBoolean("isHome", false);
                            editor.putString("idSinger", mlisSongs.get(i).getSinger_id());
                            editor.commit();
                            if (!FragmentDetailBuySongs.getInstance().isAdded())
                                FragmentUtil.addFragment(getActivity(), FragmentDetailBuySongs.getInstance(), true);
                        }
                    }
                }

            }

            @Override
            public void OnLongItemClickListener(final int position) {
              /*  final Dialog dialog_yes = new Dialog(getContext());
                dialog_yes.setContentView(R.layout.dialog_yes_no);
                Button yes = (Button) dialog_yes.findViewById(R.id.btn_dialog_yes);
                Button no = (Button) dialog_yes.findViewById(R.id.btn_dialog_no);
                TextView txt_message = (TextView) dialog_yes.findViewById(R.id.dialog_message);
                txt_message.setText("Bạn có muốn xoá bài hát " + listConllection.get(position).getContent_title() + " ra khỏi bộ sưu tập");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        presenterConllection.deleteConllection(sesionID, msisdn,
                                listConllection.get(position).getContent_id(), objLogin.getsUserName());
                        dialog_yes.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_yes.dismiss();
                    }
                });
                dialog_yes.show();*/

            }
        });
        // khởi tạo cho recycleview bai hat tương tự
        lisRingtunes_same = new ArrayList<>();
        adapterRingtunes = new AdapterRingtunes(lisRingtunes_same, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_lis_song_same.setHasFixedSize(true);
        recycle_lis_song_same.setNestedScrollingEnabled(false);
        recycle_lis_song_same.setLayoutManager(mLayoutManager);
        recycle_lis_song_same.setItemAnimator(new DefaultItemAnimator());
        recycle_lis_song_same.setAdapter(adapterRingtunes);

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

    public static void deleteItem(Context context, final Item item) {

        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText(String.valueOf(Html.fromHtml("Bạn có chắc chắn muốn xoá bài hát <font color='#6f2b8e'>"
                        + item.getContent_title() + "</font> ra khỏi bộ sưu tập không ?")))
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
                        presenterConllection.deleteConllection(sesionID, msisdn,
                                item.getContent_id(), user_id);

                    }
                })
                .show();
    }

    boolean is_chon = false;

    private void initEvent() {
        txt_chon_bosuutap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChon) {
                    isChon = !isChon;
                    txt_chon_bosuutap.setText("Bỏ chọn");
                    if (listConllection.size() > 0) {
                        for (int i = 0; i < listConllection.size(); i++) {
                            listConllection.get(i).setIs_check(isChon);
                        }
                    }
                    adapterCollection.notifyDataSetChanged();

                } else {
                    isChon = !isChon;
                    txt_chon_bosuutap.setText("Chọn");
                    if (listConllection.size() > 0) {
                        for (int i = 0; i < listConllection.size(); i++) {
                            listConllection.get(i).setIs_check(isChon);
                        }
                    }
                    adapterCollection.notifyDataSetChanged();
                }

               /* if (!isChon){
                    txt_chon_bosuutap.setText("Bỏ chọn");
                    isChon = !isChon;
                }else {
                    txt_chon_bosuutap.setText("Chọn");
                    isChon = !isChon;
                }*/
            }
        });
        back_conllection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }
        });
        img_search_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.popBackStack(getActivity());
                FragmentHomeActivity.tab_search();
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
        } else {
            lisRingtunes_same.clear();
            lisRingtunes_same.addAll(MyApplication.lisRingtunesNew);
            adapterRingtunes.notifyDataSetChanged();
        }
    }

    @Override
    public void showConllection(final List<Item> listItems) {
        listConllection.clear();
        String id_songs = "";
        if (listItems != null && listItems.size() > 0) {
            txt_title_conllection.setVisibility(View.VISIBLE);
            txt_notification_conllection.setVisibility(View.GONE);
            txt_chon_bosuutap.setVisibility(View.VISIBLE);
            img_search_collection.setVisibility(View.GONE);
            listConllection.addAll(listItems);
            for (int i = 0; i < listConllection.size(); i++) {
                if (i < listConllection.size() - 1)
                    id_songs = id_songs + listConllection.get(i).getContent_id() + ",";
                else id_songs = id_songs + listConllection.get(i).getContent_id();
            }
            presenterConllection.get_info_songs_collection(id_songs, user_id);

        } else {
            adapterCollection.notifyDataSetChanged();
            lisRingtunes_same.addAll(MyApplication.lisRingtunesNew);
            adapterRingtunes.notifyDataSetChanged();
            txt_notification_conllection.setVisibility(View.VISIBLE);
            txt_title_conllection.setVisibility(View.GONE);
            txt_chon_bosuutap.setVisibility(View.GONE);
            img_search_collection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDeleteSuccessfull(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                presenterConllection.getConllection(sesionID, msisdn);
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText(list.get(1))
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();

                            }
                        }).show();
                //  Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();

            } else {
                show_notification("Thông báo", list.get(1));
                //Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            }
        } else
            show_notification("Thông báo", "Lỗi kết nối, mời bạn kiểm tra lại kết nối mạng và thử lại");
            //Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void show_list_songs_collection(List<Ringtunes> listSongs) {
        mlisSongs.clear();
        if (listSongs.size() > 0) {
            for (int i = 0; i < listSongs.size(); i++) {
                for (int j = 0; j < listConllection.size(); j++) {
                    if (listSongs.get(i).getId().equals(listConllection.get(j).getContent_id())) {
                        mlisSongs.add(listSongs.get(i));
                        listConllection.get(j).setImage_url(listSongs.get(i).getImage_file());
                        listConllection.get(j).setProduct_name(listSongs.get(i).getProduct_name());
                        listConllection.get(j).setSinger_id(listSongs.get(i).getSinger_id());
                        listConllection.get(j).setPath(listSongs.get(i).getPath());
                    }
                }
            }
            for (int k = 0; k < listConllection.size(); k++) {
                listConllection.get(k).setIs_check(isChon);
            }
            adapterCollection.notifyDataSetChanged();
            int random = Utilities.getRandomNumber(listSongs.size());
            // Log.i("collection", ""+random);
            presenterConllection.api_suggestion_play(listSongs.get(random).getSinger_id(),
                    listSongs.get(random).getId(), Constant.USER_ID);
        } else {
            hideDialogLoading();
            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
        }


    }


}
