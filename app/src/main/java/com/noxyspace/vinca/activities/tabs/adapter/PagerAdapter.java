package com.noxyspace.vinca.activities.tabs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.noxyspace.vinca.activities.tabs.NewsTab;
import com.noxyspace.vinca.activities.tabs.OwnTab;
import com.noxyspace.vinca.activities.tabs.MarketTab;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;

    private NewsTab newsTab;
    private OwnTab ownTab;
    private MarketTab marketTab;

    public PagerAdapter(FragmentManager fm) {
        super(fm);

        this.numberOfTabs = 3;

        this.newsTab = new NewsTab();
        this.ownTab = new OwnTab();
        this.marketTab = new MarketTab();
    }

    public void notifyTabSelection(int position) {
        switch (position) {
            case 0:
                this.newsTab.onTabSelected();
                break;

            case 1:
                this.ownTab.onTabSelected();
                break;

            case 2:
                this.marketTab.onTabSelected();
                break;

            default:
                break;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return this.newsTab;

            case 1:
                return this.ownTab;

            case 2:
                return this.marketTab;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}