package com.example.mauri.intergrami.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

public class HideStatusBar {
    public static void hideStatus(Activity a){
        if(Build.VERSION.SDK_INT<16){
            a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView=a.getWindow().getDecorView();
            int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
