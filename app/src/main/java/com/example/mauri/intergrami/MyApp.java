package com.example.mauri.intergrami;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by mauri on 23/04/2018.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);
    }
}
