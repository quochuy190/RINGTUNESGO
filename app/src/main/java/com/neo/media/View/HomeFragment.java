package com.neo.media.View;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neo.media.Adapter.AdapterViewpager;
import com.neo.media.Fragment.Home.View.FragmentHome;
import com.neo.media.Fragment.Singer.View.FragmentSinger;
import com.neo.media.Fragment.Topic.View.FragmentTocpic;
import com.neo.media.Fragment.Type.View.FragmentType;
import com.neo.media.R;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.CustomTabLayout;
import com.neo.media.untils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.neo.media.MainNavigationActivity.ab;

/**
 * Created by QQ on 7/6/2017.
 */

public class HomeFragment extends BaseFragment {
    public static String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.tablayout_toolbar)
    CustomTabLayout tbHome;
    public static CustomViewPager viewpagerHome;
    private boolean isPlaying;
    static HomeFragment fragment;
    AdapterViewpager adapter;
    boolean isDoubleClick = false;

    public static HomeFragment getInstance() {
        if (fragment == null)
            fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home, container, false);
        ButterKnife.bind(this, rootView);
        viewpagerHome = (CustomViewPager) rootView.findViewById(R.id.vp_home);
        //  isPlaying = MainActivity.storage.getBoolean(Storage.IS_PLAYING, false);
        init();
        return rootView;
    }
/*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


    }*/

    @Override
    public void onResume() {
        super.onResume();

    }

    private void init() {
        setUpViewPager();
        setUpTablayout();

    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new AdapterViewpager(getChildFragmentManager());
        adapter.addFragment(new FragmentHome(), getString(R.string.Fragment_Home));
      //  adapter.addFragment(new FragmentAlbum(), getString(R.string.Fragment_Album));
        adapter.addFragment(new FragmentType(), getString(R.string.Fragment_Type));
        adapter.addFragment(new FragmentTocpic(), getString(R.string.Fragment_Topic));
        adapter.addFragment(new FragmentSinger(), getString(R.string.Fragment_Singer));
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
    }

    public void fragmentBackTack() {
        if (viewpagerHome.getCurrentItem() != 0) {
            viewpagerHome.setCurrentItem(0);
        } else {
            if (isDoubleClick) {
                getActivity().finish();
                return;
            }
            this.isDoubleClick = true;
            Toast.makeText(getContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isDoubleClick = false;
                }
            }, 2000);
        }
    }


    private void setUpTablayout() {
        tbHome.setupWithViewPager(viewpagerHome);
      //  tbHome.setTabMode(TabLayout.MODE_SCROLLABLE);
        //  tbHome.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.text_botton)));
        //   tbHome.sette

        tbHome.setSelectedTabIndicatorColor(getResources().getColor(R.color.purple));

        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
       // tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
        tbHome.getTabAt(1).setIcon(R.drawable.stype_blue);
        tbHome.getTabAt(2).setIcon(R.drawable.topic_blue);
        tbHome.getTabAt(3).setIcon(R.drawable.singer_blue);
        // tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
        viewpagerHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int i = position;
                switch (i) {
                    case 0:
                        ab.setDisplayHomeAsUpEnabled(false);
                        ab.setDisplayShowHomeEnabled(false);
                        ab.setTitle("HOME");
                        tbHome.getTabAt(0).setIcon(R.drawable.home_purple);
           //             tbHome.getTabAt().setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_blue);
                        //    tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);

                        break;
                    case 1:
                        ab.setDisplayHomeAsUpEnabled(false);
                        ab.setDisplayShowHomeEnabled(false);
                        ab.setTitle("THỂ LOẠI");
                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                       // tbHome.getTabAt(1).setIcon(R.drawable.album_purple);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_purple);
                        tbHome.getTabAt(2).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_blue);
                        //   tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;
                    case 2:
                        ab.setDisplayHomeAsUpEnabled(false);
                        ab.setDisplayShowHomeEnabled(false);
                        ab.setTitle("CHỦ ĐỀ");
                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                        //tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.tocpic_purple);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_blue);
                        //   tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;
                    case 3:
                        ab.setDisplayHomeAsUpEnabled(false);
                        ab.setDisplayShowHomeEnabled(false);
                        ab.setTitle("CA SĨ");
                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                       // tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_purple);
                        //   tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;
                  /*  case 4:
                        ab.setDisplayHomeAsUpEnabled(false);
                        ab.setDisplayShowHomeEnabled(false);
                        ab.setTitle("CA SĨ");
                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(4).setIcon(R.drawable.singer_purple);
                        //    tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;*/
                  /*  case 5:
                     *//*   ab.setDisplayHomeAsUpEnabled(false);
                        ab.setDisplayShowHomeEnabled(false);
                        ab.setTitle("CÀI ĐẶT");*//*
                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(4).setIcon(R.drawable.singer_blue);
                        tbHome.getTabAt(5).setIcon(R.drawable.setup_purple);
                        break;*/
                }


            }

            @Override
            public void onPageSelected(int position) {
                int i = position;
                switch (i) {
                    case 0:

                        tbHome.getTabAt(0).setIcon(R.drawable.home_purple);
                      //  tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_blue);
                        //    tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);

                        break;
                    case 1:

                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                     //   tbHome.getTabAt(1).setIcon(R.drawable.album_purple);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_purple);
                        tbHome.getTabAt(2).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_blue);
                        //    tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;
                    case 2:

                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                       // tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.tocpic_purple);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_blue);
                        //    tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;
                    case 3:

                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                        //tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.singer_purple);
                        //    tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;
                   /* case 4:

                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(4).setIcon(R.drawable.singer_purple);
                        //    tbHome.getTabAt(5).setIcon(R.drawable.setup_blue);
                        break;*/
                    /*case 5:

                        tbHome.getTabAt(0).setIcon(R.drawable.home_blue);
                        tbHome.getTabAt(1).setIcon(R.drawable.album_blue);
                        tbHome.getTabAt(2).setIcon(R.drawable.stype_blue);
                        tbHome.getTabAt(3).setIcon(R.drawable.topic_blue);
                        tbHome.getTabAt(4).setIcon(R.drawable.singer_blue);
                     //   tbHome.getTabAt(5).setIcon(R.drawable.setup_purple);
                        break;*/
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setUpViewPager() {
        if (viewpagerHome != null) {
            setupViewPager(viewpagerHome);
        }
    }


}
