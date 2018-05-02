package com.example.mauri.intergrami.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.mauri.intergrami.Activities.Home;
import com.example.mauri.intergrami.Activities.login;
import com.example.mauri.intergrami.R;

public class Splash extends AppCompatActivity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs=getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        Intent intentLogin= new Intent(this,login.class);
        Intent intentHome= new Intent(this, Home.class);


        if(isNetDisponible()&&isOnlineNet()){
            Toast.makeText(this, "SI HAY INTERNET JIJIJI", Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(prefs.getString("correo",""))&&
                !TextUtils.isEmpty(prefs.getString("password",""))){
            startActivity(intentHome);
        }else{
            startActivity(intentLogin);
        }
        finish();
        }else{
            Toast.makeText(this, "Error  de red, no hay jijijiji", Toast.LENGTH_SHORT).show();
            //Mandar un cuadrod de dialogo en este bloque!
            //
            finish();
        }




    }
    private boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
    public Boolean isOnlineNet() {
        try{
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 "+getResources().getString(R.string.ip_server));
            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
