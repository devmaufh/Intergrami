package com.example.mauri.intergrami.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UploadImages {
    public static void ServiceUploadFotos(String ip, String phpName, final String Imagepath, final String nameImage, final Context context, int numeberImage){
        final ProgressDialog dialog= new ProgressDialog(context);
        dialog.setTitle("Subiendo fotos, restante: "+numeberImage);
        dialog.show();
        String url="http://"+ip+"/intergrami/resources/"+phpName;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                if(response.trim().equalsIgnoreCase("exito"))
                    dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error en carga", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<>();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(Imagepath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parametros.put("foto", ConvertImgString.convierteImgString(bitmap));
                parametros.put("name", nameImage);
                return parametros;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
    public static String randomName(String id_user) {
        String name="";
        long millies=System.currentTimeMillis();
        String date= Calendar.getInstance().getTime().
                toString().
                replace(":","").
                replace(".","").
                replace(" ","").
                replace("'","");
        name=id_user+"_"+millies+date+((int)Math.random()*1000);
        return name;
    }
}