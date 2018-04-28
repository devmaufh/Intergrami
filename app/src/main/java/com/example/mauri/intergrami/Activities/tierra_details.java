package com.example.mauri.intergrami.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tierra_details extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    String id_tierra;
    List<String> urls;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tierra_details);
        binUI();
        progressDialog.setTitle(getResources().getString(R.string.cargando));
        progressDialog.show();
    }
    private void setToolbar(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_tierra_detail); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        setTitle("Detalles");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);//Icono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this,getIntent().getStringExtra("id_tierra"),Toast.LENGTH_SHORT).show();
    }
    private void binUI(){
        setToolbar();
        id_tierra=getIntent().getStringExtra("id_tierra");
        ip= getResources().getString(R.string.ip_server);
        rq= Volley.newRequestQueue(getApplicationContext());
        progressDialog= new ProgressDialog(this);
        urls= new ArrayList<String>();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this,"BAck", Toast.LENGTH_LONG).show();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Servidor xdxdxdxd
    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(JSONObject response) {
    }
}