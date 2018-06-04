package com.neo.media.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.neo.media.Fragment.Favorite.FragmentPlayerFull;
import com.neo.media.R;
import com.neo.media.untils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 11/20/2017.
 */

public class ActivityNotifycation extends AppCompatActivity {
    public static ActionBar ab;
    @BindView(R.id.fame_main)
    FrameLayout frameLayout;
    public static RelativeLayout relative_tab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        // myRealm = RealmController.with(this).getRealm();
        relative_tab = (RelativeLayout) findViewById(R.id.relative_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();

        if (!FragmentHomeActivity.getInstance().isAdded())

            FragmentUtil.pushFragment(getSupportFragmentManager(), R.id.fame_main, FragmentHomeActivity.getInstance(), null);
        else {
            //FragmentUtil.removeFragment(getSupportFragmentManager(), FragmentHomeActivity.getInstance());
            FragmentUtil.pushFragment(getSupportFragmentManager(), R.id.fame_main, FragmentHomeActivity.getInstance(), null);

        }
    }

    String notifi;
    String image;
    String subtype;

    private void initData() {
        subtype = getIntent().getStringExtra("subtype");
        image = getIntent().getStringExtra("image");
        notifi = getIntent().getStringExtra("type");
        String title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");
        String id_Singer = getIntent().getStringExtra("idsinger");
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void notification() {

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fame_main);
        if (fragment instanceof FragmentHomeActivity) {
            FragmentHomeActivity.getInstance().fragmentBackTack();
        } else if (fragment instanceof FragmentPlayerFull) {
            FragmentPlayerFull.getInstance().fragmentBackTack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Prefs.with(this).save("is_Phien_DN", false);
    }
}
