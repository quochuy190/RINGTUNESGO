package com.neo.ringtunesgo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DatTien on 23/02/2017.
 */

public class AdapterViewPagerVideoPlayList extends FragmentPagerAdapter {
    private List<Fragment> lis;
    public AdapterViewPagerVideoPlayList(FragmentManager fm,List<Fragment> lis) {
        super(fm);
        this.lis = lis;

    }

    @Override
    public Fragment getItem(int position) {

        return lis.get(position);
    }

    @Override
    public int getCount() {
        return lis.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Thông tin ca sỹ";
            case 1 : return "Danh sách bài hát";

        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
