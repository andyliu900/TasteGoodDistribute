package com.tastegood.distribute.order.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by surandy on 2016/11/4.
 */

public class OrderManagerAdapter extends FragmentPagerAdapter {

    String[] _titles;
    private List<Fragment> mFragments;

    public OrderManagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        _titles = titles;
        mFragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

}
