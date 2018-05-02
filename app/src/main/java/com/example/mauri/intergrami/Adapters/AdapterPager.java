package com.example.mauri.intergrami.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mauri.intergrami.Fragments.EnVentaFragment;
import com.example.mauri.intergrami.Fragments.AcercadeFragment;

public class AdapterPager  extends FragmentStatePagerAdapter{
    private int numberOfTabs;

    public AdapterPager(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs=numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new EnVentaFragment();
            case 1:
                return new AcercadeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
