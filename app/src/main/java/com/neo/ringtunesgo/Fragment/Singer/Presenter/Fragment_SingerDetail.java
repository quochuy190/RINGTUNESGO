package com.neo.ringtunesgo.Fragment.Singer.Presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.ringtunesgo.Adapter.AdapterRingtunes;
import com.neo.ringtunesgo.Config.Constant;
import com.neo.ringtunesgo.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.Model.Singer;
import com.neo.ringtunesgo.MyApplication;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by QQ on 10/5/2017.
 */

public class Fragment_SingerDetail extends BaseFragment implements PresenterSingerImpl.View {
    public static Fragment_SingerDetail fragmentSingerDetail;

    public static Fragment_SingerDetail getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (Fragment_SingerDetail.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new Fragment_SingerDetail();
            }
        }
        return fragmentSingerDetail;
    }

    @BindView(R.id.img_singerdetail)
    ImageView img_singer;
    @BindView(R.id.img_buysong_detail_nen)
    ImageView img_singer_backgroup;
    @BindView(R.id.txt_name_singerdetail)
    TextView txt_name_singerdetail;
    @BindView(R.id.txt_hitssingerdetail)
    TextView txt_hitssingerdetail;
    @BindView(R.id.txt_singer_deps)
    TextView txt_singer_deps;
    @BindView(R.id.recycle_list_song_buysinger)
    RecyclerView recycle_list_song_buysinger;
    SharedPreferences fr;
    String id_Singer;
    Boolean isHome;
    List<Ringtunes> lisSongs;
    Singer singer;
    boolean back_down = false;
    @BindView(R.id.relative_back_đown)
    RelativeLayout relative_back_đown;
    @BindView(R.id.img_back_down)
    ImageView img_back_đown;
    @BindView(R.id.img_back_up)
    ImageView img_back_up;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterRingtunes adapterRingtunes;
    int page;
    String index = "20";
    @BindView(R.id.txt_singer_deps_full)
    TextView txt_singer_deps_full;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        page = 1;
        singer = new Singer();
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        id_Singer = fr.getString("idSinger", "");
        singer.setFULL_NAME(fr.getString("name_singer", ""));
        singer.setHits(fr.getString("hits_singer", ""));
        singer.setPhoto(fr.getString("url_imgSinger", ""));
        singer.setId(id_Singer);
        lisSongs = new ArrayList<>();
        PresenterSinger presenterSinger = new PresenterSinger(this);
        presenterSinger.getDetailSinger(id_Singer);
        presenterSinger.getSongsBySinger(id_Singer, "" + page, index);
    }

    public void updateData(Singer objSinger) {
        if (objSinger != null) {
            if (objSinger.getFULL_NAME() != null)
                txt_name_singerdetail.setText(objSinger.getFULL_NAME());
            if (objSinger.getPhoto() != null) {
                Glide.with(getContext()).load(Constant.IMAGE_URL + objSinger.getPhoto().replaceAll(" ", "%20")).into(img_singer);
                Glide.with(getContext()).load(Constant.IMAGE_URL + objSinger.getPhoto().replaceAll(" ", "%20")).into(img_singer_backgroup);
            }
            if (objSinger.getHits() != null)
                txt_hitssingerdetail.setText(objSinger.getHits());
            if (objSinger.getDESCRIPTION() != null&&objSinger.getDESCRIPTION().length()>0) {
                relative_back_đown.setVisibility(View.VISIBLE);
                txt_singer_deps.setText(objSinger.getDESCRIPTION());
                txt_singer_deps_full.setText(objSinger.getDESCRIPTION());
            } else relative_back_đown.setVisibility(View.GONE);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singer_detail, container, false);
        ButterKnife.bind(this, view);
        initSongs();
        initEvnet();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initSongs() {
        adapterRingtunes = new AdapterRingtunes(lisSongs, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_list_song_buysinger.setNestedScrollingEnabled(false);
        recycle_list_song_buysinger.setHasFixedSize(true);
        recycle_list_song_buysinger.setLayoutManager(mLayoutManager);
        recycle_list_song_buysinger.setItemAnimator(new DefaultItemAnimator());
        recycle_list_song_buysinger.setAdapter(adapterRingtunes);

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                MyApplication.player_ring = lisSongs.get(position);
                editor.putBoolean("isHome", false);
                editor.putString("idSinger", lisSongs.get(position).getSinger_id());
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
    public void onResume() {
        super.onResume();
        MainNavigationActivity.appbar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MainNavigationActivity.appbar.setVisibility(View.VISIBLE);
    }

    private void initEvnet() {
        relative_back_đown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!back_down) {
                    txt_singer_deps.setVisibility(View.GONE);
                    txt_singer_deps_full.setVisibility(View.VISIBLE);
                    back_down = !back_down;
                    img_back_đown.setVisibility(View.GONE);
                    img_back_up.setVisibility(View.VISIBLE);
                } else {
                    txt_singer_deps.setVisibility(View.VISIBLE);
                    txt_singer_deps_full.setVisibility(View.GONE);
                    back_down = !back_down;
                    img_back_up.setVisibility(View.GONE);
                    img_back_đown.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void showSingerDetail(List<Singer> lisSinger) {
        if (lisSinger.size() > 0) {
            if (lisSinger.get(0).getDESCRIPTION() != null) {
                singer.setDESCRIPTION(lisSinger.get(0).getDESCRIPTION());
                updateData(singer);
            }
        } else updateData(singer);
    }

    @Override
    public void showSongbySinger(List<Ringtunes> lisRing) {
        if (lisRing.size() > 0) {
            lisSongs.addAll(lisRing);
            adapterRingtunes.notifyDataSetChanged();
        }
    }
}
