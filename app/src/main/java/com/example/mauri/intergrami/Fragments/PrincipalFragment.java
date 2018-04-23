package com.example.mauri.intergrami.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mauri.intergrami.Adapters.MyAdapterProducts;
import com.example.mauri.intergrami.Adapters.MyAdapterTierras;
import com.example.mauri.intergrami.Models.Productos;
import com.example.mauri.intergrami.Models.Tierras;
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment  {
    List<Productos> productos;
    List<Tierras> tierras;

    public RecyclerView rvProductos;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    private Spinner sp;


    private SharedPreferences prefs;
    public PrincipalFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_principal, container, false);



        rvProductos = (RecyclerView) v.findViewById(R.id.principal_RvProductos);
        sp = (Spinner) v.findViewById(R.id.principal_spOpciones);
        //setProductos(v);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1) setProductos(v);
                if(i==2) setTierras(v);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }

    private List<Productos> getAllProducts(){
        return new ArrayList<Productos>(){{
            add(new Productos(1,"Jitomate",15000,"http://192.168.1.75/intergrami/resources/fotos_productos/2.png"));
            add(new Productos(2,"Melon",24000,"http://hydroenv.com.mx/catalogo/images/00_Redaccion/cultivo_de_hortalizas/cultivo_de_melon/portada_melon.jpg"));
            add(new Productos(3,"Sandía",16000,"http://agronomaster.com/wp-content/uploads/2017/02/El-Cultivo-De-Sand%C3%ADas-3.jpg"));
            add(new Productos(4,"Aguacate",178000,"https://exoticfruitbox.com/wp-content/uploads/2015/10/aguacate.jpg"));
        }};
    }
    private  List<Tierras> getAllTierras(){
        return new ArrayList<Tierras>(){{
            add(new Tierras(1,"Si","1000 metros","https://www.agroempresario.com.ar/img/upload/nota/916f9e9dda10bb1dc4dc.jpg"));
            add(new Tierras(1,"No","15600 metros","https://grulacjunior.files.wordpress.com/2016/01/pineapple-field-oahu.jpg"));
        }};
    }

    private void setProductos(final View v){
        productos=getAllProducts();
        mLayoutManager= new LinearLayoutManager(v.getContext());
        mAdapter= new MyAdapterProducts(productos, R.layout.cardview_productos, new MyAdapterProducts.OnItemClickListener() {
            @Override
            public void onItemClick(Productos producto, int position) {
                Toast.makeText(v.getContext(),producto.getNombre(),Toast.LENGTH_SHORT).show();
            }
        });
        rvProductos.setLayoutManager(mLayoutManager);
        rvProductos.setAdapter(mAdapter);
    }
    private void setTierras(final View v){
        tierras=getAllTierras();
        mLayoutManager= new LinearLayoutManager(v.getContext());
        mAdapter= new MyAdapterTierras(tierras, R.layout.cardview_tierras, new MyAdapterTierras.OnItemClickListener() {
            @Override
            public void onItemClick(Tierras tierra, int position) {
                Toast.makeText(v.getContext(),tierra.getTamaño(),Toast.LENGTH_SHORT).show();
            }
        });
        rvProductos.setLayoutManager(mLayoutManager);
        rvProductos.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        if(productos!=null){
            for(int i=0;i<productos.size();i++){
                productos.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
        if(tierras!=null){
            for(int i=0;i<tierras.size();i++){
                tierras.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
        super.onStop();
    }
}
