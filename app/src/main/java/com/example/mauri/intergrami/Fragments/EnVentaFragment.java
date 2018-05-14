package com.example.mauri.intergrami.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.Activities.Product_details;
import com.example.mauri.intergrami.Activities.Registerproducts;
import com.example.mauri.intergrami.Adapters.MyAdapterProducts;
import com.example.mauri.intergrami.Models.Productos;
import com.example.mauri.intergrami.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnVentaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    List<Productos> productos;
    public RecyclerView rvProductos;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences prefs;
    private ProgressDialog progressDialog;
    private TextView texto;
    private FloatingActionButton add_producto;
    public EnVentaFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_en_venta, container, false);
        bindUI(v);
        Service_Misproductos();
        add_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Registerproducts.class);
                startActivity(intent);
            }
        });
        return v;
    }
    private void bindUI(View v){
        ip= getResources().getString(R.string.ip_server);
        productos= new ArrayList<Productos>();
        rq= Volley.newRequestQueue(v.getContext());
        add_producto=(FloatingActionButton)v.findViewById(R.id.enventa_fbaAddProducto);
        prefs= getActivity().getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        progressDialog= new ProgressDialog(v.getContext());
        texto= (TextView)v.findViewById(R.id.enventa_texto);
        rvProductos= (RecyclerView)v.findViewById(R.id.enventa_recycler);
        progressDialog.setMessage(getResources().getText(R.string.cargando));
        progressDialog.show();

    }
    private void setProductos(final View v) {
        Toast.makeText(getContext(), "SEtting productrs", Toast.LENGTH_LONG).show();
        mLayoutManager= new LinearLayoutManager(v.getContext());
        mAdapter= new MyAdapterProducts(productos, R.layout.cardview_productos, new MyAdapterProducts.OnItemClickListener() {
            @Override
            public void onItemClick(Productos producto, int position) {
                Toast.makeText(getContext(), "CLICK MIS productos NO VENDIDOS JIJIJI", Toast.LENGTH_SHORT).show();
            }
        });
        rvProductos.setLayoutManager(mLayoutManager);
        rvProductos.setAdapter(mAdapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();


    }

    @Override
    public void onResponse(JSONObject response) {
        texto.setVisibility(View.INVISIBLE);
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "Respuesta correcta del servidor", Toast.LENGTH_SHORT).show();
        //Cast de array Json
        try {
            JSONArray cast= response.getJSONArray("productos");
            for (int i = 0; i < cast.length(); i++) {
                JSONObject jo= cast.getJSONObject(i);
                Productos p= new Productos();
                String ur=jo.optString("urlfoto").replace("\\","/");
                p.setId_product(jo.optString("id_producto"));
                p.setNombre(jo.optString("nombre"));
                p.setPrecio(jo.optString("precio"));
                p.setDescripcion(jo.optString("descripcion"));
                p.setUrlFoto("http://"+ip+ur);
                p.setFecha(jo.optString("fecha"));
                productos.add(p);
                Toast.makeText(getContext(),p.getId_product()+p.getNombre()+p.getUrlFoto(),Toast.LENGTH_SHORT).show();
            }
            setProductos(getView());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void Service_Misproductos(){
        progressDialog.setMessage(getResources().getText(R.string.cargando));
        progressDialog.show();
        String id_user=prefs.getString("id_user","'null'");
        Toast.makeText(getContext(), "ID del user: "+id_user, Toast.LENGTH_SHORT).show();
        String url="http://"+ip+"/intergrami/vistas/vista_mis_productos.php?id_user="+id_user;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
        Toast.makeText(getContext(),"Ejecutando webService",Toast.LENGTH_LONG).show();
    }
}
