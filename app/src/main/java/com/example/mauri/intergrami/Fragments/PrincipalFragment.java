package com.example.mauri.intergrami.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.Activities.tierra_details;
import com.example.mauri.intergrami.Activities.Product_details;
import com.example.mauri.intergrami.Adapters.MyAdapterProducts;
import com.example.mauri.intergrami.Adapters.MyAdapterTierras;
import com.example.mauri.intergrami.Models.Productos;
import com.example.mauri.intergrami.Models.Tierras;
import com.example.mauri.intergrami.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    String ip; //Ip del servidor
    RequestQueue rq;
    JsonRequest jrq;
    List<Productos> productos;
    List<Tierras> tierras;
    public RecyclerView rvProductos;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    private Spinner sp;
    private SharedPreferences prefs;
    private ProgressDialog progressDialog;
    public PrincipalFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_principal, container, false);
        ip= getResources().getString(R.string.ip_server);
        Toast.makeText(v.getContext(), "On CReate", Toast.LENGTH_SHORT).show();

        productos= new ArrayList<Productos>();
        tierras= new ArrayList<Tierras>();
        rq= Volley.newRequestQueue(v.getContext());
        prefs= getActivity().getSharedPreferences("datos_user",Context.MODE_PRIVATE);
        progressDialog= new ProgressDialog(v.getContext());
        rvProductos = (RecyclerView) v.findViewById(R.id.principal_RvProductos);
        sp = (Spinner) v.findViewById(R.id.principal_spOpciones);
        Service_Misproductos();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    productos.clear();
                    Service_Misproductos();
                    setProductos(v);
                }
                if(i==2){
                    tierras.clear();
                    Service_mistierras();
                    setTierras(v);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }



    private void setProductos(final View v) {
        //Toast.makeText(getContext(), "SEtting productrs", Toast.LENGTH_LONG).show();
        mLayoutManager= new LinearLayoutManager(v.getContext());
        mAdapter= new MyAdapterProducts(productos, R.layout.cardview_productos, new MyAdapterProducts.OnItemClickListener() {
            @Override
            public void onItemClick(Productos producto, int position) {
                Intent intent= new Intent(getContext(),Product_details.class);
                intent.putExtra("id_producto",producto.getId_product());
                startActivity(intent);
            }
        });
        rvProductos.setLayoutManager(mLayoutManager);
        rvProductos.setAdapter(mAdapter);
    }
    private void setTierras(final View v){
        //tierras=getAllTierras();
        mLayoutManager= new LinearLayoutManager(v.getContext());
        mAdapter= new MyAdapterTierras(tierras, R.layout.cardview_tierras, new MyAdapterTierras.OnItemClickListener() {
            @Override
            public void onItemClick(Tierras tierra, int position) {
                Intent intent= new Intent(getContext(),tierra_details.class);
                intent.putExtra("id_tierra",tierra.getId_tierra());
                startActivity(intent);
            }
        });
        rvProductos.setLayoutManager(mLayoutManager);
        rvProductos.setAdapter(mAdapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Toast.makeText(getContext(),"Server not found",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onResponse(JSONObject response) {
        progressDialog.dismiss();
        //Toast.makeText(getActivity(), "Respuesta correcta del servidor", Toast.LENGTH_SHORT).show();
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
               // Toast.makeText(getContext(),p.getId_product()+p.getNombre()+p.getUrlFoto(),Toast.LENGTH_SHORT).show();
            }
            setProductos(getView());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray castt= response.getJSONArray("tierras");
            for (int i = 0; i < castt.length(); i++)
            {
                JSONObject jo= castt.getJSONObject(i);
                String ur=jo.optString("urlfoto").replace("\\","/");
                Tierras t= new Tierras();
                t.setId_tierra(jo.optString("id_tierra"));
                t.setMonto(jo.optString("monto"));
                t.setTamaño(jo.optString("tamaño"));
                t.setUrlfoto("http://"+ip+ur);
                tierras.add(t);
               // Toast.makeText(getContext(), t.getId_tierra()+t.getTamaño(), Toast.LENGTH_SHORT).show();
            }
            setTierras(getView());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void Service_Misproductos(){
        progressDialog.setMessage(getResources().getText(R.string.cargando));
        progressDialog.show();
        String id_user=prefs.getString("id_user","'null'");
       // Toast.makeText(getContext(), "ID del user: "+id_user, Toast.LENGTH_SHORT).show();
        String url="http://"+ip+"/intergrami/vistas/vista_productos.php?id_user="+id_user;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
        //Toast.makeText(getContext(),"Ejecutando webService",Toast.LENGTH_LONG).show();
    }
    private void Service_mistierras(){
        String id_user=prefs.getString("id_user","'null'");
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url="http://"+ip+"/intergrami/vistas/vista_tierras.php?id_user="+id_user;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
       // Toast.makeText(getContext(),"Ejecutando webService",Toast.LENGTH_LONG).show();
    }
    private void animacion(){
        ((Activity) getContext()).overridePendingTransition(R.anim.goup,R.anim.godown);
    }
}
