package com.example.mauri.intergrami.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mauri.intergrami.Fragments.AcercadeFragment;
import com.example.mauri.intergrami.Fragments.ComprasFragment;
import com.example.mauri.intergrami.Fragments.ConfiguracionFragment;
import com.example.mauri.intergrami.Fragments.MisProductosFragment;
import com.example.mauri.intergrami.Fragments.PrincipalFragment;
import com.example.mauri.intergrami.Fragments.RentasFragment;
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
    private CircleImageView perfil_nav;
    private EditText name_user;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.navviewHome);
        perfil_nav=(CircleImageView)findViewById(R.id.nav_image_profile);
        name_user=(EditText)findViewById(R.id.Nav_EdnameUser);
        prefs=getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        setToolbar();

        name_user.setText(prefs.getString("correo",""));
        Toast.makeText(getApplicationContext(),prefs.getString("correo",""),Toast.LENGTH_LONG).show();
        String url=prefs.getString("urlfoto","www.facebook.com/null.jpg");
        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
        Picasso.with(getApplicationContext()).load(url).into(perfil_nav);

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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean fragmentTransaction=false;
                Fragment fragment=null;
                switch(item.getItemId()){
                    case R.id.menu_nav_home:
                        fragment = new PrincipalFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_nav_compras:
                        fragment= new ComprasFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_nav_rentas:
                        fragment= new RentasFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_nav_misproductos:
                        fragment= new MisProductosFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_nav_configuracion:
                        fragment= new ConfiguracionFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_nav_acercade:
                        fragment= new AcercadeFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_nav_cerrarsesion:
                        Toast.makeText(getApplicationContext(),R.string.Toast_cierra_sesion,Toast.LENGTH_SHORT).show();
                        break;
                }
                if(fragmentTransaction){
                   changeFragment(fragment,item);
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });



    }
    private void setToolbar(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar_home); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_nav);//Icono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragmentByDefault(){
        changeFragment(new PrincipalFragment(),navigationView.getMenu().getItem(0));
    }
    private void changeFragment(Fragment fragment,MenuItem item){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,fragment)
                .commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    @Override//Abre menu lateral xdxd
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //Abrir menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
