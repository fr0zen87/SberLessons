package com.example.lesson25;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class DetailsAdapter extends FragmentPagerAdapter {

    private List<String> data;

    public DetailsAdapter(FragmentManager fm, List<String> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(data.get(position));
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
