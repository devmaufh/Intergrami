package com.example.mauri.intergrami.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.SimpleTextChangedWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class login extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {
    //Variables para conexion con servidor
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    int flagPassword=0;
    //
    SharedPreferences prefs;//PErsistencia de datos para que no se loggee cada de que entre a la app
    TextFieldBoxes tfbCorreo,tfbContraseña;
    private Button btn_log,btnRegister;
    private ExtendedEditText txtMail,txtPassword;
    private Switch swRemember;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindUI();
        setToolbar();
        setCredentialsIfExist();
        Toast.makeText(this,ip,Toast.LENGTH_LONG).show();



        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidData(txtMail.getText().toString(),txtPassword.getText().toString())){
                    progressDialog.setMessage(getResources().getString(R.string.iniciandosesion));
                    progressDialog.show();
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
    @SuppressLint("WrongViewCast")
    public void bindUI(){
        ip= getResources().getString(R.string.ip_server);
        btn_log=(Button)findViewById(R.id.Login_btnLogin);
        btnRegister=(Button)findViewById(R.id.login_btnRegister);
        tfbCorreo=(TextFieldBoxes)findViewById(R.id.login_textfieldboxes_correo);
        tfbContraseña=(TextFieldBoxes)findViewById(R.id.login_textfieldboxes_contraseña);
        txtMail=(ExtendedEditText) findViewById(R.id.login_textfieldboxes_curp_edCorreo);
        txtPassword=(ExtendedEditText) findViewById(R.id.login_textfieldboxes_curp_edContraseña);

        swRemember=(Switch)findViewById(R.id.Login_Switch_Rememberme);
        rq= Volley.newRequestQueue(getApplicationContext());
        prefs= getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        progressDialog= new ProgressDialog(this);


        tfbCorreo.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(txtMail.getText().toString().length()>40)
                    tfbCorreo.setError(getResources().getString(R.string.valida_correo),true);
            }
        });
        tfbContraseña.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(txtPassword.getText().toString().length()>40)
                    tfbContraseña.setError(getResources().getString(R.string.valida_correo),true);
            }
        });
        tfbContraseña.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagPassword++;
                if(flagPassword%2==0)
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());//edContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//edContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                txtPassword.setSelection(txtPassword.getText().length());
            }
        });

    }
    public void setActivityRegister(){
        startActivity(new Intent(this,Register.class));
    }
    public void setActivityHome(){
        Intent intent= new Intent(this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    //******* SERVIDOR
    @Override
    public void onErrorResponse(VolleyError error) {
        //No responde el servidor xd+
        progressDialog.dismiss();
        Toast.makeText(this,"Usuario Incorrecto",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Usuario correcto",Toast.LENGTH_SHORT).show();
        try {
            progressDialog.dismiss();
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
            //Toast.makeText(this,urlfoto,Toast.LENGTH_LONG).show();

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
            tfbCorreo.setError(getResources().getString(R.string.correo_invalido),true);
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.correo_invalido),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidPassword(password)){
            tfbContraseña.setError(getResources().getString(R.string.contraseña_invalida),true);
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.contraseña_invalida),
                    Toast.LENGTH_SHORT).show();
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
    private void setCredentialsIfExist(){
        String email=prefs.getString("correo","");
        String contraseña=prefs.getString("password","");
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(contraseña)){
            txtMail.setText(email);
            txtPassword.setText(contraseña);
        }
    }
    private void setToolbar(){
        //Toolbar toolbar= (Toolbar)findViewById(R.id.activity_login_toolbar); //Muestra el toolbar como ActionBar
        //setSupportActionBar(toolbar);//Muestra titulo de toolbar
        //toolbar.setTitle("Intergrami");

    }
    ///////////////////////////////////////////////////777
    //                      Obtener imagen):
}
