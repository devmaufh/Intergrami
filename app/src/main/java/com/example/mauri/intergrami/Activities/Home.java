package com.example.mauri.intergrami.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.Fragments.AcercadeFragment;
import com.example.mauri.intergrami.Fragments.ComprasFragment;
import com.example.mauri.intergrami.Fragments.ConfiguracionFragment;
import com.example.mauri.intergrami.Fragments.MisProductosFragment;
import com.example.mauri.intergrami.Fragments.PrincipalFragment;
import com.example.mauri.intergrami.Fragments.RentasFragment;
import com.example.mauri.intergrami.R;
import com.example.mauri.intergrami.Utils.ClearFiles;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    private TextView txtName;
    private CircleImageView imageProfiel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences prefs;
    private Dialog notificacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        notificacion = new Dialog(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navviewHome);
        setToolbar();
        ip= getResources().getString(R.string.ip_server);
        rq= Volley.newRequestQueue(getApplicationContext());
        prefs = getSharedPreferences("datos_user", MODE_PRIVATE);
        Service_notificacion();
        setFragmentByDefault();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        View header = navigationView.inflateHeaderView(R.layout.header_navigation_drawer);//Infla NavHeader para manipular sus elementos :v

        imageProfiel = (CircleImageView) header.findViewById(R.id.nav_image_profile);
        String url = prefs.getString("urlfoto", "nothing.com/x.jpg|");
        //************************************************* MOSTRAR IMAGEN DESDE EL DISPOSITIVO --usar ASYNCTASK para guardarla
        Picasso.with(header.getContext()).load("http://" + url).into(imageProfiel);

        txtName = (TextView) header.findViewById(R.id.NavProfile);
        txtName.setText(prefs.getString("correo", "Error al cargar datos"));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean fragmentTransaction = false;
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_nav_home:
                        fragment = new PrincipalFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_nav_compras:
                        fragment = new ComprasFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_nav_rentas:
                        fragment = new RentasFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_nav_misproductos:
                        fragment = new MisProductosFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_nav_configuracion:
                        fragment = new ConfiguracionFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_nav_acercade:
                        fragment = new AcercadeFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_nav_cerrarsesion:
                        Toast.makeText(getApplicationContext(), R.string.Toast_cierra_sesion, Toast.LENGTH_SHORT).show();
                        removeSharedPreferences();
                        ClearFiles.deleteCache(getApplicationContext());
                        logOut();
                        break;
                }
                if (fragmentTransaction) {
                    changeFragment(fragment, item);
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_nav);//Icono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragmentByDefault() {
        changeFragment(new PrincipalFragment(), navigationView.getMenu().getItem(0));
    }

    private void changeFragment(Fragment fragment, MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    @Override//Abre menu lateral xdxd
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Abrir menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeSharedPreferences() {
        prefs.edit().clear().commit();
    }

    private void logOut() {
        Intent intent = new Intent(this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void notificacionCompra(String nombre, String urlfoto, String montocompra,String id_compra) {
        notificacion.setContentView(R.layout.popup_notifiacion);
        FloatingActionButton btnClose = (FloatingActionButton) notificacion.findViewById(R.id.notificacion_botonclose);
        TextView nombre_prod=(TextView)notificacion.findViewById(R.id.notificacion_nombreproducto);
        ImageView foto_prod=(ImageView)notificacion.findViewById(R.id.notificacion_fotoProducto);
        TextView monto=(TextView)notificacion.findViewById(R.id.notificacion_montocompra);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificacion.dismiss();
            }
        });
        nombre_prod.setText(nombre);
        Picasso.with(getApplicationContext()).load(urlfoto).into(foto_prod);
        monto.setText(getResources().getString(R.string.Monto)+montocompra);
        notificacion.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        notificacion.show();
    }

    // Conexión con el servidor ):
    //                                  OBTENER PRODUCTO CON EL ID_USER
    /*
            Select * from notificacion_producto where id_user=2;
        +-----------+-------------+---------+---------+--------+---------------------------------------------+
        | id_compra | id_producto | id_user | nombre  | monto  | urlfoto                                     |
        +-----------+-------------+---------+---------+--------+---------------------------------------------+
        |         2 |           6 |       2 | Cebolla | 140000 | /intergrami/resources/fotos_productos/8.jpg |
        +-----------+-------------+---------+---------+--------+---------------------------------------------+
     */


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Sin notificaciones jijiji", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray=response.optJSONArray("notificacion");
            JSONObject jsonObject=null;
            jsonObject=jsonArray.getJSONObject(0);
            String id_compra=jsonObject.optString("id_compra");
            String nombre= jsonObject.optString("nombre");
            String urlfoto="http://"+ip+jsonObject.optString("urlfoto").replace("\\","/");
            String monto=jsonObject.optString("monto");
            notificacionCompra(nombre,urlfoto,monto,id_compra);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void Service_notificacion(){
        String id_user=prefs.getString("id_user","'null'");
        String url="http://"+ip+"/intergrami/vistas/notificacion_compra.php?id_user="+id_user;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
}
