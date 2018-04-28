package com.example.mauri.intergrami.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mauri.intergrami.Models.Tierras;
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mauri on 21/04/2018.
 */

public class MyAdapterTierras  extends RecyclerView.Adapter<MyAdapterTierras.ViewHolder> {
    List<Tierras> tierras;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MyAdapterTierras(List<Tierras> tierra,int layout, OnItemClickListener listener){
        this.tierras=tierra;
        this.layout=layout;
        this.itemClickListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        context=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tierras.get(position),itemClickListener);

    }

    @Override
    public int getItemCount() {
        return tierras.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTamaño,tvMonto;
        ImageView foto;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTamaño=(TextView)itemView.findViewById(R.id.tierras_cardview_edTamaño);
            tvMonto=(TextView) itemView.findViewById(R.id.tierras_cardview_edmonto);
            foto=(ImageView)itemView.findViewById(R.id.tierras_cardview_ivFoto);
        }
        public void bind(final Tierras tierra, final OnItemClickListener listener){
            tvTamaño.setText(tierra.getTamaño());
            tvMonto.setText("Monto: $"+tierra.getMonto());
            Picasso.with(context).load(tierra.getUrlfoto()).into(foto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(tierra,getAdapterPosition());
                }
            });

        }

    }
    public interface OnItemClickListener{
        void onItemClick(Tierras tierra, int position);
    }
}
