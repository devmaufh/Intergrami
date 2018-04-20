package com.example.mauri.intergrami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {
    Button btn_log,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindUI();
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityHome();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityRegister();
            }
        });
    }
    public void bindUI(){
        btn_log=(Button)findViewById(R.id.Login_btnLogin);
        btnRegister=(Button)findViewById(R.id.login_btnRegister);
    }
    public void setActivityRegister(){
        startActivity(new Intent(this,Register.class));
    }
    public void setActivityHome(){
        startActivity(new Intent(this,Home.class));
    }
}
