package com.example.mauri.intergrami.Activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.Adapters.MyAdapterProductDetails;
import com.example.mauri.intergrami.Fragments.Product_detail;
import com.example.mauri.intergrami.R;
import com.example.mauri.intergrami.Utils.PopupwindowFull;
import com.example.mauri.intergrami.Utils.SetFullImages;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Product_details extends AppCompatActivity  {
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    private RecyclerView fotos;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ProgressDialog dialog;
    private String id_producto;
    private String monto;
    private String id_user;
    private boolean foto=true;
    private List<String> urls;
    TextView txtTitulo,txtFecha,txtPrecio,txtDescripcion,txtNombreV;
    RatingBar calificacion_vendedor;
    CircleImageView foto_vendedor;
    private PopupWindow window;
    private Button btnComprar;
    private  boolean flag=false;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setToolbar();
        bindUI();
        id_producto=getIntent().getStringExtra("id_producto");
        dialog.setTitle(getResources().getString(R.string.cargando));
        dialog.show();
        getDatosServidor(id_producto);
        getFotos(id_producto);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(Product_details.this, "ClickCompras", Toast.LENGTH_SHORT).show();
                id_user=prefs.getString("id_user","'null'");
                monto=txtPrecio.getText().toString();
                confirmaCompra();
                /*if(flag) {
                    compra(id_producto, id_user, monto);
                    finish();
                }else flag=false;*/
            }
        });
    }

    private void setToolbar(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_details); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        setTitle(getResources().getString(R.string.detalles));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);//Icono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Toast.makeText(this,getIntent().getStringExtra("id_producto"),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
               // Toast.makeText(this,"BAck", Toast.LENGTH_LONG).show();
                animacion();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void bindUI(){
        ip= getResources().getString(R.string.ip_server);
        fotos=(RecyclerView)findViewById(R.id.product_details_RecyclerFotos);
        rq= Volley.newRequestQueue(getApplicationContext());
        dialog= new ProgressDialog(this);
        urls= new ArrayList<String>();
        txtTitulo=(TextView)findViewById(R.id.product_detail_TituloProducto);
        txtFecha=(TextView)findViewById(R.id.product_details_tvFecha);
        txtPrecio=(TextView)findViewById(R.id.product_details_tvPrecio);
        txtDescripcion=(TextView)findViewById(R.id.product_details_tvDescripcion);
        txtNombreV=(TextView)findViewById(R.id.product_details_tvNombreVendendor);
        calificacion_vendedor=(RatingBar)findViewById(R.id.product_detail_RaitinBar);
        foto_vendedor=(CircleImageView)findViewById(R.id.product_details_profileVendedor);
        btnComprar=(Button) findViewById(R.id.product_details_COMPRAr);
        prefs=getSharedPreferences("datos_user",Context.MODE_PRIVATE);
        flag=false;
    }
    private void setFotosRecycler(){
        boolean isImageFitToScreen;

        mLayoutManager= new StaggeredGridLayoutManager(3,1);
         mAdapter= new MyAdapterProductDetails(urls, R.layout.cardview_productdetails, new MyAdapterProductDetails.OnItemClickListener() {
           @Override
           public void onItemClick(String url, int position) {
               SetFullImages.startViewerImages(getApplicationContext(),urls,position);

              }
          });
         fotos.setLayoutManager(mLayoutManager);
       fotos.setAdapter(mAdapter);

    }
    //              Servidor
    public void getDatosServidor(String id_prod){
        String url="http://"+ip+"/intergrami/vistas/detail_product.php?id_producto="+id_prod;
        jrq= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               // Toast.makeText(Product_details.this, "Si responde getDatosServidor", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    JSONArray cast=response.getJSONArray("datos");
                    for (int i = 0; i < cast.length(); i++) {
                        JSONObject obj= cast.getJSONObject(i);
                        txtTitulo.setText(obj.optString("nombre"));
                        txtFecha.setText(obj.optString("fecha"));
                        txtPrecio.setText(obj.optString("precio"));
                        txtDescripcion.setText(obj.optString("descripcion"));
                        txtNombreV.setText(obj.optString("nombre_vendedor"));
                        String fotovendedor=("http://"+ip+""+obj.opt("urlfoto_vendedor")).replace("\\","/");
                        Picasso.with(getApplicationContext()).load(fotovendedor).into(foto_vendedor);
                        float calificacion=Float.parseFloat(obj.optString("calificacion"));
                        calificacion_vendedor.setRating(calificacion);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_details.this, "Error con servidor", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        rq.add(jrq);
    }
    public void getFotos(String id_prod){
        String url="http://"+ip+"/intergrami/vistas/fotos_productos.php?id_producto="+id_prod;//+id_prod;
        jrq= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray cast=response.getJSONArray("fotos");
                    for (int i = 0; i < cast.length(); i++) {
                        JSONObject jo=cast.getJSONObject(i);
                        String url="http://"+ip+jo.optString("urlfoto").replace("\\","/");
                        urls.add(url);
                    }
                    setFotosRecycler();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_details.this, "Error con servidor \nNOT FOUND", Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(jrq);
    }
    private void animacion(){
        overridePendingTransition(R.anim.godown,R.anim.goup);
    }

    private void compra(String id_producto,String id_user,String monto){
        String url="http://"+ip+"/intergrami/insercciones/inserta_compras.php?id_producto="+id_producto+"&id_user="+id_user+"&monto="+monto;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Product_details.this, "Response: "+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_details.this, "ErrorResponse: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    private void confirmaCompra(){
        DialogInterface.OnClickListener dialogClickListener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        compra(id_producto, id_user, monto);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        flag=false;
                        break;
                }
            }
        };
        AlertDialog.Builder builder= new AlertDialog.Builder(Product_details.this);
        builder.setMessage("¿Deseas comprar este producto?").setPositiveButton("Si",dialogClickListener).setNegativeButton("No",dialogClickListener).show();

    }
}
