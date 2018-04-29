package com.example.mauri.intergrami.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.Adapters.MyAdapterProductDetails;
import com.example.mauri.intergrami.Adapters.MyAdapterProducts;
import com.example.mauri.intergrami.Models.Productos;
import com.example.mauri.intergrami.R;
import com.example.mauri.intergrami.Utils.PopupwindowFull;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class tierra_details extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    String id_tierra;
    private boolean foto=true;
    private RecyclerView fotos;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtDescripcion, txtNombre,txtMonto;
    private CircleImageView foto_vendedor;
    private Spinner time;
    private Button btnRenta;
    private RatingBar cali;
    private PopupWindow window;

    List<String> urls;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tierra_details);
        binUI();
        Service_fotos();
        progressDialog.setTitle(getResources().getString(R.string.cargando));
        progressDialog.show();
    }
    private void setToolbar(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_tierra_detail); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        setTitle(getResources().getString(R.string.detalles));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);//Icono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this,getIntent().getStringExtra("id_tierra"),Toast.LENGTH_SHORT).show();
    }
    private void binUI(){
        setToolbar();
        id_tierra=getIntent().getStringExtra("id_tierra");
        Toast.makeText(this, id_tierra, Toast.LENGTH_SHORT).show();
        ip= getResources().getString(R.string.ip_server);
        rq= Volley.newRequestQueue(getApplicationContext());
        progressDialog= new ProgressDialog(this);
        urls= new ArrayList<String>();
        fotos= (RecyclerView)findViewById(R.id.tierra_detail_recycler_fotos);
        txtDescripcion=(TextView)findViewById(R.id.tierra_detail_tvDescripcion);
        txtNombre=(TextView)findViewById(R.id.tierra_details_tvNombreVendendor);
        txtMonto=(TextView)findViewById(R.id.tierra_detail_tvMonto);
        foto_vendedor=(CircleImageView)findViewById(R.id.tierra_details_profileVendedor);
        time=(Spinner)findViewById(R.id.tierra_detail_spinnertimepo);
        btnRenta=(Button)findViewById(R.id.tierra_detail_btnRentar);
        cali=(RatingBar)findViewById(R.id.tierra_detail_RaitinBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this,"BAck", Toast.LENGTH_LONG).show();
                animacion();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void setFotosRecycler(){
        mLayoutManager= new LinearLayoutManager(getApplicationContext());
        mAdapter= new MyAdapterProductDetails(urls, R.layout.cardview_productdetails, new MyAdapterProductDetails.OnItemClickListener() {
            @Override
            public void onItemClick(String url, int position) {
                Intent intent= new Intent(getApplicationContext(),PopupwindowFull.class);
                intent.putExtra("urlfoto",url);
                startActivity(intent);
            }
        });
        fotos.setLayoutManager(mLayoutManager);
        fotos.setAdapter(mAdapter);
    }

    //Servidor xdxdxdxd
    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Toast.makeText(this, "Error en respuesta del server", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.dismiss();
        if(foto){
            try {

                JSONArray cast=response.getJSONArray("fotos");

                for (int i = 0; i < cast.length(); i++) {
                    JSONObject jo=cast.getJSONObject(i);
                    String url="http://"+ip+jo.optString("urlfoto").replace("\\","/");
                    urls.add(url);

                }
                setFotosRecycler();
                foto=false;
                getDatosServidor(id_tierra);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            JSONArray cast= null;
            try {
                cast = response.getJSONArray("datos");
                JSONObject jsonObject=cast.getJSONObject(0);
                txtDescripcion.setText(jsonObject.optString("descripcion"));
                txtMonto.setText(getResources().getString(R.string.monto_por_mes)+jsonObject.optString("monto"));
                txtNombre.setText(jsonObject.optString("nombre"));
                String fotovendedor=("http://"+ip+""+jsonObject.opt("urlfoto")).replace("\\","/");
                Picasso.with(this).load(fotovendedor).into(foto_vendedor);
                float calificacion=Float.parseFloat(jsonObject.optString("calificacion"));
                cali.setRating(calificacion);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void getDatosServidor(String id_tierra) {
        String url="http://"+ip+"/intergrami/vistas/detail_tierra.php?id_tierra="+id_tierra;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    private void Service_fotos(){
        String url="http://"+ip+"/intergrami/vistas/fotos_tierras.php?id_tierra="+id_tierra;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    private void animacion(){
        overridePendingTransition(R.anim.godown,R.anim.goup);
    }
}