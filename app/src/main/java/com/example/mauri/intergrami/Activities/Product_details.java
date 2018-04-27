package com.example.mauri.intergrami.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RatingBar;
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
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Product_details extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    private RecyclerView fotos;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ProgressDialog dialog;
    private String id_producto;
    private boolean foto=true;
    private List<String> urls;
    TextView txtTitulo,txtFecha,txtPrecio,txtDescripcion,txtNombreV;
    RatingBar calificacion_vendedor;
    CircleImageView foto_vendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setToolbar();
        bindUI();
        id_producto=getIntent().getStringExtra("id_producto");
        //getDatosServidor(id_producto);
        //setFotosRecycler();
        getFotos(id_producto);
        dialog.setTitle(getResources().getString(R.string.cargando));
        dialog.show();

    }

    private void setToolbar(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_details); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        setTitle("Detalles");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);//Icono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this,getIntent().getStringExtra("id_producto"),Toast.LENGTH_SHORT).show();
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
    }
    private void setFotosRecycler(){
       mLayoutManager= new StaggeredGridLayoutManager(3,1);
       mAdapter= new MyAdapterProductDetails(urls, R.layout.cardview_productdetails, new MyAdapterProductDetails.OnItemClickListener() {
           @Override
           public void onItemClick(String url, int position) {
               Toast.makeText(getApplicationContext(),"NAda",Toast.LENGTH_SHORT).show();
           }
       });
       fotos.setLayoutManager(mLayoutManager);
       fotos.setAdapter(mAdapter);

    }
    private List<String> getDatos(){
        return new ArrayList<String>(){{
           add("http://192.168.1.65/intergrami/resources/fotos_productos/1.jpg");
           add("http://192.168.1.65/intergrami/resources/fotos_productos/2.jpg");
            add("http://192.168.1.65/intergrami/resources/fotos_productos/3.jpg");
            add("http://192.168.1.65/intergrami/resources/fotos_productos/2.jpg");
            add("http://192.168.1.65/intergrami/resources/fotos_productos/1.jpg");
            add("http://192.168.1.65/intergrami/resources/fotos_productos/1.jpg");
            add("http://192.168.1.65/intergrami/resources/fotos_productos/2.jpg");
            add("http://192.168.1.65/intergrami/resources/fotos_productos/3.jpg");
            add("http://192.168.1.65/intergrami/resources/fotos_productos/2.jpg");
        }};
    }

    //              Servidor
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Resupuesta incorrecta", Toast.LENGTH_SHORT).show();
        dialog.dismiss();


    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Resupuesta correcta", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        if(foto){
            Toast.makeText(this,"FOTOOOAOOASOASDASD", Toast.LENGTH_SHORT).show();
            //Obtener las fotos
            try {
                JSONArray cast=response.getJSONArray("fotos");
                for (int i = 0; i < cast.length(); i++) {
                    JSONObject jo=cast.getJSONObject(i);
                    String url="http://"+ip+jo.optString("urlfoto").replace("\\","/");
                    urls.add(url);
                }
                setFotosRecycler();
                foto=false;
                getDatosServidor(id_producto);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else{
            try {
                JSONArray cast=response.getJSONArray("datos");
                JSONObject jsonObject=cast.getJSONObject(0);
                txtTitulo.setText(jsonObject.optString("nombre"));
                txtFecha.setText(jsonObject.optString("fecha"));
                txtPrecio.setText(jsonObject.optString("precio"));
                txtDescripcion.setText(jsonObject.optString("descripcion"));
                txtNombreV.setText(jsonObject.optString("nombre_vendedor"));
                String fotovendedor=("http://"+ip+""+jsonObject.opt("urlfoto_vendedor")).replace("\\","/");
                Picasso.with(this).load(fotovendedor).into(foto_vendedor);
                float calificacion=Float.parseFloat(jsonObject.optString("calificacion"));
                calificacion_vendedor.setRating(calificacion);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void getDatosServidor(String id_prod){
        String url="http://"+ip+"/intergrami/vistas/detail_product.php?id_producto="+id_prod;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    public void getFotos(String id_prod){
        String url="http://"+ip+"/intergrami/vistas/fotos_productos.php?id_producto="+id_prod;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
}
