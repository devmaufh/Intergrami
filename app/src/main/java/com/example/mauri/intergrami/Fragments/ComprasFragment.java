package com.example.mauri.intergrami.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.Adapters.MyAdapterMisCompras;
import com.example.mauri.intergrami.Adapters.MyAdapterMisproductoscomprados;
import com.example.mauri.intergrami.Models.Mis_productos;
import com.example.mauri.intergrami.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprasFragment extends Fragment {
    private String ip; //Ip del servidor

    private RecyclerView rvMiscompras;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Mis_productos> mis_productosList;
    private SharedPreferences prefs;
    private ProgressDialog progressDialog;
    private String id_user;
    public ComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_compras, container, false);
        //setItemsRecycler(v);
        bind(v);
        ServiceMisproductosComprados();
        return v;
    }
    private void ServiceMisproductosComprados(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url="http://"+ip+"/intergrami/vistas/vista_mis_compras_cardview.php?id_user="+id_user;
        //vista_mis_compras_cardview.php?id_user=2
        JsonRequest jrq= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray cast=response.getJSONArray("datos");
                    for (int i = 0; i < cast.length(); i++) {
                        JSONObject jsonObject = cast.getJSONObject(i);
                        Mis_productos miproducto = new Mis_productos();
                        miproducto.setId_compra(jsonObject.getString("id_compra"));
                        miproducto.setNombre(jsonObject.getString("nombre"));
                        miproducto.setUrlfoto(("http://"+ip+jsonObject.getString("urlfoto")).replace("\\","/"));
                       // Toast.makeText(getContext(), miproducto.getUrlfoto(), Toast.LENGTH_SHORT).show();
                        mis_productosList.add(miproducto);
                    }
                    setItemsRecycler(getView());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jrq);
    }
    private void setItemsRecycler(final View v){
        //mis_productosList=getAllProducts();
        mLayoutManager=new LinearLayoutManager(v.getContext());
        mAdapter=new MyAdapterMisproductoscomprados(mis_productosList, R.layout.cardview_miscompras, new MyAdapterMisproductoscomprados.OnItemClickListener() {
            @Override
            public void OnItemClick(Mis_productos miproducto, int position) {
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
    public void bind(View view){
        ip= getResources().getString(R.string.ip_server);
        prefs= getActivity().getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        mis_productosList= new ArrayList<Mis_productos>();
        progressDialog= new ProgressDialog(view.getContext());
        rvMiscompras=(RecyclerView)view.findViewById(R.id.compras_rvCompras);
        progressDialog.setMessage(getResources().getText(R.string.cargando));
        progressDialog.show();
        id_user=prefs.getString("id_user","'null'");

    }

}
