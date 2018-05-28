package com.example.mauri.intergrami.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mauri.intergrami.Fragments.Start1Fragment;
import com.example.mauri.intergrami.Fragments.start2Fragment;

public class AdapterPagerStart extends FragmentStatePagerAdapter{
    int numberFragments;
    public AdapterPagerStart(FragmentManager fm, int FragmentsCount) {
        super(fm);
        this.numberFragments=FragmentsCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Start1Fragment();
            case 1:
                return new start2Fragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return numberFragments;
    }
}
