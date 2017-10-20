package com.neo.media.Fragment.Home.View;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.neo.media.Adapter.AdapterAlbum;
import com.neo.media.Adapter.AdapterEventHome;
import com.neo.media.Adapter.AdapterRingtunes;
import com.neo.media.Adapter.AdapterSinger;
import com.neo.media.Adapter.AdapterSlogan;
import com.neo.media.Adapter.AdapterTopic;
import com.neo.media.Adapter.AdapterType;
import com.neo.media.Adapter.AdapterViewPagerHome;
import com.neo.media.Config.Config;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.Fragment.Home.Presenter.PresenterHome;
import com.neo.media.Fragment.Singer.Presenter.Fragment_SingerDetail;
import com.neo.media.Model.Banner;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Singer;
import com.neo.media.Model.Topic;
import com.neo.media.Model.Type;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.View.HomeFragment;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.CustomSliderView;
import com.neo.media.untils.DialogUtil;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.MainNavigationActivity.fragmentActivity;
import static com.neo.media.MyApplication.player_ring;

/**
 * Created by QQ on 7/7/2017.
 */

public class FragmentHome extends BaseFragment implements FragmentHomeImpl, SwipeRefreshLayout.OnRefreshListener, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {
    String TAG = FragmentHome.class.getSimpleName();
    public static FragmentHome fragment;
    PresenterHome presenterHome;
    @BindView(R.id.pull_to_refresh_home)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycle_home_event)
    RecyclerView recycle_home_event;
    @BindView(R.id.recycle_slogan)
    RecyclerView recycle_slogan;
    @BindView(R.id.list_topic_hot)
    RecyclerView recyclerTopichot;
    @BindView(R.id.list_album_hot)
    RecyclerView recyclerAlbumHot;
    @BindView(R.id.list_ringtunes_hot)
    RecyclerView recyclerRingtunesHot;
    @BindView(R.id.list_ringtunes_new)
    RecyclerView recyclerRingtunesNew;
    @BindView(R.id.list_singer)
    RecyclerView recyclerSinger;
    @BindView(R.id.list_type)
    RecyclerView recyclerType;
    @BindView(R.id.chudehot)
    RelativeLayout viewAll_Topic;
    @BindView(R.id.next_ringtuneshot_IMG)
    RelativeLayout viewAll_Ringtunes_hot;
    @BindView(R.id.next_ringtunesnew_img)
    RelativeLayout viewAll_Ringtunes_new;
    @BindView(R.id.relative_type)
    RelativeLayout viewAll_Type;
    @BindView(R.id.relative_singer)
    RelativeLayout viewAll_Singer;
    AdapterTopic adapterTopic;
    AdapterEventHome adapterEventHome;
    AdapterAlbum adapterAlbum;
    AdapterRingtunes adapterRingtunes;
    AdapterSinger adapterSinger;
    AdapterSlogan adapterSlogan;
    AdapterType adapterType;
    List<Topic> listTop;
    View view;
    RecyclerView.LayoutManager mLayoutManager;
    String page = "1";
    String index = "4";
    AutoScrollViewPager objViewPager;
    TabLayout objTabLayout;
    AdapterViewPagerHome objAdapter;
    //RemindTask autoTask;
    String sUsername;
    List<String> arr;
    Timer timer;
    int page1 = 1;
    List<Ringtunes> lisRing, lisRingNew, listRingHot;
    public SharedPreferences fr;
    List<Banner> lisBanner;
    List<String> lisBanner_String;
    Handler handler = new Handler();
    List<Topic> listTopic;
    List<Singer> listSinger;
    List<Type> listType;
    @BindView(R.id.slider1)
    SliderLayout mDemoSlider;
    HashMap<String, String> url_maps;
    List<Topic> lisPromotion;
    List<Topic> lisSlogan;
    boolean is3g;
    boolean isWifi;

    public static FragmentHome getInstance() {
        if (fragment == null) {
            synchronized (FragmentSongs.class) {
                if (fragment == null) {
                    fragment = new FragmentHome();

                }
            }
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        sUsername = fr.getString("user_id", "");
        lisBanner = new ArrayList<>();
        lisBanner_String = new ArrayList<>();
        lisRingNew = new ArrayList<>();
        listRingHot = new ArrayList<>();
        listTopic = new ArrayList<>();
        listSinger = new ArrayList<>();
        listType = new ArrayList<>();
        lisPromotion = new ArrayList<>();
        lisSlogan = new ArrayList<>();
        url_maps = new HashMap<String, String>();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homes, container, false);
        ButterKnife.bind(this, view);
        initNetwork();
        objViewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        objTabLayout = (TabLayout) view.findViewById(R.id.tbl_showroom_detail_test);

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
        if (lisBanner_String.size() > 0) {
            showViewpager(lisBanner_String);
        }
    }

    public static void addFragmentBuySongs() {
        FragmentUtil.addFragment(fragmentActivity, FragmentDetailBuySongs.getInstance(), true);
    }

    public void initNetwork() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        //For 3G check
        is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

    }

    @Override
    public void onPause() {
        super.onPause();
        hideDialogLoading();
    }

    private void initPulltoRefesh() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    //
    private void initEvent() {
        viewAll_Topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.viewpagerHome.setCurrentItem(2);
            }
        });
       /* viewAll_Album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.viewpagerHome.setCurrentItem(1);
            }
        });*/
        viewAll_Type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.viewpagerHome.setCurrentItem(1);
            }
        });
        viewAll_Singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.viewpagerHome.setCurrentItem(3);
            }
        });
        viewAll_Ringtunes_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listRingHot.size() > 0) {
                    SharedPreferences.Editor editor = fr.edit();
                    editor.putString("option", Config.RINGTUNES_HOT);
                    editor.putString("title", "Ringtunes Hot");
                    editor.putString("id", "");
                    editor.putString("url_image_title", listRingHot.get(0).getImage_file());
                    editor.commit();
                    FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
                }

            }
        });
        viewAll_Ringtunes_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lisRingNew.size() > 0) {
                    SharedPreferences.Editor editor = fr.edit();
                    editor.putString("option", Config.RINGTUNES_NEW);
                    editor.putString("title", "Ringtunes Mới");
                    editor.putString("id", "");
                    editor.putString("url_image_title", lisRingNew.get(0).getImage_file());
                    editor.commit();
                    FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
                }
            }
        });

    }

    private void initHeaderSlider(HashMap<String, String> url_maps) {
        if (getContext() == null) return;
        for (String name : url_maps.keySet()) {
            CustomSliderView textSliderView = new CustomSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
//                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private void init() {
        if (listTop == null) {
            listTop = new ArrayList<>();
        }
        initTopic();
        initPromotion();
        initRingtunesHot();
        initRingtunesNew();
        initSinger();
        initType();
        initSlogan();

        if (url_maps.size() > 0) {
            initHeaderSlider(url_maps);
        }
        initPulltoRefesh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadDataErorr() {

    }

    @Override
    public void showViewpager(final List<String> lisStringBanner) {
        objAdapter = new AdapterViewPagerHome(lisStringBanner, R.layout.item_pager, getContext());
        objViewPager.setAdapter(objAdapter);
        objViewPager.startAutoScroll();
        objViewPager.setInterval(5000);
        objTabLayout.setupWithViewPager(objViewPager);

        if (lisStringBanner.size()>0){
            if (lisBanner.size()>0){
                objAdapter.setSetOnItemClickListener(new setOnItemClickListener() {
                    @Override
                    public void OnItemClickListener(int position) {
                        //String possion = lisStringBanner.get(position);
                        clickViewPage(lisBanner.get(position).getTYPE(), lisBanner.get(position));
                    }

                    @Override
                    public void OnLongItemClickListener(int position) {

                    }
                });
            }
        }
    }
    public void clickViewPage(String type, Banner objBaner){
        String subtype = objBaner.getSUBTYPE();
        String id = objBaner.getId();
        String image = objBaner.getImage();
        if (type.equals("1")) {
            SharedPreferences.Editor editor = fr.edit();
            editor.putBoolean("notifi", true);
            editor.putString("id_songs", id);
            editor.putString("id_singer", "");
            editor.commit();
            FragmentHome.addFragmentBuySongs();
        } else if (type.equals("2")) {
            SharedPreferences.Editor editor = fr.edit();
            switch (subtype) {
                case "1":
                    editor.putString("option", Config.EVENT);
                    editor.putString("title", "Ringtunes Mới");
                    break;
                case "2":
                    editor.putString("option", Config.EVENT);
                    editor.putString("type_event", "event_details");
                    editor.putString("title", "Ringtunes Mới");
                    break;
                case "3":
                    editor.putString("option", Config.EVENT);
                    editor.putString("type_event", "promotion_details");
                    editor.putString("title", "Event");
                    break;
                case "4":
                    editor.putString("option", Config.RINGTUNES_HOT);
                    editor.putString("title", "Ringtunes Hot");
                    break;
                case "5":
                    editor.putString("option", Config.RINGTUNES_NEW);
                    editor.putString("title", "Ringtunes New");
                    break;
                case "6":
                    editor.putString("option", Config.TYPE);
                    editor.putString("title", "Thể Loại");
                    break;
                case "7":
                    editor.putString("option", Config.SINGER);
                    editor.putString("title", "Ca sĩ");
                    break;
                case "8":
                    editor.putString("option", Config.TOPIC);
                    editor.putString("title", "Chủ đề");
                    break;
            }
            editor.putString("id", id);
            editor.putString("url_image_title", image);
            editor.commit();
            FragmentUtil.addFragment(fragmentActivity, FragmentSongs.getInstance(), true);
        }
    }

    public void initTopic() {
        adapterTopic = new AdapterTopic(listTopic, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerTopichot.setNestedScrollingEnabled(false);
        recyclerTopichot.setHasFixedSize(true);
        recyclerTopichot.setLayoutManager(mLayoutManager);
        recyclerTopichot.setItemAnimator(new DefaultItemAnimator());
        recyclerTopichot.setAdapter(adapterTopic);
        adapterTopic.notifyDataSetChanged();


        adapterTopic.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("option", Config.TOPIC);
                editor.putString("title", listTopic.get(position).getPackage_name());
                editor.putString("id", listTopic.get(position).getId());
                editor.putString("url_image_title", listTopic.get(position).getPhoto());
                editor.commit();
                if (!FragmentSongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }


    @Override
    public void showListTopic(final List<Topic> list) {
        listTopic.clear();
        if (list != null && list.size() > 0) {
            listTopic.addAll(list);
            adapterTopic.notifyDataSetChanged();
        }
    }

    /*  @Override
      public void showListAlbum(final List<Album> listAlbum) {
          if (listAlbum != null && listAlbum.size() > 0) {
              adapterAlbum = new AdapterAlbum(listAlbum, getContext());
              mLayoutManager = new GridLayoutManager(getContext(), 2);
              //  recyclerAlbumHot.setNestedScrollingEnabled(false);
              recyclerAlbumHot.setLayoutManager(mLayoutManager);
              recyclerAlbumHot.setItemAnimator(new DefaultItemAnimator());
              recyclerAlbumHot.setAdapter(adapterAlbum);
              adapterAlbum.notifyDataSetChanged();

              adapterAlbum.setOnIListener(new setOnItemClickListener() {
                  @Override
                  public void OnItemClickListener(int position) {
                      Bundle bundle = new Bundle();
                      bundle.putString("option", Config.TOPIC);
                      bundle.putString("title", listAlbum.get(position).getPackage_name());
                      bundle.putString("id", listAlbum.get(position).getId());
                      bundle.putString("url_image_title", listAlbum.get(position).getPhoto());
                      FragmentUtil.addFragmentData(getActivity(), FragmentSongs.getInstance(), true, bundle);

                  }

                  @Override
                  public void OnLongItemClickListener(int position) {

                  }
              });
          }
      }*/
    public void initPromotion() {
        adapterEventHome = new AdapterEventHome(lisPromotion, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_home_event.setNestedScrollingEnabled(false);
        recycle_home_event.setHasFixedSize(true);
        recycle_home_event.setLayoutManager(mLayoutManager);
        recycle_home_event.setItemAnimator(new DefaultItemAnimator());
        recycle_home_event.setAdapter(adapterEventHome);
        adapterEventHome.notifyDataSetChanged();


        adapterEventHome.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("option", Config.EVENT);
                editor.putString("id", lisPromotion.get(position).getId());
                if (lisPromotion.get(position).getBigphoto() != null) {
                    editor.putString("type_event", "promotion_details");
                    editor.putString("url_image_title", lisPromotion.get(position).getPhoto());
                    editor.putString("title", lisPromotion.get(position).getPackage_name());
                }
                if (lisPromotion.get(position).getTHUMBNAIL_IMAGE() != null) {
                    editor.putString("type_event", "event_details");
                    editor.putString("url_image_title", lisPromotion.get(position).getTHUMBNAIL_IMAGE());
                    editor.putString("title", lisPromotion.get(position).getNAME());
                }
                editor.commit();
                if (!FragmentSongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    public void initSlogan() {
        adapterSlogan = new AdapterSlogan(lisSlogan, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_slogan.setNestedScrollingEnabled(false);
        recycle_slogan.setHasFixedSize(true);
        recycle_slogan.setLayoutManager(mLayoutManager);
        recycle_slogan.setItemAnimator(new DefaultItemAnimator());
        recycle_slogan.setAdapter(adapterSlogan);
        adapterSlogan.notifyDataSetChanged();

        adapterSlogan.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
          /*      SharedPreferences.Editor editor = fr.edit();
                editor.putString("name_singer", listSinger.get(position).getSinger_name());
                editor.putString("hits_singer", listSinger.get(position).getHits());
                editor.putString("url_imgSinger", listSinger.get(position).getPhoto());
                editor.putString("idSinger", listSinger.get(position).getId());
                editor.commit();
                if (!FragmentDetailBuySongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), Fragment_SingerDetail.getInstance(), true);*/
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    public void showslogan_idx(final List<Topic> list) {
        lisSlogan.clear();
        if (list != null && list.size() > 0) {
            lisSlogan.addAll(list);
            adapterSlogan.notifyDataSetChanged();
        }
    }

    public void showPromotion(final List<Topic> list) {
        if (list != null && list.size() > 0) {
            lisPromotion.addAll(list);
        }
        adapterEventHome.notifyDataSetChanged();
    }

    public void showEvent(final List<Topic> list) {
        if (list != null && list.size() > 0) {
            lisPromotion.clear();
            lisPromotion.addAll(list);
        }
        presenterHome.promotion_idx("promotion_idx", sUsername);
    }

    public void initRingtunesHot() {
        adapterRingtunes = new AdapterRingtunes(listRingHot, getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerRingtunesHot.setNestedScrollingEnabled(false);
        recyclerAlbumHot.setHasFixedSize(true);
        recyclerRingtunesHot.setLayoutManager(mLayoutManager);
        recyclerRingtunesHot.setItemAnimator(new DefaultItemAnimator());
        recyclerRingtunesHot.setAdapter(adapterRingtunes);
        adapterRingtunes.notifyDataSetChanged();

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                player_ring = listRingHot.get(position);
                editor.putBoolean("isHome", true);
                editor.putString("idSinger", listRingHot.get(position).getSinger_id());
                editor.commit();
                if (!FragmentSongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentDetailBuySongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showListRingtunesHot(final List<Ringtunes> lisRingtunesHot) {
        listRingHot.clear();
        if (lisRingtunesHot != null && lisRingtunesHot.size() > 0) {
            listRingHot.addAll(lisRingtunesHot);
            adapterRingtunes.notifyDataSetChanged();
        }
    }

    public void initRingtunesNew() {
        adapterRingtunes = new AdapterRingtunes(lisRingNew, getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerRingtunesNew.setNestedScrollingEnabled(false);
        recyclerAlbumHot.setHasFixedSize(true);
        recyclerRingtunesNew.setLayoutManager(mLayoutManager);
        recyclerRingtunesNew.setItemAnimator(new DefaultItemAnimator());
        recyclerRingtunesNew.setAdapter(adapterRingtunes);
        adapterRingtunes.notifyDataSetChanged();

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                player_ring = lisRingNew.get(position);
                editor.putBoolean("isHome", true);
                editor.putString("idSinger", lisRingNew.get(position).getSinger_id());
                editor.commit();
                /*
                    FragmentUtil.replaceFragment(getActivity().getSupportFragmentManager(), R.id.fame_main,
                            FragmentDetailBuySongs.getInstance(), FragmentSongs.class.getSimpleName(),
                            FragmentDetailBuySongs.class.getSimpleName());*/
              /*  Bundle bundle = new Bundle();
                bundle.putBoolean("isHome", true);
                bundle.putSerializable("objRing", lisRingNew.get(position));*/
                if (!FragmentDetailBuySongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentDetailBuySongs.getInstance(), true);

            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showListRingtunesNew(final List<Ringtunes> lisRingtunesNew) {
        lisRingNew.clear();
        if (lisRingtunesNew != null && lisRingtunesNew.size() > 0) {
            lisRingNew.addAll(lisRingtunesNew);
            MyApplication.lisRingtunesNew = lisRingNew;
            adapterRingtunes.notifyDataSetChanged();
        }
    }

    public void initSinger() {
        adapterSinger = new AdapterSinger(listSinger, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerSinger.setNestedScrollingEnabled(false);
        recyclerSinger.setHasFixedSize(true);
        recyclerSinger.setLayoutManager(mLayoutManager);
        recyclerSinger.setItemAnimator(new DefaultItemAnimator());
        recyclerSinger.setAdapter(adapterSinger);
        adapterSinger.notifyDataSetChanged();

        adapterSinger.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("name_singer", listSinger.get(position).getSinger_name());
                editor.putString("hits_singer", listSinger.get(position).getHits());
                editor.putString("url_imgSinger", listSinger.get(position).getPhoto());
                editor.putString("idSinger", listSinger.get(position).getId());
                editor.commit();
                if (!FragmentDetailBuySongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), Fragment_SingerDetail.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showListSinger(final List<Singer> list) {
        listSinger.clear();
        if (list != null && list.size() > 0) {
            listSinger.addAll(list);
            adapterSinger.notifyDataSetChanged();
        }

    }

    public void initType() {
        adapterType = new AdapterType(listType, getContext());
        // mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerType.setNestedScrollingEnabled(false);
        recyclerType.setHasFixedSize(true);
        recyclerType.setLayoutManager(mLayoutManager);
        recyclerType.setItemAnimator(new DefaultItemAnimator());
        recyclerType.setAdapter(adapterType);
        adapterType.notifyDataSetChanged();

        adapterType.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("option", Config.TYPE);
                editor.putString("title", listType.get(position).getName());
                editor.putString("id", listType.get(position).getId());
                editor.putString("url_image_title", listType.get(position).getThumbnal_image());
                editor.commit();
                if (!FragmentSongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showListType(final List<Type> list) {
        listType.clear();
        if (list != null && list.size() > 0) {
            listType.addAll(list);
            adapterType.notifyDataSetChanged();
        }
    }

    @Override
    public void showlistBanner(List<Banner> list) {
        if (list == null) return;
        lisBanner.clear();
        lisBanner_String.clear();
        for (int i = 0; i < list.size(); i++) {
            lisBanner_String.add(list.get(i).getIMAGE_FILE());
        }
        lisBanner.addAll(list);
        showViewpager(lisBanner_String);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = "1";
                initData();
                init();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }




/*    class RemindTask extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (page1 > 6) {//so anh trong view pager
                        page1 = -1;
                        objViewPager.setCurrentItem(page1++);
                    } else {

                        objViewPager.setCurrentItem(page1++);
                    }
                }
            });

        }
    }*/

    private void initData() {
        initNetwork();
        if (!is3g && !isWifi) {
            DialogUtil.showDialog(getActivity(), "Thông báo", "Không có kết nối vui lòng kiểm tra lại mạng");
        } else {
            presenterHome = new PresenterHome(this);
            presenterHome.getLisTopoc(page, index);
            //  presenterHome.getLisAlbum(page, index);
            presenterHome.getRingtunesHot(page, index);
            presenterHome.getRingtunesNew(page, index);
            presenterHome.getListSinger(page, index);
            presenterHome.getListType(page, index);
            presenterHome.getbanner_header(sUsername);
            presenterHome.getrankRingtunes(page, index, sUsername);
            presenterHome.promotion_idx("event_idx", sUsername);
            presenterHome.getslogan_idx("slogan_idx", sUsername);
        }

        // init();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
