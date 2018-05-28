package com.example.mauri.intergrami.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mauri.intergrami.Adapters.AdapterPagerStart;
import com.example.mauri.intergrami.R;
import com.example.mauri.intergrami.Utils.HideStatusBar;

public class StartActivity extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        HideStatusBar.hideStatus(this);
        viewPager=(ViewPager)findViewById(R.id.start_viewPager);
        AdapterPagerStart adapterPagerStart= new AdapterPagerStart(getSupportFragmentManager(),2);
        viewPager.setAdapter(adapterPagerStart);

    }
}
