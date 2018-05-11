package com.example.mauri.intergrami.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mauri.intergrami.Models.Mis_productos;
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mauri on 21/04/2018.
 */

public class MyAdapterMisCompras extends RecyclerView.Adapter<MyAdapterMisCompras.ViewHolder>{
    List<Mis_productos> misproductos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MyAdapterMisCompras(List<Mis_productos> mis_productos, int layout, OnItemClickListener listener){
        this.misproductos=mis_productos;
        this.layout=layout;
        this.itemClickListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh= new ViewHolder(v);
        context=parent.getContext();
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(misproductos.get(position),itemClickListener);

    }

    @Override
    public int getItemCount() {
        return misproductos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public  TextView tvNombre,tvFolio,tvMonto;
        public ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre=(TextView)itemView.findViewById(R.id.misproductos_cardview_edNombreP);
            tvFolio=(TextView)itemView.findViewById(R.id.misproductos_cardview_Folio);
            tvMonto=(TextView)itemView.findViewById(R.id.misproductos_cardview_Monto);
            foto=(ImageView)itemView.findViewById(R.id.misproductos_cardview_ivFoto);
        }
        public void bind(final Mis_productos mis_producto, final OnItemClickListener listener){
            tvNombre.setText(mis_producto.getNombre());
            tvFolio.setText("Folio: "+mis_producto.getId_compra());
            tvMonto.setText("$"+mis_producto.getMonto());
            Picasso.with(context).load(mis_producto.getUrlfoto()).into(foto);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(mis_producto,getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(Mis_productos miproducto, int position);
    }
}
