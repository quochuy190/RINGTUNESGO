package com.neo.media.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.neo.media.Adapter.AdapterViewpager;
import com.neo.media.Fragment.Welcome.FragmentWelcome2;
import com.neo.media.Fragment.Welcome.FragmentWelcome3;
import com.neo.media.Fragment.Welcome.FragmentWelcome4;
import com.neo.media.R;
import com.neo.media.View.Splash_Screen.SplashScreen;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.alexrs.prefs.lib.Prefs;

/**
 * Created by QQ on 12/4/2017.
 */

public class ActivityWelcom extends AppCompatActivity {
    @BindView(R.id.viewpager_welcome)
    ViewPager viewPager;
    @BindView(R.id.tab_welcome)
    TabLayout tabLayout;
    AdapterViewpager adapter;
    @BindView(R.id.txt_done)
    TextView txt_done;
    boolean is_Welcom;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        ButterKnife.bind(this);
        is_Welcom = Prefs.with(this).getBoolean("isWelcome", false);
        if (!is_Welcom){
            if (viewPager != null) {
                setupViewPager(viewPager);
            }
            setUpTablayout();
        }else {
            startActivity(new Intent(ActivityWelcom.this, SplashScreen.class));
            finish();
        }
        initEvent();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new AdapterViewpager(getSupportFragmentManager());
        adapter.addFragment(new FragmentWelcome4(),".");
        adapter.addFragment(new FragmentWelcome2(), ".");
        adapter.addFragment(new FragmentWelcome3(), ".");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position==2){
                    txt_done.setVisibility(View.VISIBLE);
                }else {
                    txt_done.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position==2){
                    txt_done.setVisibility(View.VISIBLE);
                }else {
                    txt_done.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        txt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.with(ActivityWelcom.this).save("isWelcome", true);
                startActivity(new Intent(ActivityWelcom.this, SplashScreen.class));
                finish();
            }
        });
    }

    private void setUpTablayout() {

      //  tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
     //   tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.text_botton)));
        //   tbHome.sette

     //   tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.purple));
    }
}
