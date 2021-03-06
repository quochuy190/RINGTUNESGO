package com.neo.media.Fragment.DetailSongs.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neo.media.Activity.ActivityMainHome;
import com.neo.media.Adapter.AdapterListSongs;
import com.neo.media.Adapter.AdapterSongsCustom;
import com.neo.media.Config.Config;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.DetailSongs.Presenter.Presenter_Detail_Ringtunes;
import com.neo.media.Fragment.Favorite.FragmentPlayerFull;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.MyApplication.player_ring;

/**
 * Created by QQ on 7/12/2017.
 */

public class FragmentSongs extends BaseFragment implements View_RingtunesImpl, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = FragmentSongs.class.getSimpleName();
    Presenter_Detail_Ringtunes presenter_detail_ringtunes;
    public static FragmentSongs fragment;
    @BindView(R.id.recycler_list_downloaded)
    RecyclerView recycleSongs;
    /*    @BindView(R.id.pull_to_refresh_songs)
        SwipeRefreshLayout swipeRefreshLayout;*/
/*    @BindView(R.id.txt_title_lis_songs)
    TextView txt_title;*/
/*    @BindView(R.id.img_back_lisSongs)
    ImageView img_back_lisSongs;*/
    AdapterListSongs adapterRingtunes;
    AdapterSongsCustom adapterSongsCustom;
    List<Ringtunes> lisRing;
    private String url_image_title;
    String id;
    String title;
    int page = 1;
    int index = 30;
    boolean isLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.prb_loading_more_detailsongs)
    public ProgressBar progressBar;
    SharedPreferences pre;
    public String option;
    @BindView(R.id.movie_poster)
    ImageView imgHeader;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String type_event;
    @BindView(R.id.img_playall)
    ImageView img_playall;

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
        presenter_detail_ringtunes = new Presenter_Detail_Ringtunes(this);
        showData(page, index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloaded_music, container, false);
        ButterKnife.bind(this, view);
        init();
        initEvent();
        setToolbar();
        //  initPulltoRefresh();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private boolean isFavorite = false;

    private void initData() {
        lisRing = new ArrayList<>();
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        /*url_image_title = pre.getString("url_image_title", "");*/
        id = pre.getString("id", "");
        type_event = pre.getString("type_event", "");
        title = pre.getString("title", "");
        option = pre.getString("option", "");
        url_image_title = pre.getString("url_image_title", "");
        //Glide.with(getContext()).load(url_image_title).
        isFavorite = pre.getBoolean("isFavorite", false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityMainHome.relative_tab.setVisibility(View.GONE);

    }


    private void init() {
        adapterRingtunes = new AdapterListSongs(lisRing, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        // recycleSongs.setNestedScrollingEnabled(false);
        recycleSongs.setHasFixedSize(true);
        recycleSongs.setLayoutManager(mLayoutManager);
        recycleSongs.setItemAnimator(new DefaultItemAnimator());
        recycleSongs.setAdapter(adapterRingtunes);

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                player_ring = Ringtunes.getInstance();
                //  showNotification(lisRing.get(position).getProduct_name());
                SharedPreferences.Editor editor = pre.edit();
                player_ring = lisRing.get(position);
                editor.putBoolean("isHome", false);
                editor.putString("idSinger", lisRing.get(position).getSinger_id());
                editor.commit();

                if (!FragmentDetailBuySongs.getInstance().isAdded())
                    FragmentUtil.pushFragmentLayoutMain(getFragmentManager(), R.id.fame_main, FragmentDetailBuySongs.getInstance(),
                            TAG);
               /* if (!FragmentDetailBuySongs.getInstance().isAdded())
                    FragmentUtil.pushFragment(getActivity().getSupportFragmentManager(), R.id.fame_main,
                            FragmentDetailBuySongs.getInstance(), null);*/
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    private void initEvent() {
        img_playall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.lisPlayRing.clear();
                if (lisRing.size() > 1) {
                    for (int i = 1; i < lisRing.size(); i++) {
                        MyApplication.lisPlayRing.add(lisRing.get(i));
                    }
                    if (!FragmentPlayerFull.getInstance().isAdded())
                        FragmentUtil.addFragmentData(getActivity(), FragmentPlayerFull.getInstance(), true, null);
                }
            }
        });
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
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (!isLoading) {
                            isLoading = true;
                            lisRing.add(null);
                            adapterRingtunes.notifyDataSetChanged();
                            page++;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showData(page, index);
                                }
                            }, 500);
                        }
                    }
                 /*   GridLayoutManager layoutmanager = (GridLayoutManager) recyclerView.getLayoutManager();
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
                    }*/
                }
            }
        });
       /* img_back_lisSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentUtil.popBackStack(getActivity());
            }
        });*/
    }

    private void showData(int page, int index) {

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
            case Config.EVENT:
                if (type_event.equals("event_details"))
                    presenter_detail_ringtunes.getByEvent("event_details", id, "" + page, "" + index, Constant.USER_ID);
                else if (type_event.equals("promotion_details"))
                    presenter_detail_ringtunes.getByEvent("promotion_details", id, "" + page, "" + index, Constant.USER_ID);
                break;
        }
    }


    @Override
    public void showListRingtunes(final List<Ringtunes> listRingtunes) {
        hideDialogLoading();
        if (page > 1) {
            lisRing.remove(lisRing.size() - 1);
            adapterRingtunes.notifyDataSetChanged();
            if (listRingtunes != null && listRingtunes.size() > 0) {
                isLoading = false;
                lisRing.addAll(listRingtunes);
                adapterRingtunes.notifyDataSetChanged();
            }
        } else {
            lisRing.add(0, new Ringtunes("", url_image_title));
            if (listRingtunes != null && listRingtunes.size() > 0) {
                isLoading = false;
                lisRing.addAll(listRingtunes);
            }
            adapterRingtunes.notifyDataSetChanged();
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
        //     appbar.setVisibility(View.VISIBLE);
        if (isFavorite) {
            SharedPreferences.Editor editor = pre.edit();
            editor.putBoolean("isFavorite", false);
            editor.commit();
            ActivityMainHome.relative_tab.setVisibility(View.GONE);
        } else
            ActivityMainHome.relative_tab.setVisibility(View.VISIBLE);
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
                //  swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }

    @SuppressLint("RestrictedApi")
    private void setToolbar() {
        Glide.with(getContext()).load(Constant.IMAGE_URL + url_image_title).into(imgHeader);
        //collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
       /* collapsingToolbar.setTitle(title);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);*/

        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                /*actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDefaultDisplayHomeAsUpEnabled(true);*/

            }
        } else {
            // Don't inflate. Tablet is in landscape mode.
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
        if (!getActivity().isFinishing()) {
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
