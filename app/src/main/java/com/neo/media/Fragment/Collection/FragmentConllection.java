package com.neo.media.Fragment.Collection;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterCollection;
import com.neo.media.Adapter.AdapterRingtunes;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.Login;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.DialogUtil;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.Utilities;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.MyApplication.listConllection;


/**
 * Created by QQ on 7/24/2017.
 */

public class FragmentConllection extends BaseFragment implements ConllectionInteface.View {
    //cais nayf
    public static FragmentConllection fragmentConllection;
    PresenterConllection presenterConllection;
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
    public String sesionID;
    public String msisdn;
    @BindView(R.id.relative_conllection)
    RelativeLayout relative_conllection;
    @BindView(R.id.txt_notification_conllection)
    TextView txt_notification_conllection;
    String user_id;
    SharedPreferences pre;
    boolean isHome;
    public List<Ringtunes> mlisSongs;

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
        isHome = pre.getBoolean("isHome", false);
        user_id = pre.getString("user_id", "");
        //id_Singer= pre.getString("isSinger", "");
        sesionID = pre.getString("sesionID", "");
        msisdn = pre.getString("msisdn", "");
        presenterConllection = new PresenterConllection(this);
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
        objInfo = realm.where(Info_User.class).findFirst();
        // String s = objInfo.getError_code();
        objLogin = realm.where(Login.class).findFirst();
        if (objLogin != null) {
            if (objInfo != null) {
                if (objInfo.getStatus() != null && objInfo.getStatus().equals("2")) {
                    relative_conllection.setVisibility(View.VISIBLE);
                    txt_notification_conllection.setVisibility(View.GONE);
                    sesionID = objLogin.getsSessinonID();
                    msisdn = objLogin.getMsisdn();
                    presenterConllection.getConllection(sesionID, msisdn);
                } else {
                    relative_conllection.setVisibility(View.GONE);
                    txt_notification_conllection.setVisibility(View.VISIBLE);
                }
            }
        }
        MainNavigationActivity.appbar.setVisibility(View.GONE);
        txt_title.setText("BỘ SƯU TẬP CÁ NHÂN");

    }

    @Override
    public void onPause() {
        super.onPause();
        if (isHome)
            MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
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
                final Dialog dialog_yes = new Dialog(getContext());
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
                dialog_yes.show();

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

    private void initEvent() {
        back_conllection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
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
        }else {
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
            DialogUtil.showDialog(getContext(), "Thông báo", "Bạn chưa có bài hát nào trong bộ sưu tập");
        }
    }

    @Override
    public void showDeleteSuccessfull(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
                presenterConllection.getConllection(sesionID, msisdn);
            } else {
                Toast.makeText(getContext(), list.get(1), Toast.LENGTH_SHORT).show();
            }
        } else Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void show_list_songs_collection(List<Ringtunes> listSongs) {
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
            adapterCollection.notifyDataSetChanged();
            int random = Utilities.getRandomNumber(listSongs.size());
            // Log.i("collection", ""+random);
            presenterConllection.api_suggestion_play(listSongs.get(random).getSinger_id(),
                    listSongs.get(random).getId(), Constant.USER_ID);
        } else{
            hideDialogLoading();
            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
        }


    }


}
