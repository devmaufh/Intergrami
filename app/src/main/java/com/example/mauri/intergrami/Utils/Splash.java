package com.example.mauri.intergrami.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.mauri.intergrami.Activities.Home;
import com.example.mauri.intergrami.Activities.login;

public class Splash extends AppCompatActivity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs=getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        Intent intentLogin= new Intent(this,login.class);
        Intent intentHome= new Intent(this, Home.class);

        if(!TextUtils.isEmpty(prefs.getString("correo",""))&&
                !TextUtils.isEmpty(prefs.getString("password",""))){
            startActivity(intentHome);
        }else{
            startActivity(intentLogin);
        }
        finish();
    }
}
