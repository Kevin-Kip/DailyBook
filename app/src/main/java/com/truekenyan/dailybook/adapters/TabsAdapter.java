package com.truekenyan.dailybook.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.truekenyan.dailybook.fragments.ExtrasFragment;
import com.truekenyan.dailybook.fragments.HomeFragment;

/**
 * Created by password
 * on 6/28/18.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem (int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ExtrasFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount () {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle (int position) {
        switch (position){
            case 0:
                return "HOME";
            case 1:
                return "EXTRAS";
        }
        return null;
    }
}
