package com.neo.media.Activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neo.media.Fragment.CaNhan.FragmentCanhan;
import com.neo.media.Fragment.Favorite.FragmentFavorite;
import com.neo.media.Fragment.Home.View.FragmentHome;
import com.neo.media.Fragment.Search.FragmentSearchNew;
import com.neo.media.R;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import static com.neo.media.Activity.ActivityMainHome.relative_tab;

/**
 * Created by QQ on 11/20/2017.
 */

public class FragmentHomeActivity extends BaseFragment {
    public static String TAG = FragmentHomeActivity.class.getSimpleName();
    public static FragmentHomeActivity fragment;
    public static String phone_addprofile = "";

    public static FragmentHomeActivity getInstance() {
        if (fragment == null) {
            synchronized (FragmentHomeActivity.class) {
                if (fragment == null)
                    fragment = new FragmentHomeActivity();
            }
        }
        return fragment;
    }
    public static BottomBar bottomBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_activity, container, false);
        bottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        initBottomBar();

        return view;
    }



    private void initBottomBar() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_thongke:
                        relative_tab.setVisibility(View.VISIBLE);
                        ActivityMainHome.ab.setTitle("Trang chủ");
                    /*    if (!FragmentHome.getInstance().isAdded()){
                            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame_home, FragmentHome.getInstance());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }*/
                        FragmentUtil.pushFragment(getChildFragmentManager(), R.id.frame_home,
                                FragmentHome.getInstance(), null);
                        break;
                    case R.id.tab_thuong:
                        relative_tab.setVisibility(View.GONE);
                        ActivityMainHome.ab.setTitle("Tìm kiếm");
                      /*  if (!FragmentSearchNew.getInstance().isAdded()){
                            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame_home, FragmentSearchNew.getInstance());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }*/
                        FragmentUtil.pushFragment(getChildFragmentManager(), R.id.frame_home,
                                FragmentSearchNew.getInstance(), null);

                        break;
                    case R.id.tab_dathang:
                        relative_tab.setVisibility(View.GONE);
                        FragmentUtil.pushFragment(getChildFragmentManager(), R.id.frame_home,
                                FragmentCanhan.getInstance(), null);
                        ActivityMainHome.ab.setTitle("Cá nhân");
                     /*   if (!FragmentCanhan.getInstance().isAdded()){
                            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame_home, FragmentCanhan.getInstance());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }*/
                        break;
                    case R.id.tab_favorite:
                        relative_tab.setVisibility(View.GONE);
                        FragmentUtil.pushFragment(getChildFragmentManager(), R.id.frame_home,
                                FragmentFavorite.getInstance(), null);
                      //  ActivityMainHome.ab.setTitle("Yêu thích");
                        if (!FragmentCanhan.getInstance().isAdded()){
                            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame_home, FragmentFavorite.getInstance());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        break;
                }
            }
        });

    }
    public static void tab_search(){
        bottomBar.selectTabAtPosition(1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    boolean isDoubleClick;

    public void fragmentBackTack() {
        if (isDoubleClick) {
            getActivity().finish();
            return;
        }
        this.isDoubleClick = true;
        Toast.makeText(getContext(), "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isDoubleClick = false;
            }
        }, 2000);
    }

}
