package com.example.mauri.intergrami.Utils;

import android.content.Context;
import android.content.Intent;

import com.example.mauri.intergrami.Activities.ImageViewer;

import java.util.ArrayList;
import java.util.List;

public class SetFullImages {
    public static void startViewerImages(Context c, List<String> imagesPath,int position){
        Intent intent= new Intent(c,ImageViewer.class);
        intent.putExtra("urls",(ArrayList<String>)imagesPath);
        intent.putExtra("position",position);
        c.startActivity(intent);
    }
}
