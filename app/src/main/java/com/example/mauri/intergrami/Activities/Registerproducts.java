package com.example.mauri.intergrami.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mauri.intergrami.Adapters.AdapterPhotos2;
import com.example.mauri.intergrami.R;
import com.example.mauri.intergrami.Utils.ConvertImgString;
import com.example.mauri.intergrami.Utils.SetFullImages;
import com.example.mauri.intergrami.Utils.UploadImages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.SimpleTextChangedWatcher;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class Registerproducts extends AppCompatActivity {
    String ip;
    int auxiliarForRemovePhoto;
    StringRequest stringRequest;
    RequestQueue queue;
    List<String> imagesPath;
    TextFieldBoxes tfbNombreproducto,tfbDescripcionproducto,tfbPrecio;
    ExtendedEditText edNombre, edDescripcion,edPrecio;
    RecyclerView rvPhotos;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton fbaAddPhotos;
    Button btnRegistrar;
    List<Urlinfo>namesPhotos;

    private SharedPreferences prefs;
    String id_user;
    //For image Selection
    int PICK_IMAGE_MULTIPLE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerproducts);
        bindUI();
        setToolbar();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                if(imagesPath.size()>0){
                    //ServiceUploadFotos(imagesPath.get(0));
                    UploadAllImages();
                }
            }
        });
    }
    private void bindUI(){
        tfbNombreproducto=(TextFieldBoxes)findViewById(R.id.registroproductos_textfieldboxes_nombreproducto);
        tfbDescripcionproducto=(TextFieldBoxes)findViewById(R.id.registroproductos_textfieldboxes_descripcion);
        tfbPrecio=(TextFieldBoxes)findViewById(R.id.registroproductos_textfieldboxes_precio);
        edNombre=(ExtendedEditText)findViewById(R.id.registroproductos_textfieldboxes_nombre_edNombre);
        edDescripcion=(ExtendedEditText)findViewById(R.id.registroproductos_textfieldboxes_descripcion_edDescripcion);
        edPrecio=(ExtendedEditText)findViewById(R.id.registroproductos_textfieldboxes_precio_edPrecio);
        rvPhotos=(RecyclerView)findViewById(R.id.registroproductos_recyclerFotos);
        fbaAddPhotos=(FloatingActionButton)findViewById(R.id.registroproductos_fbaAddPhoto);
        btnRegistrar=(Button)findViewById(R.id.registrarproductos_btnRegistrar);
        imagesPath= new ArrayList<String>();
        ip=getResources().getString(R.string.ip_server);
        queue= Volley.newRequestQueue(getApplicationContext());
        prefs=getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        id_user=prefs.getString("id_user","'null'");
        namesPhotos= new ArrayList<Urlinfo>();
    }
    private void setToolbar(){
        Toolbar toolbar= (Toolbar)findViewById(R.id.registroproductos_toolbar); //Muestra el toolbar como ActionBar
        setSupportActionBar(toolbar);//Muestra titulo de toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);//Icono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getResources().getString(R.string.registrarproducto));
        fbaAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multipleSelectionImages();
            }
        });
    }
    private void eventsTextFieldBoxes(){
        tfbNombreproducto.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
            @Override
            public void onTextChanged(String s, boolean b) {
                if(edNombre.getText().toString().length()>70)
                    tfbNombreproducto.setError(getResources().
                            getString(R.string.registro_valida_nombre),true);

                }
        });
    }
    private void multipleSelectionImages(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,getResources().getString(R.string.seleccionefotos)), PICK_IMAGE_MULTIPLE);
    }
    private void setImages3(){
        mLayoutManager= new GridLayoutManager(getApplicationContext(),4);
        mAdapter= new AdapterPhotos2(imagesPath, R.layout.cardview_fotos, new AdapterPhotos2.LongClickListener() {
            @Override
            public void onItemLongClick(String url, int position) {
                auxiliarForRemovePhoto = position;
            }
        }, new AdapterPhotos2.OnClickListener() {
            @Override
            public void OnItemClickListener(String url, int position) {
                SetFullImages.startViewerImages(getApplicationContext(),imagesPath,position);
            }
        });
        rvPhotos.setHasFixedSize(true);
        rvPhotos.setItemAnimator(new DefaultItemAnimator());
        rvPhotos.setLayoutManager(mLayoutManager);
        rvPhotos.setAdapter(mAdapter);
        registerForContextMenu(rvPhotos);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_MULTIPLE) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        Toast.makeText(this, "URI:  "+imageUri.toString(), Toast.LENGTH_SHORT).show();
                        imagesPath.add(imageUri.toString());
                    }
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            } else if(data.getData() != null) {
                String imagePath = data.getData().getPath();
                Toast.makeText(this, "PATH: "+imagePath, Toast.LENGTH_SHORT).show();
                imagesPath.add(imagePath.toString());
            }
        }
        if(imagesPath.size()>0) {
            //setImagesOnRecycler();
            //newRecycler();
            setImages3();
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //adapterFotosG.getItemSelected(item);
        //mAdapter.get
        switch (item.getItemId()) {
            case R.id.menufoto_eliminar:
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                removeFotoFromRecycler(auxiliarForRemovePhoto);
                break;
            case R.id.menufoto_portada:
                Toast.makeText(this, "Portada", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        //Toast.makeText(this, "Seleccionó algo jijiji :v", Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }
    private void removeFotoFromRecycler(int position){
        imagesPath.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    private void UploadAllImages(){
        for (int i = 0; i < imagesPath.size(); i++) {
            String nameImage=UploadImages.randomName(id_user);
            UploadImages.ServiceUploadFotos(ip,
                    "AddPhotos.php",
                    imagesPath.get(i),
                    nameImage,
                    Registerproducts.this,
                    (i+1));
            namesPhotos.add(new Urlinfo(nameImage,false));
        }
    }
    //                                                    *****NOTA******
    //                                          Primero insertar producto en tabla Productos
    //                                       Después insertar las url de las fotos en tabla fotos_productos con id currval(seq_id_foto_productos_))
    private void insertNamesPhotos(){
        int counter=0;
        do{
            if(counter==namesPhotos.size())
            {
                break;
            }
            else{
                for (int i = 0; i < namesPhotos.size(); i++) {
                    if(!namesPhotos.get(i).isUpload()){
                        if(ServiceFotosProducots(namesPhotos.get(i).getName()))
                        {
                            namesPhotos.get(i).setUpload(true);
                            counter++;
                      }
                    }
               }
             }
        }
        while(counter==namesPhotos.size());
    }
    private boolean ServiceFotosProducots(String name) {
        final boolean[] flag = {false};
        String url="";
        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("true"))
                    flag[0] =true;
                else flag[0]=true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registerproducts.this, "Error en respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        });
        return flag[0];
    }

    public class Urlinfo{
        private String  name;
        private boolean isUpload;
        public Urlinfo(String name, boolean isUpload) {
            this.name =name;
            this.isUpload = isUpload;
        }
        public boolean isUpload() {
            return isUpload;
        }
        public String  getName() {
            return name;
        }

        public void setUpload(boolean upload) {
            isUpload = upload;
        }
    }
}
