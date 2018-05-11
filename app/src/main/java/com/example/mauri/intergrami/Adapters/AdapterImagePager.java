package com.example.mauri.intergrami.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterImagePager extends PagerAdapter{
    Context context;
    List<String> images;
    LayoutInflater layoutInflater;

    public AdapterImagePager(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        this.layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==((RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView=layoutInflater.inflate(R.layout.imageviewer_item,container,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.ImageViewer_ImageItem);
        Picasso.with(context).load(images.get(position)).into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
