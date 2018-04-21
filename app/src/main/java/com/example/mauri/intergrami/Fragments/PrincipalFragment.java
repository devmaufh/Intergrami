package com.example.mauri.intergrami.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mauri.intergrami.Adapters.MyAdapterProducts;
import com.example.mauri.intergrami.Models.Productos;
import com.example.mauri.intergrami.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment {
    List<Productos> productos;

    private RecyclerView rvProductos;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public PrincipalFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_principal, container, false);
        productos=getAllProducts();
        rvProductos=(RecyclerView) v.findViewById(R.id.principal_RvProductos);
        mLayoutManager= new LinearLayoutManager(v.getContext());

        mAdapter= new MyAdapterProducts(productos, R.layout.cardview_productos, new MyAdapterProducts.OnItemClickListener() {
            @Override
            public void onItemClick(Productos producto, int position) {
                Toast.makeText(v.getContext(),producto.getNombre(),Toast.LENGTH_SHORT).show();
            }
        });
        rvProductos.setLayoutManager(mLayoutManager);
        rvProductos.setAdapter(mAdapter);
        return v;
    }

    private List<Productos> getAllProducts(){
        return new ArrayList<Productos>(){{
            add(new Productos(1,"Jitomate",15000,"https://alimentossaludables.mercola.com/jitomates.html"));
            add(new Productos(2,"Melon",24000,"http://hydroenv.com.mx/catalogo/images/00_Redaccion/cultivo_de_hortalizas/cultivo_de_melon/portada_melon.jpg"));
            add(new Productos(3,"Sandía",16000,"http://agronomaster.com/wp-content/uploads/2017/02/El-Cultivo-De-Sand%C3%ADas-3.jpg"));
            add(new Productos(4,"Aguacate",178000,"https://exoticfruitbox.com/wp-content/uploads/2015/10/aguacate.jpg"));
        }};
    }

}
