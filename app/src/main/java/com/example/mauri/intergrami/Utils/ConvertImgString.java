package com.example.mauri.intergrami.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ConvertImgString {
    public static String convierteImgString(Bitmap bitmap){
        ByteArrayOutputStream arry= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,arry);
        byte[] imagenByte=arry.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }
    public static Bitmap convertPathtoBitmap(String path){
        return BitmapFactory.decodeFile(path);
    }
}
