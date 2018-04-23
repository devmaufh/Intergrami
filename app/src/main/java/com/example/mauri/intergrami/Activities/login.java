package com.example.mauri.intergrami.Activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class login extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {
    //Variables para conexion con servidor
    String ip="192.168.1.75"; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    //
    SharedPreferences prefs;//PErsistencia de datos para que no se loggee cada de que entre a la app

    private Button btn_log,btnRegister;
    private ImageView imagen;
    private EditText txtMail,txtPassword;
    private Switch swRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindUI();
        Picasso.with(this).load(R.drawable.finalfinal).into(imagen);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidData(txtMail.getText().toString(),txtPassword.getText().toString())){
                    IniciaSesion();
                }
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
        imagen=(ImageView)findViewById(R.id.login_Logo);
        txtMail=(EditText)findViewById(R.id.Login_edCorreo);
        txtPassword=(EditText)findViewById(R.id.Login_edPassword);
        swRemember=(Switch)findViewById(R.id.Login_Switch_Rememberme);
        rq= Volley.newRequestQueue(getApplicationContext());
        prefs= getSharedPreferences("datos_user", Context.MODE_PRIVATE);

    }
    public void setActivityRegister(){
        startActivity(new Intent(this,Register.class));
    }
    public void setActivityHome(){
        startActivity(new Intent(this,Home.class));
    }


    //******* SERVIDOR
    @Override
    public void onErrorResponse(VolleyError error) {
        //No responde el servidor xd
        Toast.makeText(this,"Usuario Incorrecto",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Usuario correcto",Toast.LENGTH_SHORT).show();
        try {
            JSONArray jsonArray=response.optJSONArray("datos");
            JSONObject jsonObject=null;
            jsonObject=jsonArray.getJSONObject(0);
            String id_user=jsonObject.optString("id_user");
            String nombre=jsonObject.optString("nombre");
            String curp=jsonObject.optString("curp");
            String direccion=jsonObject.optString("direccion");
            String correo=jsonObject.optString("correo");
            String password=jsonObject.optString("password");
            String urlfoto=ip+jsonObject.optString("urlfoto");
            String telefono=jsonObject.optString("telefono");
            Toast.makeText(this,urlfoto,Toast.LENGTH_LONG).show();

            SaveOnPreferences(id_user,nombre,curp,direccion,correo,password,urlfoto,telefono);

            setActivityHome();
        }catch (Exception e){
            e.printStackTrace();
        }
        //Responde el servidor
    }
    //********************//
    ///// Validación de campos
    private boolean isValidEmail(String email){//Valida el email
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password){ //Valída la contraseña
        return password.length()>4;
    }
    private boolean isValidData(String email, String password){
        if(!isValidEmail(email)){
            Toast.makeText(getApplicationContext(),"El correo no es válido, intentelo nuevamente",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(getApplicationContext(),"La contraseña debe contener más de 4 caractéres",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    //      *********************************************//
    public void IniciaSesion(){
        String url="http://"+ip+"/intergrami/login.php?correo="+txtMail.getText().toString()+
                "&contraseña="+txtPassword.getText().toString();
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    private void SaveOnPreferences(String idUser,String nombre,String curp,String direccion,String correo,String password,String urlfoto, String telefono){
        urlfoto=urlfoto.replace("\\","/");
        if(swRemember.isChecked()){
            SharedPreferences.Editor editor= prefs.edit();//Para escribir xd
            editor.putString("id_user",idUser);
            editor.putString("nombre",nombre);
            editor.putString("curp",curp);
            editor.putString("direccion",direccion);
            editor.putString("correo",correo);
            editor.putString("password",password);
            editor.putString("urlfoto",urlfoto);
            editor.putString("telefono",telefono);
            editor.commit();//Detiene hilo principal hasta que termina de almacenar datos
            Toast.makeText(this,"Datos guardados en preferences",Toast.LENGTH_SHORT).show();

        }
    }
    //Obtiene imagen de url y la almacena en directorio en memoria interna
}
