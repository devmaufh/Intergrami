package com.example.mauri.intergrami.Activities;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mauri.intergrami.Adapters.AdapterImagePager;
import com.example.mauri.intergrami.R;
import com.example.mauri.intergrami.Utils.HideStatusBar;

import java.util.ArrayList;
import java.util.List;

public class ImageViewer extends AppCompatActivity {
    ViewPager viewPager;
    List<String> images;
    AdapterImagePager adapterImagePager;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        viewPager=(ViewPager)findViewById(R.id.ImageViewer_viewPager);
        images= (ArrayList<String>) getIntent().getSerializableExtra("urls");
        position=getIntent().getIntExtra("position",0);
        adapterImagePager= new AdapterImagePager(this,images);
        viewPager.setAdapter(adapterImagePager);
        viewPager.setCurrentItem(position);
        HideStatusBar.hideStatus(this);
    }
}
