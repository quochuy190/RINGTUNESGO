package com.neo.ringtunesgo.Fragment.DetailSongs.View;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.ringtunesgo.Adapter.AdapterRingtunes;
import com.neo.ringtunesgo.Adapter.AdapterSongsCustom;
import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.ringtunesgo.Fragment.DetailSongs.Presenter.Presenter_Detail_Ringtunes;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.neo.ringtunesgo.MainNavigationActivity.appbar;
import static com.neo.ringtunesgo.MyApplication.player_ring;

/**
 * Created by QQ on 7/12/2017.
 */

public class FragmentSongs extends BaseFragment implements View_RingtunesImpl, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = FragmentSongs.class.getSimpleName();
    Presenter_Detail_Ringtunes presenter_detail_ringtunes;
    public static FragmentSongs fragment;
    @BindView(R.id.list_detail_songs)
    RecyclerView recycleSongs;
    @BindView(R.id.pull_to_refresh_songs)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_title_lis_songs)
    TextView txt_title;
    @BindView(R.id.img_back_lisSongs)
    ImageView img_back_lisSongs;
    AdapterRingtunes adapterRingtunes;
    AdapterSongsCustom adapterSongsCustom;
    List<Ringtunes> lisRing;
    private String url_image_title;
    String id;
    String title;
    int page = 1;
    int index = 20;
    boolean isLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.prb_loading_more_detailsongs)
    public ProgressBar progressBar;
    SharedPreferences pre;
    public String option;
    public static FragmentSongs getInstance() {
        if (fragment == null) {
            synchronized (FragmentSongs.class) {
                if (fragment == null) {
                    fragment = new FragmentSongs();
                }
            }
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        page = 1;
        showData(page, index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_songs, container, false);
        ButterKnife.bind(this, view);
        init();
        initEvent();
        initPulltoRefresh();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void initData() {
        lisRing = new ArrayList<>();
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        /*url_image_title = pre.getString("url_image_title", "");*/
        id = pre.getString("id", "");
        title = pre.getString("title", "");
        option = pre.getString("option", "");
        url_image_title = pre.getString("url_image_title", "");
    }

    @Override
    public void onResume() {
        super.onResume();
        txt_title.setText(""+title);
        MainNavigationActivity.appbar.setVisibility(View.GONE);
    }


    private void initPulltoRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void init() {
        adapterSongsCustom = new AdapterSongsCustom(getContext(), lisRing, "abc");
        mLayoutManager = new GridLayoutManager(getContext(), 1);
       // recycleSongs.setNestedScrollingEnabled(false);
        recycleSongs.setHasFixedSize(true);
        recycleSongs.setLayoutManager(mLayoutManager);
        recycleSongs.setItemAnimator(new DefaultItemAnimator());
        recycleSongs.setAdapter(adapterSongsCustom);

        adapterSongsCustom.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                player_ring=Ringtunes.getInstance();
                //  showNotification(lisRing.get(position).getProduct_name());
                SharedPreferences.Editor editor=pre.edit();
                player_ring = lisRing.get(position);
                editor.putBoolean("isHome", false);
                editor.putString("idSinger", lisRing.get(position).getSinger_id());
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
        recycleSongs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    GridLayoutManager layoutmanager = (GridLayoutManager) recyclerView.getLayoutManager();
                    visibleItemCount = layoutmanager.getChildCount();
                    totalItemCount = layoutmanager.getItemCount();
                    pastVisiblesItems = layoutmanager.findFirstVisibleItemPosition();
                    Log.i(TAG, visibleItemCount + " " + totalItemCount + " " + presenter_detail_ringtunes);
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (!isLoading) {
                            isLoading = true;
                            page++;
                            showData(page, index);

                            CountDownTimer countDownTimer = new CountDownTimer(5 * 200, 200) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                       progressBar.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onFinish() {
                                    progressBar.setVisibility(View.GONE);
                                }
                            };
                            countDownTimer.start();
                        }
                    }
                }
            }
        });
        img_back_lisSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentUtil.popBackStack(getActivity());
            }
        });
    }

    private void showData(int page, int index) {
        presenter_detail_ringtunes = new Presenter_Detail_Ringtunes(this);
        switch (option) {
            case Config.TOPIC:
                presenter_detail_ringtunes.getByTopic(id, "" + page, "" + index);
                break;
            case Config.ALBUM:
                presenter_detail_ringtunes.getByAlbum(id, "301", "" + page, "" + index);
                break;
            case Config.TYPE:
                presenter_detail_ringtunes.getByType(id, "1", "" + page, "" + index);
                break;
            case Config.SINGER:
                presenter_detail_ringtunes.getBySinger(id, "0", "" + page, "" + index);
                break;
            case Config.RINGTUNES_HOT:
                presenter_detail_ringtunes.getByRingtunesHot("252", "" + page, "" + index);
                break;
            case Config.RINGTUNES_NEW:
                presenter_detail_ringtunes.getByRingtunesNew("251", "" + page, "" + index);
                break;
        }
    }


    @Override
    public void showListRingtunes(final List<Ringtunes> listRingtunes) {
        hideDialogLoading();
        if (listRingtunes != null && listRingtunes.size() > 0) {
            Log.i(TAG, "page " + page + " list.size" + listRingtunes.size() + " lisRing.size" + lisRing.size());
            isLoading = false;
            lisRing.addAll(listRingtunes);
            Ringtunes objRing = new Ringtunes();
            objRing.setImage_file(url_image_title);
            lisRing.add(0, objRing);
            adapterSongsCustom.notifyDataSetChanged();
        }
    }

    @Override
    public void showBacgroup(String urlImage) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        appbar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                page = 1;
                isLoading = true;
                lisRing = new ArrayList<>();
                showData(page, index);
                init();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }

    public void showNotification(String title) {
        int notification = 113;
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                        // .setSmallIcon(R.drawable.icon_bag_playing)
                        .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.icon_pause_service))
                        .setContentTitle(title)
                        .setContentText("Mời bạn nhấn để cập nhật version");

        NotificationManager mNotifyMgr =
                (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notification, mBuilder.build());

    }
    public void hideDialogLoadingSongs() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected ProgressDialog dialog;
    private Handler StopDialogLoadingHandler = new Handler();
    public void showDialogLoadingSongs() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 5000);
        if (!getActivity().isFinishing()){
            if (dialog == null) {
                dialog = new ProgressDialog(getActivity());
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
            }
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

}
