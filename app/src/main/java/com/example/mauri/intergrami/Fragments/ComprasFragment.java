package com.example.mauri.intergrami.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mauri.intergrami.Adapters.MyAdapterMisCompras;
import com.example.mauri.intergrami.Models.Mis_productos;
import com.example.mauri.intergrami.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprasFragment extends Fragment {
    private RecyclerView rvMiscompras;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Mis_productos> mis_productosList;


    public ComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_compras, container, false);
        rvMiscompras=(RecyclerView)v.findViewById(R.id.compras_rvCompras);

        //setItemsRecycler(v);
        return v;
    }
    private void setItemsRecycler(final View v){
        //mis_productosList=getAllProducts();
        mLayoutManager=new LinearLayoutManager(v.getContext());
        mAdapter= new MyAdapterMisCompras(mis_productosList, R.layout.cardview_misproductos, new MyAdapterMisCompras.OnItemClickListener() {
            @Override
            public void OnItemClick(Mis_productos miproducto, int position) {
                Toast.makeText(v.getContext(),miproducto.getId_compra()+"\n"+miproducto.getMonto(),Toast.LENGTH_SHORT).show();
            }
        });
        rvMiscompras.setLayoutManager(mLayoutManager);
        rvMiscompras.setAdapter(mAdapter);
    }

    private void clearData(){
        if(mis_productosList!=null){
            for(int i=0;i<mis_productosList.size();i++){
                mis_productosList.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        clearData();
    }
}
