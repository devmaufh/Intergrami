package com.example.mauri.intergrami.Utils;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

public class PopupwindowFull extends Activity{
    ImageView img;
    FloatingActionButton btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_full_image);
        img=(ImageView)findViewById(R.id.popupImagefull_FOTOTOTOTOT);
        Picasso.with(this).load(getIntent().getStringExtra("urlfoto")).into(img);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width= ActionBar.LayoutParams.MATCH_PARENT;
        int height= ActionBar.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width,height);
        btnClose=(FloatingActionButton)findViewById(R.id.popup_closebtn);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
