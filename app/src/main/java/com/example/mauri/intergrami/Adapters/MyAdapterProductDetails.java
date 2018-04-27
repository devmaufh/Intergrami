package com.example.mauri.intergrami.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterProductDetails extends RecyclerView.Adapter<MyAdapterProductDetails.ViewHolder> {
    List<String> url;
    int layout;
    OnItemClickListener onItemClickListener;
    Context context;
    public MyAdapterProductDetails(List<String> url, int layout,OnItemClickListener listener){
        this.url=url;
        this.layout=layout;
        this.onItemClickListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        context=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(url.get(position),onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return url.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            foto=(ImageView)itemView.findViewById(R.id.productdetails_cardview_ivFoto);
        }
        public void bind(final String url, final OnItemClickListener listener){
            Picasso.with(context).load(url).into(foto);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.onItemClick(url,getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(String url, int position);
    }
}
