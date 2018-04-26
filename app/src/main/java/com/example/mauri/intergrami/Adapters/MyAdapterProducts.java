package com.example.mauri.intergrami.Adapters;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mauri.intergrami.Models.Productos;
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by mauri on 21/04/2018.
 */

public class MyAdapterProducts extends RecyclerView.Adapter<MyAdapterProducts.ViewHolder> {
    List<Productos> productos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    public MyAdapterProducts(List<Productos> products, int layout, OnItemClickListener listener){
        this.productos=products;
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
        holder.bind(productos.get(position),itemClickListener);

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre,precio,descripcion,fecha;
        ImageView foto;
        public ViewHolder(View itemView) {
            super(itemView);
            nombre=(TextView)itemView.findViewById(R.id.productos_cardView_tvNombre);
            precio=(TextView)itemView.findViewById(R.id.productos_cardView_tvPrecio);
            foto=(ImageView)itemView.findViewById(R.id.productos_cardView_IvFoto);
            descripcion=(TextView)itemView.findViewById(R.id.productos_cardView_Descripcion);
            fecha=(TextView)itemView.findViewById(R.id.productos_cardView_tvfecha);
        }
        public void bind(final Productos producto, final OnItemClickListener listener){
            nombre.setText(producto.getNombre());
            precio.setText("$"+producto.getPrecio());
            descripcion.setText(producto.getDescripcion());
            fecha.setText(producto.getFecha());
            Picasso.with(context).load(producto.getUrlFoto()).into(foto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(producto,getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Productos producto, int position);
    }

}









