package com.example.myapplication.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> TabFragmentList;
    private List<String> TabTitle;
    public TabFragmentAdapter(FragmentManager fm, List<Fragment> TabFragmentList, List<String> TabTitle) {
        super(fm);
        this.TabFragmentList=TabFragmentList;
        this.TabTitle=TabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return TabFragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TabTitle.get(position);
    }
}
