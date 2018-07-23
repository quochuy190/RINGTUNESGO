package com.neo.media.Fragment.Favorite;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.Adapter.AdapterFavorite;
import com.neo.media.Config.Config;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Songs;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.setOnItemClickListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.Config.Constant.IMAGE_URL;
import static com.neo.media.MyApplication.lisPromotion;
import static com.neo.media.MyApplication.player_ring;

/**
 * Created by QQ on 7/7/2017.
 */

public class FragmentFavorite extends BaseFragment {
    //  String TAG = FragmentAlbum.class.getSimpleName();
    public static FragmentFavorite fragment;
    public static List<Ringtunes> lisRingtunes;
    public static List<Songs> lisSongs;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isClickItem =false;

    public static FragmentFavorite getInstance() {
        if (fragment == null) {
            synchronized (FragmentFavorite.class) {
                if (fragment == null)
                    fragment = new FragmentFavorite();
            }
        }
        return fragment;
    }

    public static AdapterFavorite adapterRingtunes;

    public static RecyclerView recycle_favorite;
    public static Realm realm;
    @BindView(R.id.img_banner_favorite)
    ImageView img_banner_favorite;
    public static TextView txt_favorite;
    public static Button btn_listener_all;
    @BindView(R.id.txt_list_favorite_count)
    TextView txt_list_favorite_count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(this).getRealm();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        btn_listener_all = view.findViewById(R.id.btn_listener_all);
        txt_favorite = view.findViewById(R.id.txt_emty_favorite);
        recycle_favorite = view.findViewById(R.id.recycle_favorite);
        initData();
        initTopic();
        initEvent();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initEvent() {
        img_banner_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickItem) {
                    isClickItem = true;
                    SharedPreferences.Editor editor = pre.edit();
                    editor.putString("option", Config.EVENT);
                    editor.putBoolean("isFavorite", true);
                    editor.putString("id", MyApplication.lisPromotion.get(0).getId());
                    if (lisPromotion.size()>0){
                        if (lisPromotion.get(0).getBigphoto() != null) {
                            editor.putString("type_event", "promotion_details");
                            editor.putString("url_image_title", lisPromotion.get(0).getBigphoto());
                            editor.putString("title", lisPromotion.get(0).getNAME());
                        }
                        if (lisPromotion.get(0).getTHUMBNAIL_IMAGE() != null) {
                            editor.putString("type_event", "event_details");
                            editor.putString("url_image_title", lisPromotion.get(0).getTHUMBNAIL_IMAGE());
                            editor.putString("title", lisPromotion.get(0).getNAME());
                        }
                    }
                    editor.commit();
                    if (!FragmentSongs.getInstance().isAdded())
                        FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isClickItem = false;
                        }
                    }, 500);}

            }
        });
        btn_listener_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickItem) {
                    isClickItem = true;
                    if (lisRingtunes.size() > 0) {
                        MyApplication.lisPlayRing.clear();
                        MyApplication.lisPlayRing.addAll(lisRingtunes);
                        if (!FragmentPlayerFull.getInstance().isAdded())
                            FragmentUtil.addFragmentData(getActivity(), FragmentPlayerFull.getInstance(), true, null);
                    } else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Thông báo")
                                .setContentText("Bạn chưa có bài hát nào trong bộ yêu thích, ")
                                .setConfirmText("Đóng")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isClickItem = false;
                        }
                    }, 500);}


                // FragmentUtil.addFragment(getActivity(), FragmentPlayerFull.getInstance(), true);

            }
        });
    }

    SharedPreferences pre;
    String url_image_title;

    public static void initData() {
        lisRingtunes = new ArrayList<>();
        lisSongs = realm.where(Songs.class).findAll();
        if (lisSongs != null && lisSongs.size() > 0) {
            for (int i = 0; i < lisSongs.size(); i++) {
                Ringtunes objRing = new Ringtunes();
                objRing.setId(lisSongs.get(i).getId());
                objRing.setCOUNTER(lisSongs.get(i).getCOUNTER());
                objRing.setCp_name(lisSongs.get(i).getCp_name());
                objRing.setCreate_date(lisSongs.get(i).getCreate_date());
                objRing.setDescription(lisSongs.get(i).getDescription());
                objRing.setHist(lisSongs.get(i).getHist());
                objRing.setImage_file(lisSongs.get(i).getImage_file());
                objRing.setIs_new(lisSongs.get(i).getIs_new());
                objRing.setModify_date(lisSongs.get(i).getModify_date());
                objRing.setPath(lisSongs.get(i).getPath());
                objRing.setPrice(lisSongs.get(i).getPrice());
                objRing.setProduct_name(lisSongs.get(i).getProduct_name());
                objRing.setProduct_name_no(lisSongs.get(i).getProduct_name_no());
                objRing.setProduct_name_s(lisSongs.get(i).getProduct_name_s());
                objRing.setsExpiration(lisSongs.get(i).getsExpiration());
                objRing.setSinger_id(lisSongs.get(i).getSinger_id());
                objRing.setSinger_name(lisSongs.get(i).getSinger_name());
                objRing.setStatus_id(lisSongs.get(i).getStatus_id());

                lisRingtunes.add(objRing);
            }
            if (lisRingtunes.size() > 0) {
                txt_favorite.setVisibility(View.GONE);
                recycle_favorite.setVisibility(View.VISIBLE);
                btn_listener_all.setVisibility(View.VISIBLE);
            }
        } else {
            txt_favorite.setVisibility(View.VISIBLE);
            recycle_favorite.setVisibility(View.GONE);
            btn_listener_all.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        //ActivityMainHome.relative_tab.setVisibility(View.VISIBLE);
    }

    public void initTopic() {
        if (MyApplication.img_banner_favorite.length() > 0)
            Glide.with(getContext()).load(IMAGE_URL + MyApplication.img_banner_favorite).into(img_banner_favorite);
        else {
            Glide.with(getActivity()).load(IMAGE_URL + lisRingtunes.get(0).getImage_file()).into(img_banner_favorite);
        }
        //  Glide.with(getActivity()).load(IMAGE_URL +lisSongs.get(0).getImage_file()).into(img_banner_favorite);
        txt_list_favorite_count.setText("Tổng: " + lisRingtunes.size() + " bài");
        adapterRingtunes = new AdapterFavorite(lisRingtunes, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        // recycleTopic.setNestedScrollingEnabled(false);
        recycle_favorite.setHasFixedSize(true);
        recycle_favorite.setLayoutManager(mLayoutManager);
        recycle_favorite.setItemAnimator(new DefaultItemAnimator());
        recycle_favorite.setAdapter(adapterRingtunes);
        adapterRingtunes.notifyDataSetChanged();

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (!isClickItem) {
                    isClickItem = true;
                    player_ring = Ringtunes.getInstance();
                    //  showNotification(lisRing.get(position).getProduct_name());
                    SharedPreferences.Editor editor = pre.edit();
                    editor.putBoolean("isHome", false);
                    editor.putString("idSinger", lisRingtunes.get(position).getSinger_id());
                    editor.commit();
                    player_ring = lisRingtunes.get(position);
                    if (!FragmentDetailBuySongs.getInstance().isAdded())
                        FragmentUtil.addFragment(getActivity(), FragmentDetailBuySongs.getInstance(), true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isClickItem = false;
                        }
                    }, 500);}
            }
            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    public static void deleteItem(Context context, final int position) {
        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Thông báo")
                .setContentText(String.valueOf(Html.fromHtml("Bạn có chắc chắn muốn xoá bài hát <font color='#6f2b8e'>"
                        + lisRingtunes.get(position).getProduct_name() + "</font> ra khỏi danh sách yêu thích không ?")))
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
                        realm.beginTransaction();
                        RealmResults<Songs> rows = realm.where(Songs.class).equalTo("Id",
                                lisRingtunes.get(position).getId()).findAll();
                        if (rows != null)
                            rows.clear();
                        realm.commitTransaction();
                        initData();
                        adapterRingtunes.update_notifycation(lisRingtunes);
                    }
                })
                .show();
    }

}
