package com.noxyspace.vinca.activities.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.noxyspace.vinca.activities.tabs.NewsTab;
import com.noxyspace.vinca.activities.tabs.OwnTab;
import com.noxyspace.vinca.activities.tabs.RecentTab;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new NewsTab();
            case 1:
                return new OwnTab();
            case 2:
                return new RecentTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
